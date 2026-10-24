package cn.oyzh.pkg.util.compact;

import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.thread.ThreadUtil;
import com.github.luben.zstd.Zstd;
import com.github.luben.zstd.ZstdInputStream;
import com.github.luben.zstd.ZstdOutputStream;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
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
 * 构建器将应用 JAR 与依赖 JAR 展开为原始 ZIP 条目，通过多个 Zstd 流并行压缩，
 * 并把最小启动器和 Zstd 运行时嵌入最终 JAR。启动时再按 JAR 哈希重建运行缓存。
 */
public final class CompactJarBuilderV2 {

    private static final String PAYLOAD_INDEX_ENTRY = "META-INF/compact-index.tsv";
    private static final String PAYLOAD_ENTRY = "compact-payload.zst";
    private static final String APPLICATION_PREFIX = "a/";
    private static final String DEPENDENCY_PREFIX = "d/";

    private static final String DEFAULT_CLASSPATH_INDEX = "BOOT-INF/classpath.idx";
    private static final String DEFAULT_LIBRARY_PREFIX = "BOOT-INF/lib/";
    private static final String CLASSPATH_INDEX_ATTRIBUTE = "Spring-Boot-Classpath-Index";
    private static final String LIBRARY_PREFIX_ATTRIBUTE = "Spring-Boot-Lib";

    private static final String LAUNCHER_CLASS = CompressedJarLauncherV2.class.getName();
    private static final String LAUNCHER_CLASS_PATH = LAUNCHER_CLASS.replace('.', '/');
    private static final String AOT_CLASS_PATH = AotCacheManager.class.getName().replace('.', '/');
    private static final String ZSTD_CLASS_PREFIX = "com/github/luben/zstd/";

    /**
     * 原始 ZIP 分块是 I/O 型任务，保留三个并行度即可。
     */
    private static final int DEFAULT_RAW_CHUNK_THREADS = 3;

    /** 默认块级压缩并发数，控制 Zstd 本机内存峰值。 */
    private static final int DEFAULT_ZSTD_COMPRESS_THREADS = 2;

    /**
     * 默认 windowLog 为 24，即使用 16 MiB 搜索窗口，在压缩率和本机内存之间取得平衡。
     */
    private static final int DEFAULT_ZSTD_WINDOW_LOG = 24;

    /**
     * 限制可配置窗口上限，避免误配置导致打包进程申请过大的本机内存。
     */
    private static final int MAX_SAFE_WINDOW_LOG = 27;

    /**
     * 仅允许并发参数处于该范围，防止配置错误创建大量 CPU 密集型线程。
     */
    private static final int MIN_PARALLELISM = 1;
    private static final int MAX_PARALLELISM = 16;

    private CompactJarBuilderV2() {
    }

    /**
     * 命令行入口。
     *
     * @param args 可执行 JAR、应用 JAR、输出 JAR，以及可选的 Zstd 压缩级别
     * @throws Exception 构建失败
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 3 || args.length > 4) {
            throw new IllegalArgumentException("Usage: CompactJarBuilderV2 <executable.jar> <application.jar> <output.jar> [zstd-level]");
        }
        int level = args.length == 4 ? Integer.parseInt(args[3]) : Zstd.maxCompressionLevel();
        build(Paths.get(args[0]), Paths.get(args[1]), Paths.get(args[2]), level);
    }

    /**
     * 构建可独立启动的压缩 JAR。
     * <p>
     * 最终 JAR 中只嵌入启动器、Zstd 运行时和多段压缩数据，不再单独保存已压缩的应用 JAR。
     *
     * @param executableJar  包含依赖和 {@code classpath.idx} 的 Spring Boot 可执行 JAR
     * @param applicationJar 不包含外部依赖的应用 JAR
     * @param outputJar      压缩后的可执行 JAR
     * @param level          Zstd 压缩级别
     * @throws Exception 读取输入或写入输出失败
     */
    public static void build(Path executableJar, Path applicationJar, Path outputJar, int level) throws Exception {
        validateArguments(executableJar, applicationJar, outputJar, level);
        Path outputDirectory = outputJar.toAbsolutePath().getParent();
        Files.createDirectories(outputDirectory);
        Path payload = Files.createTempFile(outputDirectory, "compact-jar-payload-", ".zst");
        try {
            PayloadInfo payloadInfo = writePayload(executableJar, applicationJar, payload, level);
            Manifest manifest = createManifest(payload, applicationJar, payloadInfo, level);
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
    private static void validateArguments(Path executableJar, Path applicationJar, Path outputJar, int level) throws IOException {
        if (level < Zstd.minCompressionLevel() || level > Zstd.maxCompressionLevel()) {
            throw new IllegalArgumentException("Zstd level must be between "
                    + Zstd.minCompressionLevel() + " and " + Zstd.maxCompressionLevel());
        }
        if (!Files.isRegularFile(executableJar)) {
            throw new IOException("Executable JAR not found: " + executableJar);
        }
        if (!Files.isRegularFile(applicationJar)) {
            throw new IOException("Application JAR not found: " + applicationJar);
        }
        Path normalizedOutput = outputJar.toAbsolutePath().normalize();
        Path normalizedExecutable = executableJar.toAbsolutePath().normalize();
        Path normalizedApplication = applicationJar.toAbsolutePath().normalize();
        if (normalizedExecutable.equals(normalizedApplication)) {
            throw new IllegalArgumentException("Executable JAR and application JAR must be different files");
        }
        if (normalizedOutput.equals(normalizedExecutable)
                || normalizedOutput.equals(normalizedApplication)) {
            throw new IllegalArgumentException("Output JAR must not overwrite an input JAR");
        }
    }

    /**
     * 将应用和依赖的原始条目写入多个独立 Zstd 流。
     * <p>
     * 每个流都包含完整 ZIP 条目和依赖索引。原始块生成和 Zstd 压缩均并行执行，
     * 启动阶段也可并行解压，从而缩短打包和首次缓存创建时间。
     */
    private static PayloadInfo writePayload(Path executableJar, Path applicationJar, Path payload, int level) throws Exception {
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
            rawChunks.addAll(invokeParallel(rawChunkTasks, DEFAULT_RAW_CHUNK_THREADS));
            List<Callable<Path>> compressionTasks = new ArrayList<>(rawChunks.size());
            CompressionConfig compressionConfig = createCompressionConfig(rawChunks.size());
            for (Path rawChunk : rawChunks) {
                compressionTasks.add(() -> compressChunk(
                        rawChunk, payload.getParent(), level, compressionConfig.windowLog()));
            }
            compressedChunks.addAll(invokeParallel(compressionTasks, compressionConfig.parallelism()));

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
     * 依赖列表来自 classpath 索引，连续切分可保持类路径相邻依赖位于同一 Zstd 字典中。
     * 实测比按文件大小切分略好，同时三个块通常也足够均衡。
     */
    private static List<List<String>> splitDependencies(List<String> dependencies) {
        if (dependencies.size() <= 1) {
            return dependencies.isEmpty() ? List.of() : List.of(dependencies);
        }
        int middle = (dependencies.size() + 1) / 2;
        return List.of(dependencies.subList(0, middle), dependencies.subList(middle, dependencies.size()));
    }

    /**
     * 使用独立 Zstd 流压缩一个原始 ZIP 块。
     * <p>
     * 每个块都是自包含的 Zstd Frame，并启用内容校验；启动器只按 Manifest 长度切块，
     * 因此不会为了定位下一帧而扫描或整段缓存 payload。
     */
    private static Path compressChunk(Path rawChunk, Path directory, int level, int windowLog) throws Exception {
        Path compressedChunk = Files.createTempFile(directory, "compact-zstd-", ".zst");
        try (InputStream input = new BufferedInputStream(Files.newInputStream(rawChunk));
             OutputStream output = new BufferedOutputStream(Files.newOutputStream(compressedChunk));
             ZstdOutputStream zstdOutput = new ZstdOutputStream(output, level)) {
            zstdOutput.setChecksum(true);
            zstdOutput.setWindowLog(windowLog);
            input.transferTo(zstdOutput);
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
     * 根据 CPU 数量和调用方上限计算 Zstd 窗口大小及块级并发数。
     * <p>
     * {@code compact.jar.zstd.window.log} 控制搜索窗口，默认 24（16 MiB）；
     * {@code compact.jar.zstd.compress.threads} 控制块级并发，默认 2。
     * Zstd 主要使用本机内存，较小窗口和受控并发可以降低长驻 JVM 打包时的 RSS 峰值。
     */
    private static CompressionConfig createCompressionConfig(int taskCount) {
        int defaultWindowLog = RuntimeUtil.processorCount() >= 16 ? 25 : DEFAULT_ZSTD_WINDOW_LOG;
        int windowLog = readIntProperty("compact.jar.zstd.window.log", defaultWindowLog,
                Math.max(10, Zstd.windowLogMin()), Math.min(MAX_SAFE_WINDOW_LOG, Zstd.windowLogMax()));
        int defaultThreads;
        if (SystemUtil.isCIEnv()) {
            defaultThreads = 1;
        } else if (RuntimeUtil.processorCount() >= 16) {
            defaultThreads = 3;
        } else if (RuntimeUtil.processorCount() >= 8) {
            defaultThreads = 2;
        } else {
            defaultThreads = DEFAULT_ZSTD_COMPRESS_THREADS;
        }
        int parallelismLimit = readIntProperty("compact.jar.zstd.compress.threads", defaultThreads,
                MIN_PARALLELISM, MAX_PARALLELISM);
        int parallelism = Math.max(1, Math.min(taskCount, parallelismLimit));
        return new CompressionConfig(windowLog, parallelism);
    }

    /**
     * 读取并校验整数系统属性；值非法时立即失败，避免静默使用与调用方预期不同的配置。
     */
    private static int readIntProperty(String name, int defaultValue, int minimum, int maximum) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        int result;
        try {
            result = Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(name + " must be an integer: " + value, ex);
        }
        if (result < minimum || result > maximum) {
            throw new IllegalArgumentException(
                    name + " must be between " + minimum + " and " + maximum + ": " + value);
        }
        return result;
    }

    /**
     * 一次构建中固定的压缩参数，保证所有 Zstd 块使用相同窗口大小。
     *
     * @param windowLog   Zstd windowLog，窗口大小为 2 的该次幂
     * @param parallelism 实际 Zstd 块级压缩并发数
     */
    private record CompressionConfig(int windowLog, int parallelism) {
    }

    /**
     * 使用固定数量的线程执行 CPU 密集型任务，并在返回前保证线程全部退出。
     * <p>
     * 这里使用 {@link ThreadUtil#newThread(Runnable)} 创建一次性工作线程，
     * 不使用其共享缓存线程池，避免打包命令结束后因空闲线程等待而延迟退出。
     * 工作线程从同一任务队列取任务，因此压缩并发不会随着块数量无限增长。
     */
    private static <T> List<T> invokeParallel(List<Callable<T>> tasks, int maxParallelism) throws Exception {
        if (maxParallelism < MIN_PARALLELISM || maxParallelism > MAX_PARALLELISM) {
            throw new IllegalArgumentException("Parallelism must be between "
                    + MIN_PARALLELISM + " and " + MAX_PARALLELISM);
        }
        List<FutureTask<T>> futures = new ArrayList<>(tasks.size());
        for (Callable<T> task : tasks) {
            futures.add(new FutureTask<>(task));
        }
        int workerCount = Math.min(maxParallelism, futures.size());
        List<Thread> threads = new ArrayList<>(workerCount);
        try {
            AtomicInteger nextTask = new AtomicInteger();
            for (int i = 0; i < workerCount; i++) {
                Thread thread = ThreadUtil.newThread(() -> {
                    int taskIndex;
                    while ((taskIndex = nextTask.getAndIncrement()) < futures.size()) {
                        futures.get(taskIndex).run();
                    }
                });
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
            deleteSuccessfulPathResults(futures);
            throw ex;
        }
    }

    /**
     * 并行任务失败时，清理已经成功生成的临时文件，避免异常构建在输出目录留下垃圾。
     */
    private static <T> void deleteSuccessfulPathResults(List<FutureTask<T>> futures) {
        for (FutureTask<T> future : futures) {
            if (!future.isDone() || future.isCancelled()) {
                continue;
            }
            try {
                T result = future.get();
                if (result instanceof Path path) {
                    Files.deleteIfExists(path);
                }
            } catch (Exception ignored) {
            }
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
                writeStoredEntry(output, prefix + entry.getName(), dependencyZip, entry);
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
            try (InputStream input = application.getInputStream(entry)) {
                writeStoredEntry(output, APPLICATION_PREFIX + entry.getName(), input, entry);
            }
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
            Set<String> seenNames = new HashSet<>();
            Set<String> seenFileNames = new HashSet<>();
            try (InputStream input = jarFile.getInputStream(indexEntry)) {
                String index = new String(input.readAllBytes(), StandardCharsets.UTF_8);
                for (String line : index.split("\\R")) {
                    String value = line.trim();
                    if (!value.startsWith("-") || !value.contains("\"")) {
                        continue;
                    }
                    String name = value.substring(value.indexOf('"') + 1, value.lastIndexOf('"'));
                    if (name.startsWith(libraryPrefix) && name.endsWith(".jar")) {
                        String fileName = name.substring(name.lastIndexOf('/') + 1);
                        validateDependencyName(name, fileName);
                        if (!seenNames.add(name) || !seenFileNames.add(fileName)) {
                            throw new IOException("Duplicate dependency in classpath index: " + name);
                        }
                        names.add(name);
                    }
                }
            }
            return Collections.unmodifiableList(names);
        }
    }

    private static void validateDependencyName(String name, String fileName) throws IOException {
        if (fileName.isBlank() || fileName.equals(".") || fileName.equals("..")
                || fileName.indexOf('/') >= 0 || fileName.indexOf('\\') >= 0
                || fileName.indexOf('\t') >= 0 || fileName.indexOf('\r') >= 0 || fileName.indexOf('\n') >= 0) {
            throw new IOException("Invalid dependency name: " + name);
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
     * 以 STORED 方式写入原始条目，把实际压缩交给外层 Zstd 流完成。
     */
    private static void writeStoredEntry(ZipOutputStream output, String name, byte[] data, long time) throws IOException {
        try (InputStream input = new java.io.ByteArrayInputStream(data)) {
            writeStoredEntry(output, name, input, data.length, crc32(data), time);
        }
    }

    /**
     * 以 STORED 方式流式写入条目。优先使用 ZIP 元数据中的长度和 CRC，
     * 未知元数据时先写临时文件，避免把超大条目完整载入堆内存。
     */
    private static void writeStoredEntry(ZipOutputStream output, String name, InputStream input, ZipEntry sourceEntry) throws IOException {
        long size = sourceEntry.getSize();
        long crc = sourceEntry.getCrc();
        if (size >= 0 && crc >= 0) {
            writeStoredEntry(output, name, input, size, crc, sourceEntry.getTime());
            return;
        }
        Path temporary = Files.createTempFile("compact-entry-", ".bin");
        try {
            long sizeValue;
            long crcValue;
            try (OutputStream fileOutput = Files.newOutputStream(temporary)) {
                CRC32 checksum = new CRC32();
                byte[] buffer = new byte[64 * 1024];
                long total = 0;
                int length;
                while ((length = input.read(buffer)) >= 0) {
                    fileOutput.write(buffer, 0, length);
                    checksum.update(buffer, 0, length);
                    total += length;
                }
                sizeValue = total;
                crcValue = checksum.getValue();
            }
            try (InputStream fileInput = Files.newInputStream(temporary)) {
                writeStoredEntry(output, name, fileInput, sizeValue, crcValue, sourceEntry.getTime());
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    private static void writeStoredEntry(ZipOutputStream output, String name, InputStream input,
                                         long size, long crc, long time) throws IOException {
        ZipEntry entry = new ZipEntry(name);
        entry.setMethod(ZipEntry.STORED);
        entry.setSize(size);
        entry.setCompressedSize(size);
        entry.setCrc(crc);
        if (time >= 0) {
            entry.setTime(time);
        }
        output.putNextEntry(entry);
        input.transferTo(output);
        output.closeEntry();
    }

    private static long crc32(byte[] data) {
        CRC32 checksum = new CRC32();
        checksum.update(data);
        return checksum.getValue();
    }

    private static Manifest createManifest(Path payload, Path applicationJar, PayloadInfo payloadInfo, int level) throws Exception {
        Manifest manifest = new Manifest();
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attributes.putValue("Main-Class", LAUNCHER_CLASS);
        attributes.putValue("Start-Class", payloadInfo.startClass());
        attributes.putValue("Payload-Entry", PAYLOAD_ENTRY);
        attributes.putValue("Application-JAR-SHA256", sha256(applicationJar));
        attributes.putValue("Payload-SHA256", sha256(payload));
        attributes.putValue("Payload-Chunk-Sizes", joinChunkSizes(payloadInfo.chunkSizes()));
        attributes.putValue("Zstd-Level", Integer.toString(level));
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
     * 从当前运行时 ClassPath 自动收集启动器、Zstd Java 类及各平台原生库。
     * <p>
     * 原生库必须与 Java 类一起嵌入，否则压缩 JAR 在未安装 zstd-jni 的环境中无法启动。
     */
    private static void addRuntimeClasses(JarOutputStream output) throws Exception {
        addClassSource(output, CompressedJarLauncherV2.class,
                name -> isRuntimeClass(name, LAUNCHER_CLASS_PATH) || isRuntimeClass(name, AOT_CLASS_PATH));
        addClassSource(output, ZstdInputStream.class,
                name -> name.startsWith(ZSTD_CLASS_PREFIX) || isZstdNativeResource(name));
    }

    private static boolean isRuntimeClass(String name, String classPath) {
        return name.endsWith(".class")
                && (name.equals(classPath + ".class") || name.startsWith(classPath + "$"));
    }

    /**
     * 判断条目是否属于 zstd-jni 随附的平台原生库。
     */
    private static boolean isZstdNativeResource(String name) {
        return name.startsWith("aix/") || name.startsWith("darwin/")
                || name.startsWith("freebsd/") || name.startsWith("linux/")
                || name.startsWith("win/");
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
