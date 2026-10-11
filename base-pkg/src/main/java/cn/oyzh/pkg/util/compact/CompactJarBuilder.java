package cn.oyzh.pkg.util.compact;

import cn.oyzh.common.thread.ThreadUtil;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.function.Predicate;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 通用 Spring Boot 可执行 JAR 二次压缩构建器。
 * <p>
 * 构建器将应用 JAR 与依赖 JAR 展开为原始 ZIP 条目，通过多个 XZ 流并行压缩，
 * 并把最小启动器和 XZ 运行时嵌入最终 JAR。启动时再按 JAR 哈希重建运行缓存。
 */
public final class CompactJarBuilder {

    private static final String PAYLOAD_INDEX_ENTRY = "META-INF/compact-index.tsv";
    private static final String PAYLOAD_ENTRY = "compact-payload.xz";
    private static final String APPLICATION_PREFIX = "a/";
    private static final String DEPENDENCY_PREFIX = "d/";

    private static final String DEFAULT_CLASSPATH_INDEX = "BOOT-INF/classpath.idx";
    private static final String DEFAULT_LIBRARY_PREFIX = "BOOT-INF/lib/";
    private static final String CLASSPATH_INDEX_ATTRIBUTE = "Spring-Boot-Classpath-Index";
    private static final String LIBRARY_PREFIX_ATTRIBUTE = "Spring-Boot-Lib";

    private static final String LAUNCHER_CLASS = CompressedJarLauncher.class.getName();
    private static final String LAUNCHER_CLASS_PATH = LAUNCHER_CLASS.replace('.', '/');
    private static final String XZ_CLASS_PREFIX = "org/tukaani/xz/";

    private CompactJarBuilder() {
    }

    /**
     * 命令行入口。
     *
     * @param args 可执行 JAR、应用 JAR、输出 JAR，以及可选的 XZ 压缩级别
     * @throws Exception 构建失败
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 3 || args.length > 4) {
            throw new IllegalArgumentException("Usage: CompactJarBuilder <executable.jar> <application.jar> <output.jar> [xz-preset]");
        }
        int preset = args.length == 4 ? Integer.parseInt(args[3]) : 9;
        build(Paths.get(args[0]), Paths.get(args[1]), Paths.get(args[2]), preset);
    }

    /**
     * 构建可独立启动的压缩 JAR。
     * <p>
     * 最终 JAR 中只嵌入启动器、XZ 运行时和多段压缩数据，不再单独保存已压缩的应用 JAR。
     *
     * @param executableJar  包含依赖和 {@code classpath.idx} 的 Spring Boot 可执行 JAR
     * @param applicationJar 不包含外部依赖的应用 JAR
     * @param outputJar      压缩后的可执行 JAR
     * @param xzPreset       XZ 压缩级别，范围 0 到 9
     * @throws Exception 读取输入或写入输出失败
     */
    public static void build(Path executableJar, Path applicationJar, Path outputJar, int xzPreset) throws Exception {
        validateArguments(executableJar, applicationJar, outputJar, xzPreset);
        Path outputDirectory = outputJar.toAbsolutePath().getParent();
        Files.createDirectories(outputDirectory);
        Path payload = Files.createTempFile(outputDirectory, "compact-jar-payload-", ".xz");
        try {
            PayloadInfo payloadInfo = writePayload(executableJar, applicationJar, payload, xzPreset);
            Manifest manifest = createManifest(payload, applicationJar, payloadInfo, xzPreset);
            try (JarOutputStream output = new JarOutputStream(Files.newOutputStream(outputJar), manifest)) {
                addRuntimeClasses(output);
                addStoredFile(output, PAYLOAD_ENTRY, payload);
            }
        } finally {
            Files.deleteIfExists(payload);
        }
    }

    /**
     * 校验输入文件和压缩参数，避免输出文件覆盖输入文件。
     */
    private static void validateArguments(Path executableJar, Path applicationJar, Path outputJar, int xzPreset) throws IOException {
        if (xzPreset < 0 || xzPreset > 9) {
            throw new IllegalArgumentException("XZ preset must be between 0 and 9");
        }
        if (!Files.isRegularFile(executableJar)) {
            throw new IOException("Executable JAR not found: " + executableJar);
        }
        if (!Files.isRegularFile(applicationJar)) {
            throw new IOException("Application JAR not found: " + applicationJar);
        }
        Path normalizedOutput = outputJar.toAbsolutePath().normalize();
        if (normalizedOutput.equals(executableJar.toAbsolutePath().normalize())
                || normalizedOutput.equals(applicationJar.toAbsolutePath().normalize())) {
            throw new IllegalArgumentException("Output JAR must not overwrite an input JAR");
        }
    }

    /**
     * 将应用和依赖的原始条目写入多个独立 XZ 流。
     * <p>
     * 每个流都包含完整 ZIP 条目和依赖索引。原始块生成和 XZ 压缩均并行执行，
     * 启动阶段也可并行解压，从而缩短打包和首次缓存创建时间。
     */
    private static PayloadInfo writePayload(Path executableJar, Path applicationJar, Path payload, int xzPreset) throws Exception {
        List<String> dependencies = readDependencyNames(executableJar);
        String startClass = resolveStartClass(executableJar, applicationJar);
        List<Path> rawChunks = new ArrayList<>();
        List<Path> compressedChunks = new ArrayList<>();
        try {
            byte[] index = createIndex(dependencies);
            List<Callable<Path>> rawChunkTasks = new ArrayList<>();
            rawChunkTasks.add(() -> createApplicationChunk(applicationJar, payload.getParent(), index));
            for (List<String> dependencyGroup : splitDependencies(dependencies)) {
                rawChunkTasks.add(() -> createDependencyChunk(
                        executableJar, payload.getParent(), index, dependencies, dependencyGroup));
            }
            rawChunks.addAll(invokeParallel(rawChunkTasks));
            List<Callable<Path>> compressionTasks = new ArrayList<>(rawChunks.size());
            for (Path rawChunk : rawChunks) {
                compressionTasks.add(() -> compressChunk(rawChunk, payload.getParent(), xzPreset));
            }
            compressedChunks.addAll(invokeParallel(compressionTasks));

            List<Long> chunkSizes = new ArrayList<>(compressedChunks.size());
            try (OutputStream fileOutput = Files.newOutputStream(payload, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Path compressedChunk : compressedChunks) {
                    chunkSizes.add(Files.size(compressedChunk));
                    try (InputStream input = Files.newInputStream(compressedChunk)) {
                        input.transferTo(fileOutput);
                    }
                }
            }
            return new PayloadInfo(startClass, dependencies, chunkSizes);
        } finally {
            deleteTemporaryFiles(rawChunks);
            deleteTemporaryFiles(compressedChunks);
        }
    }

    /**
     * 将依赖按数量均衡分成两个大块；应用单独占一块，兼顾压缩率与并行度。
     * <p>
     * 依赖列表来自 classpath 索引，连续切分可保持类路径相邻依赖位于同一 XZ 字典中。
     * 实测比按文件大小切分略好，同时三个块通常也足够均衡。
     */
    private static List<List<String>> splitDependencies(List<String> dependencies) {
        if (dependencies.size() <= 1) {
            return List.of(dependencies);
        }
        int middle = (dependencies.size() + 1) / 2;
        return List.of(dependencies.subList(0, middle), dependencies.subList(middle, dependencies.size()));
    }

    /**
     * 使用独立 XZ 流压缩一个原始 ZIP 块。
     */
    private static Path compressChunk(Path rawChunk, Path directory, int xzPreset) throws Exception {
        Path compressedChunk = Files.createTempFile(directory, "compact-xz-", ".xz");
        try (InputStream input = new BufferedInputStream(Files.newInputStream(rawChunk));
             OutputStream output = new BufferedOutputStream(Files.newOutputStream(compressedChunk));
             XZOutputStream xzOutput = new XZOutputStream(output, new LZMA2Options(xzPreset))) {
            input.transferTo(xzOutput);
        } catch (Exception ex) {
            Files.deleteIfExists(compressedChunk);
            throw ex;
        }
        return compressedChunk;
    }

    private static void deleteTemporaryFiles(List<Path> files) {
        for (Path file : files) {
            try {
                Files.deleteIfExists(file);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 创建应用 JAR 的原始 ZIP 块；每次任务使用独立 JarFile，允许与依赖块并行读取。
     */
    private static Path createApplicationChunk(Path applicationJar, Path directory, byte[] index) throws Exception {
        Path rawChunk = Files.createTempFile(directory, "compact-raw-", ".zip");
        try (JarFile application = new JarFile(applicationJar.toFile());
             ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(rawChunk)))) {
            writeStoredEntry(output, PAYLOAD_INDEX_ENTRY, index, -1);
            writeApplication(output, application);
        } catch (Exception ex) {
            deleteTemporaryFile(rawChunk);
            throw ex;
        }
        return rawChunk;
    }

    /**
     * 创建一组依赖 JAR 的原始 ZIP 块；每次任务使用独立 JarFile，保证并发读取互不干扰。
     */
    private static Path createDependencyChunk(Path executableJar, Path directory, byte[] index,
                                              List<String> dependencies, List<String> dependencyGroup) throws Exception {
        Path rawChunk = Files.createTempFile(directory, "compact-raw-", ".zip");
        try (JarFile executable = new JarFile(executableJar.toFile());
             ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(rawChunk)))) {
            writeStoredEntry(output, PAYLOAD_INDEX_ENTRY, index, -1);
            for (String dependencyName : dependencyGroup) {
                writeDependency(output, executable, dependencyName, dependencies.indexOf(dependencyName));
            }
        } catch (Exception ex) {
            deleteTemporaryFile(rawChunk);
            throw ex;
        }
        return rawChunk;
    }

    private static void deleteTemporaryFile(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
        }
    }

    /**
     * 并行执行有限数量的 CPU 密集型任务，并在返回前线程全部退出。
     * <p>
     * 这里使用 {@link ThreadUtil#newThread(Runnable)} 创建一次性工作线程，
     * 不使用其共享缓存线程池，避免打包命令结束后因空闲线程等待而延迟退出。
     */
    private static <T> List<T> invokeParallel(List<Callable<T>> tasks) throws Exception {
        List<FutureTask<T>> futures = new ArrayList<>(tasks.size());
        List<Thread> threads = new ArrayList<>(tasks.size());
        try {
            for (Callable<T> task : tasks) {
                FutureTask<T> future = new FutureTask<>(task);
                Thread thread = ThreadUtil.newThread(future);
                futures.add(future);
                threads.add(thread);
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            List<T> results = new ArrayList<>(futures.size());
            for (FutureTask<T> future : futures) {
                results.add(future.get());
            }
            return results;
        } catch (Exception ex) {
            for (FutureTask<T> future : futures) {
                future.cancel(true);
            }
            throw ex;
        }
    }

    /**
     * 展开一个嵌套依赖 JAR，并为条目添加稳定的依赖序号前缀。
     */
    private static void writeDependency(ZipOutputStream output, JarFile executable, String dependencyName, int index) throws IOException {
        JarEntry dependencyEntry = executable.getJarEntry(dependencyName);
        if (dependencyEntry == null) {
            throw new IOException("Missing dependency entry: " + dependencyName);
        }
        String prefix = String.format("%s%05d/", DEPENDENCY_PREFIX, index);
        try (InputStream dependencyStream = executable.getInputStream(dependencyEntry);
             ZipInputStream dependencyZip = new ZipInputStream(new BufferedInputStream(dependencyStream))) {
            ZipEntry entry;
            while ((entry = dependencyZip.getNextEntry()) != null) {
                writeStoredEntry(output, prefix + entry.getName(), dependencyZip.readAllBytes(), entry.getTime());
            }
        }
    }

    /**
     * 展开不包含外部依赖的应用 JAR。
     */
    private static void writeApplication(ZipOutputStream output, JarFile application) throws IOException {
        Enumeration<JarEntry> entries = application.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            byte[] data;
            try (InputStream input = application.getInputStream(entry)) {
                data = input.readAllBytes();
            }
            writeStoredEntry(output, APPLICATION_PREFIX + entry.getName(), data, entry.getTime());
        }
    }

    /**
     * 从 Spring Boot 的 classpath 索引读取依赖顺序。
     * <p>
     * 索引和依赖目录优先读取 Manifest，以兼容不同 Spring Boot 版本或自定义布局。
     */
    private static List<String> readDependencyNames(Path executableJar) throws IOException {
        try (JarFile jarFile = new JarFile(executableJar.toFile())) {
            Manifest manifest = jarFile.getManifest();
            String indexName = manifestAttribute(manifest, CLASSPATH_INDEX_ATTRIBUTE, DEFAULT_CLASSPATH_INDEX);
            String libraryPrefix = manifestAttribute(manifest, LIBRARY_PREFIX_ATTRIBUTE, DEFAULT_LIBRARY_PREFIX);
            JarEntry indexEntry = jarFile.getJarEntry(indexName);
            if (indexEntry == null) {
                throw new IOException("Missing classpath index: " + indexName);
            }
            List<String> names = new ArrayList<>();
            try (InputStream input = jarFile.getInputStream(indexEntry)) {
                String index = new String(input.readAllBytes(), StandardCharsets.UTF_8);
                for (String line : index.split("\\R")) {
                    String value = line.trim();
                    if (!value.startsWith("-") || !value.contains("\"")) {
                        continue;
                    }
                    String name = value.substring(value.indexOf('"') + 1, value.lastIndexOf('"'));
                    if (name.startsWith(libraryPrefix) && name.endsWith(".jar")) {
                        names.add(name);
                    }
                }
            }
            if (names.isEmpty()) {
                throw new IOException("No dependencies found in classpath index: " + indexName);
            }
            return Collections.unmodifiableList(names);
        }
    }

    /**
     * 优先使用 Spring Boot 的 Start-Class，也支持应用 JAR Manifest 中的 Main-Class。
     */
    private static String resolveStartClass(Path executableJar, Path applicationJar) throws IOException {
        try (JarFile executable = new JarFile(executableJar.toFile())) {
            String startClass = manifestAttribute(executable.getManifest(), "Start-Class", null);
            if (startClass != null && !startClass.isBlank()) {
                return startClass;
            }
        }
        try (JarFile application = new JarFile(applicationJar.toFile())) {
            String mainClass = manifestAttribute(application.getManifest(), "Main-Class", null);
            if (mainClass != null && !mainClass.isBlank()) {
                return mainClass;
            }
        }
        throw new IOException("Missing start class: set Start-Class in the executable JAR or Main-Class in the application JAR");
    }

    private static String manifestAttribute(Manifest manifest, String name, String defaultValue) {
        if (manifest == null) {
            return defaultValue;
        }
        String value = manifest.getMainAttributes().getValue(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static byte[] createIndex(List<String> dependencies) {
        StringBuilder text = new StringBuilder("format\t1\n");
        for (int dependencyIndex = 0; dependencyIndex < dependencies.size(); dependencyIndex++) {
            String name = dependencies.get(dependencyIndex);
            text.append(String.format("%05d", dependencyIndex)).append('\t').append(name.substring(name.lastIndexOf('/') + 1)).append('\n');
        }
        return text.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 以 STORED 方式写入原始条目，把实际压缩交给外层 XZ 流完成。
     */
    private static void writeStoredEntry(ZipOutputStream output, String name, byte[] data, long time) throws IOException {
        ZipEntry entry = new ZipEntry(name);
        entry.setMethod(ZipEntry.STORED);
        entry.setSize(data.length);
        entry.setCompressedSize(data.length);
        CRC32 crc = new CRC32();
        crc.update(data);
        entry.setCrc(crc.getValue());
        if (time >= 0) {
            entry.setTime(time);
        }
        output.putNextEntry(entry);
        output.write(data);
        output.closeEntry();
    }

    private static Manifest createManifest(Path payload, Path applicationJar, PayloadInfo payloadInfo, int preset) throws Exception {
        Manifest manifest = new Manifest();
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attributes.putValue("Main-Class", LAUNCHER_CLASS);
        attributes.putValue("Start-Class", payloadInfo.startClass());
        attributes.putValue("Payload-Entry", PAYLOAD_ENTRY);
        attributes.putValue("Application-JAR-SHA256", sha256(applicationJar));
        attributes.putValue("Payload-SHA256", sha256(payload));
        attributes.putValue("Payload-Chunk-Sizes", joinChunkSizes(payloadInfo.chunkSizes()));
        attributes.putValue("XZ-Preset", Integer.toString(preset));
        attributes.putValue("Cache-Version", "4");
        attributes.putValue("Dependency-Count", Integer.toString(payloadInfo.dependencies().size()));
        return manifest;
    }

    private static String joinChunkSizes(List<Long> chunkSizes) {
        StringBuilder value = new StringBuilder();
        for (long chunkSize : chunkSizes) {
            if (value.length() > 0) {
                value.append(',');
            }
            value.append(chunkSize);
        }
        return value.toString();
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
        StringBuilder value = new StringBuilder(digest.getDigestLength() * 2);
        for (byte item : digest.digest()) {
            value.append(String.format("%02x", item));
        }
        return value.toString();
    }

    /**
     * 从当前运行时 ClassPath 自动收集启动器和 XZ 解压所需类，无需调用方手工准备类目录。
     */
    private static void addRuntimeClasses(JarOutputStream output) throws Exception {
        addClassSource(output, CompressedJarLauncher.class,
                name -> name.startsWith(LAUNCHER_CLASS_PATH) && name.endsWith(".class"));
        addClassSource(output, XZInputStream.class, name -> name.startsWith(XZ_CLASS_PREFIX));
    }

    /**
     * 从类所在目录或 JAR 中复制符合条件的 class 文件。
     */
    private static void addClassSource(JarOutputStream output, Class<?> type, Predicate<String> filter) throws Exception {
        Path source = codeSourcePath(type);
        if (Files.isDirectory(source)) {
            addDirectoryClasses(output, source, filter);
        } else if (Files.isRegularFile(source)) {
            addJarClasses(output, source, filter);
        } else {
            throw new IOException("Unsupported code source for " + type.getName() + ": " + source);
        }
    }

    private static Path codeSourcePath(Class<?> type) throws Exception {
        CodeSource codeSource = type.getProtectionDomain().getCodeSource();
        if (codeSource == null) {
            throw new IOException("Missing code source for " + type.getName());
        }
        URI location = codeSource.getLocation().toURI();
        return Paths.get(location).toAbsolutePath().normalize();
    }

    private static void addDirectoryClasses(JarOutputStream output, Path root, Predicate<String> filter) throws IOException {
        List<Path> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(root)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> filter.test(normalizeEntryName(root.relativize(path))))
                    .forEach(files::add);
        }
        Collections.sort(files);
        for (Path file : files) {
            String name = normalizeEntryName(root.relativize(file));
            try (InputStream input = Files.newInputStream(file)) {
                addDeflatedEntry(output, name, input);
            }
        }
    }

    private static void addJarClasses(JarOutputStream output, Path jarPath, Predicate<String> filter) throws IOException {
        try (JarFile jarFile = new JarFile(jarPath.toFile())) {
            List<String> names = new ArrayList<>();
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && filter.test(entry.getName())) {
                    names.add(entry.getName());
                }
            }
            Collections.sort(names);
            for (String name : names) {
                try (InputStream input = jarFile.getInputStream(jarFile.getJarEntry(name))) {
                    addDeflatedEntry(output, name, input);
                }
            }
        }
    }

    private static String normalizeEntryName(Path path) {
        return path.toString().replace('\\', '/');
    }

    private static void addDeflatedEntry(JarOutputStream output, String name, InputStream input) throws IOException {
        JarEntry entry = new JarEntry(name);
        output.putNextEntry(entry);
        input.transferTo(output);
        output.closeEntry();
    }

    private static void addStoredFile(JarOutputStream output, String name, Path file) throws IOException {
        long size = Files.size(file);
        CRC32 crc = new CRC32();
        try (InputStream input = Files.newInputStream(file)) {
            byte[] buffer = new byte[64 * 1024];
            int length;
            while ((length = input.read(buffer)) >= 0) {
                crc.update(buffer, 0, length);
            }
        }
        JarEntry entry = new JarEntry(name);
        entry.setMethod(ZipEntry.STORED);
        entry.setSize(size);
        entry.setCompressedSize(size);
        entry.setCrc(crc.getValue());
        output.putNextEntry(entry);
        try (InputStream input = Files.newInputStream(file)) {
            input.transferTo(output);
        }
        output.closeEntry();
    }

    private record PayloadInfo(String startClass, List<String> dependencies, List<Long> chunkSizes) {
    }
}
