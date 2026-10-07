# cn.oyzh.common.file

## FileUtil

- 职责：文件工具类，提供文件/目录的创建、读写、复制、移动、删除、遍历、统计与 Unix 权限处理。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | 全部为静态方法 |

- 方法（按功能分组）：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `File touch(String)` / `touch(File)` | 创建文件 | 父目录不存在则 `mkdirs` 后 `createNewFile` |
| `BufferedWriter getWriter(File,Charset,boolean)` | 获取写入器 | `BufferedWriter(OutputStreamWriter(FileOutputStream))` |
| `BufferedReader getReader(String,Charset)` / `(File,Charset)` | 获取读取器 | 包装 FileInputStream |
| `File writeString(String,File,Charset,boolean)` / `writeString(String,File)` / `writeUtf8String(String,File)` / `(String,String)` | 写字符串 | `FileWriter` 写出，可追加；UTF-8 变体 |
| `void writeBytes(byte[],String)` / `(byte[],File)` | 写字节 | `FileOutputStream.write` |
| `void writeUtf8Lines(Collection<String>,File)` | 写多行 | 拼接系统换行符后 `writeString` |
| `void appendLines(List<String>,String,String)` | 追加多行 | `getWriter(...,true)` 逐行写 |
| `byte[] readBytes(String)` / `(File)` | 读字节 | 流式读入 ByteArrayOutputStream |
| `List<String> readLines(URL,Charset)` / `(InputStream,Charset)` | 读所有行 | BufferedReader 逐行读取 |
| `String readString(InputStream,Charset)` / `(URL,Charset)` / `(File,Charset)` | 读全部内容 | 逐行拼接并补换行 |
| `String readUtf8String(String)` / `(File)` | UTF-8 读字符串 | 委托 `readString` |
| `boolean exists(String)` / `(File)` / `(Path)` / `(String,String...)` | 是否存在 | `File.exists`/`Files.exists` |
| `boolean isDirectory(File)` / `(String)`、`boolean isFile(String)` / `(File)` | 类型判断 | `Files.isDirectory`/`isRegularFile` |
| `File[] ls(Path)` / `(File)` / `(String)` / `(String,FileFilter)` | 列目录 | `listFiles`，可按过滤器 |
| `boolean del(String)` / `(File)` / `(String,boolean)` / `(File,boolean)` | 删除 | `deleteRecursively`，失败且 force 时用 rmdir(Windows)/rm -rf 兜底 |
| `boolean deleteRecursively(File)` | 递归删除 | 目录（非符号链接）先递归子项，再 `delete` |
| `boolean mkdir(Path)` / `(File)` / `(String)`、`void forceMkdir(File)` | 创建目录 | `mkdirs` |
| `boolean clean(String)` / `(File)` | 清空目录内容 | 遍历子项递归删除，保留目录 |
| `boolean cleanDir(String)` / `(Path)` / `(File)` | 删除目录及其内容 | 递归删除目录 |
| `boolean cleanFile(File)` | 清空文件 | 写入空字节数组 |
| `void moveFile(...)`、`boolean moveDir(...)`、`boolean renameFile(...)` | 移动/重命名 | 校验存在与覆盖，`renameTo`/`Files.move` |
| `File copy(File,File,boolean)` / `(String,String,boolean)` | 复制文件或目录 | 目标为目录时补文件名，`copyDirectory`/`copyFile` |
| `void copy(String,String)` / `(File,File)` | 复制（重载） | 按文件/目录组合分派 |
| `void copyFile(String,String)` / `(File,File)` | 复制单文件 | 确保父目录存在，`Files.copy(REPLACE_EXISTING)` |
| `void copyDirectory(String,String)` / `(File,File)` | 复制目录 | 递归复制子文件/子目录 |
| `File copyContent(File,File,boolean)` | 复制目录内容 | `copyDirectory` |
| `File move(File,File,boolean)` | 移动 | `Files.move`，可覆盖 |
| `List<File> getAllFiles(String)` / `(File)`、`void getAllFiles(File,List<File>)` | 递归取所有文件 | 收集非目录文件 |
| `void getAllFiles(String,ExceptionConsumer<File>)` / `(File,...)` | 递归回调 | 对每个文件执行回调 |
| `long size(File)` / `(String)` | 文件大小 | `File.length` |
| `String tmpPath()`、`File tmpdir()`、`File newTmpFile(String)`、`File createTempFile(String,boolean)` | 临时文件 | 基于 `java.io.tmpdir` / `Files.createTempFile` |
| `int getUnixMode(Path)` | 获取 Unix 权限 | `Files.getPosixFilePermissions` 转八进制，非 POSIX 回退 0755/0644 |
| `int posixPermissionsToInt(Set<PosixFilePermission>)` | 权限转八进制 | 逐位拼 0400/0200/… |
| `void calcDir(File,LongAdder,LongAdder,BiConsumer,Function)` | 统计目录 | 递归累加文件数与大小，可过滤与回调 |
| `void clearDir(File,LongAdder,LongAdder,BiConsumer,Function)` | 清理目录并统计 | 递归删除文件并累加统计 |

- 调用链：`FileUtil.copy(File,File,boolean) → copyDirectory/copyFile → Files.copy`
- 调用链：`FileUtil.createTempFile → Files.createTempFile`
- 调用链：`ArchiveUtil.createZip → FileUtil.getUnixMode → posixPermissionsToInt`

## FileNameUtil

- 职责：文件名/路径工具类，提供扩展名处理、类型判断、路径拼接与解析。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | 全部为静态方法 |

- 方法（按功能分组）：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String extName(String)` / `(File)` | 取扩展名 | 最后一个 `.` 之后的部分，无点返回空串 |
| `String removeExtName(String)` | 移除扩展名 | 截断到最后一个 `.` |
| `boolean isType(String,String...)` | 扩展名是否匹配 | 忽略大小写比较 |
| `String getSuffix(String)` | 取文件名后缀 | 最后一个 `.` 之后，无点返回 null |
| `String parent(String)` | 取父路径 | 基于 `/` 截断 |
| `String name(String)` | 取文件名部分 | 基于最后一个 `/` 截断 |
| `String concat(String...)` | 追加路径 | 用 `File.separator` 拼接，Windows 去首分隔符 |
| `String concat(String,String)` | 目录+文件名拼接 | 统一用 `/`，自动处理重复/缺失分隔符 |
| 大量 `isXxxType(String)` | 文件类型判断 | 约 150 个，按扩展名忽略大小写判断。代表：`isSqlType`(sql)、`isXmlType`(xml)、`isCsvType`、`isHtmlType`、`isWordType`(doc/docx)、`isExcelType`(xls/xlsx/excel)、`isPdfType`、`isImageType`(jpg/jpeg/png/gif/bmp)、`isCompressType`(zip/rar/7z/tar.gz/xz/gz/tgz)、`isJavaType`、`isCType`(c/h)、`isCppType`(cpp/hpp/hxx)、`isVimType`(vim/vimrc…)、`isDsstoreType`(ds_store) 等 |

- 调用链：`ArchiveUtil.createZip → FileNameUtil.isDsstoreType(FileNameUtil.extName(entryName))`
- 调用链：`WorkbookHelper.create(File) → FileNameUtil.getSuffix → create(boolean,File)`
- 调用链：`JulUtil.getLogFile → FileNameUtil.concat(filePath, fileName)`

## FastFileWriter

- 职责：快速文件写入器，每次写入后立即 flush，实现 `Closeable`。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| writer | FileWriter | 底层文件写入器 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `FastFileWriter(String)` / `(File)` | 构造（UTF-8） | 委托 `(File, StandardCharsets.UTF_8)` |
| `FastFileWriter(File,Charset)` | 构造 | `new FileWriter(file, charset)` |
| `void appendLine(String)` | 追加一行并刷新 | 缺换行则补 `\n`，`writer.append` 后 `fulsh()` |
| `void appendLines(Collection<String>)` | 批量追加并刷新 | 拼接各行后 `append` + `fulsh` |
| `void writeLine(String)` | 写入一行并刷新 | 写内容后调用 `appendLine` |
| `void writeLines(Collection<String>)` | 批量写入 | 拼接后 `writer.write`，不刷新 |
| `void fulsh()` | 刷新缓冲 | `writer.flush()`（方法名拼写如此） |
| `void close()` | 关闭 | flush 后 close，异常打印 |

- 调用链：`FastFileWriter.writeLine → appendLine → fulsh → FileWriter.flush`

## LineFileWriter

- 职责：按行写文件的写入器，实现 `AutoCloseable`。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| writer | BufferedWriter | 底层缓冲写入器 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `LineFileWriter(String)` / `(File)` | 构造（UTF-8） | 委托 `(File, StandardCharsets.UTF_8)` |
| `LineFileWriter(File,Charset)` | 构造 | `FileUtil.getWriter(file, charset, false)` |
| `void write(String)` | 写入不换行 | `writer.write` |
| `void writeLine(String)` | 写入一行 | 追加系统换行符 |
| `void close()` | 关闭 | `writer.close` |
| `LineFileWriter create(String,String)` / `(String,Charset)` / `(File,String)` / `(File,Charset)` | 静态工厂 | new LineFileWriter |

- 调用链：`LineFileWriter(构造) → FileUtil.getWriter`

## PropertiesFile

- 职责：属性文件对象，继承 `Properties`，支持从类路径资源加载。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | 继承 Properties |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `PropertiesFile()` | 空构造 | `super()` |
| `PropertiesFile(String)` | 从资源加载 | `ResourceUtil.getResourceAsStream(fileName)` 后 `load` |

- 调用链：`Project.load → PropertiesFile(String) → ResourceUtil.getResourceAsStream`

## SkipAbleFileReader

- 职责：可跳过行的文件读取器，支持自定义换行符与行号跟踪，实现 `AutoCloseable`。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| reader | BufferedReader | 底层缓冲读取器 |
| currentLine | int | 当前已读取行号 |
| lineBreak | String | 自定义换行符，null 时用默认行为 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `SkipAbleFileReader(String)` / `(File)` | 构造（UTF-8） | 委托带 charset |
| `SkipAbleFileReader(String,Charset)` / `(File,Charset)` | 构造 | `FileUtil.getReader` |
| `boolean ready()` | 是否就绪 | `reader.ready()` |
| `void skipLine()` / `skipLine(int)` | 跳过行 | 循环 `readLine` |
| `void jumpLine(int)` | 跳到指定行 | `skipLine(toLine - currentLine)` |
| `String _readLine()` | 读一行（私有） | 自定义换行符时按字符/双字符匹配读取，否则 `reader.readLine()` |
| `String readLine()` | 读一行并计行号 | `_readLine` 后 currentLine++ |
| `List<String> readLines(int)` | 读指定行数 | 循环 readLine 收集 |
| `int read()` | 读一个字符 | `reader.read()` |
| `void lineBreak(String)` | 设置换行符 | 直接赋值 |
| `void close()` | 关闭 | `reader.close` |

- 调用链：`SkipAbleFileReader.skipLine → readLine → _readLine`
