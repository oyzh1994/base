package cn.oyzh.common.system;

import cn.oyzh.common.util.StringUtil;

/**
 * 系统工具类
 *
 * @author oyzh
 * @since 2023/3/9
 */
public class OSUtil {

    /**
     * 获取系统类型
     *
     * @return 系统类型
     */
    public static String getOSType() {
        return System.getProperty("os.name").toUpperCase();
    }

    /**
     * 是否linux
     *
     * @return 结果
     */
    public static boolean isLinux() {
        return getOSType().contains("LINUX");
    }

    /**
     * 是否windows
     *
     * @return 结果
     */
    public static boolean isWindows() {
        return getOSType().contains("WINDOWS");
    }

    /**
     * 是否macos
     *
     * @return 结果
     */
    public static boolean isMacOS() {
        return getOSType().contains("MAC");
    }

    /**
     * 是否arm32架构
     *
     * @return 结果
     */
    public static boolean isArm32() {
        // 获取系统架构
        String osArch = System.getProperty("os.arch");
        return StringUtil.containsAnyIgnoreCase(osArch, "arm");
    }

    /**
     * 是否arm64架构
     *
     * @return 结果
     */
    public static boolean isAarch64() {
        // 获取系统架构
        String osArch = System.getProperty("os.arch");
        return StringUtil.containsAnyIgnoreCase(osArch, "aarch64", "arm64");
    }

    /**
     * 是否x64架构
     *
     * @return 结果
     */
    public static boolean isX64() {
        // 获取系统架构
        String osArch = System.getProperty("os.arch");
        return StringUtil.containsAnyIgnoreCase(osArch, "x86_64", "amd64");
    }

    /**
     * 是否x86架构
     *
     * @return 结果
     */
    public static boolean isX86() {
        // 获取系统架构
        String osArch = System.getProperty("os.arch");
        return StringUtil.contains(osArch, "x86");
    }

    /**
     * 获取平台架构名称
     *
     * @return 结果
     */
    public static String getArchName() {
        if (isAarch64()) {
            return "arm64";
        }
        if (isArm32()) {
            return "arm32";
        }
        if (isX64()) {
            return "amd64";
        }
        if (isX86()) {
            return "x86";
        }
        return "unknown";
    }
}
