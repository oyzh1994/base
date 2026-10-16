package cn.oyzh.pkg.util.compact;

import com.github.luben.zstd.Zstd;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Zstd 版可执行 JAR 二次压缩工具的统一调用入口。
 * <p>
 * V2 与 XZ 版接口保持一致，但输出使用 {@code compact-payload.zst}，
 * 并在最终 JAR 中嵌入 zstd-jni 的 Java 类和平台原生库。缓存仍按启动 JAR
 * 与 payload 的 SHA-256 隔离，调用方可通过
 * {@link CompressedJarLauncherV2#warmCache(Path)} 提前创建运行缓存。
 */
public final class CompactJarToolV2 {

    /** 默认采用 Zstd 当前版本支持的最高压缩级别。 */
    private static final int DEFAULT_ZSTD_LEVEL = Zstd.maxCompressionLevel();

    private CompactJarToolV2() {
    }

    /**
     * 命令行入口。
     *
     * @param args 可执行 JAR、应用 JAR、输出 JAR，以及可选的 Zstd 级别
     * @throws Exception 打包失败
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 3 || args.length > 4) {
            throw new IllegalArgumentException(
                    "Usage: CompactJarToolV2 <executable.jar> <application.jar> <output.jar> [zstd-level]");
        }
        int level = args.length == 4 ? Integer.parseInt(args[3]) : DEFAULT_ZSTD_LEVEL;
        compact(Paths.get(args[0]), Paths.get(args[1]), Paths.get(args[2]), level);
    }

    /**
     * 使用默认 Zstd 最高压缩级别构建压缩 JAR。
     *
     * @param executableJar  包含依赖和 {@code classpath.idx} 的 Spring Boot 可执行 JAR
     * @param applicationJar 不包含外部依赖的应用 JAR
     * @param outputJar      压缩后的可执行 JAR
     * @throws Exception 打包失败
     */
    public static void compact(Path executableJar, Path applicationJar, Path outputJar) throws Exception {
        compact(executableJar, applicationJar, outputJar, DEFAULT_ZSTD_LEVEL);
    }

    /**
     * 使用指定 Zstd 压缩级别构建压缩 JAR。
     *
     * @param executableJar  包含依赖和 {@code classpath.idx} 的 Spring Boot 可执行 JAR
     * @param applicationJar 不包含外部依赖的应用 JAR
     * @param outputJar      压缩后的可执行 JAR
     * @param level          Zstd 压缩级别
     * @throws Exception 打包失败
     */
    public static void compact(Path executableJar, Path applicationJar, Path outputJar, int level) throws Exception {
        CompactJarBuilderV2.build(executableJar, applicationJar, outputJar, level);
    }
}
