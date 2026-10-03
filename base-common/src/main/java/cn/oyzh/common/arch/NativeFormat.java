package cn.oyzh.common.arch;

/**
 * 原生库的二进制容器格式。
 *
 * <p>同一个架构在不同系统上的库格式不同，而各系统只认自己那一种：
 * Windows 只能加载 PE，macOS 只能加载 Mach-O，Linux/FreeBSD/Solaris/AIX 等只能加载 ELF。
 *
 * @author oyzh
 * @since 2026-09-29
 */
public enum NativeFormat {

    /** ELF，Linux/FreeBSD/Solaris/AIX/Android 等系统使用，对应 .so */
    ELF,

    /** PE/COFF，Windows 使用，对应 .dll */
    PE,

    /** Mach-O，macOS 使用，对应 .dylib */
    MACHO,

    /** 通用二进制（Fat/Universal Mach-O），一个文件内含多个架构 */
    FAT_MACHO,

    /** 无法识别 */
    UNKNOWN
}
