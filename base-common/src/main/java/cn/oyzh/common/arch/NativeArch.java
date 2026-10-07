package cn.oyzh.common.arch;

import java.nio.ByteOrder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 归一化后的架构枚举。
 *
 * <p>每个值都带有字长与字节序，因为字长或字节序不同的动态库无法互相加载：
 * ppc64(大端) 与 ppc64le(小端)、mips64(大端) 与 mips64el(小端) 必须区分对待。
 *
 * <p>未纳入的架构（SH、Alpha、PA-RISC、CSKY、XTENSA、E2K 等）当前没有任何
 * JDK 目标平台，判定为 {@link #UNKNOWN}，即“无法确认可用”。如需支持，
 * 只需在枚举中增加值，并在 {@link NativeArchDetector} 的三个解析器中各加一个分支。
 *
 * @author oyzh
 * @since 2026-09-29
 */
public enum NativeArch {

    /** Intel x86 32 位，小端 */
    X86("x86", 32, ByteOrder.LITTLE_ENDIAN, "x86", "i386", "i486", "i586", "i686", "ia32", "x86_32"),

    /** Intel x86 64 位（amd64/x86_64），小端 */
    X86_64("x86_64", 64, ByteOrder.LITTLE_ENDIAN, "x86_64", "amd64", "x64", "x86-64", "amd64e"),

    /** ARM 32 位，小端 */
    ARM("arm", 32, ByteOrder.LITTLE_ENDIAN, "arm", "arm32", "armv6", "armv6l", "armv7", "armv7l", "armhf", "armel"),

    /** ARM 64 位（aarch64），小端 */
    ARM64("arm64", 64, ByteOrder.LITTLE_ENDIAN, "aarch64", "arm64", "armv8"),

    /** PowerPC 32 位，大端 */
    PPC("ppc", 32, ByteOrder.BIG_ENDIAN, "ppc", "ppc32", "powerpc", "powerpc32"),

    /** PowerPC 64 位，大端 */
    PPC64("ppc64", 64, ByteOrder.BIG_ENDIAN, "ppc64", "ppc64be", "powerpc64", "powerpc64be"),

    /** PowerPC 64 位，小端 */
    PPC64LE("ppc64le", 64, ByteOrder.LITTLE_ENDIAN, "ppc64le", "ppc64el", "powerpc64le", "powerpc64el"),

    /** MIPS 32 位，大端 */
    MIPS("mips", 32, ByteOrder.BIG_ENDIAN, "mips", "mips32", "mips32be", "mipsbe"),

    /** MIPS 32 位，小端 */
    MIPSEL("mipsel", 32, ByteOrder.LITTLE_ENDIAN, "mipsel", "mips32el", "mips32le", "mipsle"),

    /** MIPS 64 位，大端 */
    MIPS64("mips64", 64, ByteOrder.BIG_ENDIAN, "mips64", "mips64be"),

    /** MIPS 64 位，小端（龙芯 3 系列等） */
    MIPS64EL("mips64el", 64, ByteOrder.LITTLE_ENDIAN, "mips64el", "mips64le", "loongson64"),

    /** RISC-V 32 位，小端 */
    RISCV32("riscv32", 32, ByteOrder.LITTLE_ENDIAN, "riscv32"),

    /** RISC-V 64 位，小端 */
    RISCV64("riscv64", 64, ByteOrder.LITTLE_ENDIAN, "riscv64"),

    /** LoongArch 32 位，小端 */
    LOONGARCH32("loongarch32", 32, ByteOrder.LITTLE_ENDIAN, "loongarch32", "loong32"),

    /** LoongArch 64 位（龙芯 3A5000 及以后），小端 */
    LOONGARCH64("loongarch64", 64, ByteOrder.LITTLE_ENDIAN, "loongarch64", "loong64"),

    /** 申威 SW64，64 位，小端 */
    SW64("sw64", 64, ByteOrder.LITTLE_ENDIAN, "sw64", "sw_64", "sunway"),

    /** IBM S/390 64 位（s390x），大端 */
    S390X("s390x", 64, ByteOrder.BIG_ENDIAN, "s390x", "s390"),

    /** SPARC 32 位，大端 */
    SPARC("sparc", 32, ByteOrder.BIG_ENDIAN, "sparc", "sparc32"),

    /** SPARC 64 位（sparcv9），大端 */
    SPARC64("sparc64", 64, ByteOrder.BIG_ENDIAN, "sparcv9", "sparc64"),

    /** Intel Itanium（ia64），小端 */
    IA64("ia64", 64, ByteOrder.LITTLE_ENDIAN, "ia64", "itanium"),

    /** 无法识别 */
    UNKNOWN("unknown", 0, ByteOrder.nativeOrder());

    /** os.arch 取值到架构的查找表（含枚举名与全部别名） */
    private static final Map<String, NativeArch> OS_ARCH_LOOKUP;

    static {
        Map<String, NativeArch> lookup = new HashMap<>();
        for (NativeArch arch : values()) {
            lookup.put(arch.name().toLowerCase(Locale.ROOT), arch);
            for (String alias : arch.aliases) {
                lookup.put(alias, arch);
            }
        }
        OS_ARCH_LOOKUP = Collections.unmodifiableMap(lookup);
    }

    /** 架构标识 */
    private final String id;

    /** 字长（32/64） */
    private final int bits;

    /** 字节序 */
    private final ByteOrder byteOrder;

    /** 架构别名列表 */
    private final List<String> aliases;

    /**
     * 构造架构枚举
     *
     * @param id        架构标识
     * @param bits      字长
     * @param byteOrder 字节序
     * @param aliases   架构别名
     */
    NativeArch(String id, int bits, ByteOrder byteOrder, String... aliases) {
        this.id = id;
        this.bits = bits;
        this.byteOrder = byteOrder;
        this.aliases = List.of(aliases);
    }

    /**
     * 架构标识，取值与常见 os.arch 一致（如 x86_64、mips64el、loongarch64），
     * 可用于拼接原生库目录名或日志输出
     *
     * @return 架构标识
     */
    public String getId() {
        return this.id;
    }

    /**
     * 字长（32/64），{@link #UNKNOWN} 为 0
     *
     * @return 字长
     */
    public int getBits() {
        return this.bits;
    }

    /**
     * 是否 64 位
     *
     * @return 结果
     */
    public boolean is64Bit() {
        return this.bits == 64;
    }

    /**
     * 字节序
     *
     * @return 字节序
     */
    public ByteOrder getByteOrder() {
        return this.byteOrder;
    }

    /**
     * 架构别名（小写，均为实际出现过的 os.arch 取值）
     *
     * @return 别名
     */
    public List<String> getAliases() {
        return this.aliases;
    }

    /**
     * 由 os.arch 的取值解析架构，无法识别时返回 {@link #UNKNOWN}
     *
     * @param osArch os.arch 的取值，如 aarch64、mips64el、sw_64
     * @return 架构
     */
    public static NativeArch ofOsArch(String osArch) {
        if (osArch == null) {
            return UNKNOWN;
        }
        String key = osArch.trim().toLowerCase(Locale.ROOT);
        if (key.isEmpty()) {
            return UNKNOWN;
        }
        NativeArch arch = OS_ARCH_LOOKUP.get(key);
        return arch != null ? arch : matchLoosely(key);
    }

    /**
     * 当前 JVM 运行时的架构（来自 os.arch，不是操作系统的架构）
     *
     * @return 架构
     */
    public static NativeArch getCurrent() {
        return ofOsArch(System.getProperty("os.arch", ""));
    }

    /**
     * 兜底匹配：处理带厂商后缀、版本后缀的取值，如 loongarch64v1.0、armv7a、riscv64gc、
     * sw_64-unknown-linux-gnu
     */
    private static NativeArch matchLoosely(String key) {
        if (key.startsWith("loongarch") || key.startsWith("loong")) {
            return key.contains("32") ? LOONGARCH32 : LOONGARCH64;
        }
        if (key.startsWith("sw64") || key.startsWith("sw_64") || key.startsWith("sunway")) {
            return SW64;
        }
        if (key.startsWith("mips")) {
            boolean is64 = key.contains("64");
            boolean little = key.contains("el") || key.contains("le");
            if (is64) {
                return little ? MIPS64EL : MIPS64;
            }
            return little ? MIPSEL : MIPS;
        }
        if (key.startsWith("riscv")) {
            return key.contains("32") ? RISCV32 : RISCV64;
        }
        if (key.startsWith("aarch64") || key.startsWith("arm64")) {
            return ARM64;
        }
        if (key.startsWith("arm")) {
            return ARM;
        }
        if (key.startsWith("ppc") || key.startsWith("powerpc")) {
            if (key.contains("64")) {
                return (key.contains("le") || key.contains("el")) ? PPC64LE : PPC64;
            }
            return PPC;
        }
        if (key.startsWith("s390")) {
            return S390X;
        }
        if (key.startsWith("sparc")) {
            return (key.contains("v9") || key.contains("64")) ? SPARC64 : SPARC;
        }
        if (key.startsWith("ia64") || key.startsWith("itanium")) {
            return IA64;
        }
        if (key.startsWith("amd") || key.startsWith("x86") || key.startsWith("x64")
                || key.startsWith("i386") || key.startsWith("i686")) {
            return (key.contains("64") || key.startsWith("amd")) ? X86_64 : X86;
        }
        return UNKNOWN;
    }
}
