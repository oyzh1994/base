package cn.oyzh.pkg.util.compact;

import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.system.SystemUtil;
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

    /**
     * 原始 ZIP 分块是 I/O 型任务，保留三个并行度即可。
     */
    private static final int DEFAULT_RAW_CHUNK_THREADS = 3;

    /**
     * XZ 编码器内存开销较大，默认限制为两个，避免多个大字典同时分配。
     */
    private static final int DEFAULT_XZ_COMPRESS_THREADS = 2;

    /**
     * preset 9 默认字典为 64 MiB；默认上限压到 32 MiB，实测压缩率损失很小。
     */
    private static final int DEFAULT_XZ_DICT_SIZE = 32 * 1024 * 1024;

    /**
     * 仅允许并发参数处于该范围，防止配置错误创建大量 CPU 密集型线程。
     */
    private static final int MIN_PARALLELISM = 1;
    private static final int MAX_PARALLELISM = 16;

    /**
     * JAR 条目和 XZ 流之间使用较大的缓冲区，减少首次缓存重建时的小块读写次数。
     */
    private static final int IO_BUFFER_SIZE = 256 * 1024;
    private static final ThreadLocal<byte[]> COPY_BUFFERS = ThreadLocal.withInitial(() -> new byte[IO_BUFFER_SIZE]);

    /**
     * preset 9 默认 nice length 较保守；提高到 128 可用少量打包 CPU 换取更好的 LZMA2 匹配。
     */
    private static final int DEFAULT_XZ_NICE_LENGTH = 273;

    /**
     * 最多使用当前空闲堆的四分之三，给调用方业务线程和 GC 留出空间。
     */
    private static final int COMPRESSION_HEAP_PERCENT = 75;

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
        Path normalizedExecutable = executableJar.toAbsolutePath().normalize();
        Path normalizedApplication = applicationJar.toAbsolutePath().normalize();
        if (normalizedExecutable.equals(normalizedApplication)) {
            throw new IllegalArgumentException("Executable JAR and application JAR must be different files");
        }
        if (normalizedOutput.equals(normalizedExecutable) || normalizedOutput.equals(normalizedApplication)) {
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
            List<Long> dependencySizes = readDependencySizes(executableJar, dependencies);
            List<Callable<Path>> rawChunkTasks = new ArrayList<>();
            rawChunkTasks.add(() -> createApplicationChunk(applicationJar, payload.getParent(), index));
            for (List<String> dependencyGroup : splitDependencies(dependencies, dependencySizes)) {
                rawChunkTasks.add(() -> createDependencyChunk(executableJar, payload.getParent(), index, dependencies, dependencyGroup));
            }
            rawChunks.addAll(invokeParallel(rawChunkTasks, DEFAULT_RAW_CHUNK_THREADS));
            List<Callable<Path>> compressionTasks = new ArrayList<>(rawChunks.size());
            CompressionConfig compressionConfig = createCompressionConfig(xzPreset, rawChunks.size());
            for (Path rawChunk : rawChunks) {
                compressionTasks.add(() -> compressChunk(rawChunk, payload.getParent(), xzPreset, compressionConfig.dictSize()));
            }
            compressedChunks.addAll(invokeParallel(compressionTasks, compressionConfig.parallelism()));

            List<Long> chunkSizes = new ArrayList<>(compressedChunks.size());
            try (OutputStream fileOutput = Files.newOutputStream(payload, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Path compressedChunk : compressedChunks) {
                    chunkSizes.add(Files.size(compressedChunk));
                    try (InputStream input = Files.newInputStream(compressedChunk)) {
                        copy(input, fileOutput);
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
     * 将依赖按嵌套 JAR 大小均衡分成两个大块；应用单独占一块，兼顾压缩率与并行度。
     * <p>
     * 依赖列表来自 classpath 索引，连续切分可保持类路径相邻依赖位于同一 XZ 字典中。
     * 旧版按依赖数量切分时，内容大小差异会造成解压线程等待最长块；按大小切分可让两个
     * 依赖块更接近，通常能缩短首次缓存创建时间，同时仍只使用三个 XZ 流。
     */
    private static List<List<String>> splitDependencies(List<String> dependencies, List<Long> dependencySizes) {
        if (dependencies.size() <= 1) {
            return dependencies.isEmpty() ? List.of() : List.of(dependencies);
        }
        if (dependencySizes.size() != dependencies.size()) {
            throw new IllegalArgumentException("Dependency size count mismatch");
        }
        long total = 0;
        for (long size : dependencySizes) {
            if (size < 0 || total > Long.MAX_VALUE - size) {
                throw new IllegalArgumentException("Invalid dependency size");
            }
            total += size;
        }
        long left = 0;
        long bestDifference = Long.MAX_VALUE;
        int middle = (dependencies.size() + 1) / 2;
        for (int index = 0; index < dependencies.size() - 1; index++) {
            left += dependencySizes.get(index);
            long difference = Math.abs(left - (total - left));
            if (difference < bestDifference) {
                bestDifference = difference;
                middle = index + 1;
            }
        }
        return List.of(dependencies.subList(0, middle), dependencies.subList(middle, dependencies.size()));
    }

    /**
     * 使用独立 XZ 流压缩一个原始 ZIP 块。
     * <p>
     * 字典大小由 {@link CompressionConfig} 按可用堆内存计算，避免 preset 9 默认的
     * 64 MiB 字典被多个编码器同时放大。
     */
    private static Path compressChunk(Path rawChunk, Path directory, int xzPreset, int dictSize) throws Exception {
        Path compressedChunk = Files.createTempFile(directory, "compact-xz-", ".xz");
        LZMA2Options options = createLzma2Options(xzPreset, dictSize);
        try (InputStream input = new BufferedInputStream(Files.newInputStream(rawChunk), IO_BUFFER_SIZE); OutputStream output = new BufferedOutputStream(Files.newOutputStream(compressedChunk), IO_BUFFER_SIZE); XZOutputStream xzOutput = new XZOutputStream(output, options)) {
            copy(input, xzOutput);
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
     * 创建统一的 LZMA2 参数；preset 9 使用更长的匹配长度，优化最终产物大小。
     */
    private static LZMA2Options createLzma2Options(int preset, int dictSize) throws Exception {
        LZMA2Options options = new LZMA2Options(preset);
        options.setDictSize(dictSize);
        if (preset == LZMA2Options.PRESET_MAX) {
            options.setMatchFinder(LZMA2Options.MF_BT4);
            options.setNiceLen(readIntProperty("compact.jar.xz.nice.length", DEFAULT_XZ_NICE_LENGTH, LZMA2Options.NICE_LEN_MIN, LZMA2Options.NICE_LEN_MAX));
            options.setDepthLimit(0);
        }
        return options;
    }

    /**
     * 创建应用 JAR 的原始 ZIP 块；每次任务使用独立 JarFile，允许与依赖块并行读取。
     */
    private static Path createApplicationChunk(Path applicationJar, Path directory, byte[] index) throws Exception {
        Path rawChunk = Files.createTempFile(directory, "compact-raw-", ".zip");
        try (JarFile application = new JarFile(applicationJar.toFile()); ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(rawChunk), IO_BUFFER_SIZE))) {
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
    private static Path createDependencyChunk(Path executableJar, Path directory, byte[] index, List<String> dependencies, List<String> dependencyGroup) throws Exception {
        Path rawChunk = Files.createTempFile(directory, "compact-raw-", ".zip");
        try (JarFile executable = new JarFile(executableJar.toFile()); ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(rawChunk), IO_BUFFER_SIZE))) {
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
     * 根据 preset、调用方上限和当前空闲堆，计算 XZ 字典大小及压缩并发数。
     * <p>
     * {@code compact.jar.xz.dict.size} 是字节数上限，默认 32 MiB；
     * {@code compact.jar.xz.compress.threads} 是压缩线程上限，默认 2。
     * 两者都会继续按空闲堆自动下调，避免在 EasyShell 等长驻 JVM 内打包时挤压业务内存。
     */
    private static CompressionConfig createCompressionConfig(int xzPreset, int taskCount) throws Exception {
        // 判断机器可能的性能，动态分配内存大小
        int xzDictSize;
        if (SystemUtil.isCIEnv()) {
            xzDictSize = DEFAULT_XZ_DICT_SIZE;
        } else if (RuntimeUtil.processorCount() >= 12) {
            xzDictSize = 64 * 1024 * 1024;
        } else if (RuntimeUtil.processorCount() >= 6) {
            xzDictSize = 48 * 1024 * 1024;
        } else {
            xzDictSize = 32 * 1024 * 1024;
        }
        int dictSizeLimit = readIntProperty("compact.jar.xz.dict.size", xzDictSize, LZMA2Options.DICT_SIZE_MIN, LZMA2Options.DICT_SIZE_MAX);
        // 判断机器可能的性能，动态分配线程大小
        int xzThreads;
        if (SystemUtil.isCIEnv()) {
            xzThreads = 1;
        } else if (RuntimeUtil.processorCount() >= 16) {
            xzThreads = 4;
        } else if (RuntimeUtil.processorCount() >= 8) {
            xzThreads = 3;
        } else {
            xzThreads = DEFAULT_XZ_COMPRESS_THREADS;
        }
        int parallelismLimit = readIntProperty("compact.jar.xz.compress.threads", xzThreads, MIN_PARALLELISM, MAX_PARALLELISM);

        LZMA2Options options = new LZMA2Options(xzPreset);
        int dictSize = Math.min(options.getDictSize(), dictSizeLimit);
        options.setDictSize(dictSize);

        long compressionBudget = availableCompressionBudget();
        long encoderMemory = encoderMemoryUsage(options);
        int parallelism = Math.max(1, Math.min(taskCount, parallelismLimit));
        while (parallelism > 1 && encoderMemory * parallelism > compressionBudget) {
            parallelism--;
        }

        // 单编码器仍然超出预算时继续减半字典；低内存机器宁可牺牲少量压缩率，也要保证可构建。
        while (parallelism == 1 && encoderMemory > compressionBudget && dictSize > LZMA2Options.DICT_SIZE_MIN) {
            dictSize = Math.max(LZMA2Options.DICT_SIZE_MIN, dictSize / 2);
            options.setDictSize(dictSize);
            encoderMemory = encoderMemoryUsage(options);
        }
        return new CompressionConfig(dictSize, parallelism);
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
            throw new IllegalArgumentException(name + " must be between " + minimum + " and " + maximum + ": " + value);
        }
        return result;
    }

    /**
     * 返回可分配给压缩器的估算堆预算，保留至少四分之一空闲堆给业务线程与 GC。
     */
    private static long availableCompressionBudget() {
        Runtime runtime = Runtime.getRuntime();
        long used = runtime.totalMemory() - runtime.freeMemory();
        long free = Math.max(0, runtime.maxMemory() - used);
        return free / 100 * COMPRESSION_HEAP_PERCENT;
    }

    /**
     * xz-java 的内存估算单位是 KiB，这里统一换算为字节。
     */
    private static long encoderMemoryUsage(LZMA2Options options) {
        return options.getEncoderMemoryUsage() * 1024L;
    }

    /**
     * 一次构建中固定的压缩参数，保证所有 XZ 块使用相同字典大小。
     *
     * @param dictSize    LZMA2 字典大小，单位为字节
     * @param parallelism 实际 XZ 压缩并发数
     */
    private record CompressionConfig(int dictSize, int parallelism) {
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
            throw new IllegalArgumentException("Parallelism must be between " + MIN_PARALLELISM + " and " + MAX_PARALLELISM);
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
        try (InputStream dependencyStream = executable.getInputStream(dependencyEntry); ZipInputStream dependencyZip = new ZipInputStream(new BufferedInputStream(dependencyStream, IO_BUFFER_SIZE))) {
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

    /**
     * 读取嵌套依赖 JAR 的存储大小，用作两个 XZ 块的近似负载权重。
     * <p>
     * 这里不展开依赖内容统计解压后大小，避免为了选择切分点重复扫描全部 ZIP 条目。
     */
    private static List<Long> readDependencySizes(Path executableJar, List<String> dependencies) throws IOException {
        List<Long> sizes = new ArrayList<>(dependencies.size());
        try (JarFile jarFile = new JarFile(executableJar.toFile())) {
            for (String dependency : dependencies) {
                JarEntry entry = jarFile.getJarEntry(dependency);
                if (entry == null || entry.getSize() < 0) {
                    throw new IOException("Missing dependency size: " + dependency);
                }
                sizes.add(entry.getSize());
            }
        }
        return sizes;
    }

    private static void validateDependencyName(String name, String fileName) throws IOException {
        if (fileName.isBlank() || fileName.equals(".") || fileName.equals("..") || fileName.indexOf('/') >= 0 || fileName.indexOf('\\') >= 0 || fileName.indexOf('\t') >= 0 || fileName.indexOf('\r') >= 0 || fileName.indexOf('\n') >= 0) {
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
     * 以 STORED 方式写入原始条目，把实际压缩交给外层 XZ 流完成。
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

    private static void writeStoredEntry(ZipOutputStream output, String name, InputStream input, long size, long crc, long time) throws IOException {
        ZipEntry entry = new ZipEntry(name);
        entry.setMethod(ZipEntry.STORED);
        entry.setSize(size);
        entry.setCompressedSize(size);
        entry.setCrc(crc);
        if (time >= 0) {
            entry.setTime(time);
        }
        output.putNextEntry(entry);
        copy(input, output);
        output.closeEntry();
    }

    private static long crc32(byte[] data) {
        CRC32 checksum = new CRC32();
        checksum.update(data);
        return checksum.getValue();
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
        addClassSource(output, CompressedJarLauncher.class, name -> name.startsWith(LAUNCHER_CLASS_PATH) && name.endsWith(".class"));
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
            paths.filter(Files::isRegularFile).filter(path -> filter.test(normalizeEntryName(root.relativize(path)))).forEach(files::add);
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
        copy(input, output);
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
            copy(input, output);
        }
        output.closeEntry();
    }

    /**
     * 使用工作线程私有的大缓冲区复制流，避免 JDK 默认小缓冲区造成大量读写调用。
     */
    private static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = COPY_BUFFERS.get();
        int length;
        while ((length = input.read(buffer)) >= 0) {
            output.write(buffer, 0, length);
        }
    }

    private record PayloadInfo(String startClass, List<String> dependencies, List<Long> chunkSizes) {
    }
}
