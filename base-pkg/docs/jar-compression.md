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

// 默认 XZ preset 9，优先保证分发体积。
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

启动器严格按这些长度切分 `compact-payload.xz`，每个块使用独立 XZ 解码器并行恢复。JAR 写出任务与 XZ 解码分离，并通过信号量限制同时保留的原始条目数量。

## 并行策略

打包阶段：

- 原始应用块和两个依赖块并行生成。
- 三个 XZ 块并行压缩。
- 使用 `ThreadUtil.newThread()` 创建一次性工作线程，不使用共享缓存线程池，避免打包进程结束后仍等待空闲线程退出。

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

缓存目录由最终压缩 JAR 的完整 SHA-256 决定：

```text
${java.io.tmpdir}/compact-jar-cache/<jar-sha256>/
```

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

缓存不会自动清理。删除对应 SHA-256 目录即可释放展开后的空间。

## 启动转发

启动器把收到的 JVM 参数原样转发给实际应用 JVM：

```bash
java @resource/vmArgs.macos.txt -jar target/application-compressed.jar
java @resource/vmArgs.linux.txt -jar target/application-compressed.jar
java @resource/vmArgs.windows.txt -jar target/application-compressed.jar
```

应用参数也会原样传给 `Start-Class`。为避免 Windows 命令行长度限制，子进程 classpath 只指向一个 `classpath.jar`；该 JAR 的 Manifest `Class-Path` 按原 `classpath.idx` 顺序列出 `application.jar` 和 `lib/*.jar`。

## 实测取舍

以下数据来自同一个约 108 MB 的实际 Spring Boot 应用、macOS、JDK 28、XZ preset 9。数值用于比较方案，不同应用和硬件会变化。

| 方案 | 产物大小 | 相对原包缩小 | 构建压缩时间 | 冷缓存创建 | 热缓存检查 |
| --- | ---: | ---: | ---: | ---: | ---: |
| 单一 XZ 流基线 | 44,752,251 bytes | 58.5474% | 84.1 秒 | 约 7.5 秒 | 约 0.05 秒 |
| 三个 XZ 块并行 | 44,873,634 bytes | 58.4349% | 44.4 秒 | 1.8–2.2 秒 | 0.10 秒 |

原始完整 JAR 为 107,960,006 bytes。三块方案相对单流只增加 121,383 bytes，即产物额外增加约 0.2712%，但压缩阶段缩短约 47%。当前实测冷缓存创建约 1.8–2.2 秒，缓存展开后约 110 MiB；热缓存检查约 0.10 秒。不同磁盘、JDK 和后台负载会改变绝对耗时。

XZ preset 主要影响打包时间与最终体积：

- preset 9：默认值，产物最小，构建最慢。
- preset 6：构建更快，产物通常明显变大。
- preset 0 到 3：适合频繁打包的开发环境，不建议用于正式分发。

当前默认使用 preset 9。若要继续优先压缩率，不建议增加分块数量；8 个以上独立 XZ 流会明显损失跨文件字典收益。三块是压缩率、并行度和首次缓存创建时间之间的平衡点。

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
- 实际应用空缓存启动：进入 JavaFX 主界面并持续运行。
- 实际应用热缓存启动：正常复用缓存。
- Manifest 分块长度、缓存版本和依赖数量读取：通过。

## 已知边界

- 该组件优化的是分发体积和首次缓存创建速度，不直接降低业务 JVM 的堆占用。
- 首次启动需要把 payload 展开到可写目录；只读安装环境应配置 `compact.jar.cache.dir`。
- 重建后的 JAR ZIP 元数据可能与原 JAR 不完全相同，但条目内容保持一致。
- 如果应用依赖 JAR 签名对 ZIP 元数据本身有额外要求，应在正式发布流程中重新执行签名和 `jarsigner -verify`。
