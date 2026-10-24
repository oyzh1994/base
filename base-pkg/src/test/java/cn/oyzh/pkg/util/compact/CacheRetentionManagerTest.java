package cn.oyzh.pkg.util.compact;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

public class CacheRetentionManagerTest {

    private static final String APPLICATION = "cn.oyzh.example.Main";
    private static final String OTHER_APPLICATION = "cn.oyzh.example.OtherMain";
    private static final Instant NOW = Instant.parse("2026-10-24T12:00:00Z");

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void removesOnlyExpiredCachesOfCurrentApplication() throws Exception {
        Path root = temporaryFolder.newFolder("cache-root").toPath();
        Path current = createCache(root, 1, APPLICATION, NOW.minus(Duration.ofDays(60)));
        Path old = createCache(root, 2, APPLICATION, NOW.minus(Duration.ofDays(31)));
        Path boundary = createCache(root, 3, APPLICATION, NOW.minus(CacheRetentionManager.RETENTION));
        Path recent = createCache(root, 4, APPLICATION, NOW.minus(Duration.ofDays(29)));
        Path otherApplication = createCache(root, 5, OTHER_APPLICATION, NOW.minus(Duration.ofDays(60)));
        Path ordinaryFile = root.resolve(cacheName(6));
        Files.writeString(ordinaryFile, "not a cache", StandardCharsets.UTF_8);
        Files.setLastModifiedTime(ordinaryFile, FileTime.from(NOW.minus(Duration.ofDays(60))));
        Path uppercaseName = root.resolve(cacheName(7).toUpperCase(Locale.ROOT));
        Files.createDirectories(uppercaseName);

        assertEquals(1, CacheRetentionManager.cleanupExpired(current, applicationId(APPLICATION), NOW));

        assertTrue(Files.isDirectory(current));
        assertFalse(Files.exists(old));
        assertTrue(Files.isDirectory(boundary));
        assertTrue(Files.isDirectory(recent));
        assertTrue(Files.isDirectory(otherApplication));
        assertTrue(Files.isRegularFile(ordinaryFile));
        assertTrue(Files.isDirectory(uppercaseName));
    }

    @Test
    public void retainsExpiredCacheWhileUsageLockIsHeld() throws Exception {
        Path root = temporaryFolder.newFolder("locked-cache-root").toPath();
        Path current = createCache(root, 1, APPLICATION, NOW.minus(Duration.ofDays(1)));
        Path old = createCache(root, 2, APPLICATION, NOW.minus(Duration.ofDays(60)));

        try (CacheRetentionManager.UsageLock lock = CacheRetentionManager.acquire(old)) {
            assumeTrue("Shared file locks are unavailable", lock.isHeld());
            assertEquals(0, CacheRetentionManager.cleanupExpired(current, applicationId(APPLICATION), NOW));
            assertTrue(Files.isDirectory(old));
        }

        assertEquals(1, CacheRetentionManager.cleanupExpired(current, applicationId(APPLICATION), NOW));
        assertFalse(Files.exists(old));
    }

    @Test
    public void recognizesLegacyCachesFromApplicationManifest() throws Exception {
        Path root = temporaryFolder.newFolder("legacy-cache-root").toPath();
        Path current = createCache(root, 1, APPLICATION, NOW.minus(Duration.ofDays(1)));
        Path legacyStartClass = createLegacyCache(root, 2, NOW.minus(Duration.ofDays(60)), "Start-Class", APPLICATION);
        Path legacyMainClass = createLegacyCache(root, 3, NOW.minus(Duration.ofDays(60)), "Main-Class", APPLICATION);
        Path otherApplication = createLegacyCache(root, 4, NOW.minus(Duration.ofDays(60)),
                "Start-Class", OTHER_APPLICATION);

        assertEquals(2, CacheRetentionManager.cleanupExpired(current, applicationId(APPLICATION), NOW));

        assertTrue(Files.isDirectory(current));
        assertFalse(Files.exists(legacyStartClass));
        assertFalse(Files.exists(legacyMainClass));
        assertTrue(Files.isDirectory(otherApplication));
    }

    @Test
    public void removesTombstonesAndDoesNotFollowSymbolicLinks() throws Exception {
        Path root = temporaryFolder.newFolder("special-cache-root").toPath();
        Path current = createCache(root, 1, APPLICATION, NOW);
        Path tombstone = root.resolve(".compact-stale-" + cacheName(2) + "-test");
        Files.createDirectories(tombstone.resolve("lib"));
        Files.writeString(tombstone.resolve("lib/dependency.jar"), "data", StandardCharsets.UTF_8);
        Path external = temporaryFolder.newFolder("external-cache").toPath();
        Files.writeString(external.resolve("keep.txt"), "keep", StandardCharsets.UTF_8);
        Path symbolicLink = root.resolve(cacheName(3));
        try {
            Files.createSymbolicLink(symbolicLink, external);
        } catch (Exception ignored) {
            symbolicLink = null;
        }

        assertEquals(0, CacheRetentionManager.cleanupExpired(current, applicationId(APPLICATION), NOW));

        assertFalse(Files.exists(tombstone));
        assertTrue(Files.exists(external.resolve("keep.txt")));
        if (symbolicLink != null) {
            assertTrue(Files.isSymbolicLink(symbolicLink));
        }
    }

    @Test
    public void cleanupNeverThrowsForMissingOrInvalidInputs() throws Exception {
        Path missingRoot = temporaryFolder.getRoot().toPath().resolve("missing");
        Path current = missingRoot.resolve(cacheName(1));

        assertEquals(0, CacheRetentionManager.cleanupExpired(current, applicationId(APPLICATION), NOW));
        assertEquals(0, CacheRetentionManager.cleanupExpired(null, applicationId(APPLICATION), NOW));
        assertEquals(0, CacheRetentionManager.cleanupExpired(current, null, NOW));
        assertEquals(0, CacheRetentionManager.cleanupExpired(current, applicationId(APPLICATION), null));
    }

    @Test
    public void cleanupAsyncRemovesExpiredCacheInBackground() throws Exception {
        Path root = temporaryFolder.newFolder("async-cache-root").toPath();
        Path current = createCache(root, 1, APPLICATION, NOW);
        Path old = createCache(root, 2, APPLICATION, Instant.now().minus(Duration.ofDays(60)));

        CacheRetentionManager.cleanupAsync(current, applicationId(APPLICATION));

        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        while (Files.exists(old) && System.nanoTime() < deadline) {
            Thread.sleep(20);
        }
        assertFalse(Files.exists(old));
    }

    @Test
    public void applicationIdentityIsStableAndApplicationSpecific() {
        assertTrue(applicationId(APPLICATION).matches("[0-9a-f]{64}"));
        assertEquals(applicationId(APPLICATION), applicationId(APPLICATION));
        assertNotEquals(applicationId(APPLICATION), applicationId(OTHER_APPLICATION));
        assertEquals(null, CacheRetentionManager.applicationId(" "));
    }

    private static Path createCache(Path root, int index, String startClass, Instant lastUsed) throws IOException {
        Path cache = root.resolve(cacheName(index));
        Files.createDirectories(cache);
        Files.writeString(cache.resolve("cache.ok"), "format=test\n", StandardCharsets.UTF_8);
        CacheRetentionManager.markUsed(cache, applicationId(startClass));
        ageCache(cache, lastUsed);
        return cache;
    }

    private static Path createLegacyCache(Path root, int index, Instant lastUsed, String manifestName,
                                          String startClass) throws IOException {
        Path cache = root.resolve(cacheName(index));
        Files.createDirectories(cache);
        Files.writeString(cache.resolve("cache.ok"), "format=test\n", StandardCharsets.UTF_8);
        Files.write(cache.resolve("last-used"), new byte[0]);
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().putValue(manifestName, startClass);
        try (OutputStream output = Files.newOutputStream(cache.resolve("application.jar"));
             JarOutputStream jar = new JarOutputStream(output, manifest)) {
        }
        ageCache(cache, lastUsed);
        return cache;
    }

    private static void ageCache(Path cache, Instant lastUsed) throws IOException {
        FileTime time = FileTime.from(lastUsed);
        Files.setLastModifiedTime(cache.resolve("last-used"), time);
        Files.setLastModifiedTime(cache.resolve("cache.ok"), time);
        Files.setLastModifiedTime(cache, time);
    }

    private static String cacheName(int value) {
        return String.format("%064x", value);
    }

    private static String applicationId(String startClass) {
        return CacheRetentionManager.applicationId(startClass);
    }
}
