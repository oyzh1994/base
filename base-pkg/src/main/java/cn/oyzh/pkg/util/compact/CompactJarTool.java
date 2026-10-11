package cn.oyzh.pkg.util.compact;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 可执行 JAR 二次压缩工具的统一调用入口。
 * <p>
 * 该工具适用于由 Spring Boot Maven Plugin 生成、且包含 {@code BOOT-INF/classpath.idx}
 * 的可执行 JAR。构建完成后会自动预热按 JAR 哈希隔离的运行缓存。
 */
public final class CompactJarTool {

    /** 默认 XZ 压缩级别，优先保证产物体积。 */
    private static final int DEFAULT_XZ_PRESET = 9;

    private CompactJarTool() {
    }

    /**
     * 命令行入口。
     *
     * @param args 可执行 JAR、应用 JAR、输出 JAR，以及可选的 XZ 压缩级别
     * @throws Exception 打包或缓存预热失败
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 3 || args.length > 4) {
            throw new IllegalArgumentException("Usage: CompactJarTool <executable.jar> <application.jar> <output.jar> [xz-preset]");
        }
        int preset = args.length == 4 ? Integer.parseInt(args[3]) : DEFAULT_XZ_PRESET;
        compact(Paths.get(args[0]), Paths.get(args[1]), Paths.get(args[2]), preset);
    }

    /**
     * 使用默认 XZ 压缩级别构建压缩 JAR 并预热缓存。
     *
     * @param executableJar  包含依赖和 {@code classpath.idx} 的 Spring Boot 可执行 JAR
     * @param applicationJar 不包含外部依赖的应用 JAR
     * @param outputJar      压缩后的可执行 JAR
     * @throws Exception 打包或缓存预热失败
     */
    public static void compact(Path executableJar, Path applicationJar, Path outputJar) throws Exception {
        compact(executableJar, applicationJar, outputJar, DEFAULT_XZ_PRESET);
    }

    /**
     * 构建压缩 JAR 并预热缓存。
     *
     * @param executableJar  包含依赖和 {@code classpath.idx} 的 Spring Boot 可执行 JAR
     * @param applicationJar 不包含外部依赖的应用 JAR
     * @param outputJar      压缩后的可执行 JAR
     * @param xzPreset       XZ 压缩级别，范围 0 到 9
     * @throws Exception 打包或缓存预热失败
     */
    public static void compact(Path executableJar, Path applicationJar, Path outputJar, int xzPreset) throws Exception {
        CompactJarBuilder.build(executableJar, applicationJar, outputJar, xzPreset);
        CompressedJarLauncher.warmCache(outputJar);
    }
}
