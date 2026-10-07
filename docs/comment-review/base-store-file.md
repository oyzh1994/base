# base-store-file

> 模块：base-store　包：cn.oyzh.store.file
> 说明：文件存储读写抽象，抽象出「读取器 / 写入器」两类，并按 txt、csv、json、xml、excel、html 等格式提供具体实现；`FileHelper` 按文件类型工厂化创建。记录以 `FileRecord`（Map<Integer,Object>，键为字段下标）承载。

## TypeFileReader
> 包：cn.oyzh.store.file

- 职责：文件类型读取器抽象基类，定义按条/按批读取记录与解析文本行的模板。
- 字段：无（抽象类）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `protected void init()` | 初始化钩子 | 空实现，子类覆盖 |
  | `abstract FileRecord readRecord()` | 读一条记录 | 抽象方法，读完返回 null |
  | `List<FileRecord> readRecords(int count)` | 读指定数量 | 循环 `readRecord` 至数量满或读到 null |
  | `protected List<String> parseLine(String, Character, Character)` | 解析行 | 基于 `StringReader` 扫描，处理文本识别符包裹、转义 `\` 与字段分隔符，输出字段值列表 |

- 调用链：`FileHelper.initReader → 子类构造 → readRecords → readRecord → parseLine`

## TypeFileWriter
> 包：cn.oyzh.store.file

- 职责：文件类型写入器抽象基类，定义写头/写尾/写记录与文本行格式化模板。
- 字段：无（抽象类）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `protected void init()` | 初始化钩子 | 空实现 |
  | `Object parameterized(Object)` | 值参数化 | 为 null 返回 ""，否则 `toString()` |
  | `void writeHeader()` / `void writeTrial()` | 写头/写尾 | 空实现，子类覆盖 |
  | `abstract void writeRecord(FileRecord)` | 写一条记录 | 抽象方法 |
  | `void writeRecords(List<FileRecord>)` | 写多条 | 循环 `writeRecord` |
  | `protected String formatLine(Object[]|List, prefix, fieldSeparator, txtIdentifier, recordSeparator)` | 格式化一行 | 按前缀 + 文本识别符包裹各值，字段分隔符拼接，末尾追加记录分隔符 |

- 调用链：`FileHelper.initWriter → 子类构造 → writeHeader/writeRecords/writeTrial`

## FileColumn
> 包：cn.oyzh.store.file

- 职责：文件字段描述（名称、描述、位置）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | name | String | 字段名称 |
  | desc | String | 字段描述 |
  | position | int | 字段位置（列序） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `FileColumn(String)` / `FileColumn(String,String)` / `FileColumn(String,int)` | 三种构造 | 赋值名称/描述/位置 |
  | `getName/setName`、`getDesc/setDesc`、`getPosition/setPosition` | 属性读写 | 简单 getter/setter |

- 调用链：`FileColumns.addColumn → new FileColumn(...)`

## FileColumns
> 包：cn.oyzh.store.file

- 职责：文件字段集合，提供增删查与按位置排序。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns | List\<FileColumn\> | 字段列表（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void addColumn(String)` / `addColumn(String,String)` / `addColumn(String,int)` | 添加字段 | `new FileColumn` 加入列表 |
  | `int index(String)` | 取字段索引 | 未找到返回 -1 |
  | `String columnName(int)` | 取字段名 | 按下标取 `getName` |
  | `FileColumn column(String)` | 按名取字段 | `StringUtil.equals` 遍历匹配 |
  | `List<FileColumn> sortOfPosition()` | 按位置排序 | `Comparator.comparingInt(FileColumn::getPosition)` |
  | `void clear()` | 清空 | 清空列表 |

- 调用链：`FileHelper.initReader/initWriter → FileColumns`；`XxxTypeFileWriter.writeHeader → columns.sortOfPosition`

## FileRecord
> 包：cn.oyzh.store.file

- 职责：单条文件记录，以字段下标为 key 的 Map，并提供带类型转换的取值。
- 字段：继承 `HashMap<Integer, Object>`（key 字段下标，value 字段值）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `Object getValue(Integer, Class<?>)` | 按下标取并按类型转换 | 依次尝试 Integer/Long/Float/Double/Byte/Number 转换，其余 `toString()`；值 null 返回 null |

- 调用链：`XxxTypeFileReader.readRecord → new FileRecord + put`；`FileRecord.getValue`（导入端类型转换）

## FileReadConfig
> 包：cn.oyzh.store.file

- 职责：文件读取配置（分隔符、字符集、路径、数据起始行），支持链式设置。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | recordSeparator | String | 记录分隔符，默认系统换行 |
  | fieldSeparator | Character | 字段分隔符，默认空格 |
  | txtIdentifier | Character | 文本识别符，默认 `"` |
  | charset | String | 字符集，默认 UTF-8 |
  | filePath | String | 文件路径 |
  | dataRowStarts | Integer | 数据行起始下标（从 1 起） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `xxx()` / `xxx(v)`（filePath/charset/recordSeparator/txtIdentifier/fieldSeparator/dataRowStarts） | 链式读写 | setter 返回 `this` |
  | `getXxx/setXxx` | 传统读写 | 简单 getter/setter |

- 调用链：`调用方链式构建 FileReadConfig → FileHelper.initReader`

## FileWriteConfig
> 包：cn.oyzh.store.file

- 职责：文件写入配置（分隔符、字符集、路径、是否含标题、xml/excel 节点名、是否压缩），支持链式设置。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | recordSeparator | String | 记录分隔符 |
  | txtIdentifier | Character | 文本识别符 |
  | includeTitle | boolean | 是否包含标题行 |
  | charset | String | 字符集 |
  | filePath | String | 文件路径 |
  | prefix | String | 前缀 |
  | rootNodeName | String | xml 根节点名，默认 "Nodes" |
  | itemNodeName | String | xml 节点名，默认 "Node" |
  | sheetName | String | excel 工作薄名，默认 "Nodes" |
  | compress | boolean | 是否压缩（紧凑输出） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `xxx()` / `xxx(v)`（filePath/charset/compress/prefix/rootNodeName/itemNodeName/includeTitle/sheetName/txtIdentifier/recordSeparator） | 链式读写 | setter 返回 `this` |

- 调用链：`调用方链式构建 FileWriteConfig → FileHelper.initWriter`

## FileHelper
> 包：cn.oyzh.store.file

- 职责：文件读写器工厂，按文件类型（后缀）创建具体实现。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static TypeFileWriter initWriter(String, FileWriteConfig, FileColumns)` | 创建写入器 | 依 `FileNameUtil.isExcelType/isHtmlType/isJsonType/isXmlType/isCsvType/isTxtType` 返回对应 Writer |
  | `static TypeFileReader initReader(String, FileReadConfig, FileColumns)` | 创建读取器 | 依类型返回 Excel/Json/Xml/Csv/Txt Reader |

- 调用链：`FileHelper.initWriter → ExcelTypeFileWriter/... ；FileHelper.initReader → CsvTypeFileReader/...`

## TxtTypeFileReader
> 包：cn.oyzh.store.file

- 职责：txt 类型文件读取器，按行解析为记录。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns | FileColumns | 字段列表 |
  | config | FileReadConfig | 导入配置 |
  | reader | SkipAbleFileReader | 文件读取器 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `TxtTypeFileReader(FileReadConfig, FileColumns)` | 构造 | `SkipAbleFileReader` 打开文件，必要时设置换行符 |
  | `FileRecord readRecord()` | 读一条 | `readLine` + `parseLine(分隔符来自配置)`，装填 `FileRecord` |
  | `void close()` | 关闭 | 关流并清空引用 |

- 调用链：`FileHelper.initReader → TxtTypeFileReader → readRecord → parseLine`

## TxtTypeFileWriter
> 包：cn.oyzh.store.file

- 职责：txt 类型文件写入器。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns | FileColumns | 字段列表 |
  | config | FileWriteConfig | 导出配置 |
  | writer | LineFileWriter | 行写入器 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `TxtTypeFileWriter(FileWriteConfig, FileColumns)` | 构造 | `LineFileWriter.create` |
  | `void writeRecord(FileRecord)` | 写一条 | 将记录按 index 放入数组，`formatLine` 空格分隔写入 |
  | `Object parameterized(Object)` | 参数化 | null 输出 `""`，否则父类逻辑 |
  | `void close()` | 关闭 | 关流清引用 |

- 调用链：`FileHelper.initWriter → TxtTypeFileWriter → writeRecords → writeRecord → formatLine`

## CsvTypeFileReader
> 包：cn.oyzh.store.file

- 职责：csv 类型文件读取器，按逗号解析并支持跳行。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns / config / reader | FileColumns / FileReadConfig / SkipAbleFileReader | 同 txt |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `CsvTypeFileReader(FileReadConfig, FileColumns)` | 构造 | 打开文件后 `init` |
  | `protected void init()` | 跳行 | `dataRowStarts` 非空则 `reader.skipLine(starts-1)` |
  | `FileRecord readRecord()` | 读一条 | `readLine` + `parseLine(txtIdentifier, ',')` |
  | `void close()` | 关闭 | 关流清引用 |

- 调用链：`FileHelper.initReader → CsvTypeFileReader.init → readRecord → parseLine`

## CsvTypeFileWriter
> 包：cn.oyzh.store.file

- 职责：csv 类型文件写入器，其中 `!includeTitle()` 时不写表头；值做转义。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns / config / writer | FileColumns / FileWriteConfig / LineFileWriter | 同 txt |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `CsvTypeFileWriter(FileWriteConfig, FileColumns)` | 构造 | 创建 writer 后 `init` |
  | `void writeHeader()` | 写表头 | `includeTitle` 时按 `sortOfPosition` 取 `desc` 写入 |
  | `void writeRecord(FileRecord)` | 写一条 | 逗号分隔格式化写入 |
  | `Object parameterized(Object)` | 参数化 | null 输出 ""，Number 原样，其余 `TextUtil.escape` |
  | `void close()` | 关闭 | 关流清引用 |

- 调用链：`FileHelper.initWriter → CsvTypeFileWriter.writeHeader/writeRecord`

## JsonTypeFileReader
> 包：cn.oyzh.store.file

- 职责：json 类型文件读取器，基于 fastjson2 `JSONReader` 流式读取数组元素。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | reader | JSONReader | json 读取器 |
  | config | FileReadConfig | 导入配置 |
  | columns | FileColumns | 字段列表 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JsonTypeFileReader(FileReadConfig, FileColumns)` | 构造 | `JSONReader.of(FileUtil.getReader)` 后 `init` |
  | `protected void init()` | 进入数组 | `reader.startArray()` |
  | `FileRecord readRecord()` | 读一条 | 判断 `]`/结束，`readObject` 后按 `columns` 位置装填 |
  | `void close()` | 关闭 | 关流清引用 |

- 调用链：`FileHelper.initReader → JsonTypeFileReader.init → readRecord → reader.readObject`

## JsonTypeFileWriter
> 包：cn.oyzh.store.file

- 职责：json 类型文件写入器，流式输出 JSON 数组，支持压缩模式。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns / writer / config | FileColumns / LineFileWriter / FileWriteConfig | 基本字段 |
  | firstWrite | boolean | 是否首次写入（控制逗号） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JsonTypeFileWriter(FileWriteConfig, FileColumns)` | 构造 | 创建 writer |
  | `void writeHeader()` | 写 `[` | 压缩/非压缩不同写法 |
  | `void writeTrial()` | 写 `]` | 同上 |
  | `void writeRecord(FileRecord)` | 写一条 | 手写 JSON 对象，按 `compress` 决定缩进；值经 `parameterized` |
  | `Object parameterized(Object)` | 参数化 | null→"null"，Number 原样，其余 `JSONUtil.toJson` |
  | `void close()` | 关闭 | 关流清引用 |

- 调用链：`FileHelper.initWriter → JsonTypeFileWriter.writeHeader/writeRecord/writeTrial`

## XmlTypeFileReader
> 包：cn.oyzh.store.file

- 职责：xml 类型文件读取器，基于 StAX `XMLEventReader` 逐节点解析。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | reader | XMLEventReader | xml 事件读取器 |
  | config / columns | FileReadConfig / FileColumns | 配置与字段 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `XmlTypeFileReader(FileReadConfig, FileColumns)` | 构造 | `XMLHelper.newFactory().createXMLEventReader` 后 `init` |
  | `protected void init()` | 定位根节点 | 读到首个 StartElement |
  | `FileRecord readRecord()` | 读一条 | 状态机解析根/子节点与字符数据，按字段名取 `position` 装填 |
  | `void close()` | 关闭 | 关 reader 清引用 |

- 调用链：`FileHelper.initReader → XmlTypeFileReader.init → readRecord`

## XmlTypeFileWriter
> 包：cn.oyzh.store.file

- 职责：xml 类型文件写入器，输出声明、根节点与 item 节点。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns / config / writer | FileColumns / FileWriteConfig / LineFileWriter | 基本字段 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `XmlTypeFileWriter(FileWriteConfig, FileColumns)` | 构造 | 创建 writer |
  | `void writeHeader()` | 写声明+根 | `rootNodeName` 决定根标签 |
  | `void writeTrial()` | 写根结束 | `</rootNodeName>` |
  | `void writeRecord(FileRecord)` | 写一条 | 手写 `<item>` 及子标签，空值输出自闭合标签 |
  | `void close()` | 关闭 | 关流清引用 |

- 调用链：`FileHelper.initWriter → XmlTypeFileWriter.writeHeader/writeRecord/writeTrial`

## ExcelTypeFileReader
> 包：cn.oyzh.store.file

- 职责：excel 类型文件读取器，基于 POI 读取首张 sheet 的数据行。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | workbook | Workbook | excel 工作薄 |
  | columns / config | FileColumns / FileReadConfig | 配置与字段 |
  | currentRowIndex | int | 当前行索引 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `ExcelTypeFileReader(FileReadConfig, FileColumns)` | 构造 | 判断 xlsx，`WorkbookHelper.create` 打开后 `init` |
  | `protected void init()` | 定位起始行 | `dataRowStarts-1` |
  | `FileRecord readRecord()` | 读一条 | 逐 cell 按 `CellType` 取值（布尔/数值/日期/字符串），按列索引装填 |
  | `void close()` | 关闭 | 关 workbook |

- 调用链：`FileHelper.initReader → ExcelTypeFileReader.init → readRecord → WorkbookHelper`

## ExcelTypeFileWriter
> 包：cn.oyzh.store.file

- 职责：excel 类型文件写入器，基于 POI 写出 sheet 与数据行。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns / config | FileColumns / FileWriteConfig | 配置与字段 |
  | workbook | Workbook | 工作薄 |
  | xlsRowIndex | int | 行索引，初始 1 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `ExcelTypeFileWriter(FileWriteConfig, FileColumns)` | 构造 | `WorkbookHelper.create(isXlsx)` |
  | `void writeHeader()` | 写表头 | `includeTitle` 时建 sheet 与首行（`desc`），落盘 |
  | `private void flush()` | 刷新 | `WorkbookHelper.write` |
  | `protected void writeRecord(FileRecord, boolean)` | 写一条 | 按值类型（Date/Double/String/Boolean/Calendar/LocalDate/LocalDateTime/Number）写入 cell；flush 决定是否落盘 |
  | `void writeRecord(FileRecord)` / `void writeRecords(List)` | 覆盖写法 | 单条 flush；多条批量后统一 flush |
  | `void close()` | 关闭 | 关 workbook |

- 调用链：`FileHelper.initWriter → ExcelTypeFileWriter.writeHeader/writeRecords → flush`

## HtmlTypeFileWriter
> 包：cn.oyzh.store.file

- 职责：html 类型文件写入器，输出带样式表格的 HTML。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columns / config / writer | FileColumns / FileWriteConfig / LineFileWriter | 基本字段 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `HtmlTypeFileWriter(FileWriteConfig, FileColumns)` | 构造 | 创建 writer |
  | `void writeHeader()` | 写头 | 输出 DOCTYPE/样式表/`<table>` 及 `<th>` 表头 |
  | `void writeTrial()` | 写尾 | 输出 `</table></body></html>` |
  | `void writeRecord(FileRecord)` | 写一条 | 按 index 组数组，输出 `<tr><td>` 行 |
  | `void close()` | 关闭 | 关流清引用 |

- 调用链：`FileHelper.initWriter → HtmlTypeFileWriter.writeHeader/writeRecord/writeTrial`
