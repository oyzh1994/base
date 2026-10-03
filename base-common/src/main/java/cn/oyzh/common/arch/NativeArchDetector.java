package cn.oyzh.common.arch;

import cn.oyzh.common.system.OSUtil;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 从二进制数据（byte[]）判断原生库的目标架构与格式。
 * 支持 ELF(.so) / PE(.dll) / Mach-O(.dylib) / Fat Mach-O(Universal)。
 * <p>
 * 只需读取文件头部（约 4KB）即可判断，不需要完整文件。
 *
 * <p>已覆盖的架构：x86、x86_64、arm、arm64、ppc、ppc64(大端/小端)、riscv32、riscv64、
 * mips/mipsel/mips64/mips64el（龙芯）、loongarch32/loongarch64（龙芯）、sw64（申威）、
 * s390x、sparc、sparc64、ia64。
 *
 * @author oyzh
 * @since 2026-09-29
 */
public final class NativeArchDetector {

    /** ELF 头部中 e_machine 之前的内容长度 */
    private static final int ELF_MACHINE_MIN_LENGTH = 0x14;

    /** PE 头中 e_lfanew 的位置 */
    private static final int PE_LFANEW_OFFSET = 0x3C;

    /** PE 签名（PE\0\0）长度，签名之后紧跟 2 字节的 machine */
    private static final int PE_SIGNATURE_LENGTH = 4;

    /** Fat Mach-O 头部允许的最大架构数量 */
    private static final int MAX_FAT_ARCHES = 32;

    /** 32 位 Fat 头中每个架构信息的长度 */
    private static final int FAT_ARCH_LENGTH = 20;

    /** 64 位 Fat 头中每个架构信息的长度 */
    private static final int FAT_ARCH_64_LENGTH = 32;

    // Fat 头 magic（均为大端存放）
    private static final int FAT_MAGIC = 0xCAFEBABE;
    private static final int FAT_CIGAM = 0xBEBAFECA;
    private static final int FAT_MAGIC_64 = 0xCAFEBABF;
    private static final int FAT_CIGAM_64 = 0xBFBAFECA;

    // Mach-O magic
    private static final int MH_MAGIC = 0xFEEDFACE;
    private static final int MH_MAGIC_64 = 0xFEEDFACF;
    private static final int MH_CIGAM = 0xCEFAEDFE;
    private static final int MH_CIGAM_64 = 0xCFFAEDFE;

    // ELF e_machine
    private static final int EM_SPARC = 0x02;
    private static final int EM_386 = 0x03;
    private static final int EM_MIPS = 0x08;
    private static final int EM_MIPS_RS3_LE = 0x0A;
    private static final int EM_SPARC32PLUS = 0x12;
    private static final int EM_PPC = 0x14;
    private static final int EM_PPC64 = 0x15;
    private static final int EM_S390 = 0x16;
    private static final int EM_ARM = 0x28;
    private static final int EM_SPARCV9 = 0x2B;
    private static final int EM_IA_64 = 0x32;
    private static final int EM_X86_64 = 0x3E;
    private static final int EM_AARCH64 = 0xB7;
    private static final int EM_RISCV = 0xF3;
    private static final int EM_LOONGARCH = 0x102;
    /** binutils 分配的 EM_SW64（申威） */
    private static final int EM_SW64 = 268;
    /** 早期申威工具链在未分配编号前使用的 EM_SW_64 */
    private static final int EM_SW64_LEGACY = 0x9916;

    // PE machine
    private static final int PE_I386 = 0x014C;
    private static final int PE_R3000 = 0x0162;
    private static final int PE_R4000 = 0x0166;
    private static final int PE_R10000 = 0x0168;
    private static final int PE_WCEMIPSV2 = 0x0169;
    private static final int PE_ARM = 0x01C0;
    private static final int PE_THUMB = 0x01C2;
    private static final int PE_ARMNT = 0x01C4;
    private static final int PE_POWERPC = 0x01F0;
    private static final int PE_POWERPCFP = 0x01F1;
    private static final int PE_IA64 = 0x0200;
    private static final int PE_MIPS16 = 0x0266;
    private static final int PE_MIPSFPU = 0x0366;
    private static final int PE_MIPSFPU16 = 0x0466;
    private static final int PE_RISCV32 = 0x5032;
    private static final int PE_RISCV64 = 0x5064;
    private static final int PE_LOONGARCH32 = 0x6232;
    private static final int PE_LOONGARCH64 = 0x6264;
    private static final int PE_AMD64 = 0x8664;
    private static final int PE_ARM64 = 0xAA64;
    private static final int PE_ARM64EC = 0xA641;
    private static final int PE_ARM64X = 0xA64E;

    // Mach-O cpu type
    private static final int CPU_TYPE_X86 = 7;
    private static final int CPU_TYPE_ARM = 12;
    private static final int CPU_TYPE_SPARC = 14;
    private static final int CPU_TYPE_POWERPC = 18;
    private static final int CPU_TYPE_X86_64 = 0x01000007;
    private static final int CPU_TYPE_ARM64 = 0x0100000C;
    private static final int CPU_TYPE_POWERPC64 = 0x01000012;

    private NativeArchDetector() {
    }

    // ======================== 对外 API ========================

    /**
     * 判断这段二进制数据（可以是文件头部）是否与当前 JVM 兼容。
     * <p>
     * 架构与格式都要匹配：架构必须是当前 JVM 的架构，格式必须是当前系统能加载的格式
     * （Windows 只认 PE，macOS 只认 Mach-O，其他系统只认 ELF），否则即使架构一致也不能加载。
     *
     * @param data 二进制数据
     * @return 结果
     */
    public static boolean isCompatibleWithCurrentJvm(byte[] data) {
        if (data == null || data.length < 8) {
            return false;
        }
        if (!isFormatCompatibleWithCurrentOs(detectFormat(data))) {
            return false;
        }
        NativeArch current = NativeArch.getCurrent();
        return current != NativeArch.UNKNOWN && detectArches(data).contains(current);
    }

    /**
     * 判断原生库格式是否与当前操作系统匹配。
     * <p>
     * Windows 只能加载 PE，macOS 只能加载 Mach-O（含通用二进制），
     * 其余系统（Linux/FreeBSD/Solaris/AIX/Android 等）只能加载 ELF。
     *
     * @param format 原生库格式
     * @return 结果
     */
    public static boolean isFormatCompatibleWithCurrentOs(NativeFormat format) {
        if (format == null || format == NativeFormat.UNKNOWN) {
            return false;
        }
        if (OSUtil.isWindows()) {
            return format == NativeFormat.PE;
        }
        if (OSUtil.isMacOS()) {
            return format == NativeFormat.MACHO || format == NativeFormat.FAT_MACHO;
        }
        return format == NativeFormat.ELF;
    }

    /**
     * 解析出这段数据支持的所有架构（Fat Mach-O 会有多个），无法识别的架构不会返回
     *
     * @param data 二进制数据
     * @return 架构列表，无法识别时为空列表
     */
    public static List<NativeArch> detectArches(byte[] data) {
        if (data == null || data.length < 8) {
            return Collections.emptyList();
        }
        // ELF：7F 45 4C 46
        if ((data[0] & 0xFF) == 0x7F && data[1] == 'E'
                && data[2] == 'L' && data[3] == 'F') {
            return toArches(parseElf(data));
        }
        // PE：MZ
        if (data[0] == 'M' && data[1] == 'Z') {
            return toArches(parsePe(data));
        }
        // Mach-O / Fat Mach-O
        int magic = readIntBE(data, 0);
        if (magic == MH_MAGIC || magic == MH_MAGIC_64
                || magic == MH_CIGAM || magic == MH_CIGAM_64) {
            return toArches(parseMachO(data));
        }
        if (magic == FAT_MAGIC || magic == FAT_CIGAM
                || magic == FAT_MAGIC_64 || magic == FAT_CIGAM_64) {
            return parseFatMachO(data);
        }
        return Collections.emptyList();
    }

    /**
     * 解析这段数据的容器格式
     *
     * @param data 二进制数据
     * @return 格式，无法识别时为 {@link NativeFormat#UNKNOWN}
     */
    public static NativeFormat detectFormat(byte[] data) {
        if (data == null || data.length < 8) {
            return NativeFormat.UNKNOWN;
        }
        if ((data[0] & 0xFF) == 0x7F && data[1] == 'E'
                && data[2] == 'L' && data[3] == 'F') {
            return NativeFormat.ELF;
        }
        if (data[0] == 'M' && data[1] == 'Z') {
            return NativeFormat.PE;
        }
        int magic = readIntBE(data, 0);
        if (magic == MH_MAGIC || magic == MH_MAGIC_64
                || magic == MH_CIGAM || magic == MH_CIGAM_64) {
            return NativeFormat.MACHO;
        }
        // Fat 头必须能读出合法的架构数量，避免把 java class 文件的 CAFEBABE 当成通用二进制
        if (readFatHeader(data) != null) {
            return NativeFormat.FAT_MACHO;
        }
        return NativeFormat.UNKNOWN;
    }

    /**
     * 当前 JVM 运行时的架构（来自 os.arch，不是操作系统的架构）
     *
     * @return 架构
     */
    public static NativeArch getCurrentJvmArch() {
        return NativeArch.getCurrent();
    }

    // ======================== ELF 解析 ========================

    /**
     * 解析 ELF 的 e_machine。
     * <p>
     * 同一套指令集的大端/小端实现（如 mips64/mips64el、ppc64/ppc64le）共用同一个
     * e_machine，字长、字节序要靠 e_ident 中的 EI_CLASS、EI_DATA 区分，二者不同则不能互相加载。
     */
    private static NativeArch parseElf(byte[] d) {
        if (d.length < ELF_MACHINE_MIN_LENGTH) {
            return null;
        }
        int eiClass = d[4] & 0xFF;           // 1=32位 2=64位
        int eiData = d[5] & 0xFF;            // 1=小端 2=大端
        if (eiClass != 1 && eiClass != 2) {
            return null;
        }
        if (eiData != 1 && eiData != 2) {
            return null;
        }
        boolean is64 = eiClass == 2;
        boolean little = eiData == 1;
        ByteOrder order = little ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
        int machine = readShort(d, 0x12, order) & 0xFFFF;   // e_machine
        return elfMachineToArch(machine, is64, little);
    }

    private static NativeArch elfMachineToArch(int machine, boolean is64, boolean little) {
        return switch (machine) {
            case EM_386 -> is64 ? NativeArch.UNKNOWN : NativeArch.X86;
            case EM_X86_64 -> NativeArch.X86_64;
            // 大端 arm/arm64 没有 JDK 目标平台，不能当作小端库使用
            case EM_ARM -> (!is64 && little) ? NativeArch.ARM : NativeArch.UNKNOWN;
            case EM_AARCH64 -> (is64 && little) ? NativeArch.ARM64 : NativeArch.UNKNOWN;
            // 32 位 PowerPC 只有大端有 JDK 目标平台
            case EM_PPC -> (!is64 && !little) ? NativeArch.PPC : NativeArch.UNKNOWN;
            case EM_PPC64 -> {
                if (!is64) {
                    yield NativeArch.UNKNOWN;
                }
                yield little ? NativeArch.PPC64LE : NativeArch.PPC64;
            }
            case EM_MIPS, EM_MIPS_RS3_LE -> {
                if (is64) {
                    yield little ? NativeArch.MIPS64EL : NativeArch.MIPS64;
                }
                yield little ? NativeArch.MIPSEL : NativeArch.MIPS;
            }
            // RISC-V、LoongArch 目前只有小端实现
            case EM_RISCV -> !little ? NativeArch.UNKNOWN
                    : (is64 ? NativeArch.RISCV64 : NativeArch.RISCV32);
            case EM_LOONGARCH -> !little ? NativeArch.UNKNOWN
                    : (is64 ? NativeArch.LOONGARCH64 : NativeArch.LOONGARCH32);
            case EM_SW64, EM_SW64_LEGACY -> (is64 && little) ? NativeArch.SW64 : NativeArch.UNKNOWN;
            // 31 位的 s390 没有 JDK 目标平台
            case EM_S390 -> (is64 && !little) ? NativeArch.S390X : NativeArch.UNKNOWN;
            case EM_SPARC, EM_SPARC32PLUS -> (!is64 && !little) ? NativeArch.SPARC : NativeArch.UNKNOWN;
            case EM_SPARCV9 -> is64 ? NativeArch.SPARC64 : NativeArch.UNKNOWN;
            case EM_IA_64 -> (is64 && little) ? NativeArch.IA64 : NativeArch.UNKNOWN;
            default -> NativeArch.UNKNOWN;
        };
    }

    // ======================== PE 解析 ========================

    private static NativeArch parsePe(byte[] d) {
        if (d.length < 0x40) {
            return null;
        }
        int peOff = readIntLE(d, PE_LFANEW_OFFSET);       // e_lfanew
        if (peOff < 0 || peOff + PE_SIGNATURE_LENGTH + 2 > d.length) {
            return null;
        }
        if (d[peOff] != 'P' || d[peOff + 1] != 'E'
                || d[peOff + 2] != 0 || d[peOff + 3] != 0) {
            return null;
        }
        int machine = readShort(d, peOff + PE_SIGNATURE_LENGTH, ByteOrder.LITTLE_ENDIAN) & 0xFFFF;
        return peMachineToArch(machine);
    }

    /**
     * PE/COFF 强制小端，所以 MIPS 系列的 PE 库一律按小端处理
     */
    private static NativeArch peMachineToArch(int machine) {
        return switch (machine) {
            case PE_I386 -> NativeArch.X86;
            case PE_AMD64 -> NativeArch.X86_64;
            case PE_ARM, PE_THUMB, PE_ARMNT -> NativeArch.ARM;
            // ARM64EC/ARM64X 都是可以在 arm64 上运行的 arm64 代码
            case PE_ARM64, PE_ARM64EC, PE_ARM64X -> NativeArch.ARM64;
            case PE_IA64 -> NativeArch.IA64;
            case PE_POWERPC, PE_POWERPCFP -> NativeArch.PPC;
            case PE_R3000, PE_R4000, PE_R10000, PE_WCEMIPSV2,
                 PE_MIPS16, PE_MIPSFPU, PE_MIPSFPU16 -> NativeArch.MIPSEL;
            case PE_RISCV32 -> NativeArch.RISCV32;
            case PE_RISCV64 -> NativeArch.RISCV64;
            case PE_LOONGARCH32 -> NativeArch.LOONGARCH32;
            case PE_LOONGARCH64 -> NativeArch.LOONGARCH64;
            default -> NativeArch.UNKNOWN;
        };
    }

    // ======================== Mach-O 解析 ========================

    private static NativeArch parseMachO(byte[] d) {
        if (d.length < 8) {
            return null;
        }
        int magic = readIntBE(d, 0);
        ByteOrder order;
        if (magic == MH_MAGIC || magic == MH_MAGIC_64) {
            order = ByteOrder.BIG_ENDIAN;
        } else if (magic == MH_CIGAM || magic == MH_CIGAM_64) {
            order = ByteOrder.LITTLE_ENDIAN;
        } else {
            return null;
        }
        int cpuType = readInt(d, 4, order);
        return machoCpuToArch(cpuType);
    }

    private static List<NativeArch> parseFatMachO(byte[] d) {
        FatHeader header = readFatHeader(d);
        if (header == null) {
            return Collections.emptyList();
        }
        List<NativeArch> result = new ArrayList<>();
        int off = 8;
        for (int i = 0; i < header.archCount() && off + header.archLength() <= d.length; i++) {
            int cpuType = readInt(d, off, header.order());
            NativeArch a = machoCpuToArch(cpuType);
            if (a != NativeArch.UNKNOWN && !result.contains(a)) {
                result.add(a);
            }
            off += header.archLength();
        }
        return result;
    }

    /**
     * Fat 头信息
     *
     * @param order      头字段的字节序
     * @param archCount  架构数量
     * @param archLength 每个架构信息的长度（32 位头 20 字节，64 位头 32 字节）
     */
    private record FatHeader(ByteOrder order, int archCount, int archLength) {
    }

    /**
     * 读取 Fat Mach-O 头，非法时返回 null
     */
    private static FatHeader readFatHeader(byte[] d) {
        if (d.length < 8) {
            return null;
        }
        int magic = readIntBE(d, 0);
        ByteOrder order;
        int archLength;
        if (magic == FAT_MAGIC) {
            order = ByteOrder.BIG_ENDIAN;
            archLength = FAT_ARCH_LENGTH;
        } else if (magic == FAT_CIGAM) {
            order = ByteOrder.LITTLE_ENDIAN;
            archLength = FAT_ARCH_LENGTH;
        } else if (magic == FAT_MAGIC_64) {
            order = ByteOrder.BIG_ENDIAN;
            archLength = FAT_ARCH_64_LENGTH;
        } else if (magic == FAT_CIGAM_64) {
            order = ByteOrder.LITTLE_ENDIAN;
            archLength = FAT_ARCH_64_LENGTH;
        } else {
            return null;
        }
        int count = readInt(d, 4, order);      // nfat_arch
        if (count <= 0 || count > MAX_FAT_ARCHES) {
            return null;
        }
        return new FatHeader(order, count, archLength);
    }

    private static NativeArch machoCpuToArch(int cpuType) {
        return switch (cpuType) {
            case CPU_TYPE_X86 -> NativeArch.X86;
            case CPU_TYPE_X86_64 -> NativeArch.X86_64;
            case CPU_TYPE_ARM -> NativeArch.ARM;
            case CPU_TYPE_ARM64 -> NativeArch.ARM64;
            case CPU_TYPE_POWERPC -> NativeArch.PPC;
            case CPU_TYPE_POWERPC64 -> NativeArch.PPC64;
            // NeXTSTEP 时代的 SPARC 机器，保留识别能力
            case CPU_TYPE_SPARC -> NativeArch.SPARC;
            default -> NativeArch.UNKNOWN;
        };
    }

    // ======================== 字节读取工具 ========================

    private static List<NativeArch> toArches(NativeArch arch) {
        if (arch == null || arch == NativeArch.UNKNOWN) {
            return Collections.emptyList();
        }
        return Collections.singletonList(arch);
    }

    private static int readIntBE(byte[] d, int i) {
        return ((d[i] & 0xFF) << 24) | ((d[i + 1] & 0xFF) << 16)
                | ((d[i + 2] & 0xFF) << 8) | (d[i + 3] & 0xFF);
    }

    private static int readIntLE(byte[] d, int i) {
        return (d[i] & 0xFF) | ((d[i + 1] & 0xFF) << 8)
                | ((d[i + 2] & 0xFF) << 16) | ((d[i + 3] & 0xFF) << 24);
    }

    private static int readInt(byte[] d, int i, ByteOrder o) {
        return o == ByteOrder.BIG_ENDIAN ? readIntBE(d, i) : readIntLE(d, i);
    }

    private static int readShort(byte[] d, int i, ByteOrder o) {
        return o == ByteOrder.BIG_ENDIAN
                ? ((d[i] & 0xFF) << 8) | (d[i + 1] & 0xFF)
                : (d[i] & 0xFF) | ((d[i + 1] & 0xFF) << 8);
    }
}
