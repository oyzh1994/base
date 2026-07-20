# base

这是一个使用 Java 的集成库，支持 i18n、SSH、事件总线、存储，以及各类其他例如字符串处理的实现，减少第三方库的依赖。

---

## 依赖说明

1. **JDK 版本**：要求 25

---

## 结构说明

| 模块 | 说明 |
|---|---|
| `base-common` | 基础模块，各种功能相关实现 |
| `base-event` | 事件模块，事件总线实现 |
| `base-i18n` | i18n 模块，i18n 资源及实现 |
| `base-ssh` | SSH 模块，SSH 客户端实现 |
| `base-store` | 存储模块，数据存储实现 |

---

## Maven

### 安装
```bash
mvn -X clean install -DskipTests
```

### 注意
- 检查 cmd 里面 `java -version` 的版本号和项目版本号是否一致，否则可能出现无效的目标版本号 25 之类的问题
