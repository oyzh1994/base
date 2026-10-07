# cn.oyzh.common.log

## JulLog

- 职责：基于 java.util.logging(JUL) 的日志门面，静态方法输出各级别日志并装配控制台/文件 Handler。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| LOGGER | Logger | JUL 日志对象，名称 "JulLog"（静态常量） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| 静态块 | 初始化日志系统 | 关闭父 Handler；`JulUtil.getLogLevel` 设级别；非 jar 运行时添加 `JulConsoleHandler`；始终添加 `JulFileHandler` |
| `void setLevel(JulLevel)` | 设置日志等级 | `LOGGER.setLevel(level.toLevel())` |
| `void trace(...)` / `debug(...)` / `info(...)` / `warn(...)` / `error(...)` | 各级别日志 | 各级别 isLoggable 判断后 `LOGGER.log(record(...))`，均含 `(String,Object...)` 与 `(String,Throwable)` 重载 |
| `JulLogRecord record(Level,String,Object...)` | 构建日志记录 | 委托带 Throwable 重载 |
| `JulLogRecord record(Level,String,Throwable,Object...)` | 构建日志记录 | 抓当前线程堆栈取调用点（第 4 层），设置参数/异常/时间/线程名/线程 id/行号/类名/方法名 |
| `boolean isTraceEnabled()` / `isDebugEnabled()` / `isInfoEnabled()` / `isWarnEnabled()` / `isErrorEnabled()` | 各级别是否启用 | 比较 `JulLevel` 的 ordinal |
| `Logger getLogger()` | 获取日志对象 | 返回 LOGGER |

- 调用链：`JulLog.info → JulLog.record → JulLogRecord 构造 → LOGGER.log`
- 调用链：`JulLog 静态块 → JulUtil.getLogLevel/getLogFile → JulConsoleHandler/JulFileHandler`

## JulUtil

- 职责：JUL 配置工具类，确定日志文件路径与日志等级。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `File getLogFile()` | 获取日志文件 | 读取 `jullog.file` 属性（非法则报错/创建）；否则用 `getLogsDir` + 日期 + 项目名拼接文件名并 `FileUtil.touch` |
| `JulLevel getLogLevel()` | 获取日志等级 | 读取 `jullog.level` 属性解析，缺省 `JulLevel.DEBUG` |
| `String getLogsDir()` | 获取日志目录 | 依次取 `SysConst.tempDir/storeDir`，否则 `SystemUtil.userDir`，拼 "logs" |

- 调用链：`JulUtil.getLogFile → SysConst.projectName → DateHelper.formatDate → FileNameUtil.concat`

## JulConst

- 职责：JUL 常量与线程 id 输出开关。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| ENABLE_THREAD_ID | String | 线程 id 启用属性名 "jul.enable.thread.id" |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void enableThreadId()` | 启用线程 id 输出 | 设置系统属性 true |
| `void disableThreadId()` | 禁用线程 id 输出 | 清除系统属性 |
| `boolean isEnableThreadId()` | 是否启用 | 属性等于 "true" |

- 调用链：`JulLog.record → JulConst.isEnableThreadId`

## JulLevel

- 职责：项目自定义日志等级枚举，与 JUL `Level` 双向映射。
- 枚举常量：`ALL`、`TRACE`、`DEBUG`、`INFO`、`WARN`、`ERROR`、`OFF`。
- 字段：无（枚举常量即等级）。

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Level toLevel()` | 转 JUL 等级 | 委托 `toLevel(this)` |
| `JulLevel ofLevel(Level)` | JUL 等级转 JulLevel | FINEST/FINER→TRACE，FINE/CONFIG→DEBUG，其余对应映射 |
| `Level toLevel(JulLevel)` | JulLevel 转 JUL 等级 | switch 映射，TRACE→FINEST、DEBUG→CONFIG、ERROR→SEVERE 等 |
| `String nameOfLevel(Level)` | 等级名称 | 映射为 TRACE/DEBUG/INFO/WARN/ERROR，未知 UNKNOWN |
| `JulLevel ofLevel(String)` | 按名称解析 | 忽略大小写匹配，无法识别返回 null |

- 调用链：`JulLog.setLevel → JulLevel.toLevel → LOGGER.setLevel`

## JulFormatter

- 职责：JUL 格式化器抽象基类，统一处理消息占位符与参数转义，异常格式化由子类实现。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String formatMessage(LogRecord)` | 格式化消息 | 替换 `\r`；兼容 `{}`/`{0}`/% 三种占位符；参数 `pretreatmentArg` 处理；存在异常时调用抽象 `formatThrow` |
| `String formatThrow(...)` | 格式化异常（抽象） | 由子类实现 |
| `String pretreatmentArg(Object,boolean)` | 参数预处理 | null 返回 "null"；Number 直接 toString；按需转义 `\ ' "` |

- 调用链：`JulConsoleFormatter.format → JulFormatter.formatMessage → formatThrow`

## JulConsoleFormatter

- 职责：控制台日志格式化器，带 ANSI 颜色输出时间/等级/线程/位置/消息。
- 字段（ANSI 颜色常量）：`ANSI_RESET`、`ANSI_BLACK`、`ANSI_RED`、`ANSI_GREEN`、`ANSI_YELLOW`、`ANSI_BLUE`、`ANSI_PURPLE`、`ANSI_CYAN`、`ANSI_WHITE`。

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String format(LogRecord)` | 格式化日志行 | 依次拼接时间、着色等级（trace/debug/info 绿、warn 黄、error 红）、线程 id/名、类名.方法名#行号、消息 |
| `String formatThrow(...)` | 格式化异常 | 追加异常类名/消息及 `at 类.方法(文件.java:行号)` |

- 调用链：`JulConsoleHandler(构造) → JulConsoleFormatter → format → formatMessage`

## JulFileFormatter

- 职责：文件日志格式化器，纯文本无颜色输出。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String format(LogRecord)` | 格式化日志行 | 拼接时间、等级、线程 id/名、类名.方法名#行号、消息（无 ANSI 颜色） |
| `String formatThrow(...)` | 格式化异常 | 追加异常信息与位置 |

- 调用链：`JulFileHandler(构造) → JulFileFormatter → format → formatMessage`

## JulConsoleHandler

- 职责：控制台日志处理器，输出到标准输出并按环境解析字符集。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | 继承 StreamHandler |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `JulConsoleHandler()` | 构造 | `super(System.out, new JulConsoleFormatter())` 并按 `resolveEncoding` 设编码 |
| `String resolveEncoding()` | 解析字符集 | 真实控制台→CI 强制 UTF-8→`stdout.encoding`→`sun.stdout.encoding`→平台默认 |
| `void publish(LogRecord)` | 发布日志 | `super.publish` 后 `flush` |
| `void close()` | 关闭 | flush 后 close |

- 调用链：`JulLog 静态块 → JulConsoleHandler(构造) → JulConsoleFormatter`

## JulFileHandler

- 职责：文件日志处理器，以追加方式写日志文件。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | 继承 StreamHandler |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `JulFileHandler(File)` | 构造 | `super(new FileOutputStream(logFile, true), new JulFileFormatter())` |
| `void publish(LogRecord)` | 发布日志 | `super.publish` 后 `flush` |
| `void close()` | 关闭 | flush 后 close |

- 调用链：`JulLog 静态块 → JulFileHandler(构造) → FileOutputStream`

## JulLogRecord

- 职责：日志记录，在 JUL `LogRecord` 上扩展行号与线程名称。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| lineNumber | int | 日志所在行号 |
| threadName | String | 线程名称 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `JulLogRecord(Level,String)` | 构造 | `super(level, msg)` |
| `int getLineNumber()` / `void setLineNumber(int)` | 行号读写 | 标准访问器 |
| `String getThreadName()` / `void setThreadName(String)` | 线程名读写 | 标准访问器 |

- 调用链：`JulLog.record → JulLogRecord 构造/设值 → JulConsoleFormatter/JulFileFormatter 读取`
