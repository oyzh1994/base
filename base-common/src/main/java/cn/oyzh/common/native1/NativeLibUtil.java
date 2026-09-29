package cn.oyzh.common.native1;

import cn.oyzh.common.util.StringUtil;

import java.util.Locale;

/**
 * 原生库文件名的平台判断工具。
 *
 * @author oyzh
 * @since 2026-09-29
 */
public class NativeLibUtil {

    /**
     * 判断是否是原生库文件名（不限平台）
     *
     * @param name 名称（可以是文件名，也可以是 jar 内的完整条目路径）
     * @return 结果
     */
    public static boolean isNativeLibName(String name) {
        return isWindowsLib(name) || isMacosLib(name) || isLinuxLib(name) || isAixLib(name);
    }

    /**
     * 判断是否是linux/unix库，即 libfoo.so、libfoo.so.1、libfoo.so.1.2
     * <p>
     * 注意 libfoo.something 这类只是包含 .so 的名字不算
     *
     * @param name 名称（可以是文件名，也可以是 jar 内的完整条目路径）
     * @return 结果
     */
    public static boolean isLinuxLib(String name) {
        String fileName = fileName(name);
        return fileName.endsWith(".so") || fileName.contains(".so.");
    }

    /**
     * 判断是否是aix库，即 libjnidispatth.a
     *
     * @param name 名称（可以是文件名，也可以是 jar 内的完整条目路径）
     * @return 结果
     */
    public static boolean isAixLib(String name) {
        String fileName = fileName(name);
        return fileName.endsWith(".a");
    }

    /**
     * 判断是否是windows库
     *
     * @param name 名称（可以是文件名，也可以是 jar 内的完整条目路径）
     * @return 结果
     */
    public static boolean isWindowsLib(String name) {
        return StringUtil.endsWithAny(fileName(name), ".dll");
    }

    /**
     * 判断是否是macos库
     *
     * @param name 名称（可以是文件名，也可以是 jar 内的完整条目路径）
     * @return 结果
     */
    public static boolean isMacosLib(String name) {
        String fileName = fileName(name);
        return fileName.endsWith(".dylib") || fileName.endsWith(".jnilib");
    }

    /**
     * 取名称中的文件名部分（去掉目录）并转为小写
     *
     * @param name 名称
     * @return 文件名，name 为空时返回空字符串
     */
    private static String fileName(String name) {
        if (name == null) {
            return "";
        }
        String lower = name.toLowerCase(Locale.ROOT);
        int separator = Math.max(lower.lastIndexOf('/'), lower.lastIndexOf('\\'));
        return separator >= 0 ? lower.substring(separator + 1) : lower;
    }
}
