# cn.oyzh.common.date

## DateUtil

- 职责：`Date` 与时间戳/LocalDateTime 互转、时间格式化与解析工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Date of(Number)` | Number 转日期 | 委托 `of(long)` |
| `Date of(long)` | 时间戳转日期 | `new Date(l)` |
| `Date of(LocalDateTime)` | 本地日期时间转日期 | 经系统默认时区 `atZone().toInstant()` 后 `Date.from` |
| `String format(String)` | 格式化当前时间 | 委托 `format(System.currentTimeMillis(), format)` |
| `String format(long,String)` / `format(Date,String)` | 格式化时间 | `new SimpleDateFormat(format).format(...)` |
| `String format(LocalDateTime,String)` | 格式化本地日期时间 | 委托 `LocalDateTimeUtil.format` |
| `Date parse(CharSequence,String)` | 解析日期 | `new SimpleDateFormat(format).parse(...)` |
| `LocalDateTime parseLocalDateTime(CharSequence,String)` | 解析本地日期时间 | 委托 `LocalDateTimeUtil.parse` |

- 调用链：`DateUtil.format(LocalDateTime,String) → LocalDateTimeUtil.format`
- 调用链：`DateUtil.of(LocalDateTime) → ZoneId.systemDefault → Date.from`

## DateHelper

- 职责：日期辅助类，提供常用 `SimpleDateFormat` 常量与基于它们的格式化/解析方法。
- 字段（SimpleDateFormat 常量）：`DATE_TIME_FORMAT`(yyyy-MM-dd HH:mm:ss.SSS)、`DATE_TIME_SIMPLE_FORMAT`(yyyy-MM-dd HH:mm:ss)、`TIME_FORMAT`(HH:mm:ss.SSS)、`TIME_SIMPLE_FORMAT`(HH:mm:ss)、`YEAR_FORMAT`(yyyy)、`DATE_FORMAT`(yyyy-MM-dd)。

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String formatDateTime()` / `formatDateTime(Date)` / `formatDateTime(Instant)` | 含毫秒日期时间 | 用 DATE_TIME_FORMAT 格式化 |
| `Date parseDateTime(String)` | 解析含毫秒日期时间 | DATE_TIME_FORMAT.parse，异常打印 |
| `String formatDateTimeSimple()` / `(Date)` | 不含毫秒日期时间 | 用 DATE_TIME_SIMPLE_FORMAT |
| `String formatYear()` / `(Date)` | 年份 | 用 YEAR_FORMAT |
| `String formatTime()` / `(Date)` / `(Instant)` | 含毫秒时间 | 用 TIME_FORMAT |
| `String formatTimeSimple()` / `(Date)` | 不含毫秒时间 | 用 TIME_SIMPLE_FORMAT |
| `String formatDate()` / `(Date)` | 日期 | 用 DATE_FORMAT |

- 调用链：`JulUtil.getLogFile → DateHelper.formatDate`
- 调用链：`JulConsoleFormatter.format → DateHelper.formatTime(Instant)`

## CalendarUtil

- 职责：日历工具类，`Date` 转 `Calendar`。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `CalendarUtil()` | 私有构造 | 禁止实例化 |
| `Calendar of(Date)` | 日期转日历 | `Calendar.getInstance().setTime(date)` |

- 调用链：`CalendarUtil.of → Calendar.getInstance`

## LocalDateUtil

- 职责：本地日期工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `LocalDate of(Date)` | 日期转本地日期 | `LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault())` |

- 调用链：`LocalDateUtil.of → ZoneId.systemDefault`

## LocalTimeUtil

- 职责：本地时间工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `LocalTime of(Date)` | 日期转本地时间 | 委托 `of(date.toInstant())` |
| `LocalTime of(Instant)` | 时间戳转本地时间 | `LocalTime.ofInstant(instant, ZoneId.systemDefault())` |

- 调用链：`LocalTimeUtil.of(Date) → LocalTimeUtil.of(Instant)`

## LocalDateTimeUtil

- 职责：本地日期时间工具类，`Date`/`Instant`/`LocalDate` 转 `LocalDateTime` 及格式化解析。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `LocalDateTime of(Date)` | 日期转本地日期时间 | 委托 `of(date.toInstant())` |
| `LocalDateTime of(LocalDate)` | 本地日期+当前时间 | 委托 `of(date, LocalTime.now())` |
| `LocalDateTime of(LocalDate,LocalTime)` | 本地日期+时间 | `LocalDateTime.of(date, localTime)` |
| `LocalDateTime of(Instant)` | 时间戳转本地日期时间 | `LocalDateTime.ofInstant(instant, ZoneId.systemDefault())` |
| `String format(LocalDateTime,String)` | 格式化 | `DateTimeFormatter.ofPattern` 后 format |
| `LocalDateTime parse(CharSequence,String)` | 解析 | `formatter.parse` 后 `LocalDateTime.from` |

- 调用链：`DateUtil.format(LocalDateTime,String) → LocalDateTimeUtil.format`

## ZonedDateTimeUtil

- 职责：带时区日期时间工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ZonedDateTime of(Date)` | 日期转带时区日期时间 | 委托 `of(date.toInstant())` |
| `ZonedDateTime of(Instant)` | 时间戳转带时区日期时间 | `ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())` |

- 调用链：`ZonedDateTimeUtil.of(Date) → ZonedDateTimeUtil.of(Instant)`

## LocalZoneRulesProvider

- 职责：本地时区规则提供者，从 `$JAVA_HOME/lib/tzdb.dat` 加载规则并落盘缓存，供 JVM 时区使用。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| versionId | String | 时区数据版本标识 |
| regionIds | List<String> | 时区区域标识列表 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `LocalZoneRulesProvider()` | 构造 | 调用 `doLoad()`，失败抛 RuntimeException |
| `Set<String> provideZoneIds()` | 提供时区 id 集合 | regionIds 转 HashSet |
| `ZoneRules provideRules(String,boolean)` | 提供规则 | 校验区域存在，`readCache` 读取 |
| `NavigableMap<String,ZoneRules> provideVersions(String)` | 提供版本规则 | 用 versionId 与规则构造 TreeMap |
| `void doLoad()` | 加载 tzdb.dat | 逐段读取版本/区域/规则，用 `readRules` 反序列化后 `doCache` |
| `void doCache(String,ZoneRules)` | 写缓存 | 序列化 ZoneRules 到 `SysConst.cacheDir()/zoneId` |
| `ZoneRules readRules(byte[])` | 反序列化规则 | 反射调用 `java.time.zone.Ser.read` |
| `ZoneRules readCache(String)` | 读缓存 | 缓存文件不存在返回 null，否则反序列化 |
| `String toString()` | 描述 | 返回 "Local[版本]" |

- 调用链：`LocalZoneRulesProvider.doLoad → readRules(反射 Ser.read) → doCache → SysConst.cacheDir`

# cn.oyzh.common.json

> 说明：`JSONArray`、`JSONArray1`、`JSONObject`、`JSONObject1`、`JSONParser` 五个文件整文件被注释掉，属死代码，未纳入本审查。本包实际有效类仅 `JSONUtil`（基于 fastjson2 封装）。

## JSONUtil

- 职责：JSON 工具类，基于 fastjson2 封装序列化、美化、压缩、校验及与对象/列表的转换。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String toPretty(Object)` | 对象转美化 JSON | `JSON.toJSONString(obj, PrettyFormat)` |
| `String toPretty(String)` | 字符串转美化 JSON | `JSON.parse` 后美化 |
| `String toCompress(String)` | 压缩 JSON | 手写状态机去除字符串外的空白（保留转义） |
| `String toJson(Object)` | 对象转 JSON 串 | `JSON.toJSONString(obj)` |
| `JSONObject toJsonObject(Object)` | 对象转 JSON 对象 | `JSONObject.from(obj)` |
| `boolean isJson(String)` | 是否合法 JSON | 含 `{`/`[` 时用 `JSONValidator` 校验 |
| `JSONObject parseObject(String)` | 解析 JSON 对象 | `JSONObject.parseObject` 包装 |
| `JSONObject parseObject(Object)` | 对象解析 | 委托 `parseObject(toJson(obj))` |
| `JSONArray parseArray(String)` | 解析 JSON 数组 | `JSONArray.parseArray` 包装 |
| `T toBean(String,Class<T>)` / `(Object,Class<T>)` | 转 Java 对象 | 解析为 JSONObject 后 `toJavaObject` |
| `List<T> toList(String,Class<T>)` / `(JSONArray,Class<T>)` / `(JSONObject,String,Class<T>)` | 转 Java 对象列表 | 解析数组后 `toJavaList` |

- 调用链：`JSONUtil.toBean(Object,Class) → JSONUtil.toJson → JSONUtil.toBean(String,Class) → JSONObject.toJavaObject`
- 调用链：`JSONUtil.toList(JSONObject,String,Class) → JSONObject.getJSONArray → JSONArray.toJavaList`

# cn.oyzh.common.xml

## XMLDocument

- 职责：XML 文档对象，惰性解析 StAX 事件流并返回根节点。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| root | XMLElement | 根节点 |
| reader | XMLEventReader | StAX 事件读取器 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `XMLDocument(XMLEventReader)` | 构造（包级） | 保存读取器 |
| `XMLElement getRootElement()` | 获取根节点 | 首次调用时 `parse(reader)` 并关闭读取器 |
| `XMLElement parse(XMLEventReader)` | 解析事件流 | 维护当前 level 与节点映射，遇 StartElement 建节点并挂到 level-1 父节点，Characters 追加文本，EndElement 降级，level 归 0 停止 |

- 调用链：`XMLReader.read → new XMLDocument → getRootElement → XMLDocument.parse`

## XMLElement

- 职责：XML 节点，保存标签名、文本、属性及子节点，提供遍历与查询。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| text | String | 文本内容 |
| tagName | String | 标签名称 |
| attributes | Map<String,String> | 属性 |
| elements | List<XMLElement> | 子节点 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Map<String,String> attributes()` | 获取属性映射 | 为空时初始化 HashMap |
| `List<XMLElement> elements()` | 获取子节点列表 | 为空时初始化 ArrayList |
| `Iterator<XMLElement> elementIterator(String)` | 子节点迭代器 | 委托 `elements(tagName).iterator()` |
| `List<XMLElement> elements(String)` | 按标签名取子节点 | 非空时按 tagName 并行过滤，为空返回全部 |
| `XMLElement element(String)` | 取首个匹配子节点 | 顺序遍历匹配 tagName |
| `String attributeValue(String)` | 取属性值 | `attributes.get(name)` |
| `void appendText(String)` | 追加文本 | 首次赋值，其后拼接 |
| `void setTagName(String)` | 设置标签名 | 直接赋值 |

- 调用链：`XMLDocument.parse → XMLElement.setTagName/attributes().put/appendText`

## XMLHelper

- 职责：XML 辅助类，生成禁用 DTD 的 `XMLInputFactory` 以防御 XXE。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `XMLInputFactory newFactory()` | 创建输入工厂 | `XMLInputFactory.newInstance()` 并 `setProperty(SUPPORT_DTD,false)` |

- 调用链：`XMLReader.read → XMLHelper.newFactory → createXMLEventReader`

## XMLReader

- 职责：XML 读取器，从输入流创建 `XMLDocument` 并管理底层读取器关闭。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| reader | XMLEventReader | StAX 事件读取器 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `XMLDocument read(InputStream)` | 读取流（UTF-8） | 委托带编码重载 |
| `XMLDocument read(InputStream,String)` | 读取流 | `XMLHelper.newFactory` 创建事件读取器后包装为 XMLDocument |
| `void close()` | 关闭读取器 | 关闭 reader，异常打印 |

- 调用链：`XMLReader.read → XMLHelper.newFactory → XMLDocument(构造)`
