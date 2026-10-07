# base-pkg

> 模块：base-pkg　根包：cn.oyzh.pkg（含 appImage、comporess、config、filter、github、jar、jdeps、jlink、jpackage、jre、mvn、pack、util、woa 子包）
> 说明：Java 应用打包流水线。`Packer` 注册并按 order 排序执行一系统 `Handler`（前置 PreHandler / 打包 PackHandler / 后置 PostHandler），依次完成 maven 构建、jar 最小化、jdeps 依赖分析、jlink 裁剪 jre、jpackage 生成安装包、压缩、产物重命名等；配置支持 json/yaml/toml 解析与多份合并。
> 跳过（整文件注释的死代码）：`config/AppConfigHandler`、`util/ArchiveUtil`、`util/JarUtil`、`util/ZipHelper`。

## ConfigMargeAble
> 包：cn.oyzh.pkg

- 职责：配置合并接口，将另一份配置合并进当前配置。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void marge(T config)` | 合并配置 | 抽象方法，由实现类实现 |

- 调用链：`Packer.pack → PackConfig.marge ; PackConfig.marge → 各子配置.marge`

## ConfigParser
> 包：cn.oyzh.pkg

- 职责：配置解析接口，按文件后缀（json/yaml/toml）解析为 JSONObject 再转配置对象。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `default C parse(String configFile)` | 由文件解析 | 依后缀用 `JSONUtil`/`Yaml`/`Toml` 解析为对象后调用 `parse(JSONObject)` |
  | `C parse(JSONObject object)` | 由对象解析 | 抽象方法 |

- 调用链：`Packer.pack → PackConfigParser.parse(configFile) → parse(JSONObject)`

## Handler
> 包：cn.oyzh.pkg

- 职责：处理器通用接口（排序、名称、唯一性、执行）。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `default boolean unique()` | 是否唯一 | 默认 false |
  | `int order()` / `void order(int)` | 读写排序 | 抽象方法 |
  | `String name()` | 名称 | 抽象方法 |
  | `void handle(PackConfig)` | 处理 | 抽象方法 |

- 调用链：`Packer.doHandle → handler.handle(packConfig)`

## PreHandler / PackHandler / PostHandler
> 包：cn.oyzh.pkg

- 职责：处理器的三个分类标记接口（继承 `Handler`），分别在打包前置/打包/打包后阶段执行。
- 字段：无
- 方法：无（继承 Handler，仅作分类）

- 调用链：`Packer.preHandlers/packHandlers/postHandlers → 按类型筛选`

## SingleHandler
> 包：cn.oyzh.pkg

- 职责：单次执行处理器的标记接口，记录任务是否已执行，避免重复执行。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void setExecuted(boolean)` | 设置是否已执行 | 抽象方法 |
  | `boolean isExecuted()` | 是否已执行 | 抽象方法 |

- 调用链：`MvnHandler/JreHandler/JLinkHandler implements SingleHandler`，handle 开头判断 executed

## PackCost
> 包：cn.oyzh.pkg

- 职责：打包属性键常量。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | DEST | String | 打包最终目录键 "dest" |
  | GITHUB_DIST | String | github 最终目录键 |
  | PKG_PATH | String | 打包工程的路径键 |
  | PROJECT_PATH | String | 待打包工程路径键 |

- 方法：无

- 调用链：`Packer.steupGitHub → PackCost.PROJECT_PATH/GITHUB_DIST/DEST`

## PackOrder
> 包：cn.oyzh.pkg

- 职责：处理器排序常量（数值越大执行越早），提供 ORDER_MAX/MIN 及各档位值。
- 字段：`ORDER_MAX`、`ORDER_P12..ORDER_P1`、`ORDER_0`、`ORDER_M1..ORDER_M12`、`ORDER_MIN`（均为 int static final）
- 方法：无

- 调用链：各 Handler 的 order 字段默认值取自此类

## Packer
> 包：cn.oyzh.pkg

- 职责：打包器，注册各类处理器并按顺序执行完整打包流程。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | handlers | List\<Handler\> | 处理器列表（final） |
  | configParser | PackConfigParser | 配置解析器（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | 实例初始化块 | 注册默认处理器 | 依次注册 End/Jar/Jre/Dest/JLink/Start/Jdeps/Compress/JPackage/PackConfig/CompressName |
  | `registerXxxHandler(...)` | 注册各处理器 | `new XxxHandler` 后 `registerHandler` |
  | `void registerHandler(Handler)` | 注册 | 唯一性校验（重复抛异常），按 `order` 降序排序 |
  | `List<PreHandler>/PostHandler/PackHandler xxxHandlers()` | 分类筛选 | `instanceof` 过滤 |
  | `void pack(String)` / `(String, Map)` / `(String, String, Map)` | 执行打包 | 解析主配置与平台配置并 `marge`，注入 properties，AppImage 时注册处理器，依次执行 pre/pack/post 处理器 |
  | `private void doHandle(Handler, PackConfig)` | 执行单个 | 计时并记日志后 `handler.handle` |
  | `void steupGitHub(Map)` | 配置 github | 设置 dist 路径与 dest，注册 GitHubActionsHandler |

- 调用链：`Packer.pack → PackConfigParser.parse → 各 Handler.handle → PkgUtil/CompressUtil 等`

## AppImageHandler
> 包：cn.oyzh.pkg.appImage

- 职责：AppImage 后置处理器，生成 Linux AppImage 可执行文件。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | order | int | 排序 ORDER_M6 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 复制图标、生成 .desktop、生成 AppRun、必要时 `installAppImageTool`，执行 `appimagetool` 生成 `.AppImage`，设置 compressFile 与临时文件 |
  | `private String installAppImageTool(PackConfig)` | 准备工具 | 复制并 chmod 内置 appimagetool，否则 `which appimagetool` |
  | `private void copyAppIcon(PackConfig)` | 复制图标 | `FileUtil.copy` |
  | `private void initDesktop(PackConfig)` | 生成 desktop | 写入 `[Desktop Entry]` |
  | `private void initAppRun(PackConfig)` | 生成 AppRun | 写 bash 脚本并 chmod +x |

- 调用链：`Packer.pack → AppImageHandler.handle → RuntimeUtil.execForResult(appimagetool)`

## RegFilter
> 包：cn.oyzh.pkg.filter

- 职责：基于通配/正则的排除过滤器（实现 `Function<String,Boolean>`），返回 true 表示保留。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | excludes | Set\<String\> | 排除项集合（CopyOnWriteArraySet，final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `RegFilter()` / `RegFilter(Collection<String>)` | 构造 | 初始化排除项 |
  | `void addExclude(String)` / `addExcludes(Collection)` | 添加排除 | 去空白后加入 |
  | `Boolean apply(String fullName)` | 过滤 | 取文件基名；排除项含 `.*` 时按正则 `matches`，否则按相等/后缀匹配；命中返回 false |

- 调用链：`JreHandler/JarHandler/JDepsHandler.handle → new RegFilter → filter.apply`

## GitHubActionsHandler
> 包：cn.oyzh.pkg.github

- 职责：github actions 后置处理器，将构建产物移动到 dist 目录。
- 字段：order（ORDER_M8）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 读取 GITHUB_DIST；有压缩包则移动压缩包，否则移动 dest 下所有文件；目录不存在则创建 |

- 调用链：`Packer.steupGitHub → GitHubActionsHandler.handle → FileUtil.renameFile`

## MvnHandler
> 包：cn.oyzh.pkg.mvn

- 职责：maven 前置处理器，先 install 依赖工程再 package 当前工程（单次执行）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | order | int | 排序 ORDER_P9 |
  | projectDir | String | 项目工程目录（final） |
  | dependencies | List\<String\> | 依赖工程目录（final） |
  | executed | boolean | 是否已执行 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `MvnHandler(String, List<String>)` | 构造 | 保存目录 |
  | `void handle(PackConfig)` | 处理 | 已执行则跳过；`MvnUtil.mvnExec` 取 mvn，对依赖逐个 `mvn install`，再 `mvn package` |
  | `isExecuted/setExecuted` | 单次标志 | 实现 SingleHandler |

- 调用链：`Packer.pack → MvnHandler.handle → RuntimeUtil.execForResult(mvn ...)`

## PackConfig
> 包：cn.oyzh.pkg.config

- 职责：打包总配置，聚合路径、应用信息与各子配置，支持合并与属性注入。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | dest / jlinkJre / minimizeJre / jPackageInput / jarUnDir | String | 各类目录 |
  | minimizeManJar / mainJar | String | 主程序 / 最小化主程序 |
  | appName / appIcon / appVersion / buildType | String | 应用信息 |
  | compressFile | File | 最终压缩文件 |
  | jrePath / appImageRuntime / jdkPath / platform / jfxVersion | String | 运行环境信息 |
  | jarConfig / jreConfig / jDepsConfig / jLinkConfig / jPackageConfig / compressConfig | 各配置对象 | 子配置 |
  | properties | Map\<String,Object\> | 属性（final） |
  | tempFiles | List\<String\> | 临时文件（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `putProperty/getProperty` | 属性读写 | 委托 Map |
  | `addTempFile/tempFiles` | 临时文件 | 记录待清理路径 |
  | `mainJar()` | 取主程序 | 优先 minimizeManJar |
  | `mainJarName()` | 主程序文件名 | 截取路径末段 |
  | `outPath()` | 输出路径 | 优先 compressFile |
  | `appVersion()` / `mainAppVersion()` | 版本 | 去前导 v；三段点时去末段 |
  | `jrePath()` | jre 路径 | minimizeJre > jlinkJre > jrePath |
  | `isPlatformMacos/Windows/Linux` | 平台判定 | 含关键字判断 |
  | `packageType()` | 打包类型 | 取 jpackage 配置的 type |
  | `void marge(PackConfig)` | 合并 | 逐字段覆盖，子配置递归 marge |
  | 各 get/set | 属性读写 | 简单 getter/setter |

- 调用链：`Packer.pack → PackConfigParser.parse → PackConfig.marge`；各 Handler 读取 PackConfig

## PackConfigParser
> 包：cn.oyzh.pkg.config

- 职责：打包配置解析器，从 JSONObject 构建 PackConfig 及其子配置。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `PackConfig parse(JSONObject)` | 解析 | 读取 dest/appName/appIcon/mainJar/jdkPath/jrePath/platform/buildType/appVersion/jfxVersion/appImageRuntime，并解析 jre/jar/jlink/jdeps/compress/jpackage 子配置 |
  | `static PackConfig parseConfig(String)` | 由文件解析 | new 后调用 parse(configFile) |

- 调用链：`Packer.configParser.parse → PackConfigParser.parse → JreConfigParser/JarConfigParser/...`

## PackConfigHandler
> 包：cn.oyzh.pkg.config

- 职责：打包信息前置处理器，解析符号变量、清理目标目录。
- 字段：order（ORDER_P8）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | `$SYSTEM` jdkPath 替换为 JAVA_HOME；替换 `${appVersion}`/`${pkgPath}`/`${projectPath}` 变量；清空 dest 目录内容 |

- 调用链：`Packer.pack → PackConfigHandler.handle → FileUtil.cleanDir`

## ProjectHandler
> 包：cn.oyzh.pkg.config

- 职责：项目信息前置处理器，从 project.properties 读取应用名称/版本/类型。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | order | int | 排序 ORDER_P9 |
  | projectFile | String | 项目信息文件，默认 project.properties |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `ProjectHandler()` / `ProjectHandler(String)` | 构造 | 指定项目文件 |
  | `void handle(PackConfig)` | 处理 | `ResourceUtil.getResource` 加载后读取 project.name/version/type 写入 PackConfig |

- 调用链：`Packer.registerProjectHandler → ProjectHandler.handle`

## CompressConfig
> 包：cn.oyzh.pkg.comporess

- 职责：压缩配置（类型、文件名）。
- 字段：type、name（均 String）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `getType/setType`、`getName/setName` | 属性读写 | 简单 getter/setter |
  | `void marge(CompressConfig)` | 合并 | 非空字段覆盖 |

- 调用链：`PackConfig.marge → CompressConfig.marge`

## CompressConfigParser
> 包：cn.oyzh.pkg.comporess

- 职责：压缩配置解析器。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `CompressConfig parse(JSONObject)` | 解析 | 读 name/type |
  | `static parseConfig(JSONObject/String)` | 静态入口 | 委托实例方法 |

- 调用链：`PackConfigParser.parse → CompressConfigParser.parseConfig`

## CompressHandler
> 包：cn.oyzh.pkg.comporess

- 职责：压缩后置处理器，按类型压缩打包目录。
- 字段：order（ORDER_M6）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 依 type 调用 `CompressUtil.zipDest/tarDest/tgzDest`，设置 compressFile 与临时文件；无 name 抛异常 |

- 调用链：`Packer.pack → CompressHandler.handle → CompressUtil.zipDest/tarDest/tgzDest`

## CompressNameHandler
> 包：cn.oyzh.pkg.comporess

- 职责：压缩文件名后置处理器，未指定名称时按规则自动生成。
- 字段：order（ORDER_M5）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 已设置 name 则返回；否则按 appName_v版本_平台_架构_构建类型 拼接 |

- 调用链：`Packer.pack → CompressNameHandler.handle → OSUtil.getArchName`

## JarConfig
> 包：cn.oyzh.pkg.jar

- 职责：jar 裁剪配置（开关、移除空 jar、jfx/二进制库优化、跳过/排除项、jfx 路径）。
- 字段：enable、removeEmpty、javafxOptimize、binlibOptimize（Boolean）；skipsJar、excludes（Set\<String\>）；javafxPath（String）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `isEnable/isRemoveEmpty/isJavafxOptimize/isBinlibOptimize` | 判定 | BooleanUtil/默认值处理 |
  | `get/set` 各属性 | 属性读写 | 简单 getter/setter |
  | `void marge(JarConfig)` | 合并 | 集合合并、布尔覆盖 |

- 调用链：`PackConfig.marge → JarConfig.marge`

## JarConfigParser
> 包：cn.oyzh.pkg.jar

- 职责：jar 配置解析器。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JarConfig parse(JSONObject)` | 解析 | 读 excludes/skipsJar/enable/removeEmpty/javafxOptimize/binlibOptimize |
  | `static parseConfig(JSONObject/String)` | 静态入口 | 委托实例方法 |

- 调用链：`PackConfigParser.parse → JarConfigParser.parseConfig`

## JarHandler
> 包：cn.oyzh.pkg.jar

- 职责：jar 前置处理器，解压主 jar、裁剪（最小化）jar 与类库、合并类库回主 jar。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | order | int | 排序 ORDER_P7 |
  | filter / skipFilter | RegFilter | 排除/跳过过滤器 |
  | config | PackConfig | 打包配置 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 解压主 jar，启用裁剪时 `PkgUtil.minimize` + `handleLibs` + `mergeLibs`，否则直接复制；设置 minimizeManJar/jarUnDir/临时文件 |
  | `private void handleJfxLib(String, String)` | 提取 jfx 本地库 | 从 jmod 或 jar 中提取 jfx 库到临时 javafxPath |
  | `private boolean handleBinLib(String, String)` | 二进制库判定 | 校验是否为当前平台库（`NativeArchDetector`） |
  | `private boolean jarFilter(String, String)` | 过滤 | jfx 优化、二进制库优化、通用排除 |
  | `private void handleLibs(String)` | 处理类库 | 遍历 jar，跳过/删除/裁剪，多线程 `ThreadUtil.submitSmart` |
  | `private void mergeLibs(String, String, String)` | 合并类库 | 用 `jar -uvf0` 将 lib 合并回主 jar |

- 调用链：`Packer.pack → JarHandler.handle → PkgUtil.minimize / mergeLibs(jar -uvf0)`

## JDepsConfig
> 包：cn.oyzh.pkg.jdeps

- 职责：jdeps 配置（开关、summary/verbose、跳过/排除项、multi-release）。
- 字段：summary、verbose、enable（Boolean）；skips、excludes（Set\<String\>）；multiRelease（Integer）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `isEnable/isSummary/isVerbose` | 判定 | BooleanUtil.isTrue |
  | `get/set` 各属性 | 属性读写 | 简单 getter/setter |
  | `void marge(JDepsConfig)` | 合并 | multiRelease 覆盖，集合合并 |

- 调用链：`PackConfig.marge → JDepsConfig.marge`

## JDepsConfigParser
> 包：cn.oyzh.pkg.jdeps

- 职责：jdeps 配置解析器。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JDepsConfig parse(JSONObject)` | 解析 | 读 skips/excludes/multi-release/enable/summary/verbose |
  | `static parseConfig(JSONObject/String)` | 静态入口 | 委托实例方法 |

- 调用链：`PackConfigParser.parse → JDepsConfigParser.parseConfig`

## JDepsHandler
> 包：cn.oyzh.pkg.jdeps

- 职责：jdeps 前置处理器，分析 jar 依赖并把所需 JDK 模块并入 jlink 配置。
- 字段：order（ORDER_P6）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 列举系统模块（`java --list-modules`），遍历解压目录 jar，多线程执行 `PkgUtil.getJDepsCMD` 解析 `->` 依赖模块，过滤 jfx 模块，`jLinkConfig.margeAddModules(deps)` |

- 调用链：`Packer.pack → JDepsHandler.handle → PkgUtil.getJDepsCMD → jLinkConfig.margeAddModules`

## JLinkConfig
> 包：cn.oyzh.pkg.jlink

- 职责：jlink 配置（vm/输出/压缩/各类裁剪开关/添加模块/排除文件）。
- 字段：vm/output/compress/stripNativeDebugSymbols（String）；verbose/noManPages/noHeaderFiles/stripDebug/stripJavaDebugAttributes/ignoreSigningInformation/enable（Boolean）；addModules/excludeFiles（Set\<String\>）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `isEnable/isVerbose/isNoManPages/isNoHeaderFiles/isStripDebug/isStripJavaDebugAttributes/isIgnoreSigningInformation` | 判定 | BooleanUtil.isTrue |
  | `void margeAddModules(Collection<String>)` | 合并模块 | 并入 addModules |
  | `get/set` 各属性 | 属性读写 | 简单 getter/setter |
  | `void marge(JLinkConfig)` | 合并 | 集合合并、字段覆盖 |

- 调用链：`JDepsHandler.handle → jLinkConfig.margeAddModules`

## JLinkConfigParser
> 包：cn.oyzh.pkg.jlink

- 职责：jlink 配置解析器。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JLinkConfig parse(JSONObject)` | 解析 | 读 exclude-files/add-modules/vm/output/compress 及各布尔项；output 缺省生成临时 jre 目录 |
  | `static parseConfig(JSONObject/String)` | 静态入口 | 委托实例方法 |

- 调用链：`PackConfigParser.parse → JLinkConfigParser.parseConfig`

## JLinkHandler
> 包：cn.oyzh.pkg.jlink

- 职责：jlink 前置处理器，执行 jlink 生成裁剪后的 jre（单次执行）。
- 字段：order（ORDER_P5）、executed
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 未启用跳过；删除旧输出目录，`PkgUtil.getJLinkCMD` 组命令后执行，设置 jlinkJre 与临时文件 |

- 调用链：`Packer.pack → JLinkHandler.handle → PkgUtil.getJLinkCMD → RuntimeUtil.execForResult`

## JPackageConfig
> 包：cn.oyzh.pkg.jpackage

- 职责：jpackage 配置（名称、类型、目录、图标、版本、运行时镜像、vm 参数、win/mac 专属项）。
- 字段：name/type/dest/input/icon/vendor/mainJar/appVersion/copyright/description/runtimeImage/macPackageIdentifier（String）；verbose/winMenu/winShortcut/winDirChooser/enable（Boolean）；javaOptions（Set\<String\>）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `destParent()` | 目标父目录 | `new File(dest).getParent()` |
  | `isEnable/isVerbose/isWinMenu/isWinShortcut/isWinDirChooser` | 判定 | BooleanUtil.isTrue |
  | `get/set` 各属性 | 属性读写 | 简单 getter/setter |
  | `void marge(JPackageConfig)` | 合并 | javaOptions 合并、字段覆盖 |

- 调用链：`PackConfig.marge → JPackageConfig.marge`

## JPackageConfigParser
> 包：cn.oyzh.pkg.jpackage

- 职责：jpackage 配置解析器。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JPackageConfig parse(JSONObject)` | 解析 | 读 name/type/appVersion/mainJar/runtimeImage/icon/input/dest/vendor/verbose/java-options/win-menu/win-shortcut/win-dir-chooser/mac-package-identifier/description/enable |
  | `static parseConfig(JSONObject/String)` | 静态入口 | 委托实例方法 |

- 调用链：`PackConfigParser.parse → JPackageConfigParser.parseConfig`

## JPackageHandler
> 包：cn.oyzh.pkg.jpackage

- 职责：jpackage 打包处理器，准备输入目录并执行 jpackage 生成安装包（单次执行）。
- 字段：order（ORDER_0）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 未启用跳过；组装输入目录（含 jfx 库、主 jar）、补齐 jpackage 各字段缺省值、清理输出目录、`PkgUtil.getJPackageCMD` 组命令后执行 |

- 调用链：`Packer.pack → JPackageHandler.handle → PkgUtil.getJPackageCMD → RuntimeUtil.execForResult`

## JreConfig
> 包：cn.oyzh.pkg.jre

- 职责：jre 裁剪配置（开关、排除文件）。
- 字段：enable（Boolean）、excludes（Set\<String\>）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void parseConfig(JSONObject)` | 解析 excludes | 由 JSONArray 转 Set |
  | `isEnable` / `getExcludes/setExcludes` / `setEnable` | 属性读写 | Boolean 默认 true |
  | `void marge(JreConfig)` | 合并 | excludes 合并、enable 覆盖 |

- 调用链：`PackConfig.marge → JreConfig.marge`

## JreConfigParser
> 包：cn.oyzh.pkg.jre

- 职责：jre 配置解析器。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JreConfig parse(JSONObject)` | 解析 | 读 excludes/enable |
  | `static parseConfig(JSONObject/String)` | 静态入口 | 委托实例方法 |

- 调用链：`PackConfigParser.parse → JreConfigParser.parseConfig`

## JreHandler
> 包：cn.oyzh.pkg.jre

- 职责：jre 前置处理器，按排除规则裁剪 jre 目录（单次执行）。
- 字段：order（ORDER_P4）、executed
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 取 jlinkJre/jrePath 为源；启用时复制到临时目录，`RegFilter` 过滤删除文件，设置 minimizeJre |

- 调用链：`Packer.pack → JreHandler.handle → RegFilter.apply → FileUtil.del`

## DestHandler
> 包：cn.oyzh.pkg.pack

- 职责：最终产物后置处理器，按规则重命名构建产物。
- 字段：order（ORDER_M7）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | app-image 类型处理压缩包；安装包类型（msi/exe/pkg/dmg/rpm/deb）处理 dest 中产物 |
  | `private File handler(PackConfig, File)` | 重命名 | 按 appName_v版本_平台_架构_构建类型 拼名并 `FileUtil.renameFile` |

- 调用链：`Packer.pack → DestHandler.handle → FileUtil.renameFile`

## EndHandler
> 包：cn.oyzh.pkg.pack

- 职责：结束后置处理器，清理临时文件并输出打包耗时（唯一）。
- 字段：order（ORDER_MIN）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | 删除 tempFiles，按 startTime 计算并打印总耗时与产物大小 |

- 调用链：`Packer.pack → EndHandler.handle → FileUtil.del`

## StartHandler
> 包：cn.oyzh.pkg.pack

- 职责：起始前置处理器，记录打包开始时间（唯一）。
- 字段：order（ORDER_MAX）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void handle(PackConfig)` | 处理 | `putProperty("startTime", 当前毫秒)` |

- 调用链：`Packer.pack → StartHandler.handle → PackConfig.putProperty`

## JModUtil
> 包：cn.oyzh.pkg.util

- 职责：jmod 工具，解压 JDK 的 jmod 模块。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static String extract(String modeName, String jdkPath)` | 解压 jmod | 定位 `javaHome/jmods/<mod>`，`PkgUtil.getJModCMD`（jmod extract）执行，返回解压目录；不存在返回 null |

- 调用链：`WinArmHandler/WoaUtil → JModUtil.extract → RuntimeUtil.execForResult(jmod extract)`

## MvnUtil
> 包：cn.oyzh.pkg.util

- 职责：maven 工具，定位本地仓库与 mvn 可执行程序。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static String getLocalRepository()` | 本地仓库 | 解析 `~/.m2/settings.xml` 与 MAVEN_HOME 全局 settings，取 localRepository |
  | `static String mvnExec()` | mvn 路径 | 按平台返回 mvn.sh/mvn.cmd/mvn |

- 调用链：`MvnHandler.handle → MvnUtil.mvnExec`；`WinArmHandler/WoaHandler → MvnUtil.getLocalRepository`

## PkgUtil
> 包：cn.oyzh.pkg.util

- 职责：打包命令构建与 jar 最小化工具，生成 jlink/jdeps/jpackage/jmod/jar 命令行并实现 jar 过滤重打包。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static String[] getJLinkCMD(JLinkConfig)` | jlink 命令 | 拼接 --verbose/--vm/--compress/--no-*/--add-modules/--exclude-files/--output 等 |
  | `static String[] getJDepsCMD(JDepsConfig, String/List)` | jdeps 命令 | 拼接 -verbose/-summary/--multi-release 与 jar |
  | `static String[] getJPackageCMD(JPackageConfig)` | jpackage 命令 | 拼接 vendor/java-options/copyright/description/icon/input/main-jar/name/type/version/runtime-image/win/mac 参数/dest |
  | `static getJDKExecCMD(String, String/String[])` | 补全 jdk bin 路径 | `FileNameUtil.concat(jdkPath,"bin",cmd)` |
  | `static String[] getJModCMD(String, String)` | jmod 命令 | `jmod extract --dir` |
  | `static String[] getJarCfCMD(String, List<String>)` / `getJarXfCMD(String)` | jar 命令 | `jar cf`/`jar xf` |
  | `static void minimize(String, String, BiFunction)` | jar 最小化 | 用 JarInputStream/JarOutputStream 按过滤函数重打包，保留 Manifest |

- 调用链：`JLinkHandler/JDepsHandler/JPackageHandler/JarHandler/JModUtil/WinArmHandler/WoaHandler → PkgUtil.getXxxCMD / PkgUtil.minimize`

## WinArmHandler
> 包：cn.oyzh.pkg.woa

- 职责：Windows on ARM 的 jfx jmod 转为 maven 构件并安装到本地仓库。
- 字段：jfxVersion（String）、mods（String[]，静态常量）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void run()` | 入口 | 仅 windows+aarch64；开发环境更新 jfx pom；对各模块 `jfxJModToMvnJar`；清理 |
  | `private void jfxJModToMvnJar(String, String)` | 单模块转换 | `JModUtil.extract` → `jarCf`（`PkgUtil.getJarCfCMD`）→ `mvnInstall` |
  | `private void updateJfxPomFile()` | 更新 pom | 从资源模板写 `javafx-<ver>.pom` |
  | `private String copyJfxPomFile(String)` | 复制模块 pom | 由资源模板生成模块 pom 到临时目录 |
  | `private void jarCf(String, String)` | 打 jar | `PkgUtil.getJarCfCMD` + `getJDKExecCMD` 执行 |
  | `private void mvnInstall(String, String)` | 安装 | `mvnCmd` 组命令后执行 |
  | `private String[] mvnCmd(...)` | 组 mvn 命令 | `install:install-file` 带 file/pomFile/groupId/classifier=win-aarch64 |
  | `private void clean(String)` | 清理 jmods | 删除解压产物 |

- 调用链：`WinArmHandler.run → JModUtil.extract → PkgUtil.getJarCfCMD → MvnUtil.mvnExec`

## WoaHandler
> 包：cn.oyzh.pkg.woa

- 职责：Windows on ARM 的 jfx 处理，用 jmod 中的本地库更新 maven 仓库中的 jfx jar。
- 字段：jfxVersion（String）、mods（String[]，静态常量）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void run()` | 入口 | 仅 WOA；开发环境更新 pom；对各模块 `updateJfxJarFile`；清理 |
  | `private void updateJfxPomFile()` | 更新 pom | 写 `javafx-<ver>.pom` |
  | `private void updateJfxJarFile(String, String)` | 更新 jar | 定位仓库 jar，`WoaUtil.getJfxLibPath` 取新 lib，`jarXf` 解压，替换 dll，`jarCf` 重打包 |
  | `private void clean(String)` | 清理 | 删除 jmods 下目录 |
  | `private void jarCf(String, String)` / `private String jarXf(String)` | 打/解 jar | `PkgUtil.getJarCfCMD/getJarXfCMD` |

- 调用链：`WoaHandler.run → WoaUtil.getJfxLibPath → jarXf/jarCf → PkgUtil`

## WoaUtil
> 包：cn.oyzh.pkg.woa

- 职责：Windows on ARM 工具，定位 jfx 本地库路径并判定平台。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static Path getJfxLibPath(String, String)` | jfx lib 路径 | `JModUtil.extract` 取模块 lib；缺失时 WOA 下从资源 `/jfx/libs/<name>` 复制到临时目录 |
  | `static boolean isWoa()` | 是否 WOA | windows 且 aarch64 |

- 调用链：`JarHandler.handleJfxLib / WoaHandler.updateJfxJarFile → WoaUtil.getJfxLibPath`
