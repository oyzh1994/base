# base

[English](README_EN.md) | 简体中文

`base` 是一个 Java 基础集成库，提供通用工具、国际化、事件总线、存储、SSH、打包以及 MongoDB、Mosh、ZooKeeper 等基础能力，减少业务项目对零散第三方组件的直接依赖。

## 环境要求

- JDK 25
- Maven 3.9 或更高版本

## 模块结构

| 模块 | 说明 |
|---|---|
| `base-common` | 核心工具模块，提供文件、日志、网络、安全、JSON、XML、Excel、线程和常用工具能力 |
| `base-event` | 事件模块，提供通用事件总线实现 |
| `base-i18n` | 国际化模块，提供 i18n 资源管理与翻译集成 |
| `base-mongodb` | MongoDB 支持模块，封装 MongoDB 驱动及相关组件 |
| `base-mosh` | Mosh 支持模块，提供 Mosh 协议、传输和终端能力 |
| `base-pkg` | 打包模块，支持 JAR、JLink、JPackage、AppImage 等跨平台打包能力 |
| `base-ssh` | SSH 模块，提供 SSH 客户端、跳板机和端口转发能力 |
| `base-store` | 存储模块，提供文件、JDBC 和嵌入式数据库存储能力 |
| `base-zookeeper` | ZooKeeper 支持模块，提供客户端、协议和命令行相关能力 |

## Maven

安装全部模块：

```bash
mvn clean install -DskipTests
```

运行测试：

```bash
mvn test
```

## 注意事项

- 请确保终端中的 `java -version` 与项目要求一致，否则可能出现 `invalid target release: 25`。
- 子模块之间存在依赖关系，建议从根目录执行 Maven 命令，以便按正确顺序构建。

## 许可证

项目包含 `APACHE-LICENSE-2.0.txt` 和 `MIT-LICENSE.txt`，使用前请查看相应许可条款。
