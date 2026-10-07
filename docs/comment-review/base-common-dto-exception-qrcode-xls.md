# cn.oyzh.common.dto

## FriendlyInfo

- 职责：友好信息对象，同时保存原始名称/值与友好化的名称/值。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| name | String | 名称 |
| value | Object | 值 |
| originalValue | T | 原始值 |
| friendlyName | String | 友好名称 |
| friendlyValue | Object | 友好值 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String getName(boolean)` | 按友好标志取名 | friendly 为真返回 friendlyName，否则 name |
| `Object getValue(boolean)` | 按友好标志取值 | friendly 为真返回 friendlyValue，否则 value |
| `String getName()` / `void setName(String)` | 名称读写 | 标准访问器 |
| `Object getValue()` / `void setValue(Object)` | 值读写 | 标准访问器 |
| `T getOriginalValue()` / `void setOriginalValue(T)` | 原始值读写 | 标准访问器 |
| `String getFriendlyName()` / `void setFriendlyName(String)` | 友好名称读写 | 标准访问器 |
| `Object getFriendlyValue()` / `void setFriendlyValue(Object)` | 友好值读写 | 标准访问器 |
| `Object friendlyValue()` / `void friendlyValue(Object)` | 友好值快速读写 | 与 getFriendlyValue/setFriendlyValue 等价的链式风格方法 |
| `Object value()` / `void value(Object)` | 值快速读写 | 链式风格方法 |
| `void name(String)` | 设置名称 | 链式风格方法 |

- 调用链：`FriendlyInfo.getName(boolean) → 字段 friendlyName/name`

## Paging

- 职责：分页信息对象，封装数据列表、每页数量、总数、总页数、当前页及翻页计算。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| limit | long | 每页显示数量，默认 10 |
| count | long | 数据总数 |
| countPage | long | 总页数 |
| currentPage | long | 当前页（从 0 起） |
| dataList | List<T> | 数据列表 |
| EMPTY | Paging<Object> | 空数据静态实例 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Paging(long)` | 构造 | 委托 `Paging(emptyList, limit)` |
| `Paging(List<T>)` | 构造 | 委托 `dataList(dataList)` |
| `Paging(List<T>, long)` | 构造 | 修正 limit（≤0 取 10）后调用 `dataList` |
| `Paging(List<T>, long, long)` | 构造 | 记录 limit/count，按 count 与 limit 计算 countPage，currentPage 置 0 |
| `Paging<T> dataList(List<T>)` | 设置数据列表 | 空则置空列表；以列表 size 作为 count，重算 countPage，currentPage 置 0 |
| `List<T> first()` / `last()` | 首页/尾页数据 | 定位 currentPage 后调用 `pageData()` |
| `List<T> prev()` / `next()` | 上一页/下一页数据 | 调整 currentPage 后调用 `pageData()` |
| `long prevPage()` / `nextPage()` / `lastPage()` | 相邻页码/尾页页码 | 按边界计算并返回页码 |
| `void currentPage(long)` | 设置当前页 | 按 [0, countPage-1] 修正页码 |
| `long startIndex()` | 开始下标 | `currentPage * limit` |
| `List<T> page(long)` | 取指定页数据 | 设置 currentPage 后调用 `pageData()` |
| `boolean isEmpty()` | 是否为空 | dataList 为 null 或空 |
| `String formatTpl(String)` | 模板格式化 | 替换 `#count/#limit/#currentPage/#countPage` 占位符 |
| `List<T> pageData()` | 分页数据 | 计算 start/end 后 `dataList.subList` |
| `long fixPageNo(long)` | 修正页码 | 越界时收敛到 [0, countPage-1] |
| `long count()` / `limit()` / `countPage()` / `currentPage()` / `List<T> dataList()` | 访问器 | 返回对应字段 |

- 调用链：`Paging.page → Paging.currentPage → Paging.pageData → List.subList`

## Project

- 职责：项目信息对象，从 classpath 下 `/project.properties` 加载名称/类型/版本/日期/版权，单例缓存。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| name | String | 名称 |
| type | String | 类型 |
| version | String | 版本号 |
| updateDate | String | 更新日期 |
| copyright | String | 版权信息 |
| instance | Project | 当前实例（静态单例） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Project load()` | 加载项目信息 | 双重检查加锁；`new PropertiesFile("/project.properties")` 读取各字段（非空才设置），`propFile.clear()` 后返回单例 |
| `getName/setName`、`getType/setType`、`getVersion/setVersion`、`getUpdateDate/setUpdateDate`、`getCopyright/setCopyright` | 各字段访问器 | 标准读写 |

- 调用链：`Project.load → PropertiesFile.getProperty → Project.setXxx`

# cn.oyzh.common.exception

## ExceptionUtil

- 职责：异常处理工具类，取根因消息、消息匹配、判断是否中断/取消。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void throwRuntime(String)` | 抛出运行时异常 | `throw new RuntimeException(message)` |
| `String getMessage(Throwable)` | 取根因消息 | 沿 `getCause()` 取最内层；消息空则返回 `toString()` |
| `boolean hasMessage(Throwable, String...)` | 是否包含消息 | 调用 `StringUtil.containsAnyIgnoreCase(getMessage(ex), message)` |
| `boolean isInterrupt(Throwable)` | 是否中断 | 消息含 "canceled" 或 "interrupt" 即视为中断 |

- 调用链：`ExceptionUtil.hasMessage → ExceptionUtil.getMessage → StringUtil.containsAnyIgnoreCase`

## InvalidDataException

- 职责：无效数据运行时异常。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `InvalidDataException()` | 无参构造 | 消息固定为 "Data is not valid" |
| `InvalidDataException(String...)` | 带数据信息构造 | 消息 `"Data %s is not valid".formatted(ArrayUtil.toString(param))` |

- 调用链：`InvalidDataException(String...) → ArrayUtil.toString`

## InvalidParamException

- 职责：无效参数运行时异常。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `InvalidParamException(String)` | 构造 | 消息 `"Parameter '" + param + "' is invalid."` |

- 调用链：`BeanUtil.getValue → InvalidParamException`（参数校验抛出）

## PropertyNotFoundException

- 职责：属性未找到运行时异常。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `PropertyNotFoundException(String)` | 构造 | 消息 `"Property not found: " + propertyName` |

- 调用链：`BeanUtil.getValue → PropertyNotFoundException`

# cn.oyzh.common.qrcode

## QRCodeUtil

- 职责：二维码生成工具，基于 ZXing 生成二维码图片并可在中心插入 logo。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `BufferedImage createImage(String, String, int, int)` | 生成二维码图片 | 设置 MARGIN/CHARACTER_SET/ERROR_CORRECTION(M)/QR_VERSION(40) 等 hints，`MultiFormatWriter.encode` 得 BitMatrix，逐像素写入 BufferedImage |
| `void insertImage(BufferedImage, File, int, int, boolean)` | 插入 logo | `ImageIO.read` 读取 logo，needCompress 时按目标宽高缩放；在图片中心绘制 logo 并描圆角边框 |

- 调用链：`QRCodeUtil.createImage → MultiFormatWriter.encode → BitMatrix`

# cn.oyzh.common.xls

## WorkbookHelper

- 职责：Excel（POI）表格辅助类，创建/读写工作薄及行列单元格取值。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Workbook create(boolean)` | 创建空工作薄 | isXlsx 时 `new XSSFWorkbook()`，否则 `new HSSFWorkbook()` |
| `Workbook create(String)` | 按路径创建 | 委托 `create(new File(filePath))` |
| `Workbook create(boolean, String)` | 按格式+路径创建 | 委托 `create(isXlsx, new File(...))` |
| `Workbook create(File)` | 按文件创建 | 用 `FileNameUtil.getSuffix` 判断后缀，调用 `create(isXlsx, file)` |
| `Workbook create(boolean, File)` | 按格式+文件创建 | 用 FileInputStream 构造 XSSF/HSSFWorkbook |
| `void write(Workbook, String)` | 写出到文件 | `FileOutputStream` + `workbook.write` |
| `Sheet getActiveSheet(Workbook)` | 取活动工作表 | `getSheetAt(getActiveSheetIndex())` |
| `Row getFirstRow(Sheet)` | 取第一行 | `sheet.getRow(0)` |
| `Cell getFirstCell(Row)` | 取第一个单元格 | `row.getCell(0)` |
| `Cell getLastCell(Row)` | 取最后一个单元格 | `row.getCell(getLastCellNum()-1)` |

- 调用链：`WorkbookHelper.create(File) → FileNameUtil.getSuffix → WorkbookHelper.create(boolean, File)`

## BatchWorkbook

- 职责：实现 POI `Workbook` 的分批追加写入工作薄，按需创建/关闭 XSSF/HSSF 实例并落盘。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| file | File | 目标文件 |
| workbook | Workbook | 底层工作薄 |
| isXlsx | boolean | 是否 xlsx 格式 |
| firstAppend | boolean | 是否首次追加（首次新建，其后读取已有文件） |
| isClosed | boolean | 是否已关闭，初始 true |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `BatchWorkbook(File)` / `(String)` / `(boolean, String)` / `(boolean, File)` | 构造 | 保存格式与文件后调用 `initWorkbook()` |
| `void initWorkbook()` | 初始化工作薄 | 仅当 isClosed 时：xlsx 首次 `new XSSFWorkbook()` 否则读取文件；xls 首次 `new HSSFWorkbook()` 否则 `new POIFSFileSystem(...)` |
| `void closeWorkbook()` | 关闭工作薄 | isClosed 置 true 并 `workbook.close()` |
| `void append()` | 追加并落盘 | `initWorkbook` → `FileOutputStream` 写出 → `closeWorkbook` → `IOUtil.close` → firstAppend=false |
| `void close()` | 关闭 | 委托 `append()` 落盘 |
| `void write(OutputStream)` | 不支持 | 抛 `UnsupportedOperationException` |
| 大量 `getSheetXxx/setSheetXxx/createFont/createCellStyle/getName/...` | Workbook 接口方法 | 逐一委托给内部 `workbook` 的同名方法 |

- 调用链：`BatchWorkbook.close → BatchWorkbook.append → initWorkbook → workbook.write → closeWorkbook`
