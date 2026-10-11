package cn.oyzh.pkg.util.compact;

import org.tukaani.xz.XZInputStream;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 压缩 JAR 的通用启动器。
 * <p>
 * 启动器根据当前用户和自身 JAR 的 SHA-256 选择缓存目录，并行重建应用和依赖 JAR，
 * 再通过路径 JAR 将原始 JVM 参数及应用参数转发给实际主类。
 */
public final class CompressedJarLauncher {

    private static final String PAYLOAD_INDEX_ENTRY = "META-INF/compact-index.tsv";
    private static final String APPLICATION_PREFIX = "a/";
    private static final String DEPENDENCY_PREFIX = "d/";
    private static final String APPLICATION_JAR = "application.jar";
    private static final String CACHE_DIRECTORY_PROPERTY = "compact.jar.cache.dir";
    private static final String UNPACK_THREADS_PROPERTY = "compact.jar.unpack.threads";
    private static final String RESTART_CLASSPATH_PROPERTY = "compact.jar.restart.classpath";
    private static final String RESTART_START_CLASS_PROPERTY = "compact.jar.restart.start.class";

    private CompressedJarLauncher() {
    }

    /**
     * 压缩 JAR 的启动入口。
     *
     * @param args 传递给实际应用主类的参数
     * @throws Exception 缓存准备或子进程启动失败
     */
    public static void main(String[] args) throws Exception {
        Path launcherJar = getLauncherJar();
        Manifest manifest = readManifest(launcherJar);
        String payloadEntry = required(manifest, "Payload-Entry");
        String startClass = required(manifest, "Start-Class");
        String payloadSha256 = required(manifest, "Payload-SHA256");
        String applicationSha256 = required(manifest, "Application-JAR-SHA256");
        List<Long> payloadChunkSizes = parseChunkSizes(manifest);
        int dependencyCount = parseDependencyCount(manifest);
        String launcherSha256 = sha256(launcherJar);
        Path cache = cacheDirectory(launcherSha256);
        prepareCache(launcherJar, cache, payloadEntry, launcherSha256, payloadSha256, applicationSha256, payloadChunkSizes, dependencyCount);
        int status = launch(cache, startClass, args);
        System.exit(status);
    }

    private static Path getLauncherJar() throws URISyntaxException {
        Path path = Paths.get(CompressedJarLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException("Launcher must run from a jar");
        }
        return path.toAbsolutePath();
    }

    private static Manifest readManifest(Path launcherJar) throws IOException {
        try (JarFile jarFile = new JarFile(launcherJar.toFile())) {
            Manifest manifest = jarFile.getManifest();
            if (manifest == null) {
                throw new IOException("Missing launcher manifest");
            }
            return manifest;
        }
    }

    /**
     * 提前准备指定压缩 JAR 的运行缓存，适合在安装或打包阶段调用。
     *
     * @param launcherJar 压缩后的可执行 JAR
     * @throws Exception 缓存创建失败
     */
    public static void warmCache(Path launcherJar) throws Exception {
        Path absoluteLauncherJar = launcherJar.toAbsolutePath();
        Manifest manifest = readManifest(absoluteLauncherJar);
        String payloadEntry = required(manifest, "Payload-Entry");
        String payloadSha256 = required(manifest, "Payload-SHA256");
        String applicationSha256 = required(manifest, "Application-JAR-SHA256");
        List<Long> payloadChunkSizes = parseChunkSizes(manifest);
        int dependencyCount = parseDependencyCount(manifest);
        String launcherSha256 = sha256(absoluteLauncherJar);
        Path cache = cacheDirectory(launcherSha256);
        prepareCache(absoluteLauncherJar, cache, payloadEntry, launcherSha256, payloadSha256, applicationSha256, payloadChunkSizes, dependencyCount);
    }

    private static String required(Manifest manifest, String name) {
        String value = manifest.getMainAttributes().getValue(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing manifest attribute: " + name);
        }
        return value;
    }

    /**
     * 读取并校验 payload 分块长度；旧格式没有分块信息时按单个 XZ 流处理。
     */
    private static List<Long> parseChunkSizes(Manifest manifest) {
        String value = manifest.getMainAttributes().getValue("Payload-Chunk-Sizes");
        if (value == null || value.isBlank()) {
            return List.of(-1L);
        }
        List<Long> sizes = new ArrayList<>();
        for (String item : value.split(",")) {
            long size = Long.parseLong(item.trim());
            if (size <= 0) {
                throw new IllegalArgumentException("Invalid payload chunk size: " + size);
            }
            sizes.add(size);
        }
        return List.copyOf(sizes);
    }

    /**
     * 读取预期依赖数量；旧格式没有该属性时仅校验依赖 ID 是否连续。
     */
    private static int parseDependencyCount(Manifest manifest) {
        String value = manifest.getMainAttributes().getValue("Dependency-Count");
        if (value == null || value.isBlank()) {
            return -1;
        }
        int count = Integer.parseInt(value.trim());
        if (count < 0) {
            throw new IllegalArgumentException("Invalid dependency count: " + count);
        }
        return count;
    }

    /**
     * 根据当前用户和压缩 JAR 的完整 SHA-256 选择缓存目录。
     * <p>
     * 用户标识会散列为稳定目录名，因此不同系统用户即使运行完全相同的 JAR，
     * 也不会共享或覆盖彼此无权访问的缓存；同一用户仍可跨进程复用缓存。
     */
    private static Path cacheDirectory(String launcherSha256) throws Exception {
        String configured = System.getProperty(CACHE_DIRECTORY_PROPERTY);
        Path root = configured == null || configured.isBlank() ? defaultCacheRoot() : Paths.get(configured);
        Path path = root.toAbsolutePath().normalize()
                .resolve(userCacheId())
                .resolve(launcherSha256);
        System.out.println("compact path=" + path);
        return path;
    }

    /**
     * 默认缓存放在当前用户主目录下，避免多用户共享临时目录时发生目录所有权冲突。
     */
    private static Path defaultCacheRoot() {
        String userHome = System.getProperty("user.home");
        if (userHome == null || userHome.isBlank()) {
            throw new IllegalStateException("Missing system property: user.home");
        }
        return Paths.get(userHome, ".cache", "compact-jar-cache");
    }

    /**
     * 生成不含用户名明文的稳定用户缓存标识；用户名或主目录变化后允许重新创建缓存。
     */
    private static String userCacheId() throws Exception {
        String userName = System.getProperty("user.name", "");
        String userHome = System.getProperty("user.home", "");
        byte[] identity = ("user.name=" + userName + "\nuser.home=" + userHome)
                .getBytes(StandardCharsets.UTF_8);
        return toHex(MessageDigest.getInstance("SHA-256").digest(identity)).substring(0, 16);
    }

    /**
     * 使用文件锁准备缓存；只有完整重建成功后才写入校验标记。
     */
    private static void prepareCache(Path launcherJar, Path cache, String payloadEntry, String launcherSha256, String payloadSha256, String applicationSha256, List<Long> payloadChunkSizes, int dependencyCount) throws Exception {
        createPrivateDirectories(cache.getParent());
        createPrivateDirectories(cache);
        Path lockPath = cache.resolve("cache.lock");
        try (FileChannel channel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             FileLock ignored = channel.lock()) {
            if (isCacheValid(cache, launcherSha256, payloadSha256, applicationSha256)) {
                return;
            }
            clearCache(cache, lockPath);
            try (JarFile launcher = new JarFile(launcherJar.toFile())) {
                unpackPayload(launcher, payloadEntry, payloadChunkSizes, dependencyCount, cache);
            }
            String marker = "launcher=" + launcherSha256 + "\n"
                    + "payload=" + payloadSha256 + "\n"
                    + "application=" + applicationSha256 + "\n"
                    + "classpath-order=" + sha256(cache.resolve("classpath.order")) + "\n";
            Files.writeString(cache.resolve("cache.ok"), marker, StandardCharsets.UTF_8);
        }
    }

    /**
     * 创建仅当前用户可访问的缓存目录；非 POSIX 文件系统使用其默认访问控制。
     */
    private static void createPrivateDirectories(Path directory) throws IOException {
        Files.createDirectories(directory);
        if (Files.getFileAttributeView(directory, PosixFileAttributeView.class) == null) {
            return;
        }
        Files.setPosixFilePermissions(directory, Set.of(
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.OWNER_EXECUTE));
    }

    /**
     * 检查缓存标记和必要文件，防止使用不完整或旧版本缓存。
     */
    private static boolean isCacheValid(Path cache, String launcherSha256, String payloadSha256, String applicationSha256) throws Exception {
        Path marker = cache.resolve("cache.ok");
        if (!Files.isRegularFile(marker) || !Files.isRegularFile(cache.resolve(APPLICATION_JAR))
                || !Files.isRegularFile(cache.resolve("classpath.order")) || !Files.isRegularFile(cache.resolve("classpath.jar"))) {
            return false;
        }
        String value = Files.readString(marker, StandardCharsets.UTF_8);
        if (!value.contains("launcher=" + launcherSha256)
                || !value.contains("payload=" + payloadSha256)
                || !value.contains("application=" + applicationSha256)
                || !value.contains("classpath-order=" + sha256(cache.resolve("classpath.order")))) {
            return false;
        }
        Path lib = cache.resolve("lib");
        if (!Files.isDirectory(lib)) {
            return false;
        }
        List<String> dependencies = Files.readAllLines(cache.resolve("classpath.order"), StandardCharsets.UTF_8);
        for (String dependency : dependencies) {
            if (!isSafeDependencyName(dependency)
                    || !Files.isRegularFile(lib.resolve(dependency))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSafeDependencyName(String name) {
        return name != null && !name.isBlank() && !name.equals(".") && !name.equals("..")
                && name.endsWith(".jar")
                && name.indexOf('/') < 0 && name.indexOf('\\') < 0
                && name.indexOf('\t') < 0 && name.indexOf('\r') < 0 && name.indexOf('\n') < 0;
    }

    private static void clearCache(Path cache, Path lockPath) throws IOException {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(cache)) {
            for (Path entry : entries) {
                if (!entry.equals(lockPath)) {
                    deleteTree(entry);
                }
            }
        }
    }

    private static void deleteTree(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }
        try (Stream<Path> paths = Files.walk(path)) {
            for (Path item : paths.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(item);
            }
        }
    }

    /**
     * 解压多段 payload，并以有限并发重建应用 JAR 和依赖 JAR。
     * <p>
     * 每段都是独立 XZ 流。读取线程只保留尚未处理的压缩分块，
     * 解压线程每完成一个依赖 JAR 就立即交给重建线程，避免累计完整解压数据。
     */
    private static void unpackPayload(JarFile launcher, String payloadEntryName, List<Long> payloadChunkSizes, int dependencyCount, Path cache) throws Exception {
        JarEntry payloadEntry = launcher.getJarEntry(payloadEntryName);
        if (payloadEntry == null) {
            throw new IOException("Missing launcher payload: " + payloadEntryName);
        }
        Path lib = cache.resolve("lib");
        Files.createDirectories(lib);
        Map<Integer, String> dependencies = new ConcurrentSkipListMap<>();
        AtomicBoolean applicationPayloadSeen = new AtomicBoolean();
        validateChunkSizes(payloadChunkSizes, payloadEntry.getSize());
        int defaultUnpackThreads = Math.clamp(Runtime.getRuntime().availableProcessors(), 1, 3);
        int unpackThreads = Integer.getInteger(UNPACK_THREADS_PROPERTY, defaultUnpackThreads);
        //        if (unpackThreads < 1 || unpackThreads > 16) {
        //            throw new IllegalArgumentException(UNPACK_THREADS_PROPERTY + " must be between 1 and 16");
        //        }
        if (unpackThreads < 1) {
            unpackThreads = 1;
        }
        int decodeThreads = Math.min(payloadChunkSizes.size(), unpackThreads);
        ExecutorService decodeExecutor = Executors.newFixedThreadPool(decodeThreads);
        List<Future<?>> decodeJobs = new ArrayList<>();
        try {
            try (InputStream payload = launcher.getInputStream(payloadEntry)) {
                for (long chunkSize : payloadChunkSizes) {
                    byte[] compressedChunk = readChunk(payload, chunkSize, payloadEntry.getSize());
                    decodeJobs.add(decodeExecutor.submit(() -> {
                        unpackChunk(compressedChunk, cache, dependencies, applicationPayloadSeen);
                        return null;
                    }));
                }
                if (payload.read() >= 0) {
                    throw new IOException("Trailing bytes after payload chunks");
                }
            }
            for (Future<?> job : decodeJobs) {
                job.get();
            }
            if (!applicationPayloadSeen.get()) {
                throw new IOException("Missing application payload");
            }
            validateDependencies(dependencies, dependencyCount);
        } finally {
            cancelJobs(decodeJobs);
            decodeExecutor.shutdownNow();
        }
        Files.write(cache.resolve("classpath.order"), dependencies.values(), StandardCharsets.UTF_8);
        writeClasspathJar(cache, dependencies.values());
    }

    /**
     * 校验所有依赖 ID 连续且数量与 Manifest 一致，避免部分 payload 被误认为完整缓存。
     */
    private static void validateDependencies(Map<Integer, String> dependencies, int expectedCount) throws IOException {
        if (expectedCount >= 0 && dependencies.size() != expectedCount) {
            throw new IOException("Dependency count mismatch: expected=" + expectedCount + ", actual=" + dependencies.size());
        }
        for (int id = 0; id < dependencies.size(); id++) {
            if (!dependencies.containsKey(id)) {
                throw new IOException("Missing dependency id: " + id);
            }
        }
    }

    /**
     * 校验 Manifest 分块长度总和，防止截断、重复或伪造的 payload 布局进入缓存。
     */
    private static void validateChunkSizes(List<Long> chunkSizes, long payloadSize) throws IOException {
        if (payloadSize < 0 || (chunkSizes.size() == 1 && chunkSizes.get(0) < 0)) {
            return;
        }
        long total = 0;
        for (long chunkSize : chunkSizes) {
            if (chunkSize <= 0 || total > Long.MAX_VALUE - chunkSize) {
                throw new IOException("Invalid payload chunk sizes");
            }
            total += chunkSize;
        }
        if (total != payloadSize) {
            throw new IOException("Payload chunk sizes do not match payload entry size");
        }
    }

    /**
     * 按 Manifest 长度读取一个独立 XZ 块，避免把整个 payload 同时载入内存。
     */
    private static byte[] readChunk(InputStream payload, long declaredSize, long totalSize) throws IOException {
        if (declaredSize < 0) {
            return payload.readAllBytes();
        }
        if (declaredSize > Integer.MAX_VALUE || (totalSize >= 0 && declaredSize > totalSize)) {
            throw new IOException("Invalid payload chunk size: " + declaredSize);
        }
        byte[] data = payload.readNBytes((int) declaredSize);
        if (data.length != declaredSize) {
            throw new IOException("Unexpected end of payload chunk");
        }
        return data;
    }

    /**
     * 解压一个独立 XZ 块，并直接流式写入应用或依赖 JAR。
     * <p>
     * 不再把整个 JAR 的条目收集到内存队列中，单个超大资源也只使用固定大小的复制缓冲区。
     */
    private static void unpackChunk(byte[] compressedChunk, Path cache, Map<Integer, String> dependencies,
                                    AtomicBoolean applicationPayloadSeen) throws Exception {
        Path lib = cache.resolve("lib");
        Path applicationTarget = cache.resolve(APPLICATION_JAR);
        JarTarget application = null;
        JarTarget dependency = null;
        int currentId = -1;
        boolean success = false;
        try (XZInputStream xz = new XZInputStream(new ByteArrayInputStream(compressedChunk));
             ZipInputStream zip = new ZipInputStream(xz)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (PAYLOAD_INDEX_ENTRY.equals(entry.getName())) {
                    parseIndex(zip.readAllBytes(), dependencies);
                    continue;
                }
                String name = entry.getName();
                if (name.startsWith(APPLICATION_PREFIX)) {
                    if (application == null) {
                        if (!applicationPayloadSeen.compareAndSet(false, true)) {
                            throw new IOException("Duplicate application payload");
                        }
                        application = new JarTarget(applicationTarget);
                    }
                    application.add(entry.getName().substring(APPLICATION_PREFIX.length()), entry, zip);
                    continue;
                }
                if (!name.startsWith(DEPENDENCY_PREFIX)) {
                    throw new IOException("Unknown payload entry: " + name);
                }
                String relative = name.substring(DEPENDENCY_PREFIX.length());
                int separator = relative.indexOf('/');
                if (separator <= 0 || separator == relative.length() - 1) {
                    throw new IOException("Invalid payload entry: " + name);
                }
                int id = Integer.parseInt(relative.substring(0, separator));
                if (id < 0) {
                    throw new IOException("Invalid dependency id: " + id);
                }
                String originalName = relative.substring(separator + 1);
                if (id != currentId) {
                    String dependencyName = dependencies.get(id);
                    if (dependencyName == null) {
                        throw new IOException("Missing dependency index: " + id);
                    }
                    if (dependency != null) {
                        dependency.commit();
                    }
                    dependency = new JarTarget(lib.resolve(dependencyName));
                    currentId = id;
                }
                dependency.add(originalName, entry, zip);
            }
            if (dependency != null) {
                dependency.commit();
                dependency = null;
            }
            if (application != null) {
                application.commit();
                application = null;
            }
            success = true;
        } finally {
            if (!success) {
                closeQuietly(dependency);
                closeQuietly(application);
            }
        }
    }

    /**
     * 取消尚未执行或仍在执行的任务，保证异常路径能够尽快释放线程。
     */
    private static void cancelJobs(Iterable<? extends Future<?>> jobs) {
        for (Future<?> job : jobs) {
            job.cancel(true);
        }
    }

    private static void closeQuietly(JarTarget target) {
        if (target != null) {
            try {
                target.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 目标 JAR 的流式写入器；提交前只保留临时文件，不保留条目内容。
     */
    private static final class JarTarget implements AutoCloseable {
        private final Path target;
        private final Path temporary;
        private final JarOutputStream output;
        private boolean committed;

        private JarTarget(Path target) throws IOException {
            this.target = target;
            this.temporary = target.resolveSibling(target.getFileName() + ".tmp");
            this.output = new JarOutputStream(new BufferedOutputStream(Files.newOutputStream(temporary)));
            this.output.setLevel(java.util.zip.Deflater.BEST_SPEED);
        }

        private void add(String name, ZipEntry sourceEntry, InputStream input) throws IOException {
            ZipEntry outputEntry = new ZipEntry(name);
            if (sourceEntry.getTime() >= 0) {
                outputEntry.setTime(sourceEntry.getTime());
            }
            output.putNextEntry(outputEntry);
            input.transferTo(output);
            output.closeEntry();
        }

        private void commit() throws IOException {
            output.close();
            Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            committed = true;
        }

        @Override
        public void close() throws IOException {
            if (!committed) {
                output.close();
                Files.deleteIfExists(temporary);
            }
        }
    }

    /**
     * 生成 Manifest 路径 JAR，避免把完整依赖列表写入子进程命令行。
     */
    private static void writeClasspathJar(Path cache, Iterable<String> dependencies) throws Exception {
        StringBuilder classPath = new StringBuilder(new URI(null, null, APPLICATION_JAR, null).toASCIIString());
        for (String dependency : dependencies) {
            classPath.append(' ').append(new URI(null, null, "lib/" + dependency, null).toASCIIString());
        }
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().putValue("Class-Path", classPath.toString());
        Path target = cache.resolve("classpath.jar");
        Path temporary = cache.resolve("classpath.jar.tmp");
        try {
            try (JarOutputStream output = new JarOutputStream(new BufferedOutputStream(Files.newOutputStream(temporary)), manifest)) {
            }
            Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    private static void parseIndex(byte[] data, Map<Integer, String> dependencies) {
        String value = new String(data, StandardCharsets.UTF_8);
        boolean formatSeen = false;
        for (String line : value.split("\\R")) {
            if (line.startsWith("format\t")) {
                if (!"format\t1".equals(line)) {
                    throw new IllegalArgumentException("Unsupported payload index format: " + line);
                }
                formatSeen = true;
                continue;
            }
            String[] fields = line.split("\t", 2);
            if (fields.length != 2) {
                throw new IllegalArgumentException("Invalid payload index line: " + line);
            }
            int id = Integer.parseInt(fields[0]);
            String name = fields[1];
            if (id < 0 || !isSafeDependencyName(name)) {
                throw new IllegalArgumentException("Invalid payload dependency: " + line);
            }
            String previous = dependencies.putIfAbsent(id, name);
            if (previous != null && !previous.equals(name)) {
                throw new IllegalArgumentException("Conflicting payload dependency id: " + id);
            }
        }
        if (!formatSeen) {
            throw new IllegalArgumentException("Missing payload index format");
        }
    }

    /**
     * 启动实际应用 JVM，并转发启动器收到的 JVM 参数和应用参数。
     */
    private static int launch(Path cache, String startClass, String[] args) throws Exception {
        List<String> command = new ArrayList<>();
        command.add(javaExecutable());
        command.addAll(ManagementFactory.getRuntimeMXBean().getInputArguments());
        command.add("-D" + RESTART_CLASSPATH_PROPERTY + "=" + classPath(cache));
        command.add("-D" + RESTART_START_CLASS_PROPERTY + "=" + startClass);
        command.add("-cp");
        command.add(classPath(cache));
        command.add(startClass);
        command.addAll(List.of(args));
        return new ProcessBuilder(command).inheritIO().start().waitFor();
    }

    private static String javaExecutable() {
        String executable = System.getProperty("os.name").toLowerCase().contains("win") ? "java.exe" : "java";
        return Paths.get(System.getProperty("java.home"), "bin", executable).toString();
    }

    private static String classPath(Path cache) throws IOException {
        return cache.resolve("classpath.jar").toString();
    }

    private static String sha256(Path file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream input = Files.newInputStream(file)) {
            byte[] buffer = new byte[64 * 1024];
            int length;
            while ((length = input.read(buffer)) >= 0) {
                digest.update(buffer, 0, length);
            }
        }
        return toHex(digest.digest());
    }

    private static String toHex(byte[] value) {
        StringBuilder text = new StringBuilder(value.length * 2);
        for (byte item : value) {
            text.append(String.format("%02x", item));
        }
        return text.toString();
    }
}
