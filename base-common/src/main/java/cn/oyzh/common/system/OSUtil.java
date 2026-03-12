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

    private static Boolean isLinux;

    /**
     * 是否linux
     *
     * @return 结果
     */
    public synchronized static boolean isLinux() {
        if (isLinux == null) {
            synchronized (OSUtil.class) {
                isLinux = getOSType().contains("LINUX");
            }
        }
        return isLinux;
    }

    private static Boolean isWindows;

    /**
     * 是否windows
     *
     * @return 结果
     */
    public static boolean isWindows() {
        if (isWindows == null) {
            synchronized (OSUtil.class) {
                isWindows = getOSType().contains("WINDOWS");
            }
        }
        return isWindows;
    }

    private static Boolean isMacos;

    /**
     * 是否macos
     *
     * @return 结果
     */
    public static boolean isMacOS() {
        if (isMacos == null) {
            synchronized (OSUtil.class) {
                isMacos = getOSType().contains("MAC");
            }
        }
        return isMacos;
    }

    private static Boolean isArm32;

    /**
     * 是否arm32架构
     *
     * @return 结果
     */
    public static boolean isArm32() {
        if (isArm32 == null) {
            synchronized (OSUtil.class) {
                // 获取系统架构
                String osArch = System.getProperty("os.arch");
                isArm32 = StringUtil.containsAnyIgnoreCase(osArch, "arm");
            }
        }
        return isArm32;
    }

    private static Boolean isAarch64;

    /**
     * 是否arm64架构
     *
     * @return 结果
     */
    public static boolean isAarch64() {
        if (isAarch64 == null) {
            synchronized (OSUtil.class) {
                // 获取系统架构
                String osArch = System.getProperty("os.arch");
                isAarch64 = StringUtil.containsAnyIgnoreCase(osArch, "aarch64", "arm64");
            }
        }
        return isAarch64;
    }

    private static Boolean isX64;

    /**
     * 是否x64架构
     *
     * @return 结果
     */
    public static boolean isX64() {
        if (isX64 == null) {
            synchronized (OSUtil.class) {
                // 获取系统架构
                String osArch = System.getProperty("os.arch");
                isX64 = StringUtil.containsAnyIgnoreCase(osArch, "x86_64", "amd64");
            }
        }
        return isX64;
    }

    private static Boolean isX86;

    /**
     * 是否x86架构
     *
     * @return 结果
     */
    public static boolean isX86() {
        if (isX86 == null) {
            synchronized (OSUtil.class) {
                // 获取系统架构
                String osArch = System.getProperty("os.arch");
                isX86 = StringUtil.containsAnyIgnoreCase(osArch, "x86");
            }
        }
        return isX86;
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
