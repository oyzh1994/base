# cn.oyzh.common.system

## OSUtil

- 职责：操作系统与 CPU 架构判断工具类，基于 `os.name`/`os.arch` 判定并缓存结果。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| isLinux | Boolean | linux 判断结果缓存 |
| isWindows | Boolean | windows 判断结果缓存 |
| isWindowsNT | Boolean | Windows NT 判断结果缓存 |
| isWindows95 | Boolean | Windows 95/98 判断结果缓存 |
| isOS2 | Boolean | OS/2 判断结果缓存 |
| isMacos | Boolean | macOS 判断结果缓存 |
| isMacosX | Boolean | macOS X 判断结果缓存 |
| isUnix | Boolean | unix 系判断结果缓存 |
| isAix | Boolean | aix 判断结果缓存 |
| isArm32 | Boolean | arm32 架构判断结果缓存 |
| isAarch64 | Boolean | arm64 架构判断结果缓存 |
| isX64 | Boolean | x64 架构判断结果缓存 |
| isX86 | Boolean | x86 架构判断结果缓存 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String getOSType()` | 获取系统类型 | `os.name` 转大写 |
| `boolean isLinux()` | 是否 Linux | 缓存判断，`getOSType().contains("LINUX")` |
| `boolean isWindows()` | 是否 Windows | 缓存判断，含 "WINDOWS" |
| `boolean isWindowsNT()` | 是否 Windows NT | 含 "WINDOWS NT" |
| `boolean isWindows95()` | 是否 Windows 95/98 | 含 "WINDOWS 95" 或 "WINDOWS 98" |
| `boolean isOS2()` | 是否 OS/2 | 含 "OS/2" |
| `boolean isMacOS()` | 是否 macOS | 含 "MAC" |
| `boolean isMacOSX()` | 是否 macOS X | 含 "MAC OS X" |
| `boolean isUnix()` | 是否 unix 系 | 不含 WINDOWS/OS2/AIX |
| `boolean isAix()` | 是否 aix | 含 "AIX" |
| `boolean isArm32()` | 是否 arm32 | `os.arch` 含 "arm" |
| `boolean isAarch64()` | 是否 arm64 | `os.arch` 含 "aarch64"/"arm64" |
| `boolean isX64()` | 是否 x64 | `os.arch` 含 "x86_64"/"amd64" |
| `boolean isX86()` | 是否 x86 | `os.arch` 含 "x86" |
| `String getArchName()` | 获取平台架构名 | 依次判断返回 arm64/arm32/amd64/x86/unknown |

- 调用链：`OSUtil.getArchName → isAarch64/isArm32/isX64/isX86`

## SystemUtil

- 职责：系统信息与 JVM 运行时工具类，提供内存/GC/类加载查询及系统属性、环境变量访问。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| memoryMXBean | MemoryMXBean | 内存 MXBean 缓存 |
| classLoadingMXBean | ClassLoadingMXBean | 类加载 MXBean 缓存 |
| GC_FLAG | AtomicBoolean | 延迟 gc 的并发标志位 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void removeOptionalProperties()` | 移除可选系统属性 | 删除 java.vendor 等一批系统属性（已 @Deprecated） |
| `MemoryMXBean getMemoryMXBean()` | 获取内存 MXBean | 缓存 `ManagementFactory.getMemoryMXBean()` |
| `ClassLoadingMXBean getClassLoadingMXBean()` | 获取类加载 MXBean | 缓存 |
| `void gc()` | 执行 gc 并打印前后内存/类统计 | 计算堆+非堆已用内存，`memoryMXBean.gc()`，`NumberUtil.scale` 保留 2 位 |
| `void gcInterval(int)` | 定期 gc | `TaskManager.startInterval(SystemUtil::gc, interval)` |
| `void gcLater()` | 延迟 gc | 启动线程，GC_FLAG 防重入后调用 `gc()` |
| `double getUsedMemory()` | 已用内存(MB) | 堆+非堆 used 之和 / 1024 / 1024 |
| `String tmpdir()` | 临时目录 | `java.io.tmpdir` |
| `String userHome()` / `javaHome()` / `userDir()` | 用户/Java/工作目录 | 对应系统属性 |
| `void openFolderViaCommand(String)` | 打开系统目录 | 按平台用 explorer/open/xdg-open 启动 ProcessBuilder |
| `boolean isCIEnv()` | 是否发布(CI)环境 | `CI=true` 或存在 `GITHUB_ACTIONS` |
| `String mvnHomeEnv()` | Maven home | 优先 `MAVEN_HOME`，其次 `M2_HOME` |
| `String getJdkVersion()` | JDK 版本 | 由 `Runtime.version()` 拼接 |

- 调用链：`SystemUtil.gcInterval → TaskManager.startInterval → SystemUtil.gc`
- 调用链：`SystemUtil.openFolderViaCommand → OSUtil.isWindows/isMacOS/isLinux`

## ProcessBuilderUtil

- 职责：ProcessBuilder 极简工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Process exec(String...)` | 执行命令 | `new ProcessBuilder(cmd).start()` |

- 调用链：`ProcessBuilderUtil.exec → ProcessBuilder.start`

## ProcessUtil

- 职责：进程工具类，负责跨平台重启应用、判断/结束进程。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void restartApplication(int, Runnable)` | 重启应用（app 镜像场景） | 按 Windows/Linux/macOS 分别用 ProcessBuilder 构建重启命令并启动，随后 `TaskManager.startDelay` 延迟退出 |
| `boolean isRunningInAppImage()` | 是否运行于 AppImage | 检查 `APPIMAGE` 或 `APPDIR` 环境变量 |
| `void restartApplication2(int, Runnable)` | 重启应用（安装包场景） | Windows 向上查找 `{projectName}.exe`；Linux 处理 AppImage 临时目录拷贝后执行 nohup；macOS 查找 MacOS 可执行文件；最后延迟退出 |
| `boolean isProcessRunning(String...)` | 进程是否运行 | Windows 执行 `tasklist`、macOS 执行 `ps -ef`，按行匹配进程名 |
| `boolean killProcess(String)` | 按名称结束进程 | Windows `taskkill /F /IM`；Linux/macOS `killall -9` |
| `boolean killProcess(long)` | 按 pid 结束进程 | Windows `taskkill /F /PID`；Linux/macOS `kill -9` |

- 调用链：`ProcessUtil.restartApplication → OSUtil 平台判定 → ProcessBuilder.start → TaskManager.startDelay → System.exit`

## RuntimeUtil

- 职责：Runtime 工具类，封装命令执行、结果读取与关闭钩子注册。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `int processorCount()` | 处理器数量 | `availableProcessors()`，≤0 取 1 |
| `String execForStr(String)` / `(String[])` | 执行并返回字符串 | 调用 `execForResult`，优先取 input 否则 error |
| `ProcessExecResult execForResult(String)` / `(String[])` / `(String,String[],File)` / `(String[],String[],File)` | 执行并返回结果 | `exec(...)` 后 `getProcessResult` |
| `ProcessExecResult getProcessResult(Process)` | 读取进程结果 | Windows 用 gbk、否则 utf-8 读取标准/错误流，`process.waitFor()` 取退出码，回填 `ProcessExecResult` |
| `Process exec(String)` / `(String[])` / `(String,String[],File)` | 执行命令 | `Runtime.getRuntime().exec(...)` |
| `Process exec(String[], String[], File)` | 执行命令 | 用 `ProcessBuilder`，按需设置环境变量与工作目录，`redirectErrorStream(true)` |
| `int execAndWait(String[])` | 执行并等待 | `Runtime.exec` 后 `process.waitFor()` |
| `int getProcessorCount()` | 处理器数量 | 直接返回 `availableProcessors()` |
| `void addShutdownHook(Thread)` | 注册关闭钩子 | `Runtime.getRuntime().addShutdownHook` |

- 调用链：`RuntimeUtil.execForStr → execForResult → exec → getProcessResult`
- 调用链：`ThreadUtil 静态块 → RuntimeUtil.addShutdownHook`

# cn.oyzh.common.arch

## NativeArch

- 职责：归一化后的 CPU 架构枚举，每个值携带字长与字节序，并提供由 `os.arch` 解析架构的能力。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| OS_ARCH_LOOKUP | Map<String, NativeArch> | os.arch 取值/别名到架构的查找表（静态，不可变） |
| id | String | 架构标识 |
| bits | int | 字长（32/64） |
| byteOrder | ByteOrder | 字节序 |
| aliases | List<String> | 架构别名 |

- 枚举常量（代表）：`X86`、`X86_64`、`ARM`、`ARM64`、`PPC`、`PPC64`、`PPC64LE`、`MIPS`、`MIPSEL`、`MIPS64`、`MIPS64EL`、`RISCV32`、`RISCV64`、`LOONGARCH32`、`LOONGARCH64`、`SW64`、`S390X`、`SPARC`、`SPARC64`、`IA64`、`UNKNOWN`（各含 id/字长/字节序/别名）。

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `NativeArch(String,int,ByteOrder,String...)` | 构造 | 保存 id/bits/byteOrder/aliases |
| `String getId()` | 架构标识 | 返回 id |
| `int getBits()` | 字长 | 返回 bits |
| `boolean is64Bit()` | 是否 64 位 | `bits == 64` |
| `ByteOrder getByteOrder()` | 字节序 | 返回 byteOrder |
| `List<String> getAliases()` | 别名列表 | 返回 aliases |
| `NativeArch ofOsArch(String)` | 由 os.arch 解析 | 归一化后查 `OS_ARCH_LOOKUP`，未命中走 `matchLoosely` |
| `NativeArch getCurrent()` | 当前 JVM 架构 | `ofOsArch(System.getProperty("os.arch"))` |
| `NativeArch matchLoosely(String)` | 兜底宽松匹配 | 处理 loongarch/sw64/mips/riscv/arm/ppc/s390/sparc/ia64/x86 等带后缀取值 |

- 调用链：`NativeArch.getCurrent → ofOsArch → OS_ARCH_LOOKUP / matchLoosely`

## NativeFormat

- 职责：原生库二进制容器格式枚举。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | 枚举常量即字段 |

- 枚举常量：`ELF`（.so）、`PE`（.dll）、`MACHO`（.dylib）、`FAT_MACHO`（通用二进制）、`UNKNOWN`。

- 方法：无（纯枚举）。

- 调用链：`NativeArchDetector.detectFormat → NativeFormat`

## NativeArchDetector

- 职责：从二进制数据头部判断原生库的目标架构与容器格式，支持 ELF/PE/Mach-O/Fat Mach-O。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| ELF_MACHINE_MIN_LENGTH | int | ELF 中 e_machine 前内容长度（0x14） |
| PE_LFANEW_OFFSET | int | PE 头 e_lfanew 位置（0x3C） |
| PE_SIGNATURE_LENGTH | int | PE 签名长度（4） |
| MAX_FAT_ARCHES | int | Fat 头最大架构数（32） |
| FAT_ARCH_LENGTH / FAT_ARCH_64_LENGTH | int | 32/64 位 Fat 架构信息长度（20/32） |
| FAT_MAGIC 等常量 | int | Fat 头 magic |
| MH_MAGIC 等常量 | int | Mach-O magic |
| EM_* | int | ELF e_machine 取值 |
| PE_* | int | PE machine 取值 |
| CPU_TYPE_* | int | Mach-O cpu type 取值 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `boolean isCompatibleWithCurrentJvm(byte[])` | 是否与当前 JVM 兼容 | 格式需匹配当前 OS，且 `detectArches` 含当前架构 |
| `boolean isFormatCompatibleWithCurrentOs(NativeFormat)` | 格式是否匹配当前 OS | Windows→PE，macOS→MachO/FatMachO，其余→ELF |
| `List<NativeArch> detectArches(byte[])` | 解析所有架构 | 按 ELF/PE/Mach-O/Fat 魔数分派解析 |
| `NativeFormat detectFormat(byte[])` | 解析容器格式 | 探测魔数，Fat 头需合法 |
| `NativeArch getCurrentJvmArch()` | 当前 JVM 架构 | 委托 `NativeArch.getCurrent()` |
| `NativeArch parseElf(byte[])` | 解析 ELF | 读 EI_CLASS/EI_DATA 与 e_machine 映射 |
| `NativeArch elfMachineToArch(int,boolean,boolean)` | ELF machine 映射 | switch 映射并校验字长/字节序 |
| `NativeArch parsePe(byte[])` | 解析 PE | 读 e_lfanew 校验 PE 签名后取 machine |
| `NativeArch peMachineToArch(int)` | PE machine 映射 | switch 映射（PE 一律小端） |
| `NativeArch parseMachO(byte[])` | 解析 Mach-O | 由 magic 定字节序，读 cpu type 映射 |
| `List<NativeArch> parseFatMachO(byte[])` | 解析通用二进制 | 遍历各架构项收集去重 |
| `FatHeader readFatHeader(byte[])` | 读取 Fat 头 | 校验 magic 与架构数量 |
| `NativeArch machoCpuToArch(int)` | Mach-O cpu 映射 | switch 映射 |
| `readIntBE/readIntLE/readInt/readShort` | 字节读取工具 | 按字节序拼装整数 |

- 内部 record：`FatHeader(ByteOrder order, int archCount, int archLength)`。

- 调用链：`NativeArchDetector.isCompatibleWithCurrentJvm → detectFormat → isFormatCompatibleWithCurrentOs → detectArches`

## NativeLibUtil

- 职责：原生库文件名平台判断工具（按扩展名）。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `boolean isNativeLibName(String)` | 是否原生库名 | 平台库判断的或组合 |
| `boolean isLinuxLib(String)` | 是否 linux/unix 库 | 以 `.so` 结尾或含 `.so.` |
| `boolean isAixLib(String)` | 是否 aix 库 | 以 `.a` 结尾 |
| `boolean isWindowsLib(String)` | 是否 windows 库 | 后缀 dll/exe/sys/ocx/cpl/scr/efi |
| `boolean isMacosLib(String)` | 是否 macos 库 | 后缀 dylib/jnilib |
| `String fileName(String)` | 取文件名小写 | 去掉目录并转小写 |

- 调用链：`NativeLibUtil.isNativeLibName → isWindowsLib/isMacosLib/isLinuxLib/isAixLib`

# cn.oyzh.common

## Const

- 职责：全局常量对象，提供通用日期时间格式化实例。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| DATE_FORMAT | SimpleDateFormat | 日期时间格式化对象（yyyy-MM-dd HH:mm:ss.SSS） |
| DATE_TIME_FORMAT | SimpleDateFormat | 时间格式化对象（HH:mm:ss.SSS） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| 无 | - | 仅常量 |

- 调用链：`Const.DATE_FORMAT`（供全局格式化调用）

## Index

- 职责：索引接口，约定可设置/获取索引值。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `int getIndex()` | 获取索引 | 由实现类定义 |
| `void setIndex(int)` | 设置索引 | 由实现类定义 |

- 调用链：`Index.getIndex/setIndex`

## SysConst

- 职责：系统常量与目录/项目名系统属性的读写工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| TEMP_DIR | String | 临时目录系统属性名 "temp.dir" |
| CACHE_DIR | String | 缓存目录系统属性名 "cache.dir" |
| STORE_DIR | String | 存储目录系统属性名 "store.dir" |
| PROJECT_NAME | String | 项目名称系统属性名 "project.name" |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String tempDir()` / `void tempDir(String)` | 临时目录读写 | `System.getProperty/setProperty(TEMP_DIR)` |
| `String storeDir()` / `void storeDir(String)` | 存储目录读写 | 对应系统属性 |
| `String cacheDir()` / `void cacheDir(String)` | 缓存目录读写 | 对应系统属性 |
| `String projectName()` / `void projectName(String)` | 项目名称读写 | 对应系统属性 |

- 调用链：`SysConst.projectName → System.getProperty("project.name")`（被 `ProcessUtil.restartApplication` 调用）
