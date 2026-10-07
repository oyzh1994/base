# base-i18n

> 模块：base-i18n　包：cn.oyzh.i18n、cn.oyzh.i18n.baidu
> 说明：国际化（i18n）支持模块，包含资源绑定、区域管理、资源文件生成/校验，以及对接百度翻译的翻译 API 封装。

## I18nCoverChecker
> 包：cn.oyzh.i18n

- 职责：i18n 覆盖检查器，校验各语言资源文件的键集合与主体资源文件完全一致。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | prefx | String | 资源文件前缀 |
  | mainI18n | String | 主体 i18n 资源文件名称 |
  | projectPath | String | 项目路径 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `getPrefx/setPrefx`、`getMainI18n/setMainI18n`、`getProjectPath/setProjectPath` | 属性读写 | 简单 getter/setter |
  | `void i18Check()` | 执行检查 | 读取主体 properties 的 keySet，`CoverUtil.getClassesPath` 定位编译目录，`FileUtil.ls` 列出同前缀 properties 文件，逐个比对键集合，不一致则抛 `RuntimeException` |

- 调用链：`I18nCoverChecker.i18Check → FileUtil.ls → PropertiesFile.keySet`

## I18nGenerator
> 包：cn.oyzh.i18n

- 职责：i18n 生成器，用百度翻译为缺失的目标语言资源补齐翻译，并对已有翻译做标点修正。
- 字段：无（工具类）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static int i18nTranslate(String skFilePath, String cnI18nFile, String targetI18nFile, Locale targetLocale)` | 执行翻译 | 读取密钥文件初始化 `TransApi`；加载中/目标 properties；遍历中文 key，仅对目标缺失项调用 `api.trans`，解析 `trans_result.dst` 写回，每 10 条落盘一次，返回成功数 |
  | `static void i18nCorrection(String cnI18nFile, String targetI18nFile, Locale targetLocale)` | 执行修正 | 遍历中文 key，当源文不含中文标点而译文含有时，将 `。，：/;；\` 等替换为英文标点并写回 |

- 调用链：`I18nGenerator.i18nTranslate → TransApi.trans → HttpGet.get → 百度翻译`

## I18nHelper
> 包：cn.oyzh.i18n

- 职责：国际化常量与取值辅助类，集中定义约 1600 个资源键常量（形如 `public static final String XXX = "base.xxx"`）及对应静态取值方法。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | 大量 `public static final String` 常量 | String | 资源键，命名如 `OPERATION`、`SUCCESS`、`EXPORT`、`CONNECT` 等（值多为 `base.xxx`） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | 大量 `public static String xxx()` | 获取对应国际化文本 | 绝大多数实现为 `I18nResourceBundle.i18nString(常量...)`，将多个键拼接成短语（如 `operationFail()` 返回 OPERATION+FAIL） |

- 调用链：`I18nHelper.xxx() → I18nResourceBundle.i18nString(...) → I18nManager.currentLocale()`

## I18nLocale
> 包：cn.oyzh.i18n

- 职责：区域信息 Bean，描述一种语言区域的名称、Locale、显示名与说明。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | name | String | 区域名称 |
  | locale | Locale | 区域对象 |
  | displayName | String | 显示名称 |
  | description | String | 描述 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `I18nLocale(String, Locale, String, String)` | 构造 | 赋值四字段 |
  | `getName/setName`、`getLocale/setLocale`、`getDisplayName/setDisplayName`、`getDescription/setDescription` | 属性读写 | 简单 getter/setter |

- 调用链：`I18nLocales 静态块 → new I18nLocale(...)`

## I18nLocales
> 包：cn.oyzh.i18n

- 职责：维护系统支持的语言区域列表及各自的显示名称，提供 Locale 与名称/描述互查。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | ZH_YUE、RU、DA、PT、TH、EL、FI、SL、AR、NL、ES、ET、CS、SV、VI、PL、RO、HU | Locale | 各语言静态常量 |
  | locales | List\<I18nLocale\> | 区域信息列表（静态块填充简体/繁体/粤语/英日德韩法意等） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static List<Locale> locales()` | 获取区域列表 | 并行流 map 取 Locale |
  | `static String getLocaleDesc(Locale)` | 取显示名 | 匹配返回 displayName，未匹配返回首项 |
  | `static String getLocaleName(Locale)` | 取区域名 | 匹配返回 name 小写，未匹配返回首项 |
  | `static Locale getLocale(String)` | 按名取区域 | `StringUtil.equalsIgnoreCase` 匹配，未匹配返回首项 |

- 调用链：`I18nManager.apply(name) → I18nLocales.getLocale(name) → I18nLocales.locales`

## I18nManager
> 包：cn.oyzh.i18n

- 职责：国际化管理器，维护当前区域并提供切换能力。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | defaultLocale | Locale | 默认区域（`Locale.getDefault()`，final static） |
  | currentLocale | volatile Locale | 当前区域 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static Locale currentLocale()` | 当前区域 | 为空返回 defaultLocale |
  | `static String currentLocaleName()` | 当前区域名 | `I18nLocales.getLocaleName` |
  | `static void apply(String)` | 按名切换 | 空白则用默认 Locale，否则 `I18nLocales.getLocale(name)` |
  | `static synchronized void apply(Locale)` | 切换区域 | 设置 currentLocale，`I18nResourceBundle.clearResource()` 清缓存，必要时 `Locale.setDefault` |

- 调用链：`I18nManager.apply → I18nResourceBundle.clearResource`；`I18nResourceBundle.handleGetObject → I18nManager.currentLocale`

## I18nResourceBundle
> 包：cn.oyzh.i18n

- 职责：自定义 ResourceBundle，按当前区域从 `base_i18n`/`i18n` 资源文件加载文本，并做英文首字母大小写拼接。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | INSTANCE | I18nResourceBundle | 单例（final static） |
  | base_resources | Map\<Locale, ResourceBundle\> | base 资源缓存（键以 `base.` 开头） |
  | i18n_resources | Map\<Locale, ResourceBundle\> | 项目 i18n 资源缓存 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `protected Object handleGetObject(String)` | 取资源 | `initResource(key)` 后 `getObject` |
  | `Enumeration<String> getKeys()` | 键枚举 | 返回空枚举（不使用） |
  | `private ResourceBundle initResource(String)` | 初始化资源 | key 以 `base.` 开头取 `base_i18n`，否则取 `i18n`，按当前区域缓存 |
  | `boolean containsKey(String)` | 是否含键 | 委托资源 `containsKey` |
  | `void clear()` | 清空缓存 | 清空两个 Map |
  | `static String i18nString(String)` | 取字符串 | `INSTANCE.getString`，异常返回空串 |
  | `static String i18nString(String...)` | 拼接取值 | 英文区域下首段首字母大写、其余小写并以空格分隔；其它区域直接拼接 |
  | `static Object i18nObject(String)` | 取对象 | `INSTANCE.getObject` |
  | `static boolean containsI18nKey(String)` | 是否含键 | `INSTANCE.containsKey` |
  | `static void clearResource()` | 清资源 | `ResourceBundle.clearCache()` + `INSTANCE.clear()` |

- 调用链：`I18nHelper.xxx → I18nResourceBundle.i18nString → initResource → ResourceBundle.getBundle`

## I18nUtil
> 包：cn.oyzh.i18n

- 职责：区域归一化工具，将英/美/加及根区域统一为英语、加拿大法语统一为法语。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static Locale corrLocale(Locale)` | 纠正区域 | 命中 UK/US/CANADA/ROOT 返回 `Locale.ENGLISH`；CANADA_FRENCH 返回 `Locale.FRENCH` |

- 调用链：`I18nUtil.corrLocale(locale)`（供区域比较/翻译前处理）

## HttpGet（package-private）
> 包：cn.oyzh.i18n.baidu

- 职责：极简 HTTPS GET 请求工具（百度翻译 API 用），信任全部证书。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | SOCKET_TIMEOUT | int | 连接超时，10000ms（static final） |
  | GET | String | 请求方法名 "GET"（static final） |
  | myX509TrustManager | TrustManager | 信任所有证书的 X509TrustManager 匿名实现 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static String get(String host, Map<String,String> params)` | 发起 GET | 初始化 TLS SSLContext（信任所有），拼接 URL，读响应文本，关流并 disconnect；异常返回 null |
  | `static String getUrlWithQueryString(String, Map)` | 拼接查询串 | 按 `?`/`&` 拼接并对值做 encode |
  | `protected static void close(Closeable)` | 关闭资源 | 忽略异常关闭 |
  | `static String encode(String)` | utf-8 编码 | `URLEncoder.encode`，失败返回原值 |

- 调用链：`TransApi.doTrans → HttpGet.get → URL.openConnection`

## TransApi
> 包：cn.oyzh.i18n.baidu

- 职责：百度翻译 API 封装，负责签名与请求，带重试。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | TRANS_API_HOST | String | 翻译接口地址（static final，常量实际未用于请求） |
  | appid | String | 百度应用 ID |
  | securityKey | String | 应用密钥 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `TransApi(String, String)` | 构造 | 保存 appid 与密钥 |
  | `String trans(String query, String from, String to)` | 翻译（带重试） | 最多循环 31 次调用 `doTrans`；结果含 "Invalid Access Limit"/"TIMEOUT" 或 null 时 `ThreadUtil.sleep(500)` 重试 |
  | `private String doTrans(String, String, String)` | 单次请求 | 构建参数后 `HttpGet.get` 请求百度接口 |
  | `private Map<String,String> buildParams(String, String, String)` | 构建参数 | 组装 q/from/to/appid/salt，`MD5Util.md5Hex(appid+query+salt+key)` 生成 sign |

- 调用链：`I18nGenerator.i18nTranslate → TransApi.trans → doTrans → HttpGet.get`

## TransUtil
> 包：cn.oyzh.i18n.baidu

- 职责：区域到百度翻译语言代码的映射工具。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static String localeToName(Locale)` | 取百度语言名 | 依次判断中文/繁体/英/日/韩/德/法/意及 `I18nLocales` 中各语言，返回 zh/cht/en/jp/kor/de/fra/it 等；未匹配返回 `locale.toString()` |

- 调用链：`I18nGenerator.i18nTranslate → TransUtil.localeToName`
