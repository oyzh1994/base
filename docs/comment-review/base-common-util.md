# cn.oyzh.common.util

> 说明：BitUtil、DecimalFormatWrapper、RegexHelper 整文件被注释掉，属死代码，未纳入本审查。

## ArrayUtil

- 职责：数组操作工具类，提供取值、判空、合并、截取、复制、反转、转换与拼接。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ArrayUtil()` | 私有构造，禁止实例化 | 空实现 |
| `T first(T[])` | 获取首个元素 | null 或长度为 0 时返回 null，否则 `arr[0]` |
| `T last(T[])` | 获取最后一个元素 | null 或长度为 0 时返回 null，否则 `arr[length-1]` |
| `T[] append(T[], T[])` | 合并两个数组为新数组 | `Arrays.copyOf` 扩容 + `System.arraycopy` 拷入 |
| `boolean isEmpty(T[])` / `boolean isNotEmpty(T[])` | 判断数组是否为空/不为空 | null 或长度 0；后者取反 |
| `T indexOf(T[], int)` | 取指定索引元素 | `index < 0 || index >= length` 返回 null |
| `String toString(T[])` | 数组转字符串 | 空数组返回 ""，否则 `Arrays.toString` |
| `boolean contains(T[], T)` | 是否包含指定元素 | 遍历并用 `equals` 比较，obj 为 null 返回 false |
| `T[] sub(T[], int, int)` / `T[] subarray(T[], int, int)` | 截取数组（含头不含尾） | 参数非法时原样返回，否则 `Arrays.copyOfRange` |
| `byte[] sub(byte[], int, int)` / `byte[] subarray(byte[], int, int)` | 截取字节数组 | 同上，针对 byte[] |
| `T[] toArray(Collection<T>, Class<T>)` | 集合转数组 | 集合或类型为 null 返回 null；`Array.newInstance` 建空数组后 `toArray` |
| `void copy(byte[], byte[])` | 复制字节数组到目标数组 | `System.arraycopy` 全量拷贝 |
| `byte[] copy(byte[], int)` | 复制指定长度的字节数组 | 新建 byte[length] 后 `System.arraycopy` |
| `char[] reverse(char[])` | 反转字符数组 | 转 `ArrayList` 后 `list.reversed()` 再回填 |
| `String join(T[], String)` | 以分隔符拼接数组 | 拼接后 `substring(1)` 去掉首个分隔符 |

- 调用链：`ArrayUtil.append → Arrays.copyOf → System.arraycopy`
- 调用链：`ArrayUtil.toArray → Array.newInstance → Collection.toArray`
- 调用链：`ArrayUtil.reverse → List.reversed`

## Base64Util

- 职责：基于 JDK 的 Base64 编解码工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `byte[] decode(String)` | 解码 Base64 字符串 | `Base64.getDecoder().decode` |
| `byte[] encode(byte[])` | 编码为 Base64 字节数组 | `Base64.getEncoder().encode` |
| `String encodeToString(byte[])` | 编码为 Base64 字符串 | `Base64.getEncoder().encodeToString` |

- 调用链：`Base64Util.decode → Base64.getDecoder().decode`
- 调用链：`Base64Util.encodeToString → Base64.getEncoder().encodeToString`

## BooleanUtil

- 职责：Boolean 真值判断工具，兼顾 null 安全。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `BooleanUtil()` | 私有构造，禁止实例化 | 空实现 |
| `boolean isTrue(Boolean)` | 是否为 true | `bool != null && bool` |
| `boolean isFalse(Boolean)` | 是否为 false | `bool != null && !bool` |

- 调用链：`BooleanUtil.isTrue → Boolean 拆箱比较`

## CharsetUtil

- 职责：字符集获取、名称兼容与字符串字符集转换工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| CHARSET_GBK | Charset | GBK 字符集常量（由 `charset("gbk")` 得到） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `CharsetUtil()` | 私有构造，禁止实例化 | 空实现 |
| `Charset defaultCharset()` | 获取系统默认字符集 | `Charset.defaultCharset` |
| `String defaultCharsetName()` | 获取系统默认字符集名称 | `Charset.defaultCharset().displayName` |
| `Charset fromName(String)` | 按名称获取字符集并做兼容 | 对 utf8/utf-8、iso-8859-1、gbk、gb2312 特判，其余 `Charset.forName` |
| `String convert(String, Charset, Charset)` | 转换字符串字符集 | 委托 `TextUtil.changeCharset` |
| `Charset charset(String)` | 按名称获取字符集 | 名称为空返回 null，否则 `Charset.forName` |

- 调用链：`CharsetUtil.fromName → StringUtil.equalsAnyIgnoreCase → Charset.forName`
- 调用链：`CharsetUtil.convert → TextUtil.changeCharset`

## ClassUtil

- 职责：类的实例化、接口获取、按包扫描与原始类型判断工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ClassUtil()` | 私有构造，禁止实例化 | 空实现 |
| `T newInstance(Class<T>)` | 用 public 无参构造实例化 | 遍历 `getConstructors`，仍可用则 `Constructor.newInstance`，异常返回 null |
| `List<Class<?>> getInterfaces(Class<?>)` | 获取类（含父类）实现的全部接口 | 逐级向父类循环，用 `HashSet` 去重，私有重载递归收集 |
| `void getInterfaces(Class<?>, Set<Class<?>>)` | 私有递归收集接口 | 递归 `getInterfaces` |
| `List<Class<?>> scanClasses(String, Predicate<Class<?>>)` | 扫描包下所有类 | 经 `ClassLoader.getResources` 按 file/jar 协议处理，file 走目录扫描、jar 走 `JarFile.entries` + `Class.forName` |
| `void findClassesInDirectory(File, String, List<Class<?>>, Predicate<Class<?>>)` | 递归查找目录下 .class | 拼全限定名后 `Class.forName`，子目录递归 |
| `Class<?> forName(String)` | 按全限定名加载类 | `Class.forName`，`ClassNotFoundException` 打印栈并返回 null |
| `boolean isPrimitiveType(Class<?>)` | 是否八种原始类型 | 与 boolean/int/byte/short/char/double/float/long 逐一比较 |

- 调用链：`ClassUtil.newInstance → Constructor.newInstance`
- 调用链：`ClassUtil.scanClasses → findClassesInDirectory → Class.forName`

## CollectionUtil

- 职责：集合/Map 的判空、取元素、分割、随机、拼接与排序工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `CollectionUtil()` | 私有构造，禁止实例化 | 空实现 |
| `T indexOf(Collection<T>, int)` | 取指定索引元素 | 注意 index 须 `> 0` 才有效，遍历计数匹配 |
| `T get(List<T>, int)` / `T get(Collection<T>, int)` | 取指定索引元素 | 索引合法（`>= 0`）时返回，越界返回 null |
| `boolean isEmpty(Collection<?>)` / `isNotEmpty(Collection<?>)` | 集合判空 | null 或 isEmpty；后者取反 |
| `boolean isEmpty(Map<?,?>)` / `isNotEmpty(Map<?,?>)` | Map 判空 | null 或 isEmpty；后者取反 |
| `List<List<T>> split(Collection<T>, int)` | 按 limit 分组 | 不超过 limit 时单组；否则步长 limit `subList` |
| `List<List<T>> splitIntoParts(List<T>, int)` | 均分为 n 份 | `n<=0` 抛异常；n 超集合大小则取集合大小；余数分摊到前几组 |
| `List<List<T>> splitBySize(List<T>, int)` | 按固定 size 分组 | `size<=0` 抛异常；`IntStream.range` + `subList` |
| `boolean contains(List<?>, Object)` | 是否包含元素 | `list != null && list.contains` |
| `T getFirst(List<T>)` / `T getFirst(Collection<T>)` | 取首个元素 | List 用 `getFirst`，Collection 用迭代器首个，空返回 null |
| `T getLast(List<T>)` | 取末尾元素 | `list.getLast`，空返回 null |
| `String join(Collection<String>, String)` | 以分隔符拼接 | 集合或分隔符为 null 返回 null，否则 `String.join` |
| `void removeBlank(List<String>)` | 移除空白字符串 | `removeIf(StringUtil::isBlank)` |
| `T removeRandom(Map<?,T>)` | 移除并返回随机元素 | `Random.nextInt` 取 key 后 `map.remove` |
| `T removeRandom(List<T>)` | 移除并返回随机元素 | `Random.nextInt` 取索引后 `list.remove` |
| `T getRandom(List<T>)` | 获取随机元素 | `Random.nextInt` + `get` |
| `int size(Collection<?>)` | 集合大小 | 空集合返回 0 |
| `ArrayList<T> newArrayList()` | 新建 ArrayList | `new ArrayList<>` |
| `List<T> sort(Collection<T>, Comparator<T>)` | 排序 | 复制为 ArrayList 后 `list.sort` |

- 调用链：`CollectionUtil.splitIntoParts → List.subList`
- 调用链：`CollectionUtil.splitBySize → IntStream.range → List.subList`
- 调用链：`CollectionUtil.removeRandom(Map) → get(Collection,int) → Map.remove`

## ColorUtil

- 职责：颜色转换工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ColorUtil()` | 私有构造，禁止实例化 | 空实现 |
| `String rgbToHex(int, int, int)` | RGB 转 #RRGGBB | 各分量 `Integer.toHexString` 后补零到两位并大写 |

- 调用链：`ColorUtil.rgbToHex → Integer.toHexString`

## Competitor

- 职责：限定最大并发数量的竞争（轻量锁）器。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| max | int | 允许的最大竞争（同时持有）数量（final） |
| list | List<Object> | 当前持有的竞争对象列表（final，`CopyOnWriteArrayList`） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Competitor()` | 默认构造，最大数量为 1 | 委托 `this(1)` |
| `Competitor(int)` | 指定最大数量构造 | `max<=0` 抛 `InvalidParamException`，初始化 `CopyOnWriteArrayList` |
| `boolean tryLock(Object)` | 尝试锁定 | 自旋等待 `size() < max`（`ThreadUtil.sleep(5)`），`synchronized` 内 `add`，恒返回 true |
| `boolean release(Object)` | 释放锁定 | 列表含该对象则 `synchronized` 内 `remove` 并返回 true，否则 false |

- 调用链：`Competitor.tryLock → ThreadUtil.sleep → List.add`
- 调用链：`Competitor.release → List.contains → List.remove`

## CostUtil

- 职责：基于调用点（文件名.方法名）的耗时统计工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| COST_RECORD | Map<String, Long> | 记录各调用点起始时间的并发映射（`ConcurrentHashMap`） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `CostUtil()` | 私有构造，禁止实例化 | 空实现 |
| `void record()` | 记录起始时间 | 取自当前线程堆栈第 2 层，键为 “文件名.方法名”，值为 `System.currentTimeMillis` |
| `void printCost()` | 打印距上次 record 的耗时并清除记录 | 计算差值，`JulLog.info("{}= {}ms")` 输出，移除记录 |

- 调用链：`CostUtil.printCost → Thread.currentThread().getStackTrace → JulLog.info`

## CoverUtil

- 职责：项目类路径解析与类扫描（覆盖率/测试场景使用）。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String getClassesPath(String)` | 获取项目 classes 路径 | 截断 test-classes / classes 段，按平台剥离 `file:/` 前缀后拼 "classes"（调用 `OSUtil.isWindows`） |
| `List<Class<?>> getClasses(String)` | 获取项目中的类 | 委托 `ClassUtil.findClassesInDirectory`，过滤掉 abstract 与非 public 的类 |

- 调用链：`CoverUtil.getClasses → ClassUtil.findClassesInDirectory`
- 调用链：`CoverUtil.getClassesPath → OSUtil.isWindows`

## HexUtil

- 职责：十六进制字符串与字节数组互转工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `HexUtil()` | 私有构造，禁止实例化 | 空实现 |
| `String bytesToHex(byte[])` | 字节数组转十六进制（默认大写） | 委托 `bytesToHex(bytes, true)` |
| `String bytesToHex(byte[], boolean)` | 字节数组转十六进制 | 逐字节 `String.format("%02x")`，按 toUpperCase 决定大小写 |
| `String encodeHexStr(byte[], boolean)` | 编码为 hex 字符串 | toLowerCase 时调 `bytesToHex(bytes,false)`，否则 `bytesToHex(bytes,true)` |
| `byte[] decodeHexStr(String)` | 十六进制转字节数组 | 每两字符 `Character.digit` 组合 |

- 调用链：`HexUtil.bytesToHex(byte[]) → bytesToHex(byte[], boolean)`
- 调用链：`HexUtil.decodeHexStr → Character.digit`

## HttpUtil

- 职责：HTTP 相关辅助工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String basic(String, String)` | 生成 Basic 认证头 | 拼 `username:password` 后 `Base64.getEncoder().encodeToString`，加前缀 "Basic " |

- 调用链：`HttpUtil.basic → Base64.getEncoder().encodeToString`

## IOUtil

- 职责：IO 流的读写、复制、保存与安全关闭工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `IOUtil()` | 私有构造，禁止实例化 | 空实现 |
| `void closeQuietly(AutoCloseable)` | 静默关闭 | 委托 `close` |
| `void close(AutoCloseable)` | 关闭并忽略异常 | `closeable.close()` 捕获忽略 |
| `void closeAsync(AutoCloseable)` | 异步关闭 | `ThreadUtil.startVirtual` 内调 `closeQuietly` |
| `byte[] readBytes(InputStream)` | 读取流全部字节 | `stream.readAllBytes`，失败返回 null |
| `byte[] readBytes(String)` | 读取文件全部字节 | `new FileInputStream` 后委托 `readBytes(InputStream)` |
| `String readString(InputStream, Charset)` | 按字符集读取文本 | `readBytes` + `new String(bytes, charset)` |
| `String readUtf8String(InputStream)` | 按 UTF-8 读取文本 | `readString(stream, UTF_8)` |
| `String readDefaultString(InputStream)` | 按系统默认字符集读取文本 | `readString(stream, Charset.defaultCharset())` |
| `InputStream toStream(byte[])` | 字节数组转输入流 | `new ByteArrayInputStream` |
| `void saveToFile(InputStream, String)` | 输入流保存到文件 | `FileUtil.exists/touch` 后循环 4K 缓冲写入并关闭 |
| `void saveToStream(InputStream, OutputStream)` | 输入流复制到输出流 | 循环 4K 缓冲读写 |
| `byte[] readAtMost(InputStream, int)` | 最多读取 max 字节 | `ByteArrayOutputStream` 累积，不足 max 提前结束，抛 IOException |

- 调用链：`IOUtil.saveToFile → FileUtil.touch → FileOutputStream.write`
- 调用链：`IOUtil.closeAsync → ThreadUtil.startVirtual → IOUtil.closeQuietly`
- 调用链：`IOUtil.readUtf8String → readString → readBytes`

## JFXUtil

- 职责：为 JavaFX 在 Windows 上加载微软运行时动态库提供名称列表。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| msLibNames | String[] | Windows 微软运行时 dll 名称数组（不含扩展名，静态常量） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `List<String> msLibNames()` | 获取带 .dll 后缀的库名称列表 | 遍历 `msLibNames` 逐个拼接 ".dll" |

- 调用链：`JFXUtil.msLibNames → msLibNames 数组遍历`

## JarUtil

- 职责：JAR 运行环境判断、路径解析与解压工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| isInJar | Boolean | 是否运行在 jar 中的缓存结果（静态） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `JarUtil()` | 私有构造，禁止实例化 | 空实现 |
| `String getJarDir()` | 获取 JAR 所在目录 | 取 `getJarPath` 后截断到最后一个 "/" |
| `String getJarPath()` | 获取 JAR 文件路径 | 经 `ProtectionDomain/CodeSource` 的 URL，处理 `file:/`、`nested:/` 前缀，非 jar 返回 null |
| `boolean isInJar()` | 是否运行在 jar 中 | 双检锁缓存；判断 CodeSource 协议是否为 "jar"，异常时用 class 资源路径兜底 |
| `boolean hasClass(String)` | JAR 中是否含 class | 校验文件存在/为文件后用 `JarInputStream` 遍历条目 |
| `File unJar(String, String)` | 解压 jar | 校验后委托 `CompressUtil.unzip` |
| `boolean isJar(File)` / `boolean isJar(String)` | 是否 jar 文件 | 名称经 `FileNameUtil.extName` + `FileNameUtil.isJarType` |
| `boolean isClass(File)` / `boolean isClass(String)` | 是否 class 文件 | 名称经 `FileNameUtil.extName` + `FileNameUtil.isClassType` |

- 调用链：`JarUtil.getJarDir → getJarPath → CodeSource.getLocation`
- 调用链：`JarUtil.unJar → CompressUtil.unzip`
- 调用链：`JarUtil.isJar(String) → FileNameUtil.isJarType`

## MD5Util

- 职责：MD5 摘要计算工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `MD5Util()` | 私有构造，禁止实例化 | 空实现 |
| `String md5Hex(String)` | 计算 MD5 并转小写十六进制 | `MessageDigest.getInstance("MD5")`，`update`/`digest` 后逐字节 `%02x` |

- 调用链：`MD5Util.md5Hex → MessageDigest.getInstance("MD5") → digest`

## NumberUtil

- 职责：数字的格式化、解析、比较、换算与取整工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `NumberUtil()` | 私有构造，禁止实例化 | 空实现 |
| `String formatSize(long)` | 格式化大小 | 委托 `formatSize((double)size, null)` |
| `String formatSize(double, Integer)` | 格式化带单位大小 | 按 1024 进位选 B/KB/MB/GB/TB，可选 `DecimalFormat` 保留小数 |
| `double parseSize(String)` | 解析带单位大小 | 识别 TB/GB/MB/KB/B 前缀并乘对应系数 |
| `double scale(double, Integer)` | 保留小数 | `DecimalFormat` 格式化后去逗号再 `parseDouble` |
| `boolean isLT/isLTEq/isEq/isGT/isGTEq(Number, Number)` | 数值比较 | 任一端 null 返回 false，按 `doubleValue` 比较 |
| `double limit(double, double, double)` | 限制区间 | `Math.max` 后 `Math.min` |
| `boolean checkBound(double, double, double, double)` | 区间是否交叉 | 覆盖单点/相同/包含/被包含/左右交叉多种情况 |
| `Long parseLong(String)` | 转 Long | null 返回 null，否则 `Long.parseLong` |
| `Double parseDouble(String)` | 转 Double | null 返回 null，否则 `Double.parseDouble` |
| `Number parseNumber(String)` | 智能转 Number | `RegexUtil.isDecimal` → Double；`isNumber` → Long；兜底 Double |
| `BigDecimal parseBigDecimal(String)` | 转 BigDecimal | null 返回 null，否则 `new BigDecimal` |
| `double round(double, int)` | 四舍五入 | `BigDecimal.setScale(scaleLen, HALF_UP)` |
| `boolean isLess(BigDecimal, BigDecimal)` | BigDecimal 小于判断 | 任一端 null 返回 false，`compareTo < 0` |
| `String getBinaryStr(int)` | 取二进制字符串 | `Integer.toBinaryString` |
| `int toInt(String)` / `long toLong(String)` / `double toDouble(String)` | 字符串转基本数值 | 分别调 `Integer/Long/Double.parse*` |

- 调用链：`NumberUtil.parseNumber → RegexUtil.isDecimal / isNumber`
- 调用链：`NumberUtil.formatSize(double, Integer) → DecimalFormat.format`
- 调用链：`NumberUtil.round → BigDecimal.setScale(RoundingMode.HALF_UP)`

## ObjectUtil

- 职责：对象空值兜底工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `T nullOrElse(Object, Object)` | 首个为 null 时返回第二个 | `Objects.isNull(value)` 决定返回 value 或 object |

- 调用链：`ObjectUtil.nullOrElse → Objects.isNull`

## Pool

- 职责：抽象对象池，提供最小/最大容量、借用与归还能力。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| minSize | int | 池中对象的最小数量 |
| maxSize | int | 池中对象的最大数量 |
| list | List<T> | 池中的对象列表（懒初始化为 `CopyOnWriteArrayList`） |
| waitingBorrow | boolean | 无可用对象时是否一直等待借用 |
| listLock | Object | 借用/归还的同步锁对象（private final） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Pool(int, int)` | 构造池 | 校验 minSize/maxSize 合法性与大小关系，非法抛 `InvalidParamException` |
| `boolean isWaitingBorrow()` / `void setWaitingBorrow(boolean)` | 读写等待借用开关 | 直接读写字段 |
| `int getMinSize()` / `void setMinSize(int)` | 读写最小数量 | setter 校验 `<= maxSize` 且 `>= 0` |
| `int getMaxSize()` / `void setMaxSize(int)` | 读写最大数量 | setter 校验 `>= minSize` 且 `>= 0` |
| `List<T> list()` | 获取对象列表 | 未初始化时创建 `CopyOnWriteArrayList` |
| `void list(List<T>)` | 设置对象列表 | 直接赋值 |
| `void clear()` | 清空列表 | `list.clear` |
| `boolean isEmpty()` | 列表是否为空 | 委托 `CollectionUtil.isEmpty` |
| `int size()` | 列表长度 | 委托 `CollectionUtil.size` |
| `boolean isFull()` | 池是否已满 | `size() >= getMaxSize()` |
| `void init()` | 补齐到最小数量 | 同步方法，循环 `newObject`，连续失败超 3 次（`JulLog.warn`）退出 |
| `abstract T newObject()` | 创建新对象（抽象） | 由子类实现，可抛异常 |
| `void returnObject(T)` | 归还对象 | 对象非空且池未满时加锁 `add` |
| `T borrowObject()` | 借用对象 | 空则先 `init`；`waitingBorrow` 时自旋等待（`ThreadUtil.sleep(5)`，上限 1000 次）；`removeFirst` 取对象 |

- 调用链：`Pool.borrowObject → Pool.init → Pool.newObject`
- 调用链：`Pool.isEmpty → CollectionUtil.isEmpty`
- 调用链：`Pool.returnObject → listLock 加锁 → List.add`

## ReflectUtil

- 职责：反射读写字段、查找/调用方法的工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| objectMethodNames | List<String> | Object 类方法名缓存（静态） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ReflectUtil()` | 私有构造，禁止实例化 | 空实现 |
| `T getFieldValue(Object, String)` | 取对象字段值 | `getField` 查字段后 `getFieldValue` |
| `T getFieldValue2(Object, String)` | 取对象字段值（含父类） | `getField2` 查字段后 `getFieldValue` |
| `T getFieldValue(Field, Object)` | 取字段值 | 字段为 null 返回 null，否则 `setAccessible` + `field.get`，异常抛 `RuntimeException` |
| `void setFieldValue(Field, Object, Object)` | 设置字段值 | `setAccessible` + `field.set`，异常打印栈 |
| `void setFieldValue(String, Object, Object)` | 按字段名设置值 | object 为 Class 时按静态字段处理，否则按实例字段 |
| `void setFieldValue2(String, Object, Object)` | 按字段名设置值（含父类） | 同上但用 `getField2` |
| `void clearFieldValue(Field, Object)` | 清空字段（置 null） | `setAccessible` + `field.set(null)`，抛 Security/IllegalAccess |
| `Field getField(Class<?>, String)` | 获取字段（含非 public） | 委托 `getField(..., true, false)` |
| `Field getField2(Class<?>, String)` | 获取字段（含父类） | 委托 `getField(..., true, true)` |
| `Field getField(Class<?>, String, boolean, boolean)` | 获取字段 | 逐级（可选父类）先 `getField` 再 `getDeclaredField` |
| `Field[] getFields(Class<?>, boolean, boolean)` | 获取字段数组 | 逐级收集，用 `ArrayUtil.append` 合并 |
| `Method getMethod(Object, String, Class<?>...)` | 获取方法 | 委托 `getMethod(Class, ..., true, false, ...)` |
| `Method getMethod(Class<?>, String, Class<?>...)` | 获取方法 | 委托 `getMethod(Class, ..., true, false, ...)` |
| `Method getMethod(Class<?>, String, boolean, boolean, Class<?>...)` | 获取方法 | 逐级（可选父类）先 `getMethod` 再 `getDeclaredMethod` |
| `Method[] getMethods(Class<?>, boolean, boolean)` | 获取方法数组 | 逐级收集，用 `ArrayUtil.append` 合并 |
| `Object invoke(Object, String, Object...)` | 按方法名调用 | 按实参 `getClass` 推参数类型查方法后调用 |
| `Object invoke(Object, Method, Object...)` | 调用方法 | 方法为 null 抛 NPE，`setAccessible` 后 `invokeOnly` |
| `Object invokeOnly(Object, Method, Object...)` | 仅执行调用 | `method.invoke(obj, params)`，异常打印栈返回 null |
| `List<String> objectMethodNames()` | 获取 Object 方法名列表 | 懒加载，含 toString/notify/wait/getClass/hashCode/equals/clone 等 |

- 调用链：`ReflectUtil.getFieldValue → ReflectUtil.getField → Class.getDeclaredField`
- 调用链：`ReflectUtil.getFields → ArrayUtil.append`
- 调用链：`ReflectUtil.invoke(Object,String,Object...) → getMethod → invoke → invokeOnly`

## RegexUtil

- 职责：常用正则常量及数字/IP/搜索模式判断工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| IPV4_REGEX / IPV4_PATTERN | String / Pattern | IPv4 正则及其编译对象 |
| NUMBER_REGEX / NUMBER_PATTERN | String / Pattern | 整数正则（`-?\d+`）及其编译对象 |
| DECIMAL_REGEX / DECIMAL_PATTERN | String / Pattern | 小数正则（`-?\d+(\.\d*)?`）及其编译对象 |
| MOBILE_REGEX / MOBILE_PATTERN | String / Pattern | 中国大陆手机号正则及其编译对象 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `RegexUtil()` | 私有构造，禁止实例化 | 空实现 |
| `boolean isDecimal(String)` | 是否小数 | 空返回 false，剥去正负号后 `DECIMAL_PATTERN.matches` |
| `boolean isNumber(String)` | 是否整数 | 同上但用 `NUMBER_PATTERN` |
| `boolean isIPV4(String)` | 是否 IPv4 | 空返回 false，`IPV4_PATTERN.matches` |
| `Pattern createSearchPattern(String, boolean, boolean, boolean)` | 构建 IDE 风格搜索 Pattern | 搜索文本空抛异常；不区分大小写加 `CASE_INSENSITIVE`/`UNICODE_CASE`；非正则用 `Pattern.quote`，全词用 `\b` 包裹 |

- 调用链：`RegexUtil.isDecimal → StringUtil.isBlank → DECIMAL_PATTERN.matcher`
- 调用链：`RegexUtil.createSearchPattern → Pattern.quote / Pattern.compile`

## ResourceUtil

- 职责：类路径资源定位、路径转换与目录遍历工具。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ResourceUtil()` | 私有构造，禁止实例化 | 空实现 |
| `URL getResource(String)` | 获取资源 URL | 依次尝试 `getResource`、ClassLoader、去前导 "/" 三种方式 |
| `InputStream getResourceAsStream(String)` | 获取资源流 | 同上三种方式获取 InputStream |
| `String toExternalUrl(String)` | 转为物理地址 | `getResource` 后 `toExternalForm` |
| `String toExternalFile(String)` | 转为物理文件路径 | 取 externalForm，剥离 "file:/" 前缀 |
| `List<String> toExternalUrl(String[])` / `List<String> toExternalUrl(List<String>)` | 批量转物理地址 | 数组转 List 后逐项 `toExternalUrl` |
| `String getLocalFileUrl(String)` | 获取本地文件 URL | 反斜杠转斜杠，按 `OSUtil.isWindows` 决定前缀 |
| `String getPath(String)` | 获取本地路径 | 委托 `getPath(url, ResourceUtil.class)` |
| `String getPath(String, Class<?>)` | 获取本地路径 | `clazz.getResource` 后取 URI path，Windows 下去前导 "/" |
| `List<String> listFiles(String)` | 遍历资源目录全部文件 | file 协议用 `Files.walk`；jar 协议用 `JarFile.stream` 前缀过滤；其他协议抛异常；结果排序 |

- 调用链：`ResourceUtil.listFiles → Files.walk 或 JarFile.stream`
- 调用链：`ResourceUtil.getLocalFileUrl → OSUtil.isWindows`
- 调用链：`ResourceUtil.toExternalUrl(List) → toExternalUrl(String) → getResource`

## StringUtil

- 职责：字符串判空、比较、包含、前后缀、拼接、替换、相似度等综合工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| SPACE | String | 空格常量 " " |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `StringUtil()` | 私有构造，禁止实例化 | 空实现 |
| `String toBinary(String)` | 字符串转二进制 | null 返回 ""，否则 `toBinary(str.getBytes())` |
| `String toBinary(byte[])` | 字节数组转二进制 | 逐字节展开 8 位 |
| `String toBinary(Byte[])` | 包装字节数组转二进制 | 跳过 null 元素后逐字节展开 8 位 |
| `void deleteLast(StringBuilder, String)` | 删除最后一个指定子串 | 存在时 `deleteCharAt(lastIndexOf)` |
| `void deleteLast(StringBuilder)` | 删除最后一个字符 | `deleteCharAt(length-1)` |
| `boolean isBlank(String)` / `isEmpty(String)` | 是否空白/为空 | null 或 isBlank/isEmpty |
| `boolean isAnyBlank(String...)` | 是否任一空白 | 遍历判断 |
| `boolean isNotBlank(String)` / `boolean isNotBlank(String...)` | 是否非空白 | 单参取反；变参要求全部非空白 |
| `boolean isNotEmpty(String)` | 是否非空 | `!isEmpty` |
| `boolean equals/notEquals/equalsIgnoreCase(String, String)` | 相等/不等/忽略大小写相等 | 忽略大小写时双非 null 用 `equalsIgnoreCase`，否则 `Objects.equals` |
| `boolean equalsAny/equalsAnyIgnoreCase(String, String...)` | 是否等于任一目标 | 遍历目标比较 |
| `boolean contains(String, String)` / `containsIgnoreCase` | 是否包含 | `source.contains` / 转小写后 contains |
| `boolean containsReverse(String, String)` / `containsIgnoreCaseReverse(String, String)` | 是否互相包含 | 双向 contains 取或 |
| `boolean notContains(String, String)` | 是否不包含 | `!contains` |
| `boolean containsAny/containsAnyIgnoreCase(String, String...)` | 是否包含任一目标 | 遍历目标 contains |
| `boolean startWith/startWithIgnoreCase(String, String)` | 是否以目标开头 | `startsWith`（前者对 target 转小写） |
| `boolean startWithAny/startWithAnyIgnoreCase(String, String...)` | 是否以任一目标开头 | 遍历调 startWith |
| `boolean endsWith/endsWithIgnoreCase(String, String)` | 是否以目标结尾 | `endsWith` / 转小写后 endsWith |
| `boolean endsWithAny(String, String...)` / `endWithAny(String, String...)` | 是否以任一目标结尾 | 遍历 endsWith |
| `boolean endWithIgnoreCase/endWithAnyIgnoreCase(String, String...)` | 忽略大小写结尾判断 | 遍历 `endWithIgnoreCase` |
| `String[] split(String, int)` | 按固定长度切分 | len<=0 返回原串数组；实现含边界处理逻辑 |
| `List<String> split(String, String)` | 按正则切分 | `List.of(str.split(regex))` |
| `String emptyToDefault/blankToDefault/nullToDefault(String, String)` | 空值时返回默认值 | 分别按 isEmpty/isBlank/null 判定 |
| `String replace(String, String, String)` | 替换子串 | 三参数均非空时 `src.replace`，否则原样返回 |
| `String replaceOneTime(String, String, String)` | 替换首次出现 | 委托 `replaceNTimes(..., 1)` |
| `String replaceNTimes(String, String, String, int)` | 替换指定次数 | 循环 `indexOf` 定位并拼接替换 |
| `String replaceLast(String, String, String)` | 替换最后一次出现 | `lastIndexOf` 拼接前后段 |
| `String delete(String, int, int)` | 删除区间字符 | `StringBuilder.delete` |
| `long count(String, String)` / `long count(String, char)` | 统计出现次数 | 循环 `indexOf` / 逐字符比较 |
| `String lowerFirst/upperFirst(String)` | 首字母小写/大写 | 取首字符变换后拼接余下 |
| `String join(String, Collection<?>)` | 拼集合 | 分隔符 + 元素，最后截去首分隔符 |
| `String join(String, Object[])` / `String join(String, String[])` | 拼数组 | 转 List 后委托集合版本 |
| `int levenshteinDistance(String, String)` | 编辑距离 | 动态规划二维数组 |
| `double similarity(String, String)` | 相似度（0~1） | `1 - distance / maxLength` |
| `boolean checkCountOccurrences(String, char, int)` | 出现次数是否达标 | 达到阈值立即返回 true |
| `int length(String)` | 字符串长度 | null 返回 0 |
| `String emptyToNull(String)` | 空转 null | isEmpty 时返回 null |
| `int compare(CharSequence, CharSequence, boolean)` | 比较大小 | 处理 null（nullIsLess 决定 null 大小）后 `compareTo` |
| `String removeLast(String)` | 移除最后一个字符 | 空返回原串，否则 substring |
| `String toUpperCase/toLowerCase(String)` | 转大/小写 | null 安全 |
| `String commonPrefix(String, String)` | 两串最长公共前缀 | 逐字符比较 |
| `String commonPrefix(List<String>)` | 多串最长公共前缀 | 迭代折叠，空前缀提前结束 |
| `void clear(StringBuilder)` | 清空 StringBuilder | `builder.delete(0, length)` |

- 调用链：`StringUtil.similarity → StringUtil.levenshteinDistance`
- 调用链：`StringUtil.join(String, Object[]) → join(String, Collection)`
- 调用链：`StringUtil.commonPrefix(List) → commonPrefix(String, String)`

## TextUtil

- 职责：文本处理工具类，涵盖字符集转换、格式美化、类型探测、转义与相似度计算。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 内部类/记录：MatchText

| 成员 | 类型 | 说明 |
|---|---|---|
| `MatchText(int, String)` | record | 文本匹配结果，含匹配起始索引与匹配文本 |
| index | int | 匹配内容在原文中的起始索引 |
| text | String | 匹配到的文本 |
| INVALID | MatchText | 无效匹配（参数非法），index=-2 |
| NOT_FOUND | MatchText | 未匹配到内容，index=-1 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `TextUtil()` | 私有构造，禁止实例化 | 空实现 |
| `MatchText findText(String, String, Integer, boolean, boolean, boolean)` | 正则搜索文本 | 参数非法返回 INVALID，长度不足返回 NOT_FOUND；`RegexUtil.createSearchPattern` 编译，可选 `matcher.region` 起始位置 |
| `byte[] changeCharset(byte[], String, String)` | 字节数组换字符集 | 空/相同则原样返回，null 字符集取系统默认，`new String(bytes,from).getBytes(target)` |
| `byte[] changeCharset(byte[], Charset, Charset)` | 字节数组换字符集 | 同上，Charset 版本 |
| `String changeCharset(String, Charset, Charset)` | 字符串换字符集 | 委托 String 版并取 `displayName` |
| `String changeCharset(String, String, String)` | 字符串换字符集 | `new String(str.getBytes(from), target)`，`UnsupportedEncodingException` 打印栈 |
| `byte[] changeCharsetToBytes(String, String, String)` | 换字符集并转 byte | 先 `changeCharset` 再 `getBytes` |
| `Charset getCharset(String)` | 按名称取字符集 | null/空/"跟随系统" 返回系统默认，异常兜底默认 |
| `String beautifyFormat(Collection<String>, int, int)` | 表格化格式美化 | `CollectionUtil.split` 分行，统计各列最大显示宽度，空格补齐并按列拼 tab |
| `int getDisplayLen(String)` | 显示长度 | 中文按 1.5、其他按 1 累加后取整 |
| `boolean isChinese(char)` | 是否中文字符 | `Character.UnicodeScript.of(c) == HAN` |
| `String byteToBitStr(byte[])` | 字节数组转 bit 字符串 | 逐字节委托单字节版 |
| `String byteToBitStr(byte)` | 单字节转 bit 字符串 | `b & 0xFF` 后逐位拼 0/1 |
| `byte[] bitStrToByte(String)` | bit 字符串转字节数组 | 每 8 位 `StringUtil.split` 后 `Integer.parseInt(bit,2)` |
| `String escape(String)` | 转义不可见字符 | 委托 `escape(str, null)` |
| `String escape(String, Function<Character,String>)` | 转义不可见字符 | 对 `"` `\n` `\b` `\t` `\f` `\r` `\u` 等转义，可自定义处理函数 |
| `boolean isJsonStr(String)` | 是否 json 字符串 | 结构字符判断后 `JSONUtil.isJson` 复核 |
| `boolean isXmlStr/isHtmlStr/isCssStr/isPropertiesStr/isYamlStr/isPythonStr(String)` | 各类型字符串判断 | 基于 `StringUtil.contains/startWith` 特征匹配 |
| `boolean isBinaryStr(String)` | 是否二进制字符串 | `\+$` 正则匹配 |
| `boolean isHexStr(String)` | 是否十六进制字符串 | `[0-9a-fA-F]+$` 正则匹配 |
| `byte detectType(Object)` | 探测数据类型 | byte[] 转 String 后按 json/二进制/xml/html/python/css/properties/yaml 顺序返回编码（1~10，字符串=5，其他=6） |
| `String getLastLine(String)` | 获取最后一行 | 委托系统行分隔符版本 |
| `String getLastLine(String, String)` | 获取最后一行 | `lastIndexOf` 行分隔符后取子串 |
| `double clacCorr(String, String)` | 计算相近度 | 相等=2.0；否则按包含/前缀/后缀/大小写加分叠加相似度 |
| `String toSingleLine(String)` | 转为单行内容 | 空白序列 `replaceAll("\\s+"," ")` |

- 调用链：`TextUtil.findText → RegexUtil.createSearchPattern → Matcher.find`
- 调用链：`TextUtil.detectType → isJsonStr / isBinaryStr / isXmlStr / ...`
- 调用链：`TextUtil.beautifyFormat → CollectionUtil.split → getDisplayLen`

## UUIDUtil

- 职责：UUID 生成工具类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `UUIDUtil()` | 私有构造，禁止实例化 | 空实现 |
| `UUID randomUUID()` | 生成随机 UUID 对象 | `UUID.randomUUID` |
| `String uuid()` | 生成带连字符的 UUID 字符串 | `randomUUID().toString()` |
| `String uuidSimple()` | 生成去连字符的 UUID 字符串 | `toString().replace("-", "")` |

- 调用链：`UUIDUtil.uuid → UUIDUtil.randomUUID → UUID.randomUUID`
- 调用链：`UUIDUtil.uuidSimple → UUIDUtil.randomUUID`
