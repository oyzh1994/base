# base-x11

X11 协议的 Java 实现，由 [moaxcp/x11](https://github.com/moaxcp/x11) `0.22.0` 版本的多个 Gradle
模块合并而来。合并后只保留一个 Maven 模块，**原包名保持不变**，原模块各自成为本模块下的一个包。

## 包结构

| 原模块 | 包 |
| --- | --- |
| `x11-protocol-core` | `com.github.moaxcp.x11.protocol` |
| `x11-protocol-xproto` | `com.github.moaxcp.x11.protocol.xproto` |
| `x11-protocol-bigreq` | `com.github.moaxcp.x11.protocol.bigreq` |
| `x11-protocol-xcmisc` | `com.github.moaxcp.x11.protocol.xc_misc` |
| `x11-protocol-render` | `com.github.moaxcp.x11.protocol.render` |
| `x11-protocol-shape` | `com.github.moaxcp.x11.protocol.shape` |
| `x11-protocol-xtest` | `com.github.moaxcp.x11.protocol.xtest` |
| `x11-protocol-shm` | `com.github.moaxcp.x11.protocol.shm` |
| `x11-protocol-xfixes` | `com.github.moaxcp.x11.protocol.xfixes` |
| `x11-protocol-randr` | `com.github.moaxcp.x11.protocol.randr` |
| `x11-protocol-xkb` | `com.github.moaxcp.x11.protocol.xkb` |
| `x11-protocol-screensaver` | `com.github.moaxcp.x11.protocol.screensaver` |
| `x11-protocol-dpms` | `com.github.moaxcp.x11.protocol.dpms` |
| `x11-protocol-xinerama` | `com.github.moaxcp.x11.protocol.xinerama` |
| `x11-protocol-res` | `com.github.moaxcp.x11.protocol.res` |
| `x11-protocol-damage` | `com.github.moaxcp.x11.protocol.damage` |
| `x11-protocol-composite` | `com.github.moaxcp.x11.protocol.composite` |
| `x11-protocol-sync` | `com.github.moaxcp.x11.protocol.sync` |
| `x11-protocol-present` | `com.github.moaxcp.x11.protocol.present` |

## 合并说明

- 原项目为 Gradle 多模块工程，每个协议扩展是一个独立 jar；合并后只有一个 `base-x11` 构件。
- 各模块的 `META-INF/services/com.github.moaxcp.x11.protocol.XProtocolPlugin` 已合并为一个文件，
  `ServiceLoader` 仍然可以发现全部协议插件（当前为 18 个）。
- 原模块的 `module-info.java` 已删除，单模块不再使用 JPMS 描述符。
- 已使用 delombok 展开全部 `@Value`、`@Builder`、`@Getter`、`@Setter`、`@ToString`、`@UtilityClass`、`@NonNull`，
  源码与构建均不再依赖 Lombok。
- 已移除 Eclipse Collections：对象列表使用 `java.util.List`，协议原始数组使用 `byte[]`、`int[]`、`short[]` 等。

## 依赖

本模块无第三方运行时依赖，仅依赖 JDK 标准库。

## 构建

```bash
mvn -pl base-x11 -am clean install
```

要求 JDK 27（`maven.compiler.release=27`）。

## 来源与许可

代码来自 [moaxcp/x11](https://github.com/moaxcp/x11)，遵循 MIT 协议，
Copyright (c) 2020 John Mercier，详见本目录下的 `LICENSE`。
