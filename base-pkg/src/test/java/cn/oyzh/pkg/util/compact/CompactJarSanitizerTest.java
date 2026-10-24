package cn.oyzh.pkg.util.compact;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompactJarSanitizerTest {

    @Test
    public void enabledByDefault() {
        String property = "compact.jar.sanitize.dependencies";
        String previous = System.getProperty(property);
        try {
            System.clearProperty(property);
            assertTrue(CompactJarSanitizer.isEnabled());
            System.setProperty(property, "true");
            assertTrue(CompactJarSanitizer.isEnabled());
            System.setProperty(property, "false");
            assertFalse(CompactJarSanitizer.isEnabled());
        } finally {
            if (previous == null) {
                System.clearProperty(property);
            } else {
                System.setProperty(property, previous);
            }
        }
    }

    @Test
    public void detectsJarSignatureEntries() {
        assertTrue(CompactJarSanitizer.isSignatureEntry("META-INF/TEST.SF"));
        assertTrue(CompactJarSanitizer.isSignatureEntry("meta-inf/test.rsa"));
        assertTrue(CompactJarSanitizer.isSignatureEntry("META-INF/TEST.DSA"));
        assertTrue(CompactJarSanitizer.isSignatureEntry("META-INF/TEST.EC"));
        assertFalse(CompactJarSanitizer.isSignatureEntry("META-INF/services/foo.SF"));
        assertFalse(CompactJarSanitizer.isSignatureEntry("TEST.SF"));
    }

    @Test
    public void removesPerEntryManifestSections() {
        byte[] manifest = ("Manifest-Version: 1.0\r\n"
                + "Created-By: test\r\n"
                + "\r\n"
                + "Name: dependency/DependencyMarker.class\r\n"
                + "SHA-256-Digest: abc\r\n").getBytes(StandardCharsets.UTF_8);

        String sanitized = new String(CompactJarSanitizer.sanitizeManifest(manifest),
                StandardCharsets.UTF_8);

        assertTrue(sanitized.contains("Manifest-Version: 1.0"));
        assertTrue(sanitized.contains("Created-By: test"));
        assertFalse(sanitized.contains("Name:"));
        assertFalse(sanitized.contains("SHA-256-Digest"));
    }

    @Test
    public void keepsManifestWithoutSignatureDataUnchanged() {
        byte[] manifest = ("Manifest-Version: 1.0\r\n"
                + "Automatic-Module-Name: test.module\r\n").getBytes(StandardCharsets.UTF_8);

        assertArrayEquals(manifest, CompactJarSanitizer.sanitizeManifest(manifest));
    }

    @Test
    public void removesManifestDigestAttributes() {
        byte[] manifest = ("Manifest-Version: 1.0\r\n"
                + "SHA-256-Digest-Manifest: abc\r\n"
                + "\r\n").getBytes(StandardCharsets.UTF_8);

        String sanitized = new String(CompactJarSanitizer.sanitizeManifest(manifest),
                StandardCharsets.UTF_8);

        assertFalse(sanitized.contains("Digest-Manifest"));
    }
}
