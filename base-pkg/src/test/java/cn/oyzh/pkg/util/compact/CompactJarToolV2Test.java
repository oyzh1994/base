package cn.oyzh.pkg.util.compact;

import com.github.luben.zstd.Zstd;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Zstd V2 压缩组件集成测试。
 * <p>
 * 测试会创建带多个依赖、特殊 ZIP 条目名和资源的 Spring Boot 布局 JAR，
 * 再通过真实 {@code java -jar} 进程验证启动、缓存复用与三种缓存模式。
 */
public class CompactJarToolV2Test {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Path cacheDirectory;

    @Before
    public void setUp() throws IOException {
        cacheDirectory = temporaryFolder.newFolder("cache").toPath();
        System.setProperty("compact.jar.cache.dir", cacheDirectory.toString());
        System.setProperty("compact.jar.cache.mode", "deflate");
    }

    @After
    public void tearDown() {
        System.clearProperty("compact.jar.cache.dir");
        System.clearProperty("compact.jar.cache.mode");
        System.clearProperty("compact.jar.zstd.window.log");
        System.clearProperty("compact.jar.zstd.compress.threads");
    }

    @Test
    public void compactAndLaunchRealProcess() throws Exception {
        TestFixture fixture = createFixture(2);
        CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), fixture.outputJar());

        assertTrue(Files.size(fixture.outputJar()) > 0);
        assertRuntimeResources(fixture.outputJar());
        assertManifest(fixture.outputJar());
        assertEquals("V2_OK:--message=hello world|dependency-one|resource, \"quoted\"\nline",
                runJar(fixture.outputJar(), "--message=hello world"));
        assertApplicationRoundTrip(fixture.applicationJar());
    }

    @Test
    public void compactAndLaunchXzRealProcess() throws Exception {
        TestFixture fixture = createFixture(2);
        CompactJarTool.compact(fixture.executableJar(), fixture.applicationJar(), fixture.outputJar());

        assertEquals("V2_OK:--message=hello world|dependency-one|resource, \"quoted\"\nline",
                runJar(fixture.outputJar(), "--message=hello world"));
        try (JarFile jar = new JarFile(fixture.outputJar().toFile())) {
            assertTrue(jar.getJarEntry("compact-payload.xz") != null);
            assertEquals(CompressedJarLauncher.class.getName(),
                    jar.getManifest().getMainAttributes().getValue("Main-Class"));
        }
    }

    @Test
    public void warmCacheReusesAndRefreshesShaCache() throws Exception {
        TestFixture fixture = createFixture(2);
        CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), fixture.outputJar());

        CompressedJarLauncherV2.warmCache(fixture.outputJar());
        Path cache = findSingleCache();
        Path marker = cache.resolve("cache.ok");
        assertTrue(Files.isRegularFile(marker));
        long expectedMarkerTime = Files.getLastModifiedTime(marker).toMillis() - 2000;
        Files.setLastModifiedTime(marker, FileTime.fromMillis(expectedMarkerTime));

        CompressedJarLauncherV2.warmCache(fixture.outputJar());
        assertEquals(expectedMarkerTime, Files.getLastModifiedTime(marker).toMillis());

        Files.delete(marker);
        CompressedJarLauncherV2.warmCache(fixture.outputJar());
        assertTrue(Files.isRegularFile(marker));
    }

    @Test
    public void changedLauncherUsesDifferentShaCache() throws Exception {
        TestFixture fixture = createFixture(1);
        Path firstOutput = fixture.outputJar();
        CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), firstOutput);
        CompressedJarLauncherV2.warmCache(firstOutput);
        Path firstCache = findSingleCache();

        Path secondOutput = temporaryFolder.newFolder("changed").toPath().resolve("changed.jar");
        CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), secondOutput, 1);
        CompressedJarLauncherV2.warmCache(secondOutput);

        List<Path> caches = findCaches();
        assertEquals(2, caches.size());
        assertNotEquals(firstCache, caches.get(0).equals(firstCache) ? caches.get(1) : caches.get(0));
    }

    @Test
    public void supportsDeflateStoredAndHybridCacheModes() throws Exception {
        TestFixture fixture = createFixture(2);
        CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), fixture.outputJar());

        for (String mode : List.of("deflate", "stored", "hybrid")) {
            Path modeCache = temporaryFolder.newFolder("cache-" + mode).toPath();
            System.setProperty("compact.jar.cache.dir", modeCache.toString());
            System.setProperty("compact.jar.cache.mode", mode);
            CompressedJarLauncherV2.warmCache(fixture.outputJar());
            Path cache = findSingleCache(modeCache);
            assertTrue(Files.isRegularFile(cache.resolve("cache.ok")));
            assertApplicationRoundTrip(cache.resolve("application.jar"));
        }
    }

    @Test
    public void supportsNoDependencyApplication() throws Exception {
        TestFixture fixture = createFixture(0);
        CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), fixture.outputJar());
        assertEquals("V2_OK:empty|dependency-one|resource, \"quoted\"\nline",
                runJar(fixture.outputJar(), "empty"));
    }

    @Test
    public void rejectsInvalidArguments() throws Exception {
        TestFixture fixture = createFixture(1);
        Path output = fixture.outputJar();
        try {
            CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), output,
                    Zstd.maxCompressionLevel() + 1);
            fail("Expected invalid Zstd level to fail");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("Zstd level"));
        }
        try {
            CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), fixture.executableJar());
            fail("Expected output overwrite to fail");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("must not overwrite"));
        }
    }

    @Test
    public void rejectsInvalidOrTruncatedPayload() throws Exception {
        TestFixture fixture = createFixture(1);
        CompactJarToolV2.compact(fixture.executableJar(), fixture.applicationJar(), fixture.outputJar());
        Path invalidChunks = temporaryFolder.newFolder("invalid-chunks").toPath().resolve("invalid.jar");
        rewriteJar(fixture.outputJar(), invalidChunks, manifest -> {
            manifest.getMainAttributes().putValue("Payload-Chunk-Sizes", "0");
            return manifest;
        }, UnaryOperator.identity());

        try {
            CompressedJarLauncherV2.warmCache(invalidChunks);
            fail("Expected invalid chunk size to fail");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("chunk size"));
        }

        Path truncated = temporaryFolder.newFolder("truncated").toPath().resolve("truncated.jar");
        rewriteJar(fixture.outputJar(), truncated, UnaryOperator.identity(), data ->
                java.util.Arrays.copyOf(data, Math.max(1, data.length / 2)));
        try {
            CompressedJarLauncherV2.warmCache(truncated);
            fail("Expected truncated payload to fail");
        } catch (IOException expected) {
            assertTrue(expected.getMessage().contains("chunk sizes"));
        }

        Path corrupted = temporaryFolder.newFolder("corrupted").toPath().resolve("corrupted.jar");
        rewriteJar(fixture.outputJar(), corrupted, UnaryOperator.identity(), data -> {
            data[data.length - 8] ^= 1;
            return data;
        });
        boolean corruptedPayloadRejected = false;
        try {
            CompressedJarLauncherV2.warmCache(corrupted);
        } catch (Exception expected) {
            corruptedPayloadRejected = true;
        }
        assertTrue("Expected corrupted Zstd frame to fail", corruptedPayloadRejected);
    }

    private TestFixture createFixture(int dependencyCount) throws Exception {
        Path root = temporaryFolder.newFolder("fixture-" + System.nanoTime()).toPath();
        Path applicationJar = root.resolve("application.jar");
        Path executableJar = root.resolve("executable.jar");
        Path outputJar = root.resolve("compact-v2.jar");

        Map<String, byte[]> applicationEntries = new LinkedHashMap<>();
        applicationEntries.put(classEntryName(TestMain.class), classBytes(TestMain.class));
        if (dependencyCount == 0) {
            applicationEntries.put(classEntryName(DependencyMarker.class), classBytes(DependencyMarker.class));
        }
        applicationEntries.put("resource, \"quoted\"\nline.txt",
                "resource, \"quoted\"\nline".getBytes(StandardCharsets.UTF_8));
        applicationEntries.put("empty.txt", new byte[0]);
        writeJar(applicationJar, manifest("Main-Class", TestMain.class.getName()), applicationEntries);

        List<String> dependencies = new ArrayList<>();
        for (int index = 0; index < dependencyCount; index++) {
            String name = "dependency-" + (index + 1) + ".jar";
            Path dependency = root.resolve(name);
            Map<String, byte[]> entries = new LinkedHashMap<>();
            entries.put(classEntryName(DependencyMarker.class), classBytes(DependencyMarker.class));
            entries.put("dependency/resource.txt", ("dependency-" + (index + 1)).getBytes(StandardCharsets.UTF_8));
            writeJar(dependency, null, entries);
            dependencies.add(name);
        }

        Manifest executableManifest = manifest("Start-Class", TestMain.class.getName());
        Map<String, byte[]> executableEntries = new LinkedHashMap<>();
        StringBuilder classpathIndex = new StringBuilder();
        for (String dependency : dependencies) {
            String entryName = "BOOT-INF/lib/" + dependency;
            executableEntries.put(entryName, Files.readAllBytes(root.resolve(dependency)));
            classpathIndex.append("- \"").append(entryName).append("\"\n");
        }
        executableEntries.put("BOOT-INF/classpath.idx",
                classpathIndex.toString().getBytes(StandardCharsets.UTF_8));
        writeJar(executableJar, executableManifest, executableEntries);
        return new TestFixture(executableJar, applicationJar, outputJar);
    }

    private String runJar(Path jar, String argument) throws Exception {
        List<String> command = new ArrayList<>();
        command.add(Paths.get(System.getProperty("java.home"), "bin", "java").toString());
        command.add("--enable-native-access=ALL-UNNAMED");
        command.add("-Dcompact.jar.cache.dir=" + cacheDirectory);
        command.add("-Dcompact.jar.cache.mode=" + System.getProperty("compact.jar.cache.mode", "deflate"));
        command.add("-jar");
        command.add(jar.toString());
        command.add(argument);
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        assertTrue("Process timed out", process.waitFor(30, TimeUnit.SECONDS));
        assertEquals("Process failed: " + output, 0, process.exitValue());
        return output.strip();
    }

    private void assertRuntimeResources(Path outputJar) throws IOException {
        try (JarFile jar = new JarFile(outputJar.toFile())) {
            assertTrue(jar.getJarEntry("compact-payload.zst") != null);
            assertTrue(jar.getJarEntry("META-INF/compact-index.tsv") == null);
            assertTrue(jar.getJarEntry("com/github/luben/zstd/ZstdInputStream.class") != null);
            assertTrue(jar.stream().anyMatch(entry -> entry.getName().startsWith("linux/amd64/libzstd-jni-")
                    && entry.getName().endsWith(".so")));
            assertTrue(jar.stream().anyMatch(entry -> entry.getName().startsWith("darwin/aarch64/libzstd-jni-")
                    && entry.getName().endsWith(".dylib")));
            assertTrue(jar.stream().anyMatch(entry -> entry.getName().startsWith("win/amd64/libzstd-jni-")
                    && entry.getName().endsWith(".dll")));
        }
    }

    private void assertManifest(Path outputJar) throws IOException {
        try (JarFile jar = new JarFile(outputJar.toFile())) {
            Attributes attributes = jar.getManifest().getMainAttributes();
            assertEquals("compact-payload.zst", attributes.getValue("Payload-Entry"));
            assertEquals(CompressedJarLauncherV2.class.getName(), attributes.getValue("Main-Class"));
            assertEquals(Integer.toString(Zstd.maxCompressionLevel()), attributes.getValue("Zstd-Level"));
            assertTrue(attributes.getValue("Payload-SHA256").matches("[0-9a-f]{64}"));
            assertTrue(attributes.getValue("Application-JAR-SHA256").matches("[0-9a-f]{64}"));
        }
    }

    private void assertApplicationRoundTrip(Path applicationJar) throws IOException {
        try (JarFile jar = new JarFile(applicationJar.toFile())) {
            assertEquals("resource, \"quoted\"\nline",
                    new String(jar.getInputStream(jar.getJarEntry("resource, \"quoted\"\nline.txt"))
                            .readAllBytes(), StandardCharsets.UTF_8));
            assertEquals(0, jar.getInputStream(jar.getJarEntry("empty.txt")).readAllBytes().length);
        }
    }

    private Path findSingleCache() throws IOException {
        return findSingleCache(cacheDirectory);
    }

    private Path findSingleCache(Path root) throws IOException {
        List<Path> caches = findCaches(root);
        assertEquals("Expected one cache under " + root + ": " + caches, 1, caches.size());
        return caches.get(0);
    }

    private List<Path> findCaches() throws IOException {
        return findCaches(cacheDirectory);
    }

    private List<Path> findCaches(Path root) throws IOException {
        List<Path> result = new ArrayList<>();
        try (var paths = Files.walk(root)) {
            paths.filter(path -> path.getFileName().toString().equals("cache.ok"))
                    .map(Path::getParent)
                    .sorted()
                    .forEach(result::add);
        }
        return result;
    }

    private static Manifest manifest(String name, String value) {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().putValue(name, value);
        return manifest;
    }

    private static void writeJar(Path target, Manifest manifest, Map<String, byte[]> entries) throws IOException {
        try (JarOutputStream output = manifest == null
                ? new JarOutputStream(Files.newOutputStream(target))
                : new JarOutputStream(Files.newOutputStream(target), manifest)) {
            for (Map.Entry<String, byte[]> item : entries.entrySet()) {
                JarEntry entry = new JarEntry(item.getKey());
                entry.setMethod(ZipEntry.STORED);
                entry.setSize(item.getValue().length);
                entry.setCompressedSize(item.getValue().length);
                entry.setCrc(crc32(item.getValue()));
                output.putNextEntry(entry);
                output.write(item.getValue());
                output.closeEntry();
            }
        }
    }

    private static void rewriteJar(Path source, Path target, UnaryOperator<Manifest> manifestTransform,
                                   UnaryOperator<byte[]> payloadTransform) throws IOException {
        try (JarFile input = new JarFile(source.toFile());
             JarOutputStream output = new JarOutputStream(Files.newOutputStream(target),
                     manifestTransform.apply(input.getManifest()))) {
            var entries = input.entries();
            while (entries.hasMoreElements()) {
                JarEntry sourceEntry = entries.nextElement();
                if (sourceEntry.isDirectory() || sourceEntry.getName().equals("META-INF/MANIFEST.MF")) {
                    continue;
                }
                byte[] data = input.getInputStream(sourceEntry).readAllBytes();
                if (sourceEntry.getName().equals("compact-payload.zst")) {
                    data = payloadTransform.apply(data);
                }
                JarEntry targetEntry = new JarEntry(sourceEntry.getName());
                targetEntry.setMethod(ZipEntry.STORED);
                targetEntry.setSize(data.length);
                targetEntry.setCompressedSize(data.length);
                targetEntry.setCrc(crc32(data));
                output.putNextEntry(targetEntry);
                output.write(data);
                output.closeEntry();
            }
        }
    }

    private static long crc32(byte[] value) {
        java.util.zip.CRC32 crc = new java.util.zip.CRC32();
        crc.update(value);
        return crc.getValue();
    }

    private static String classEntryName(Class<?> type) {
        return type.getName().replace('.', '/') + ".class";
    }

    private static byte[] classBytes(Class<?> type) throws IOException {
        String name = classEntryName(type);
        try (InputStream input = type.getClassLoader().getResourceAsStream(name)) {
            if (input == null) {
                throw new IOException("Missing test class resource: " + name);
            }
            return input.readAllBytes();
        }
    }

    private record TestFixture(Path executableJar, Path applicationJar, Path outputJar) {
    }

    /** 可由压缩启动器转发的最小测试主类。 */
    public static final class TestMain {
        private TestMain() {
        }

        public static void main(String[] args) {
            String message = args.length == 0 ? "" : args[0];
            System.out.println("V2_OK:" + message + "|" + DependencyMarker.message()
                    + "|resource, \"quoted\"\nline");
        }
    }

    /** 放入依赖 JAR，验证缓存重建后的类路径顺序。 */
    public static final class DependencyMarker {
        private DependencyMarker() {
        }

        public static String message() {
            return "dependency-one";
        }
    }
}
