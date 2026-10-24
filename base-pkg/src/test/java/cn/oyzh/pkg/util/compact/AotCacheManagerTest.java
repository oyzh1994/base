package cn.oyzh.pkg.util.compact;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AotCacheManagerTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        System.setProperty("compact.jar.aot.assembly", "one-step");
    }

    @After
    public void tearDown() {
        System.clearProperty("compact.jar.aot.assembly");
    }

    @Test
    public void disabledSessionRemovesInheritedAotArguments() {
        try (AotCacheManager.Session session = AotCacheManager.Session.disabled(List.of(
                "-XX:AOTCacheOutput=old-cache",
                "-xx:aotcache=old-cache",
                "-XX:AOTMode=off",
                "-Xshare:off",
                "-Dapp.env=prod"))) {
            assertEquals(List.of("-Xshare:off", "-Dapp.env=prod"), session.jvmArguments());
        }
    }

    @Test
    public void detectsClassicCdsArgumentsIgnoringCase() {
        assertTrue(AotCacheManager.isDisabledByArguments(List.of("-XShare:Auto")));
        assertTrue(AotCacheManager.isDisabledByArguments(List.of("-XX:SHAREDARCHIVEFILE=cache.jsa")));
        assertTrue(AotCacheManager.isDisabledByArguments(List.of("-XX:DUMPLOADEDCLASSLIST=classes.txt")));
        assertTrue(AotCacheManager.isDisabledByArguments(List.of("-XX:ArchiveClassesAtExit=cache.jsa")));
        assertTrue(AotCacheManager.isDisabledByArguments(List.of("-XX:SharedArchiveConfigFile=config.txt")));
        assertTrue(AotCacheManager.isDisabledByArguments(List.of("-XX:ExtraSharedClassListFile=classes.txt")));
        assertTrue(AotCacheManager.isDisabledByArguments(List.of("-XX:+AutoCreateSharedArchive")));
        assertFalse(AotCacheManager.isDisabledByArguments(List.of("-XShare:OFF")));
        assertFalse(AotCacheManager.isDisabledByArguments(List.of("-Dapp.env=prod")));
    }

    @Test
    public void detectsConflictsInJvmOptionEnvironmentVariables() {
        assertTrue(AotCacheManager.hasConflictingEnvironment(Map.of("JAVA_TOOL_OPTIONS", "-Xshare:off")));
        assertTrue(AotCacheManager.hasConflictingEnvironment(
                Map.of("java_tool_options", "-Dapp.env=prod -XX:ArchiveClassesAtExit=cache.jsa")));
        assertTrue(AotCacheManager.hasConflictingEnvironment(
                Map.of("JDK_JAVA_OPTIONS", "-XX:AOTCache=old-cache")));
        assertTrue(AotCacheManager.hasConflictingEnvironment(
                Map.of("JAVA_TOOL_OPTIONS", "\"-Xshare:off\"")));
        assertFalse(AotCacheManager.hasConflictingEnvironment(
                Map.of("JAVA_TOOL_OPTIONS", "-Dapp.env=-Xshare:off")));
        assertFalse(AotCacheManager.hasConflictingEnvironment(Map.of("OTHER_OPTIONS", "-Xshare:off")));
    }

    @Test
    public void recognizesOnlySupportedVirtualMachines() {
        assertTrue(AotCacheManager.isSupportedVm("OpenJDK 64-Bit Server VM"));
        assertTrue(AotCacheManager.isSupportedVm("Java HotSpot(TM) 64-Bit Server VM"));
        assertFalse(AotCacheManager.isSupportedVm(""));
        assertFalse(AotCacheManager.isSupportedVm("Eclipse OpenJ9 VM"));
        assertFalse(AotCacheManager.isSupportedVm("Substrate VM"));
    }

    @Test
    public void validatesMarkerWithoutUnboundedReads() throws Exception {
        Path root = temporaryFolder.newFolder("aot marker").toPath();
        Path cache = Files.writeString(root.resolve("cache.aot"), "cache", StandardCharsets.UTF_8);
        Path marker = root.resolve("cache.ok");
        String signature = "test-signature";
        Files.writeString(marker, "format=1\nsignature=" + signature + "\nsize=5\n", StandardCharsets.UTF_8);
        assertTrue(AotCacheManager.isValid(cache, marker, signature));

        Files.write(marker, new byte[4097]);
        assertFalse(AotCacheManager.isValid(cache, marker, signature));
    }

    @Test
    public void concurrentTrainingSessionFallsBackWithoutBlocking() throws Exception {
        assumeTrue(Runtime.version().feature() >= 24);
        assumeTrue(AotCacheManager.isSupportedVm(System.getProperty("java.vm.name", "")));
        assumeFalse(AotCacheManager.hasConflictingEnvironment(System.getenv()));
        Path runtimeCache = temporaryFolder.newFolder("runtime cache").toPath();

        try (AotCacheManager.Session first = AotCacheManager.open(runtimeCache, "application")) {
            assertTrue(first.jvmArguments().stream()
                    .anyMatch(argument -> argument.startsWith("-XX:AOTCacheOutput=")));
            try (AotCacheManager.Session second = AotCacheManager.open(runtimeCache, "application")) {
                assertFalse(second.jvmArguments().stream()
                        .map(argument -> argument.toLowerCase(Locale.ROOT))
                        .anyMatch(argument -> argument.startsWith("-xx:aot")));
            }
        }
    }

    @Test
    public void twoStepTrainingRecordsConfigurationAndCleansAbortedState() throws Exception {
        assumeTrue(Runtime.version().feature() >= 24);
        assumeTrue(AotCacheManager.isSupportedVm(System.getProperty("java.vm.name", "")));
        assumeFalse(AotCacheManager.hasConflictingEnvironment(System.getenv()));
        System.setProperty("compact.jar.aot.assembly", "two-step");
        Path runtimeCache = temporaryFolder.newFolder("two step cache").toPath();
        Path aot = runtimeCache.resolve("aot");

        try (AotCacheManager.Session session = AotCacheManager.open(runtimeCache, "application")) {
            List<String> arguments = session.jvmArguments();
            assertTrue(arguments.contains("-XX:AOTMode=record"));
            assertTrue(arguments.stream().anyMatch(argument -> argument.startsWith("-XX:AOTConfiguration=")));
            assertTrue(arguments.contains("-XX:-AOTClassLinking"));
            assertFalse(arguments.stream().anyMatch(argument -> argument.startsWith("-XX:AOTCacheOutput=")));
            try (Stream<Path> paths = Files.list(aot)) {
                List<String> names = paths.map(path -> path.getFileName().toString()).toList();
                assertTrue(names.stream().anyMatch(name -> name.endsWith(".assembly")));
                assertFalse(names.stream().anyMatch(name -> name.endsWith(".aotconfig")));
            }
        }

        try (Stream<Path> paths = Files.list(aot)) {
            List<String> names = paths.map(path -> path.getFileName().toString()).toList();
            assertFalse(names.stream().anyMatch(name -> name.endsWith(".assembly")));
            assertFalse(names.stream().anyMatch(name -> name.endsWith(".training")));
            assertFalse(names.stream().anyMatch(name -> name.endsWith(".aot")));
        }
    }
}
