package cn.oyzh.pkg.util.compact;

import org.tukaani.xz.XZInputStream;

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
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 压缩 JAR 的通用启动器。
 * <p>
 * 启动器根据自身 JAR 的 SHA-256 选择缓存目录，并行重建应用和依赖 JAR，
 * 再通过路径 JAR 将原始 JVM 参数及应用参数转发给实际主类。
 */
public final class CompressedJarLauncher {

    private static final String PAYLOAD_INDEX_ENTRY = "META-INF/compact-index.tsv";
    private static final String APPLICATION_PREFIX = "a/";
    private static final String DEPENDENCY_PREFIX = "d/";
    private static final String APPLICATION_JAR = "application.jar";
    private static final String CACHE_DIRECTORY_PROPERTY = "compact.jar.cache.dir";
    private static final String UNPACK_THREADS_PROPERTY = "compact.jar.unpack.threads";

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
        String launcherSha256 = sha256(launcherJar);
        Path cache = cacheDirectory(launcherSha256);
        prepareCache(launcherJar, cache, payloadEntry, launcherSha256, payloadSha256, applicationSha256, payloadChunkSizes);
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
        String launcherSha256 = sha256(absoluteLauncherJar);
        Path cache = cacheDirectory(launcherSha256);
        prepareCache(absoluteLauncherJar, cache, payloadEntry, launcherSha256, payloadSha256, applicationSha256, payloadChunkSizes);
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
     * 根据压缩 JAR 的完整 SHA-256 选择缓存目录，确保 JAR 内容变化后自动更新缓存。
     */
    private static Path cacheDirectory(String launcherSha256) {
        String configured = System.getProperty(CACHE_DIRECTORY_PROPERTY);
        Path root = configured == null || configured.isBlank()
                ? Paths.get(System.getProperty("java.io.tmpdir"), "compact-jar-cache")
                : Paths.get(configured);
        Path path = root.resolve(launcherSha256);
        System.out.println("compact path=" + path);
        return path;
    }

    /**
     * 使用文件锁准备缓存；只有完整重建成功后才写入校验标记。
     */
    private static void prepareCache(Path launcherJar, Path cache, String payloadEntry, String launcherSha256, String payloadSha256, String applicationSha256, List<Long> payloadChunkSizes) throws Exception {
        Files.createDirectories(cache);
        Path lockPath = cache.resolve("cache.lock");
        try (FileChannel channel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             FileLock ignored = channel.lock()) {
            if (isCacheValid(cache, launcherSha256, payloadSha256, applicationSha256)) {
                return;
            }
            clearCache(cache, lockPath);
            try (JarFile launcher = new JarFile(launcherJar.toFile())) {
                unpackPayload(launcher, payloadEntry, payloadChunkSizes, cache);
            }
            String marker = "launcher=" + launcherSha256 + "\npayload=" + payloadSha256 + "\napplication=" + applicationSha256 + "\n";
            Files.writeString(cache.resolve("cache.ok"), marker, StandardCharsets.UTF_8);
        }
    }

    /**
     * 检查缓存标记和必要文件，防止使用不完整或旧版本缓存。
     */
    private static boolean isCacheValid(Path cache, String launcherSha256, String payloadSha256, String applicationSha256) throws IOException {
        Path marker = cache.resolve("cache.ok");
        if (!Files.isRegularFile(marker) || !Files.isRegularFile(cache.resolve(APPLICATION_JAR))
                || !Files.isRegularFile(cache.resolve("classpath.order")) || !Files.isRegularFile(cache.resolve("classpath.jar"))) {
            return false;
        }
        String value = Files.readString(marker, StandardCharsets.UTF_8);
        if (!value.contains("launcher=" + launcherSha256)
                || !value.contains("payload=" + payloadSha256)
                || !value.contains("application=" + applicationSha256)) {
            return false;
        }
        Path lib = cache.resolve("lib");
        if (!Files.isDirectory(lib)) {
            return false;
        }
        try (DirectoryStream<Path> files = Files.newDirectoryStream(lib, "*.jar")) {
            return files.iterator().hasNext();
        }
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
    private static void unpackPayload(JarFile launcher, String payloadEntryName, List<Long> payloadChunkSizes, Path cache) throws Exception {
        JarEntry payloadEntry = launcher.getJarEntry(payloadEntryName);
        if (payloadEntry == null) {
            throw new IOException("Missing launcher payload: " + payloadEntryName);
        }
        Path lib = cache.resolve("lib");
        Files.createDirectories(lib);
        Path applicationTarget = cache.resolve(APPLICATION_JAR);
        Map<Integer, String> dependencies = new ConcurrentSkipListMap<>();
        AtomicBoolean applicationPayloadSeen = new AtomicBoolean();
        int unpackThreads = Integer.getInteger(UNPACK_THREADS_PROPERTY, 2);
        if (unpackThreads < 1 || unpackThreads > 16) {
            throw new IllegalArgumentException(UNPACK_THREADS_PROPERTY + " must be between 1 and 16");
        }
        int decodeThreads = Math.min(payloadChunkSizes.size(), unpackThreads);
        ExecutorService decodeExecutor = Executors.newFixedThreadPool(decodeThreads);
        ExecutorService jarExecutor = Executors.newFixedThreadPool(unpackThreads);
        Semaphore slots = new Semaphore(unpackThreads + decodeThreads);
        List<Future<?>> decodeJobs = new ArrayList<>();
        Queue<Future<?>> jarJobs = new ConcurrentLinkedQueue<>();
        try {
            try (InputStream payload = launcher.getInputStream(payloadEntry)) {
                for (long chunkSize : payloadChunkSizes) {
                    byte[] compressedChunk = readChunk(payload, chunkSize, payloadEntry.getSize());
                    decodeJobs.add(decodeExecutor.submit(() -> {
                        unpackChunk(compressedChunk, cache, dependencies, applicationPayloadSeen, jarExecutor, slots, jarJobs);
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
            for (Future<?> job = jarJobs.poll(); job != null; job = jarJobs.poll()) {
                job.get();
            }
            if (!applicationPayloadSeen.get()) {
                throw new IOException("Missing application payload");
            }
        } finally {
            cancelJobs(decodeJobs);
            cancelJobs(jarJobs);
            decodeExecutor.shutdownNow();
            jarExecutor.shutdownNow();
        }
        Files.write(cache.resolve("classpath.order"), dependencies.values(), StandardCharsets.UTF_8);
        writeClasspathJar(cache, dependencies.values());
    }

    /**
     * 按 Manifest 长度读取一个独立 XZ 块，避免把整个 payload 同时载入内存。
     */
    private static byte[] readChunk(InputStream payload, long declaredSize, long totalSize) throws IOException {
        if (declaredSize < 0) {
            return payload.readAllBytes();
        }
        if (declaredSize > Integer.MAX_VALUE || declaredSize > totalSize) {
            throw new IOException("Invalid payload chunk size: " + declaredSize);
        }
        byte[] data = payload.readNBytes((int) declaredSize);
        if (data.length != declaredSize) {
            throw new IOException("Unexpected end of payload chunk");
        }
        return data;
    }

    /**
     * 解压一个独立 XZ 块，并按依赖 JAR 边界流式提交重建任务。
     */
    private static void unpackChunk(byte[] compressedChunk, Path cache, Map<Integer, String> dependencies,
                                    AtomicBoolean applicationPayloadSeen, ExecutorService jarExecutor,
                                    Semaphore slots, Queue<Future<?>> jarJobs) throws Exception {
        Path lib = cache.resolve("lib");
        Path applicationTarget = cache.resolve(APPLICATION_JAR);
        List<RawEntry> applicationEntries = new ArrayList<>();
        List<RawEntry> dependencyEntries = new ArrayList<>();
        Path dependencyTarget = null;
        int currentId = -1;
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
                    applicationEntries.add(new RawEntry(name.substring(APPLICATION_PREFIX.length()), entry.getTime(), zip.readAllBytes()));
                    continue;
                }
                if (!name.startsWith(DEPENDENCY_PREFIX)) {
                    continue;
                }
                String relative = name.substring(DEPENDENCY_PREFIX.length());
                int separator = relative.indexOf('/');
                if (separator <= 0) {
                    throw new IOException("Invalid payload entry: " + name);
                }
                int id = Integer.parseInt(relative.substring(0, separator));
                String originalName = relative.substring(separator + 1);
                if (id != currentId) {
                    if (currentId >= 0) {
                        submitJar(jarExecutor, slots, jarJobs, dependencyTarget, dependencyEntries);
                        dependencyEntries = new ArrayList<>();
                    }
                    String dependencyName = dependencies.get(id);
                    if (dependencyName == null) {
                        throw new IOException("Missing dependency index: " + id);
                    }
                    dependencyTarget = lib.resolve(dependencyName);
                    currentId = id;
                }
                dependencyEntries.add(new RawEntry(originalName, entry.getTime(), zip.readAllBytes()));
            }
        }
        if (currentId >= 0) {
            submitJar(jarExecutor, slots, jarJobs, dependencyTarget, dependencyEntries);
        }
        if (!applicationEntries.isEmpty()) {
            if (!applicationPayloadSeen.compareAndSet(false, true)) {
                throw new IOException("Duplicate application payload");
            }
            submitJar(jarExecutor, slots, jarJobs, applicationTarget, applicationEntries);
        }
    }

    /**
     * 提交单个 JAR 重建任务，并通过信号量限制待处理的解压数据量。
     */
    private static void submitJar(ExecutorService executor, Semaphore slots, Queue<Future<?>> jobs, Path target, List<RawEntry> entries) throws Exception {
        slots.acquire();
        try {
            jobs.add(executor.submit(() -> {
                try {
                    writeJar(target, entries);
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                } finally {
                    slots.release();
                }
            }));
        } catch (RuntimeException ex) {
            slots.release();
            throw ex;
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

    private static void writeJar(Path target, List<RawEntry> entries) throws IOException {
        Path temporary = target.resolveSibling(target.getFileName() + ".tmp");
        try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(temporary))) {
            output.setLevel(Deflater.BEST_SPEED);
            for (RawEntry entry : entries) {
                ZipEntry outputEntry = new ZipEntry(entry.name());
                if (entry.time() >= 0) {
                    outputEntry.setTime(entry.time());
                }
                output.putNextEntry(outputEntry);
                output.write(entry.data());
                output.closeEntry();
            }
        }
        Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    private record RawEntry(String name, long time, byte[] data) {
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
        try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(temporary), manifest)) {
        }
        Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    private static void parseIndex(byte[] data, Map<Integer, String> dependencies) {
        String value = new String(data, StandardCharsets.UTF_8);
        for (String line : value.split("\\R")) {
            if (line.startsWith("format\t")) {
                continue;
            }
            String[] fields = line.split("\t", 2);
            if (fields.length == 2) {
                dependencies.put(Integer.parseInt(fields[0]), fields[1]);
            }
        }
    }

    /**
     * 启动实际应用 JVM，并转发启动器收到的 JVM 参数和应用参数。
     */
    private static int launch(Path cache, String startClass, String[] args) throws Exception {
        List<String> command = new ArrayList<>();
        command.add(javaExecutable());
        command.addAll(ManagementFactory.getRuntimeMXBean().getInputArguments());
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
