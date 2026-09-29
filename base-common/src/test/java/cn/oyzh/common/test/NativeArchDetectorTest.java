package cn.oyzh.common.test;

import cn.oyzh.common.native1.NativeArch;
import cn.oyzh.common.native1.NativeArchDetector;
import cn.oyzh.common.native1.NativeFormat;
import cn.oyzh.common.native1.NativeLibUtil;
import cn.oyzh.common.system.OSUtil;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import java.nio.ByteOrder;
import java.util.List;

/**
 * 原生库架构与平台判断的测试。
 * <p>
 * 用例中的二进制数据都是按规范手工拼出的文件头，不依赖任何外部文件。
 *
 * @author oyzh
 * @since 2026-09-29
 */
public class NativeArchDetectorTest {

    // ELF e_machine
    private static final int EM_SPARC = 0x02;
    private static final int EM_386 = 0x03;
    private static final int EM_MIPS = 0x08;
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
    private static final int EM_SW64 = 268;

    // PE machine
    private static final int PE_I386 = 0x014C;
    private static final int PE_R4000 = 0x0166;
    private static final int PE_ARM = 0x01C0;
    private static final int PE_ARMNT = 0x01C4;
    private static final int PE_IA64 = 0x0200;
    private static final int PE_POWERPC = 0x01F0;
    private static final int PE_RISCV32 = 0x5032;
    private static final int PE_RISCV64 = 0x5064;
    private static final int PE_LOONGARCH32 = 0x6232;
    private static final int PE_LOONGARCH64 = 0x6264;
    private static final int PE_AMD64 = 0x8664;
    private static final int PE_ARM64 = 0xAA64;
    private static final int PE_ARM64EC = 0xA641;

    // Mach-O cpu type
    private static final int CPU_TYPE_X86 = 7;
    private static final int CPU_TYPE_ARM = 12;
    private static final int CPU_TYPE_POWERPC = 18;
    private static final int CPU_TYPE_POWERPC64 = 0x01000012;
    private static final int CPU_TYPE_X86_64 = 0x01000007;
    private static final int CPU_TYPE_ARM64 = 0x0100000C;

    private static final int FAT_MAGIC = 0xCAFEBABE;
    private static final int FAT_MAGIC_64 = 0xCAFEBABF;

    @Test
    public void testElfArch() {
        Assert.assertEquals(List.of(NativeArch.X86), NativeArchDetector.detectArches(elf(EM_386, false, true)));
        Assert.assertEquals(List.of(NativeArch.X86_64), NativeArchDetector.detectArches(elf(EM_X86_64, true, true)));
        Assert.assertEquals(List.of(NativeArch.ARM), NativeArchDetector.detectArches(elf(EM_ARM, false, true)));
        Assert.assertEquals(List.of(NativeArch.ARM64), NativeArchDetector.detectArches(elf(EM_AARCH64, true, true)));
        Assert.assertEquals(List.of(NativeArch.IA64), NativeArchDetector.detectArches(elf(EM_IA_64, true, true)));
        Assert.assertEquals(NativeFormat.ELF, NativeArchDetector.detectFormat(elf(EM_X86_64, true, true)));
    }

    /**
     * mips、loongarch、sw64 等国产化平台
     */
    @Test
    public void testElfDomesticArch() {
        Assert.assertEquals(List.of(NativeArch.MIPS), NativeArchDetector.detectArches(elf(EM_MIPS, false, false)));
        Assert.assertEquals(List.of(NativeArch.MIPSEL), NativeArchDetector.detectArches(elf(EM_MIPS, false, true)));
        Assert.assertEquals(List.of(NativeArch.MIPS64), NativeArchDetector.detectArches(elf(EM_MIPS, true, false)));
        Assert.assertEquals(List.of(NativeArch.MIPS64EL), NativeArchDetector.detectArches(elf(EM_MIPS, true, true)));
        Assert.assertEquals(List.of(NativeArch.LOONGARCH64),
                NativeArchDetector.detectArches(elf(EM_LOONGARCH, true, true)));
        Assert.assertEquals(List.of(NativeArch.LOONGARCH32),
                NativeArchDetector.detectArches(elf(EM_LOONGARCH, false, true)));
        Assert.assertEquals(List.of(NativeArch.SW64), NativeArchDetector.detectArches(elf(EM_SW64, true, true)));
        // 早期申威工具链使用的未分配编号
        Assert.assertEquals(List.of(NativeArch.SW64), NativeArchDetector.detectArches(elf(0x9916, true, true)));
    }

    /**
     * 同一套指令集的大端/小端、32/64 位必须区分
     */
    @Test
    public void testElfEndianAndBits() {
        Assert.assertEquals(List.of(NativeArch.PPC64), NativeArchDetector.detectArches(elf(EM_PPC64, true, false)));
        Assert.assertEquals(List.of(NativeArch.PPC64LE), NativeArchDetector.detectArches(elf(EM_PPC64, true, true)));
        Assert.assertEquals(List.of(NativeArch.PPC), NativeArchDetector.detectArches(elf(EM_PPC, false, false)));
        Assert.assertEquals(List.of(NativeArch.S390X), NativeArchDetector.detectArches(elf(EM_S390, true, false)));
        Assert.assertEquals(List.of(NativeArch.SPARC), NativeArchDetector.detectArches(elf(EM_SPARC, false, false)));
        Assert.assertEquals(List.of(NativeArch.SPARC64), NativeArchDetector.detectArches(elf(EM_SPARCV9, true, false)));
        Assert.assertEquals(List.of(NativeArch.RISCV32), NativeArchDetector.detectArches(elf(EM_RISCV, false, true)));
        Assert.assertEquals(List.of(NativeArch.RISCV64), NativeArchDetector.detectArches(elf(EM_RISCV, true, true)));
    }

    /**
     * 无法加载的组合要识别为未知，而不是硬套成某个架构：大端 arm、32 位 s390、非 ELF 头等
     */
    @Test
    public void testElfUnsupportedCombination() {
        Assert.assertTrue(NativeArchDetector.detectArches(elf(EM_ARM, false, false)).isEmpty());
        Assert.assertTrue(NativeArchDetector.detectArches(elf(EM_S390, false, false)).isEmpty());
        Assert.assertTrue(NativeArchDetector.detectArches(elf(0xFFFF, true, true)).isEmpty());
        // 非 ELF 数据
        Assert.assertTrue(NativeArchDetector.detectArches(new byte[] {1, 2, 3, 4, 5, 6, 7, 8}).isEmpty());
        Assert.assertTrue(NativeArchDetector.detectArches(null).isEmpty());
        Assert.assertTrue(NativeArchDetector.detectArches(new byte[0]).isEmpty());
    }

    @Test
    public void testPeArch() {
        Assert.assertEquals(List.of(NativeArch.X86), NativeArchDetector.detectArches(pe(PE_I386)));
        Assert.assertEquals(List.of(NativeArch.X86_64), NativeArchDetector.detectArches(pe(PE_AMD64)));
        Assert.assertEquals(List.of(NativeArch.ARM), NativeArchDetector.detectArches(pe(PE_ARM)));
        Assert.assertEquals(List.of(NativeArch.ARM), NativeArchDetector.detectArches(pe(PE_ARMNT)));
        Assert.assertEquals(List.of(NativeArch.ARM64), NativeArchDetector.detectArches(pe(PE_ARM64)));
        Assert.assertEquals(List.of(NativeArch.ARM64), NativeArchDetector.detectArches(pe(PE_ARM64EC)));
        Assert.assertEquals(List.of(NativeArch.IA64), NativeArchDetector.detectArches(pe(PE_IA64)));
        Assert.assertEquals(List.of(NativeArch.PPC), NativeArchDetector.detectArches(pe(PE_POWERPC)));
        // PE 恒为小端，所以 MIPS/龙芯/riscv 的 PE 只可能是小端
        Assert.assertEquals(List.of(NativeArch.MIPSEL), NativeArchDetector.detectArches(pe(PE_R4000)));
        Assert.assertEquals(List.of(NativeArch.RISCV32), NativeArchDetector.detectArches(pe(PE_RISCV32)));
        Assert.assertEquals(List.of(NativeArch.RISCV64), NativeArchDetector.detectArches(pe(PE_RISCV64)));
        Assert.assertEquals(List.of(NativeArch.LOONGARCH32), NativeArchDetector.detectArches(pe(PE_LOONGARCH32)));
        Assert.assertEquals(List.of(NativeArch.LOONGARCH64), NativeArchDetector.detectArches(pe(PE_LOONGARCH64)));
        Assert.assertEquals(NativeFormat.PE, NativeArchDetector.detectFormat(pe(PE_AMD64)));
        // 非法 PE 偏移
        byte[] broken = pe(PE_AMD64);
        broken[0x3C] = (byte) 0xFF;
        broken[0x3D] = (byte) 0xFF;
        Assert.assertTrue(NativeArchDetector.detectArches(broken).isEmpty());
    }

    @Test
    public void testMachOArch() {
        Assert.assertEquals(List.of(NativeArch.X86), NativeArchDetector.detectArches(macho(CPU_TYPE_X86, true)));
        Assert.assertEquals(List.of(NativeArch.X86_64), NativeArchDetector.detectArches(macho(CPU_TYPE_X86_64, true)));
        Assert.assertEquals(List.of(NativeArch.ARM), NativeArchDetector.detectArches(macho(CPU_TYPE_ARM, true)));
        Assert.assertEquals(List.of(NativeArch.ARM64), NativeArchDetector.detectArches(macho(CPU_TYPE_ARM64, true)));
        Assert.assertEquals(List.of(NativeArch.PPC64), NativeArchDetector.detectArches(macho(CPU_TYPE_POWERPC64, true)));
        Assert.assertEquals(NativeFormat.MACHO, NativeArchDetector.detectFormat(macho(CPU_TYPE_ARM64, true)));
        // 大端存放的 mach-o 头
        Assert.assertEquals(List.of(NativeArch.PPC), NativeArchDetector.detectArches(macho(CPU_TYPE_POWERPC, false)));
    }

    /**
     * 通用二进制（Fat/Universal）内含多个架构
     */
    @Test
    public void testFatMachO() {
        byte[] fat = fat(false, true, CPU_TYPE_X86_64, CPU_TYPE_ARM64);
        Assert.assertEquals(List.of(NativeArch.X86_64, NativeArch.ARM64), NativeArchDetector.detectArches(fat));
        Assert.assertEquals(NativeFormat.FAT_MACHO, NativeArchDetector.detectFormat(fat));

        byte[] fat64 = fat(true, true, CPU_TYPE_ARM64);
        Assert.assertEquals(List.of(NativeArch.ARM64), NativeArchDetector.detectArches(fat64));
        Assert.assertEquals(NativeFormat.FAT_MACHO, NativeArchDetector.detectFormat(fat64));

        // 架构数量非法时不能当成通用二进制（java class 文件也是 CAFEBABE 开头）
        byte[] classFile = new byte[64];
        classFile[0] = (byte) 0xCA;
        classFile[1] = (byte) 0xFE;
        classFile[2] = (byte) 0xBA;
        classFile[3] = (byte) 0xBE;
        classFile[7] = 0x41;    // 主版本号 65
        Assert.assertEquals(NativeFormat.UNKNOWN, NativeArchDetector.detectFormat(classFile));
        Assert.assertTrue(NativeArchDetector.detectArches(classFile).isEmpty());
    }

    /**
     * os.arch 到架构的映射，含各平台的常见别名
     */
    @Test
    public void testOsArchMapping() {
        Assert.assertEquals(NativeArch.X86_64, NativeArch.ofOsArch("amd64"));
        Assert.assertEquals(NativeArch.X86_64, NativeArch.ofOsArch("x86_64"));
        Assert.assertEquals(NativeArch.X86_64, NativeArch.ofOsArch("AMD64"));
        Assert.assertEquals(NativeArch.X86, NativeArch.ofOsArch("i686"));
        Assert.assertEquals(NativeArch.ARM64, NativeArch.ofOsArch("aarch64"));
        Assert.assertEquals(NativeArch.ARM, NativeArch.ofOsArch("armv7l"));
        Assert.assertEquals(NativeArch.MIPS64EL, NativeArch.ofOsArch("mips64el"));
        Assert.assertEquals(NativeArch.MIPSEL, NativeArch.ofOsArch("mipsel"));
        Assert.assertEquals(NativeArch.LOONGARCH64, NativeArch.ofOsArch("loongarch64"));
        Assert.assertEquals(NativeArch.SW64, NativeArch.ofOsArch("sw_64"));
        Assert.assertEquals(NativeArch.SW64, NativeArch.ofOsArch("sw64"));
        Assert.assertEquals(NativeArch.PPC64LE, NativeArch.ofOsArch("ppc64le"));
        Assert.assertEquals(NativeArch.PPC64, NativeArch.ofOsArch("ppc64"));
        Assert.assertEquals(NativeArch.S390X, NativeArch.ofOsArch("s390x"));
        Assert.assertEquals(NativeArch.SPARC64, NativeArch.ofOsArch("sparcv9"));
        Assert.assertEquals(NativeArch.RISCV64, NativeArch.ofOsArch("riscv64"));
        Assert.assertEquals(NativeArch.RISCV32, NativeArch.ofOsArch("riscv32"));
        // 厂商/版本后缀的写法
        Assert.assertEquals(NativeArch.LOONGARCH64, NativeArch.ofOsArch("loongarch64v1.0"));
        Assert.assertEquals(NativeArch.SW64, NativeArch.ofOsArch("sw_64-unknown-linux-gnu"));
        Assert.assertEquals(NativeArch.MIPS64EL, NativeArch.ofOsArch("loongson64"));
        // 无法识别
        Assert.assertEquals(NativeArch.UNKNOWN, NativeArch.ofOsArch(null));
        Assert.assertEquals(NativeArch.UNKNOWN, NativeArch.ofOsArch(""));
        Assert.assertEquals(NativeArch.UNKNOWN, NativeArch.ofOsArch("unknown-arch"));
    }

    @Test
    public void testArchMeta() {
        Assert.assertTrue(NativeArch.X86_64.is64Bit());
        Assert.assertFalse(NativeArch.X86.is64Bit());
        Assert.assertEquals(64, NativeArch.ARM64.getBits());
        Assert.assertEquals(ByteOrder.LITTLE_ENDIAN, NativeArch.MIPS64EL.getByteOrder());
        Assert.assertEquals(ByteOrder.BIG_ENDIAN, NativeArch.MIPS64.getByteOrder());
        Assert.assertEquals(ByteOrder.BIG_ENDIAN, NativeArch.S390X.getByteOrder());
        Assert.assertEquals("loongarch64", NativeArch.LOONGARCH64.getId());
        Assert.assertEquals("sw64", NativeArch.SW64.getId());
        Assert.assertEquals("x86_64", NativeArch.X86_64.getId());
    }

    /**
     * 当前平台的库必须判定为兼容；架构不符或格式不符的必须排除
     */
    @Test
    public void testCurrentPlatform() {
        NativeArch current = NativeArchDetector.getCurrentJvmArch();
        Assume.assumeTrue("当前架构不在测试覆盖范围内: " + current, current != NativeArch.UNKNOWN);

        // 当前架构 + 当前系统的库格式
        byte[] own = headerForCurrentOsFormat(current);
        Assume.assumeNotNull(own);
        Assert.assertTrue("当前平台的库应判定为兼容: " + current,
                NativeArchDetector.isCompatibleWithCurrentJvm(own));

        // 换一个架构，格式仍与当前系统一致，应当被排除
        NativeArch other = current == NativeArch.X86_64 ? NativeArch.ARM64 : NativeArch.X86_64;
        byte[] otherData = headerForCurrentOsFormat(other);
        Assume.assumeNotNull(otherData);
        Assert.assertFalse("架构不符的库应被排除: " + other,
                NativeArchDetector.isCompatibleWithCurrentJvm(otherData));

        // 格式与当前系统不符时，即使架构一致也要排除
        boolean windows = OSUtil.isWindows();
        boolean macos = OSUtil.isMacOS();
        if (!windows && !macos) {
            Assert.assertFalse(NativeArchDetector.isCompatibleWithCurrentJvm(peHeader(current)));
            Assert.assertFalse(NativeArchDetector.isCompatibleWithCurrentJvm(machoHeader(current)));
        } else if (windows) {
            Assert.assertFalse(NativeArchDetector.isCompatibleWithCurrentJvm(elfHeader(current)));
            Assert.assertFalse(NativeArchDetector.isCompatibleWithCurrentJvm(machoHeader(current)));
        } else {
            Assert.assertFalse(NativeArchDetector.isCompatibleWithCurrentJvm(elfHeader(current)));
            Assert.assertFalse(NativeArchDetector.isCompatibleWithCurrentJvm(peHeader(current)));
            // 通用二进制里含有当前架构，仍然可用
            Assert.assertTrue(NativeArchDetector.isCompatibleWithCurrentJvm(
                    fat(false, true, machoOf(other), machoOf(current))));
        }
    }

    @Test
    public void testNativeLibName() {
        Assert.assertTrue(NativeLibUtil.isNativeLibName("libfoo.so"));
        Assert.assertTrue(NativeLibUtil.isNativeLibName("libfoo.so.1"));
        Assert.assertTrue(NativeLibUtil.isNativeLibName("libfoo.so.1.2.3"));
        Assert.assertTrue(NativeLibUtil.isNativeLibName("META-INF/native/libfoo.so"));
        Assert.assertTrue(NativeLibUtil.isNativeLibName("C:\\lib\\foo.dll"));
        Assert.assertTrue(NativeLibUtil.isNativeLibName("libfoo.dylib"));
        Assert.assertTrue(NativeLibUtil.isNativeLibName("libfoo.jnilib"));
        Assert.assertFalse(NativeLibUtil.isNativeLibName("foo.jar"));
        Assert.assertFalse(NativeLibUtil.isNativeLibName("foo.something"));
        Assert.assertFalse(NativeLibUtil.isNativeLibName(null));

        // 平台判断不能互相串台
        Assert.assertTrue(NativeLibUtil.isLinuxLib("libfoo.so.1"));
        Assert.assertFalse(NativeLibUtil.isLinuxLib("foo.dll"));
        Assert.assertFalse(NativeLibUtil.isLinuxLib("foo.dylib"));
        Assert.assertFalse(NativeLibUtil.isLinuxLib("foo.something"));
        Assert.assertTrue(NativeLibUtil.isWindowsLib("foo.dll"));
        Assert.assertFalse(NativeLibUtil.isWindowsLib("libfoo.so"));
        Assert.assertTrue(NativeLibUtil.isMacosLib("foo.dylib"));
        Assert.assertFalse(NativeLibUtil.isMacosLib("libfoo.so"));
        Assert.assertFalse(NativeLibUtil.isMacosLib("foo.dll"));
    }

    // ======================== 测试数据构造 ========================

    /**
     * ELF 文件头
     */
    private static byte[] elf(int machine, boolean is64, boolean little) {
        byte[] d = new byte[64];
        d[0] = 0x7F;
        d[1] = 'E';
        d[2] = 'L';
        d[3] = 'F';
        d[4] = (byte) (is64 ? 2 : 1);       // EI_CLASS
        d[5] = (byte) (little ? 1 : 2);     // EI_DATA
        d[6] = 1;                           // EI_VERSION
        putShort(d, 0x12, machine, little); // e_machine
        return d;
    }

    /**
     * PE 文件头
     */
    private static byte[] pe(int machine) {
        byte[] d = new byte[0x100];
        d[0] = 'M';
        d[1] = 'Z';
        int peOff = 0x80;
        d[0x3C] = (byte) peOff;             // e_lfanew，小端
        d[peOff] = 'P';
        d[peOff + 1] = 'E';
        d[peOff + 4] = (byte) (machine & 0xFF);
        d[peOff + 5] = (byte) ((machine >>> 8) & 0xFF);
        return d;
    }

    /**
     * Mach-O 文件头
     */
    private static byte[] macho(int cpuType, boolean little) {
        byte[] d = new byte[32];
        putInt(d, 0, 0xFEEDFACF, little);   // MH_MAGIC_64
        putInt(d, 4, cpuType, little);
        return d;
    }

    /**
     * Fat(Universal) Mach-O 文件头
     */
    private static byte[] fat(boolean fat64, boolean little, int... cpuTypes) {
        int entryLength = fat64 ? 32 : 20;
        byte[] d = new byte[8 + entryLength * cpuTypes.length];
        putInt(d, 0, fat64 ? FAT_MAGIC_64 : FAT_MAGIC, little);
        putInt(d, 4, cpuTypes.length, little);
        for (int i = 0; i < cpuTypes.length; i++) {
            putInt(d, 8 + entryLength * i, cpuTypes[i], little);
        }
        return d;
    }

    private static byte[] elfHeader(NativeArch arch) {
        int[] conf = elfOf(arch);
        return conf == null ? null : elf(conf[0], conf[1] == 1, conf[2] == 1);
    }

    private static byte[] peHeader(NativeArch arch) {
        int machine = peOf(arch);
        return machine < 0 ? null : pe(machine);
    }

    private static byte[] machoHeader(NativeArch arch) {
        int cpuType = machoOf(arch);
        return cpuType < 0 ? null : macho(cpuType, true);
    }

    /**
     * 按当前系统的库格式生成指定架构的头部数据
     */
    private static byte[] headerForCurrentOsFormat(NativeArch arch) {
        if (OSUtil.isWindows()) {
            return peHeader(arch);
        }
        if (OSUtil.isMacOS()) {
            return machoHeader(arch);
        }
        return elfHeader(arch);
    }

    /**
     * 架构对应的 ELF 信息：{e_machine, 是否64位, 是否小端}，返回 null 表示测试未覆盖
     */
    private static int[] elfOf(NativeArch arch) {
        return switch (arch) {
            case X86 -> new int[] {EM_386, 0, 1};
            case X86_64 -> new int[] {EM_X86_64, 1, 1};
            case ARM -> new int[] {EM_ARM, 0, 1};
            case ARM64 -> new int[] {EM_AARCH64, 1, 1};
            case PPC -> new int[] {EM_PPC, 0, 0};
            case PPC64 -> new int[] {EM_PPC64, 1, 0};
            case PPC64LE -> new int[] {EM_PPC64, 1, 1};
            case MIPS -> new int[] {EM_MIPS, 0, 0};
            case MIPSEL -> new int[] {EM_MIPS, 0, 1};
            case MIPS64 -> new int[] {EM_MIPS, 1, 0};
            case MIPS64EL -> new int[] {EM_MIPS, 1, 1};
            case RISCV32 -> new int[] {EM_RISCV, 0, 1};
            case RISCV64 -> new int[] {EM_RISCV, 1, 1};
            case LOONGARCH32 -> new int[] {EM_LOONGARCH, 0, 1};
            case LOONGARCH64 -> new int[] {EM_LOONGARCH, 1, 1};
            case SW64 -> new int[] {EM_SW64, 1, 1};
            case S390X -> new int[] {EM_S390, 1, 0};
            case SPARC -> new int[] {EM_SPARC, 0, 0};
            case SPARC64 -> new int[] {EM_SPARCV9, 1, 0};
            case IA64 -> new int[] {EM_IA_64, 1, 1};
            default -> null;
        };
    }

    /**
     * 架构对应的 PE machine，返回 -1 表示测试未覆盖
     */
    private static int peOf(NativeArch arch) {
        return switch (arch) {
            case X86 -> PE_I386;
            case X86_64 -> PE_AMD64;
            case ARM -> PE_ARM;
            case ARM64 -> PE_ARM64;
            case IA64 -> PE_IA64;
            case PPC -> PE_POWERPC;
            case MIPSEL -> PE_R4000;
            case RISCV32 -> PE_RISCV32;
            case RISCV64 -> PE_RISCV64;
            case LOONGARCH32 -> PE_LOONGARCH32;
            case LOONGARCH64 -> PE_LOONGARCH64;
            default -> -1;
        };
    }

    /**
     * 架构对应的 Mach-O cpu type，返回 -1 表示测试未覆盖
     */
    private static int machoOf(NativeArch arch) {
        return switch (arch) {
            case X86 -> CPU_TYPE_X86;
            case X86_64 -> CPU_TYPE_X86_64;
            case ARM -> CPU_TYPE_ARM;
            case ARM64 -> CPU_TYPE_ARM64;
            case PPC -> CPU_TYPE_POWERPC;
            case PPC64 -> CPU_TYPE_POWERPC64;
            default -> -1;
        };
    }

    private static void putShort(byte[] d, int offset, int value, boolean little) {
        if (little) {
            d[offset] = (byte) (value & 0xFF);
            d[offset + 1] = (byte) ((value >>> 8) & 0xFF);
        } else {
            d[offset] = (byte) ((value >>> 8) & 0xFF);
            d[offset + 1] = (byte) (value & 0xFF);
        }
    }

    private static void putInt(byte[] d, int offset, int value, boolean little) {
        if (little) {
            d[offset] = (byte) (value & 0xFF);
            d[offset + 1] = (byte) ((value >>> 8) & 0xFF);
            d[offset + 2] = (byte) ((value >>> 16) & 0xFF);
            d[offset + 3] = (byte) ((value >>> 24) & 0xFF);
        } else {
            d[offset] = (byte) ((value >>> 24) & 0xFF);
            d[offset + 1] = (byte) ((value >>> 16) & 0xFF);
            d[offset + 2] = (byte) ((value >>> 8) & 0xFF);
            d[offset + 3] = (byte) (value & 0xFF);
        }
    }
}
