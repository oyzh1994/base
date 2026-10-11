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

`CompactJarTool.compact()` 完成构建后会调用 `CompressedJarLauncher.warmCache(outputJar)`。因此同一台构建机随后测试时，通常可以直接命中已经生成的运行缓存。

## 压缩结构

payload 不再使用一个全局 XZ 流，而是拆成三个大块：

1. 应用 JAR 一个块。
2. classpath 前半部分依赖一个块。
3. classpath 后半部分依赖一个块。

每个块都是独立 XZ 流，并包含一个原始 ZIP 形式的数据区：

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
- 两个参数都是上限而不是无条件值。构建器会读取 `Runtime` 当前空闲堆，只使用其中约 75% 作为编码器预算；必要时减少并发并逐级降低字典。
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

缓存目录由当前用户标识和最终压缩 JAR 的完整 SHA-256 共同决定：

```text
${user.home}/.cache/compact-jar-cache/<user-cache-id>/<jar-sha256>/
```

`<user-cache-id>` 是由 `user.name` 和 `user.home` 计算出的 16 位 SHA-256 前缀，不包含用户名明文。不同系统用户即使运行 SHA-256 完全相同的 JAR，也会得到独立目录，不会访问、清理或覆盖其他用户的缓存。同一用户的多个进程仍使用相同目录并通过文件锁协调。

压缩 JAR 中任意内容变化都会生成新的 SHA-256 和新缓存，不会误用旧应用或旧依赖。缓存内容包括：

```text
application.jar
lib/*.jar
classpath.order
classpath.jar
cache.ok
cache.lock
```

多进程并发启动使用 `cache.lock` 文件锁；只有完整重建成功后才写入 `cache.ok`。未完成或被中断的缓存不会被视为有效。

覆盖缓存根目录：

```bash
java -Dcompact.jar.cache.dir=/path/to/cache \
  -jar target/application-compressed.jar
```

即使配置了共享根目录，启动器也会继续加入 `<user-cache-id>` 用户隔离层。POSIX 系统会把用户目录权限设置为 `0700`；Windows 等非 POSIX 文件系统使用其默认访问控制。`CompactJarTool.compact()` 的预热缓存也只属于调用该工具的系统用户，其他用户首次运行时会创建自己的缓存。

缓存不会自动清理。删除对应 `<user-cache-id>/<jar-sha256>` 目录即可释放展开后的空间。

## 启动转发

启动器把收到的 JVM 参数原样转发给实际应用 JVM：

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

当前默认使用 preset 9 和 32 MiB 字典上限。若要继续优先压缩率，可提高 `compact.jar.xz.dict.size`，但不建议增加分块数量；8 个以上独立 XZ 流会明显损失跨文件字典收益。三块是压缩率、并行度和首次缓存创建时间之间的平衡点。

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
