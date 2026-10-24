package cn.oyzh.pkg.util.compact;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * 清理依赖 JAR 中的签名和逐条目摘要元数据。
 * <p>
 * JDK 24+ 的 AOT 记录模式会在应用启动前校验完整 classpath。带 {@code META-INF/*.SF}、
 * {@code *.RSA} 或大段逐条目摘要的 JAR 会被整包校验，显著拖慢首次训练启动。
 * 压缩启动器已经通过 payload SHA-256 保证分发完整性，因此依赖 JAR 自身的签名不再必要。
 * 该清理默认开启，可设置 {@code compact.jar.sanitize.dependencies=false} 关闭。
 */
final class CompactJarSanitizer {

    private static final String ENABLED_PROPERTY = "compact.jar.sanitize.dependencies";
    private static final String META_INF_PREFIX = "META-INF/";
    private static final String MANIFEST_ENTRY = "META-INF/MANIFEST.MF";

    private CompactJarSanitizer() {
    }

    static boolean isEnabled() {
        String value = System.getProperty(ENABLED_PROPERTY);
        return value == null || value.isBlank() || Boolean.parseBoolean(value.trim());
    }

    static boolean isManifestEntry(String name) {
        return MANIFEST_ENTRY.equalsIgnoreCase(name);
    }

    static boolean isSignatureEntry(String name) {
        if (!name.regionMatches(true, 0, META_INF_PREFIX, 0, META_INF_PREFIX.length())) {
            return false;
        }
        if (name.lastIndexOf('/') != META_INF_PREFIX.length() - 1) {
            return false;
        }
        String upper = name.toUpperCase(Locale.ROOT);
        return upper.endsWith(".SF")
                || upper.endsWith(".RSA")
                || upper.endsWith(".DSA")
                || upper.endsWith(".EC");
    }

    static byte[] readManifest(InputStream input) throws IOException {
        return input.readAllBytes();
    }

    /**
     * 仅保留 manifest 主节，删除逐条目签名节和主节中的摘要属性。
     */
    static byte[] sanitizeManifest(byte[] data) {
        String text = new String(data, StandardCharsets.UTF_8);
        String normalized = text.replace("\r\n", "\n").replace('\r', '\n');
        int sectionEnd = normalized.indexOf("\n\n");
        String main = sectionEnd < 0 ? normalized : normalized.substring(0, sectionEnd + 1);
        boolean changed = sectionEnd >= 0 && !normalized.substring(sectionEnd + 2).trim().isEmpty();

        StringBuilder result = new StringBuilder(main.length());
        for (String line : main.split("\n", -1)) {
            if (line.isEmpty()) {
                continue;
            }
            if (isManifestDigestAttribute(line)) {
                changed = true;
                continue;
            }
            result.append(line).append('\n');
        }
        if (!changed) {
            return data;
        }
        result.append('\n');
        return result.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static boolean isManifestDigestAttribute(String line) {
        int colon = line.indexOf(':');
        String name = (colon < 0 ? line : line.substring(0, colon)).toUpperCase(Locale.ROOT);
        return name.contains("-DIGEST-MANIFEST");
    }
}
