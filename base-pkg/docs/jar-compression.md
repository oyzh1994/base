# 通用 JAR 压缩组件

`cn.oyzh.pkg.util.compact` 提供一套通用的 Spring Boot 可执行 JAR 二次压缩方案。它把应用 JAR、依赖 JAR 和 ZIP 元数据交给 XZ 统一压缩，最终仍生成一个可以直接执行的 JAR：

```bash
java -jar target/application-compressed.jar
```

组件不绑定具体应用的启动类、包名、目录名或业务参数。启动类优先读取可执行 JAR 的 `Start-Class`，如果不存在，再读取应用 JAR 的 `Main-Class`。

## 适用范围

当前构建器面向 Spring Boot Maven Plugin 生成的嵌套 JAR 布局：

- 可执行 JAR 中包含依赖 JAR。
- 可执行 JAR 中包含 `BOOT-INF/classpath.idx`。
- 依赖默认位于 `BOOT-INF/lib/`。
- 提供不包含外部依赖的应用 JAR，用于还原应用自身条目。
- `classpath.idx` 可以没有外部依赖，此时只生成应用 payload。

不同 Spring Boot 布局可以通过 Manifest 覆盖索引和依赖目录：

```text
Spring-Boot-Classpath-Index: BOOT-INF/classpath.idx
Spring-Boot-Lib: BOOT-INF/lib/
```

构建器当前接收两个输入文件：完整可执行 JAR 和应用 JAR。它不会从可执行 JAR 中猜测或提取应用 JAR；如果构建流程已有 `*.jar.original` 或等价的 thin JAR，直接将其作为第二个参数即可。

## 文件说明

- `CompactJarTool.java`：推荐的 Java 打包入口，构建后自动预热缓存。
- `CompactJarBuilder.java`：展开嵌套 JAR、生成多段 XZ payload，并写出最终可执行 JAR。
- `CompressedJarLauncher.java`：按 SHA-256 管理缓存、并行恢复 JAR，并启动实际应用。

## 打包调用

Java API：

```java
import cn.oyzh.pkg.util.compact.CompactJarTool;

import java.nio.file.Path;

Path executableJar = Path.of("target/application.jar");
Path applicationJar = Path.of("target/application.jar.original");
Path outputJar = Path.of("target/application-compressed.jar");

// 默认 XZ preset 9，优先保证分发体积；XZ 字典默认最多使用 32 MiB。
CompactJarTool.compact(executableJar, applicationJar, outputJar);

// 构建服务器更看重打包速度时可降低 preset。
CompactJarTool.compact(executableJar, applicationJar, outputJar, 6);
```

命令行入口：

```bash
java -cp "$BASE_PKG_CLASSES:$BASE_COMMON_JAR:$XZ_JAR" \
  cn.oyzh.pkg.util.compact.CompactJarTool \
  target/application.jar \
  target/application.jar.original \
  target/application-compressed.jar \
  9
```

`CompactJarTool.compact()` 当前只负责生成压缩 JAR；安装器或构建流程需要预热时，可显式调用
`CompressedJarLauncher.warmCache(outputJar)`。没有预热脚本的分发包仍会在首次运行时创建缓存。

## 压缩结构

payload 不再使用一个全局 XZ 流，而是拆成三个大块：

1. 应用 JAR 一个块。
2. classpath 前一段依赖一个块。
3. classpath 后一段依赖一个块。

依赖切分点按嵌套 JAR 的存储大小选择，使两个依赖块的负载尽量接近。每个块都是独立 XZ 流，
并包含一个原始 ZIP 形式的数据区：

```text
META-INF/compact-index.tsv
a/<application-entry>
d/00000/<dependency-entry>
d/00001/<dependency-entry>
...
```

ZIP 条目使用 `STORED`，避免在 payload 内重复执行 DEFLATE；实际压缩由外层 XZ 完成。依赖按 classpath 顺序连续切分，尽量让相邻依赖共享同一 XZ 字典。

三个块的压缩后长度记录在 Manifest 中：

```text
Payload-Chunk-Sizes: 1468716,24427656,18804392
Cache-Version: 4
```

启动器严格按这些长度切分 `compact-payload.xz`，每个块使用独立 XZ 解码器并行恢复。应用和依赖条目直接流式写入临时 JAR，不在堆中排队保存整个条目内容。

## 并行策略

打包阶段：

- 原始应用块和两个依赖块并行生成。
- XZ 任务由固定大小的工作线程队列执行，默认最多两个编码器同时运行。
- LZMA2 字典默认最多使用 32 MiB，并会按构建 JVM 当前空闲堆自动下调字典和并发数。
- 使用 `ThreadUtil.newThread()` 创建一次性工作线程，不使用共享缓存线程池，避免打包进程结束后仍等待空闲线程退出。

压缩内存参数通过 JVM 系统属性调整：

```bash
java -Dcompact.jar.xz.dict.size=50331648 \
     -Dcompact.jar.xz.compress.threads=2 \
     -cp "$BASE_PKG_CLASSES:$BASE_COMMON_JAR:$XZ_JAR" \
     cn.oyzh.pkg.util.compact.CompactJarTool ...
```

- `compact.jar.xz.dict.size`：LZMA2 字典大小上限，单位为字节，默认 `33554432`（32 MiB）。
- `compact.jar.xz.compress.threads`：XZ 压缩线程上限，默认 `2`，允许范围为 1 到 16。
- `compact.jar.xz.nice.length`：preset 9 的 LZMA2 匹配长度上限，默认 `128`，允许范围为
  `4` 到 `273`。更大的值通常能略微改善压缩率，但会增加打包时间和 CPU。
- 字典和线程参数是上限而不是无条件值。构建器会读取 `Runtime` 当前空闲堆，只使用其中约 75% 作为编码器预算；必要时减少并发并逐级降低字典。
- preset 9 的原始默认字典是 64 MiB。它会让三个编码器同时产生较大数组分配，也是旧版 Linux 构建 OOM 的主要来源。32 MiB 默认值相当于更保守的 preset 8 字典，但其他 preset 9 匹配参数保持不变。
- 机器内存充足且更看重体积时，可显式把字典上限设为 48 MiB 或 64 MiB；系统属性写错或越界会直接报错，不会静默忽略。

启动阶段：

- 最多三个 XZ 块并行解压。
- 应用和依赖 JAR 并行重建。
- 每个解压线程处理完一个依赖 JAR 就立即提交写出，不等待整块全部解压。

启动并发数默认为 3（单核机器自动降为 1），可以通过 JVM 参数调整：

```bash
java -Dcompact.jar.unpack.threads=3 -jar target/application-compressed.jar
```

允许范围为 1 到 16。SSD 和多核机器可尝试 3 或 4；机械硬盘、低内存机器建议设置为 1 或 2。线程过多可能增加 CPU、内存和磁盘争用。

## 缓存机制

缓存目录由当前用户标识和最终压缩 JAR 的完整 SHA-256 共同决定。共享使用锁放在每个
JAR 缓存目录之外，这样缓存目录可以被整体重命名和删除，而不会破坏正在运行的进程所持有的锁：

```text
${user.home}/.cache/compact-jar-cache/<user-cache-id>/
  .locks/<jar-sha256>.lock
  <jar-sha256>/
    application.jar
    lib/*.jar
    classpath.order
    classpath.jar
    cache.ok
    cache.lock
    app-id
    last-used
    aot/
      cache.lock
      <signature>.aot
      <signature>.ok
```

`<user-cache-id>` 是由 `user.name` 和 `user.home` 计算出的 16 位 SHA-256 前缀，不包含用户名明文。不同系统用户即使运行 SHA-256 完全相同的 JAR，也会得到独立目录，不会访问、清理或覆盖其他用户的缓存。同一用户的多个进程仍使用相同目录并通过文件锁协调。

压缩 JAR 中任意内容变化都会生成新的 SHA-256 和新缓存，不会误用旧应用或旧依赖。

多进程并发启动使用 `cache.lock` 文件锁；只有完整重建成功后才写入 `cache.ok`。未完成或被中断的缓存不会被视为有效。

覆盖缓存根目录：

```bash
java -Dcompact.jar.cache.dir=/path/to/cache \
  -jar target/application-compressed.jar
```

即使配置了共享根目录，启动器也会继续加入 `<user-cache-id>` 用户隔离层。POSIX 系统会把用户目录权限设置为 `0700`；Windows 等非 POSIX 文件系统使用其默认访问控制。`CompactJarTool.compact()` 的预热缓存也只属于调用该工具的系统用户，其他用户首次运行时会创建自己的缓存。

### 历史缓存清理

同一用户的多个应用共用 `<user-cache-id>`，因此清理任务只处理与当前应用身份匹配的缓存。
启动器在缓存就绪后写入 `app-id`（Manifest `Start-Class` 的 SHA-256）和空的 `last-used` 时间标记，
并同步刷新 `cache.ok` 的修改时间。`cache.ok` 作为 `last-used` 的冗余时间信号，两者任意一个存在即可判断
最近使用时间；即使手动删除 `last-used`，已有缓存也不会因此被误判为过期。
每次应用启动并且子 JVM 成功创建后，启动器会创建一个最低优先级的守护线程执行一次清理，
不会阻塞应用启动或退出：

1. 扫描当前用户目录下名称为 64 位小写 SHA-256 的缓存目录。
2. 取 `last-used`、`cache.ok` 和缓存目录时间中的最大值。若 `last-used` 不存在或无法读取，
   还会递归检查缓存内的应用 JAR、依赖 JAR、AOT 文件等，使用普通文件的最新修改时间或访问时间。
   任一可靠时间在最近 30 天内，或时间信息无法读取时，均保留缓存。
3. 只删除 `app-id` 与当前应用一致的缓存；旧版缓存没有 `app-id` 时，从缓存内
   `application.jar` 的 `Start-Class` 或 `Main-Class` 推断身份，无法确认身份时一律保留。
4. 启动器在子进程运行期间持有 `.locks/<jar-sha256>.lock` 共享锁。清理任务只尝试非阻塞排他锁，
   正在运行的其他版本不会被删除；即使锁文件位于不支持共享锁的文件系统，删除失败也只会静默跳过。
5. 删除前先原子重命名成同目录下的 `.compact-stale-*` 目录，再递归删除；失败留下的临时目录会在
   后续启动时重试。单条缓存删除失败不会抛出异常，也不会影响应用。

仅当实际删除或清理失败时向控制台输出 `[compact-jar] ...` 消息。`.locks` 中的小锁文件不会随缓存
一起删除，但不会占用可观的磁盘空间。需要立即释放空间时，仍可手动删除对应
`<user-cache-id>/<jar-sha256>` 目录；正在运行的版本受共享锁保护，不会被清理任务误删。

## AOT 缓存

Java 24 及以上的 HotSpot 运行时会自动训练并复用 JEP 483 AOT 缓存；OpenJ9、Substrate VM
等不支持这些参数的运行时会自动跳过 AOT。AOT 文件位于对应展开缓存的 `aot/` 目录中，与应用
展开缓存一起按当前用户和压缩 JAR SHA-256 隔离：

1. 缓存不存在时进行首次训练，应用 JVM 退出后装配 AOT 缓存。
2. AOT 装配先写入临时文件，成功后再原子替换为目标文件并写入 `<signature>.ok`；
   只有标记、签名和文件大小全部匹配时才视为可用。
3. 后续启动使用 `-XX:AOTCache=<cache>` 加载缓存。
4. 应用更新会生成新的压缩 JAR SHA-256 和缓存目录，因此下一次启动会重新训练新版本。

启动器支持单阶段和两阶段两种训练流程，通过 `-Dcompact.jar.aot.assembly` 选择：

- `auto`：默认值。Windows 使用 `async`，其他系统保持 `one-step`。
- `one-step`：应用 JVM 使用 `-XX:AOTCacheOutput=<cache>`。HotSpot 会在训练进程退出时再启动
  一个内部 JVM 执行装配，退出耗时包含完整的缓存创建和落盘。
- `two-step`：应用 JVM 只使用 `-XX:AOTMode=record -XX:AOTConfiguration=<config>` 记录训练数据；
  应用退出后由启动器同步执行 `-XX:AOTMode=create` 装配。
- `async`：与 `two-step` 相同的训练/装配拆分，但应用退出后启动独立的 `javaw.exe`（Windows）
  或 `java`（其他系统）后台装配进程，启动器不等待装配完成。Windows 默认使用该模式，避免
  AOT 装配阻塞程序退出。

```bash
# Windows 默认即为 async；需要强制回退到 JDK 单阶段流程时：
java -Dcompact.jar.aot.assembly=one-step -jar target/application-compressed.jar
```

后台装配会持有 `aot/cache.lock`，装配成功前不会生成有效标记。因此装配期间再次启动程序不会
误用半个缓存，只会暂时跳过 AOT；装配完成后的下一次启动自动复用缓存。装配失败会静默清理
临时文件，下一次启动重新训练。详细耗时、退出码和 JDK 输出写入
`aot/<signature>.aot.assembly.log`，可直接用于诊断 Windows 首训慢的问题。

首次训练仍要记录类加载、方法调用等训练数据，这部分启动开销无法完全消除；`async` 解决的是
退出阶段创建大型 AOT 文件时的阻塞。Windows 上还建议把缓存放在本地可写 SSD，并在企业环境中
评估杀毒软件对缓存目录或新生成 `.aot` 文件的实时扫描开销。

`<signature>` 包含 Java 运行时版本、JDK 路径、操作系统、CPU 架构、最大堆以及有效 JVM 参数。
JDK、运行环境或 JVM 参数变化后也会重新训练，避免复用不兼容缓存。启动器会移除调用者传入的
旧 AOT cache 参数和与 AOT 冲突的 `-Xshare:off`，并自动选择训练或复用模式。其他经典 CDS 参数
（包括 `-Xshare:auto`、`-Xshare:on`、`-Xshare:dump`、`-XX:ArchiveClassesAtExit`、
`-XX:SharedArchiveFile`、`-XX:DumpLoadedClassList` 等）与 AOT cache 不兼容时会自动跳过 AOT，
保留原启动参数。若 `JAVA_TOOL_OPTIONS`、`JDK_JAVA_OPTIONS` 或 `_JAVA_OPTIONS` 中包含这些参数，
启动器也会跳过 AOT，避免它们被继承到子 JVM 后与 AOT 参数冲突。缓存加载失败时 JVM 的默认
`auto` 模式会继续运行，不会阻止应用启动。显式传入 `-XX:AOTMode=off` 或设置以下属性可关闭该功能：

```bash
java -Dcompact.jar.aot=false -jar target/application-compressed.jar
```

AOT cache 是启动优化，不替代展开缓存；程序更新后会生成新的展开缓存和 AOT 缓存，旧版本超过
30 天未使用后由上述后台清理任务删除。

## 缓存写入模式

缓存 JAR 可以选择压缩写入或直接存储，通过 JVM 系统属性切换：

```bash
# 默认模式：节省缓存磁盘空间
java -Dcompact.jar.cache.mode=deflate -jar target/application-compressed.jar

# STORED 模式：跳过 ZIP 重建压缩，换取更快的首次缓存创建
java -Dcompact.jar.cache.mode=stored -jar target/application-compressed.jar

# 混合模式：压缩资源直接存储，类文件和普通资源继续 DEFLATE
java -Dcompact.jar.cache.mode=hybrid -jar target/application-compressed.jar
```

- `deflate`：默认值。使用 `Deflater.BEST_SPEED` 重建 `application.jar` 和 `lib/*.jar`。
- `stored`：所有缓存 JAR 条目使用 ZIP `STORED`，不再执行 DEFLATE。
- `hybrid`：图片、音视频、压缩包、PDF 和常见 Office/字体资源使用 `STORED`；`.class`、文本、XML、JSON、配置文件和未知扩展名继续使用 `DEFLATE`。
- 模式写入 `cache.ok`。任意模式之间切换时，都会自动清除旧缓存并按新模式重建。
- `CompressedJarLauncher.warmCache()` 同样读取该属性，打包工具或安装程序可以按目标模式预热。
- 非法模式会立即报错，不会静默退回默认值。

EasyShell 当前输入同批次实测缓存大小约为：`deflate` 96.8 MiB、`hybrid` 91.6 MiB、`stored` 221.6 MiB。`stored` 会明显减少 DEFLATE CPU，但磁盘写入量增加约 1.3 倍；`hybrid` 的收益取决于应用中媒体和压缩资源的实际占比。最终首次启动耗时取决于 CPU、磁盘速度和缓存目录位置，应以目标机器实测为准。

## 启动转发

启动器把收到的 JVM 参数转发给实际应用 JVM；AOT cache 相关参数由启动器接管并替换：

```bash
java @resource/vmArgs.macos.txt -jar target/application-compressed.jar
java @resource/vmArgs.linux.txt -jar target/application-compressed.jar
java @resource/vmArgs.windows.txt -jar target/application-compressed.jar
```

应用参数也会原样传给 `Start-Class`。为避免 Windows 命令行长度限制，子进程 classpath 只指向一个 `classpath.jar`；该 JAR 的 Manifest `Class-Path` 按原 `classpath.idx` 顺序列出 `application.jar` 和 `lib/*.jar`。

### 应用重启

压缩启动器会向实际应用 JVM 注入两个启动信息：

```text
compact.jar.restart.classpath=<cache>/classpath.jar
compact.jar.restart.start.class=<Start-Class>
```

`cn.oyzh.common.system.ProcessUtil.restartApplication2()` 识别到这两个属性后，Linux 会直接从持久缓存以 `-cp classpath.jar Start-Class` 方式重启，不再依赖已经卸载的 AppImage 挂载点或再次运行压缩启动器。当前 JVM 参数会原样转发，并在新进程中重新写入上述属性，因此可以连续多次重启。

AppImage 首次重启时仍会把活动挂载点中的 Java 运行时复制为 `*_bak`，避免原挂载点随后卸载；后续重启发现 `APPDIR`/`APPIMAGE` 已失效，或当前 `java.home` 已位于持久备份目录时，会跳过复制并直接使用缓存。该处理不会绕过按用户和 JAR SHA-256 隔离的缓存校验。

Linux 重启进程优先通过 `setsid` 放入独立会话，确保应用退出或 AppImage 卸载时不会向新进程发送挂断信号；系统缺少 `setsid` 时回退到 `nohup`。为兼容旧版压缩启动器，即使没有注入上述属性，`ProcessUtil` 也会识别缓存中的 `classpath.jar`，并从 `sun.java.command` 恢复当前主类。

## 实测取舍

以下数据来自同一个约 103 MB 的实际 Spring Boot 应用、macOS、XZ preset 9。不同应用和硬件会变化；不同字典和并发组合的耗时、峰值 RSS 不应直接横向等价比较。

针对 EasyShell 1.2.2 的 XZ 分块与缓冲区优化回归如下：均衡切分让两个依赖块从约
24.9/18.9 MB 变为 21.8/21.7 MB，空缓存 `warmCache()` 从 3.11 秒降至 2.77 秒，
约改善 11%；产物从 45,480,131 bytes 降至 45,182,263 bytes，减少约 0.65%。
代价是构建时间从 45.5 秒增至 48.1 秒，峰值 RSS 基本不变，适合以打包 CPU 换取
更小产物和更快首次缓存创建。

| 方案 | 产物大小 | 相对旧方案 | 构建及预热时间 | 峰值 RSS |
| --- | ---: | ---: | ---: | ---: |
| 旧方案：64 MiB 字典、三个压缩线程 | 39,200,773 bytes | 基线 | 约 45 秒 | 未记录 |
| 当前默认：32 MiB 上限、`-Xmx512m` 自动降为单线程 | 39,799,089 bytes | 增加 1.53% | 66.3 秒 | 583 MB |
| 压缩优先：48 MiB 上限、两个压缩线程、`-Xmx2g` | 39,323,139 bytes | 增加 0.31% | 47.3 秒 | 1.78 GB |

另外使用包含 256 MiB 单一条目的嵌套依赖做低堆回归：`-Xmx256m`、preset 9 构建、`unzip -t` 和实际 `java -jar` 启动均通过，输出正常。这验证了大条目不再整体进入堆，同时低堆时会自动降低 XZ 字典。

XZ preset 主要影响打包时间与最终体积：

- preset 9：默认值，产物最小，构建最慢。
- preset 6：构建更快，产物通常明显变大。
- preset 0 到 3：适合频繁打包的开发环境，不建议用于正式分发。

当前默认使用 preset 9、32 MiB 字典上限和 nice length 128。若要继续优先压缩率，可提高
`compact.jar.xz.dict.size` 或 `compact.jar.xz.nice.length`，但不建议增加分块数量；8 个以上
独立 XZ 流会明显损失跨文件字典收益。三块是压缩率、并行度和首次缓存创建时间之间的平衡点。

## 正确性保障

构建和启动过程包含以下约束：

- 输出 JAR 不允许覆盖任一输入 JAR。
- Manifest 记录应用 JAR SHA-256、payload SHA-256、XZ preset 和分块长度。
- payload 顺序写入，启动时校验分块长度以及是否存在多余尾部数据。
- 依赖文件名必须唯一、不能包含路径分隔符或控制字符，索引中的重复 ID 会被拒绝。
- payload 索引 ID 必须从 0 连续编号，数量还必须与 Manifest `Dependency-Count` 一致。
- 应用 payload 只能出现一次，缺失或重复都会中止缓存创建。
- 依赖 ID、文件名和 classpath 顺序来自 `compact-index.tsv`；未知 payload 条目会直接报错，不会静默跳过。
- 缓存命中会校验 `classpath.order` 的 SHA-256，并逐一确认其中列出的 `lib/*.jar` 均存在。
- JAR 使用临时文件写出，完成后原子移动到目标路径。
- 最终 JAR 的每个 ZIP 条目仍保留名称、时间和内容 CRC；重建不会修改条目内容。

## 验证记录

- `javac` 编译三个压缩组件：通过。
- 最终压缩 JAR `unzip -t`：通过。
- 通用最小 Spring Boot 布局冒烟测试：应用和依赖恢复成功，退出码为 0。
- 256 MiB 大条目、`-Xmx256m`、preset 9 构建和启动：通过。
- 实际 EasyShell 输入使用 32 MiB 和 48 MiB 字典构建、缓存预热、ZIP 完整性检查：通过。
- 实际应用空缓存启动：进入 JavaFX 主界面并持续运行。
- 实际应用热缓存启动：正常复用缓存。
- Manifest 分块长度、缓存版本和依赖数量读取：通过。

## 已知边界

- 该组件优化的是分发体积和首次缓存创建速度，不直接降低业务 JVM 的堆占用。
- 首次启动需要把 payload 展开到可写目录；只读安装环境应配置 `compact.jar.cache.dir`。
- 重建后的 JAR ZIP 元数据可能与原 JAR 不完全相同，但条目内容保持一致。
- 如果应用依赖 JAR 签名对 ZIP 元数据本身有额外要求，应在正式发布流程中重新执行签名和 `jarsigner -verify`。
