# base-store-jdbc

> 模块：base-store　包：cn.oyzh.store.jdbc、.param、.h2
> 说明：轻量 JDBC 存储层，基于注解（`@Table`/`@Column`/`@PrimaryKey`）将 Java 模型映射到表；提供「标准操作器」（普通表 CRUD）与「键值操作器」（KEY/VALUE 两列表）两套实现，连接由 `JdbcManager` 池化管理，目前仅 H2 方言启用。
> 跳过：`cn.oyzh.store.jdbc.sqlite` 下 `SqlLiteUtil`、`SqliteStandardOperator`、`SqliteKeyValueOperator` 三个文件被整文件注释（死代码），未纳入。

## Column
> 包：cn.oyzh.store.jdbc

- 职责：字段级注解，标注实体字段对应的列名与列类型。
- 字段：无（注解）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `String value()` | 列名称 | 为空时用 Java 字段名 |
  | `String type()` | 列类型 | 为空时按 Java 字段类型推断 |

- 调用链：`ColumnDefinition.ofField → field.getAnnotation(Column.class)`

## ColumnDefinition
> 包：cn.oyzh.store.jdbc

- 职责：列定义，描述字段名、列名、列类型、是否主键与是否自动生成。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | fieldName | String | Java 字段名称 |
  | columnName | String | 列名称 |
  | columnType | String | 列类型 |
  | primaryKey | boolean | 是否主键 |
  | autoGeneration | boolean | 主键是否自动生成 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static ColumnDefinition ofField(Field)` | 由字段构建 | 跳过 static/native；读 `@Column` 决定列名/类型（H2 下 `H2Util.toSqlType`）；读 `@PrimaryKey` 设置主键与自动生成 |
  | `String getColumnName()` | 取列名 | H2 方言下转大写 |
  | `getFieldName/setFieldName`、`setColumnName`、`getColumnType/setColumnType`、`isPrimaryKey/setPrimaryKey`、`isAutoGeneration/setAutoGeneration` | 属性读写 | 简单 getter/setter |

- 调用链：`TableDefinition.ofClass → ColumnDefinition.ofField → H2Util.toSqlType`

## PrimaryKey
> 包：cn.oyzh.store.jdbc

- 职责：字段级注解，标记主键字段。
- 字段：无（注解）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `boolean autoGeneration()` | 是否自动生成主键值 | 默认 true |

- 调用链：`ColumnDefinition.ofField → field.getAnnotation(PrimaryKey.class)`

## PrimaryKeyColumn
> 包：cn.oyzh.store.jdbc

- 职责：主键列值对（列名 + 列数据）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | columnName | String | 列名称 |
  | columnData | Object | 列数据 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `PrimaryKeyColumn(String, Object)` | 构造 | 赋值 |
  | `getColumnName/setColumnName`、`getColumnData/setColumnData` | 属性读写 | 简单 getter/setter |

- 调用链：`TableDefinition.primaryKeyColumn → new PrimaryKeyColumn(...)`

## Table
> 包：cn.oyzh.store.jdbc

- 职责：类型级注解，标记实体对应的表名。
- 字段：无（注解）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `String value()` | 表名称 | 为空时用类简单名小写 |

- 调用链：`TableDefinition.ofClass → clazz.getAnnotation(Table.class)`

## TableDefinition
> 包：cn.oyzh.store.jdbc

- 职责：表定义，聚合表名与列定义，并提供主键反射读写。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | tableName | String | 表名称 |
  | columns | List\<ColumnDefinition\> | 列定义列表 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static TableDefinition ofClass(Class<?>)` | 由类型构建 | 读 `@Table` 定表名，`ReflectUtil.getFields` 收集 `@Column` 字段 |
  | `void addColumn(ColumnDefinition)` | 添加列 | 惰性建列表后加入 |
  | `boolean hasColumn(String)` | 是否含列 | 忽略大小写比较列名 |
  | `ColumnDefinition primaryKey()` / `String primaryKeyName()` | 取主键列/名 | 遍历找 `isPrimaryKey` |
  | `PrimaryKeyColumn primaryKeyColumn(Object)` | 取模型主键列 | 反射读主键字段值 |
  | `void handlePrimaryKeyValue(Object)` | 填充主键 | 主键为空且允许自动生成时 `KeyGenerator.generatorKey` 反射回填 |
  | `Object getPrimaryKeyValue(Object)` | 取主键值 | 反射读 |
  | `getTableName/setTableName`、`getColumns/setColumns` | 属性读写 | 简单 getter/setter |

- 调用链：`JdbcStandardStore 构造 → tableDefinition() → TableDefinition.ofClass`；`handlePrimaryKeyValue → KeyGenerator.generatorKey`

## KeyGenerator
> 包：cn.oyzh.store.jdbc

- 职责：按列类型生成主键值的生成器（单例）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | INSTANCE | KeyGenerator | 单例（final static） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `Object generator(String columnType)` | 生成主键 | 文本类型返回 `UUIDUtil.uuid()`；整数类型返回 `System.currentTimeMillis()+随机`；否则 null |
  | `static Object generatorKey(String)` | 静态入口 | 委托 INSTANCE |

- 调用链：`TableDefinition.handlePrimaryKeyValue → KeyGenerator.generatorKey`

## JdbcConst
> 包：cn.oyzh.store.jdbc

- 职责：JDBC 系统属性常量与读写工具（以 System Property 传递 db 配置）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | DB_FILE / DB_DIALECT / DB_CACHE_SIZE / DB_CACHE_TYPE / DB_PAGE_SIZE | String | 系统属性键 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `dbFile(String)/dbFile()` | 读写 db 文件 | `System.setProperty`/`getProperty` |
  | `dbDialect(JdbcDialect)/dbDialect()` | 读写方言 | 同上 |
  | `dbCacheSize(int)/dbCacheSize()`、`dbPageSize(int)/dbPageSize()` | 读写缓存/页大小 | 同上 |

- 调用链：`JdbcManager 静态块 → JdbcConst.dbDialect()`；`JdbcManager.takeoff → JdbcConst.dbFile/dbCacheSize/dbPageSize`

## JdbcDialect
> 包：cn.oyzh.store.jdbc

- 职责：JDBC 方言枚举，目前仅启用 H2（SQLITE 已注释）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | H2 | JdbcDialect | H2 方言常量 |

- 方法：无（枚举）——由 `JdbcManager.dialect` 持有并驱动 `JdbcUtil.wrap/wrapData` 等方言分支。

- 调用链：`JdbcManager 静态块 → JdbcDialect.valueOf(...)`；`JdbcUtil.wrap → JdbcDialect.H2 判断`

## JdbcConn
> 包：cn.oyzh.store.jdbc

- 职责：JDBC 连接包装，带状态机（0 正常/1 使用中/2 已关闭）与元数据便捷方法。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | status | AtomicInteger | 连接状态 |
  | connection | Connection | 原生连接 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JdbcConn(Connection)` | 构造 | 开启自动提交，状态置 0 |
  | `boolean isUsable()` | 是否可用 | 连接关闭则置 2；返回状态==0 |
  | `Connection takeoff()` | 取用连接 | CAS 0→1 成功返回连接，否则 null |
  | `void giveback()` | 归还连接 | 失效置 2，否则置 0 |
  | `boolean isInvalid()` | 是否失效 | 状态==2 |
  | `prepareStatement/createStatement/getMetaData` | 委托原生 | 透传 |
  | `getColumns(...)` / `getTables(...)` | 元数据查询 | H2 方言下将表/列名转大写 |
  | `setAutoCommit/rollback/commit/getConnection` | 事务与取值 | 透传 |
  | `void close()` | 关闭 | 关闭原生连接 |

- 调用链：`JdbcManager.takeoff → new JdbcConn → isUsable/takeoff`；`H2StandardOperator.initTable → connection.getTables`

## JdbcManager
> 包：cn.oyzh.store.jdbc

- 职责：连接管理器，维护连接池（连接列表）与方言，负责取用/归还/销毁。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | dialect | JdbcDialect | 当前方言（static） |
  | CONNECTIONS | List\<JdbcConn\> | 连接列表（CopyOnWriteArrayList，final static） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static boolean isH2Dialect()` | 是否 H2 | `JdbcDialect.H2 == dialect` |
  | `static JdbcConn takeoff()` | 取连接 | 复用可用连接；否则按 dbFile 拼 H2/SQLite URL（含缓存/页大小、延迟关闭参数），`DriverManager.getConnection` 后入池 |
  | `static void giveback(Connection)` / `giveback(JdbcConn)` | 归还连接 | 归还并清理失效连接 |
  | `static void destroy()` | 销毁 | H2 下执行 `SHUTDOWN`，关闭全部连接 |

- 调用链：`操作器 → JdbcManager.takeoff → JdbcConn`；`JdbcHelper.executeXxx → JdbcManager.giveback`

## JdbcOperator
> 包：cn.oyzh.store.jdbc

- 职责：操作器抽象基类，持有表定义并声明建表/改表钩子。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | tableDefinition | TableDefinition | 表定义（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JdbcOperator(TableDefinition)` | 构造 | 保存表定义 |
  | `String tableName()` / `List<ColumnDefinition> columns()` | 取表名/列 | 委托 tableDefinition |
  | `boolean initTable()` | 初始化表 | 默认 false，子类覆盖 |
  | `alterTable()` / `createTable()` | 改表/建表 | 默认空，子类覆盖 |
  | `TableDefinition getTableDefinition()` | 取表定义 | 返回字段 |

- 调用链：`H2StandardOperator/H2KeyValueOperator extends JdbcOperator`

## JdbcResultSet
> 包：cn.oyzh.store.jdbc

- 职责：结果集包装，随语句一起关闭，提供列读取便捷方法。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | resultSet | ResultSet | 结果集（final） |
  | statement | Statement | 语句（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JdbcResultSet(ResultSet, Statement)` | 构造 | 保存引用 |
  | `void close()` | 关闭 | 关闭结果集与语句 |
  | `boolean next()` | 下一行 | 透传 |
  | `int getInt(int)` / `long getLong(int)` | 取数值 | 透传 |
  | `int findColumn(String)` / `boolean containsColumn(String)` | 列查找/存在 | `findColumn` 是否 >=0 |
  | `Object getObject(String)` | 取对象值 | 透传 |

- 调用链：`JdbcHelper.executeQuery → new JdbcResultSet`；`操作器 selectOne/selectList → resultSet.getObject`

## JdbcHelper
> 包：cn.oyzh.store.jdbc

- 职责：JDBC 执行辅助，统一执行 SQL、设置参数与打印日志。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static void execute(JdbcConn, String)` | 执行 SQL | `createStatement().execute` |
  | `static int executeUpdate(JdbcConn, String, Collection<?>)` / `(..., Object...)` | 执行更新 | 预编译、`setParams`、`executeUpdate`、`commit` |
  | `static JdbcResultSet executeQuery(JdbcConn, String, Collection<?>)` / `(..., Object...)` | 执行查询 | 有参用预编译，无参用 Statement |
  | `static void setParams(PreparedStatement, Object...)` | 设置参数 | null 用 `setNull(Types.NULL)`，否则 `setObject` |

- 调用链：`JdbcStandardOperator.insert/update/delete/selectXxx → JdbcHelper.executeUpdate/executeQuery`

## JdbcStandardOperator
> 包：cn.oyzh.store.jdbc

- 职责：标准表操作器抽象基类，拼接并执行普通表的增删改查 SQL。
- 字段：无（继承 tableDefinition）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `insert(Map<String,Object>)` | 新增 | 拼 `INSERT INTO`（占位符），`JdbcHelper.executeUpdate` |
  | `update(Map, Object)` / `update(Map, PrimaryKeyColumn)` | 按主键更新 | 拼 `UPDATE ... SET ... WHERE pk=?` |
  | `exist(Object)` / `exist(PrimaryKeyColumn)` / `exist(Map)` | 是否存在 | `SELECT COUNT(*)`，计数 >0 |
  | `selectOne(Object)` / `selectOne(PrimaryKeyColumn)` | 按主键查一条 | `SELECT *`，`resultSet` 装填记录 |
  | `selectOne(QueryParam)` / `selectOne(SelectParam)` | 按条件查一条 | 拼查询列/条件/order by/limit |
  | `selectList(SelectParam)` | 条件查列表 | 同上，返回多条 |
  | `selectCount(List<QueryParam>)` / `selectCount(String, List<String>, QueryParams)` | 统计条数 | `SELECT COUNT(*)`，关键字 LIKE 多列 `OR` |
  | `selectPage(String, List<String>, PageParam)` | 分页查询 | 关键字/条件 + `LIMIT ? OFFSET ?` |
  | `delete(Object)` / `delete(PrimaryKeyColumn)` | 按主键删除 | `DELETE FROM ... WHERE pk=?` |
  | `abstract int delete(DeleteParam)` | 按参数删除 | 抽象方法，由实现类实现 |
  | `protected PrimaryKeyColumn getPrimaryKeyColumn(Object)` | 构建主键列 | 取表定义主键列名 |

- 调用链：`JdbcStandardStore.insert → operator.insert → JdbcHelper.executeUpdate`；`JdbcStandardStore.selectPage → operator.selectPage`

## JdbcStandardStore
> 包：cn.oyzh.store.jdbc

- 职责：标准表存储抽象基类，面向模型对象提供 CRUD，内部持有 `H2StandardOperator`。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | operator | JdbcStandardOperator | 标准操作器（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JdbcStandardStore()` | 构造 | `new H2StandardOperator(tableDefinition)` → `initTable` → `init` |
  | `TableDefinition tableDefinition()` | 取表定义 | 有操作器用其定义，否则 `TableDefinition.ofClass` |
  | `boolean insert(M)` | 新增模型 | `handlePrimaryKeyValue` 后 `operator.insert(toRecord)` |
  | `boolean update(M)` | 更新模型 | 取主键列后 `operator.update` |
  | `boolean exist(Object)` / `exist(Map)` | 是否存在 | 委托 operator |
  | `M selectOne(Object)` / `(SelectParam)` / `(QueryParam)` | 查单条模型 | `toModel(operator.selectOne(...))` |
  | `List<M> selectList()` / `(QueryParam)` / `(SelectParam)` | 查列表模型 | 遍历 `toModel` |
  | `long selectCount(...)` | 统计 | 委托 operator |
  | `List<M> selectPage(...)` | 分页查模型 | `toModel` 转换 |
  | `boolean delete(M)` / `delete(Object)` / `delete(DeleteParam)` | 删除 | 委托 operator，影响行数 >0 |
  | `boolean clear()` | 清空 | `delete((DeleteParam) null)` |

- 调用链：`业务 → JdbcStandardStore.insert → JdbcStore.toRecord → JdbcStandardOperator.insert → JdbcHelper`

## JdbcKeyValueOperator
> 包：cn.oyzh.store.jdbc

- 职责：键值表操作器抽象基类，对 `KEY`/`VALUE` 两列整表覆盖写、读取与清空。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `boolean update(Map<String,Object>)` | 整表覆盖写 | 事务内先 `DELETE`，再批量 `INSERT(KEY,VALUE)`，`commit`；异常回滚 |
  | `Map<String,Object> select()` | 读取全部 | `SELECT *` 组装为 Map |
  | `boolean clear()` | 清空 | `DELETE FROM` 影响行数 >0 |

- 调用链：`JdbcKeyValueStore.update → operator.update → JdbcHelper.setParams/executeBatch`

## JdbcKeyValueStore
> 包：cn.oyzh.store.jdbc

- 职责：键值表存储抽象基类，面向模型提供整体覆盖写/读/清空，内部持有 `H2KeyValueOperator`。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | operator | JdbcKeyValueOperator | 键值操作器（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JdbcKeyValueStore()` | 构造 | `new H2KeyValueOperator` → `initTable` → `init` |
  | `TableDefinition tableDefinition()` | 取表定义 | 同上 |
  | `boolean update(M)` | 覆盖写入模型 | `toRecord` 后 `operator.update` |
  | `M select()` | 查询模型 | `toModel(operator.select())` |
  | `boolean clear()` | 清空 | 委托 operator |

- 调用链：`业务 → JdbcKeyValueStore.update → JdbcStore.toRecord → JdbcKeyValueOperator.update`

## JdbcStore
> 包：cn.oyzh.store.jdbc

- 职责：存储抽象基类，定义模型与记录（Map）的双向转换与模型类/表定义抽象。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `protected void init()` | 初始化钩子 | 空实现 |
  | `protected M newModel()` | 新建模型 | `ClassUtil.newInstance(modelClass())` |
  | `abstract Class<M> modelClass()` | 模型类 | 抽象方法 |
  | `abstract TableDefinition tableDefinition()` | 表定义 | 抽象方法 |
  | `protected M toModel(Map<String,Object>)` | 记录转模型 | 按列定义反射设置字段，`JdbcUtil.toJavaValue` 转换类型 |
  | `protected Map<String,Object> toRecord(M)` | 模型转记录 | 按列定义反射取值入 Map |

- 调用链：`JdbcStandardStore/JdbcKeyValueStore.toModel/toRecord → JdbcUtil.toJavaValue`

## JdbcUtil
> 包：cn.oyzh.store.jdbc

- 职责：JDBC 数据转换工具，SQL 值与 Java 类型互转、标识符/数据值按方言包装。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `toBool/toBoolVal`、`toByte/toByteVal`、`toInt/toIntVal`、`toLong/toLongVal`、`toDouble/toDoubleVal`、`toFloat/toFloatVal`、`toShort/toShortVal`、`toChar/toCharVal` | 基础类型转换 | Number/String 分支转换 |
  | `toString/toStringBuffer/toStringBuilder` | 字符串转换 | `toString()` 包装 |
  | `toBytes(Object)` | 字节数组转换 | Blob 读取或 byte[]/Byte[] 转换 |
  | `toDate/toLocalTime/toLocalDate/toLocalDateTime/toZonedDateTime` | 日期转换 | 借助 common 日期工具 |
  | `Object toJavaValue(Class<?>, Object)` | 通用转换 | 按目标类型分派到上述方法 |
  | `Object wrap(String)` / `wrapData(Object)` | 方言包装 | H2 下用 `H2Util.wrap/wrapData` |

- 调用链：`JdbcStore.toModel → JdbcUtil.toJavaValue`；`JdbcStandardOperator → JdbcUtil.wrap/wrapData`

## H2Util
> 包：cn.oyzh.store.jdbc.h2

- 职责：H2 方言工具，标识符/数据值包装、Java→SQL 类型映射与类型匹配校验。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static String wrap(String)` | 包装标识符 | 转大写并加反引号 |
  | `static Object wrapData(Object)` | 包装数据值 | null→"", 字节/数字原样, 其余加单引号 |
  | `static String toSqlType(Class<?>)` | 类型映射 | Long→bigint、Integer→integer、String→varchar、Double→double、byte[]→blob 等 |
  | `static boolean checkSqlType(String, String)` | 类型匹配 | 处理 CHARACTER VARYING/BINARY LARGE OBJECT 等别名 |

- 调用链：`ColumnDefinition.ofField → H2Util.toSqlType`；`H2StandardOperator.alterTable → H2Util.checkSqlType`

## H2StandardOperator
> 包：cn.oyzh.store.jdbc.h2

- 职责：H2 方言的标准表操作器，实现建表/改表与按参数删除。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `H2StandardOperator(TableDefinition)` | 构造 | 调用父类 |
  | `boolean initTable()` | 初始化表 | 查表是否存在，存在则 `alterTable`，否则 `createTable` |
  | `protected void alterTable()` | 改表 | 事务内对比列：新增/类型变更/删除，分别 `ADD COLUMN`/`ALTER COLUMN`/`DROP COLUMN`；异常回滚 |
  | `protected void createTable()` | 建表 | 拼 `CREATE TABLE`（主键 PRIMARY KEY） |
  | `int delete(DeleteParam)` | 按参数删除 | 拼条件与 `LIMIT` 的 `DELETE` |

- 调用链：`JdbcStandardStore 构造 → H2StandardOperator.initTable → alterTable/createTable`

## H2KeyValueOperator
> 包：cn.oyzh.store.jdbc.h2

- 职责：H2 方言的键值表操作器，实现 KEY/VALUE 表的建表/改表。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `H2KeyValueOperator(TableDefinition)` | 构造 | 调用父类 |
  | `boolean initTable()` | 初始化表 | 存在则 `alterTable`，否则 `createTable` |
  | `protected void alterTable()` | 改表 | 检查 KEY/VALUE 两列是否存在及类型，缺失则 `ADD COLUMN`，类型不符则 `ALTER COLUMN` |
  | `protected void createTable()` | 建表 | `CREATE TABLE (KEY VARCHAR, VALUE VARCHAR)` |

- 调用链：`JdbcKeyValueStore 构造 → H2KeyValueOperator.initTable → alterTable/createTable`

## DeleteParam
> 包：cn.oyzh.store.jdbc.param

- 职责：删除参数，承载删除条数限制与查询条件。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | limit | Long | 删除条数限制 |
  | queryParams | QueryParams | 查询条件 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void addQueryParam(QueryParam)` | 添加条件 | 惰性建 QueryParams 后添加 |
  | `getLimit/setLimit`、`getQueryParams/setQueryParams` | 属性读写 | 简单 getter/setter |

- 调用链：`JdbcStandardStore.delete(DeleteParam) → operator.delete(deleteParam)`

## OrderByParam
> 包：cn.oyzh.store.jdbc.param

- 职责：排序参数（列名 + 排序类型，默认 asc）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | name | String | 列名称 |
  | type | String | 排序类型，默认 "asc" |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `OrderByParam(String)` / `OrderByParam(String, String)` | 构造 | 赋值 |
  | `getName/setName`、`getType/setType` | 属性读写 | 简单 getter/setter |

- 调用链：`SelectParam.addOrderByParam → OrderByParams.add`

## OrderByParams
> 包：cn.oyzh.store.jdbc.param

- 职责：排序参数集合（继承 ArrayList），过滤 null 并提供便捷添加。
- 字段：无（继承 ArrayList）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `boolean add(OrderByParam)` | 添加 | null 返回 false |
  | `void add(String, String)` | 按名/类型添加 | `new OrderByParam` |
  | `static OrderByParams of(OrderByParam)` | 构建 | 包装单个参数 |

- 调用链：`JdbcStandardOperator.selectList → selectParam.getOrderByParams`

## PageParam
> 包：cn.oyzh.store.jdbc.param

- 职责：分页参数（每页条数、起始位置、查询条件）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | limit | long | 每页条数 |
  | start | long | 起始位置 |
  | queryParams | QueryParams | 查询条件（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `PageParam()` / `PageParam(long, long)` | 构造 | 赋值 |
  | `void addQueryParam(QueryParam)` | 添加条件 | 委托 queryParams |
  | `getQueryParams`、`getLimit`、`getStart` | 取值 | 简单 getter |

- 调用链：`JdbcStandardStore.selectPage → operator.selectPage → LIMIT/OFFSET`

## QueryParam
> 包：cn.oyzh.store.jdbc.param

- 职责：单个查询条件（列名、数据值、运算符，默认 "="，空值用 " is null"）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | name | String | 列名称 |
  | data | Object | 数据值 |
  | operator | String | 运算符，默认 "=" |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `QueryParam(String, Object)` | 构造 | data 为 null 时 operator=" is null" |
  | `QueryParam(String, Object, String)` | 构造 | 指定运算符 |
  | `static QueryParam of(String, Object)` / `of(String, Object, String)` | 工厂 | 构建条件 |
  | `getName/setName`、`getData/setData`、`getOperator/setOperator` | 属性读写 | 简单 getter/setter |

- 调用链：`JdbcStandardOperator.selectCount/selectPage/delete → queryParam.getOperator/getData`

## QueryParams
> 包：cn.oyzh.store.jdbc.param

- 职责：查询条件集合（继承 ArrayList），过滤 null 并提供便捷添加。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `boolean add(QueryParam)` | 添加 | null 返回 false |
  | `void add(String, Object)` / `add(String, Object, String)` | 便捷添加 | `new QueryParam` |
  | `static QueryParams of(QueryParam)` | 构建 | 包装单个条件 |

- 调用链：`DeleteParam/PageParam/SelectParam 聚合 QueryParams`

## SelectParam
> 包：cn.oyzh.store.jdbc.param

- 职责：综合查询参数，包含查询列、查询条件、排序与分页（limit/offset）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | limit | Long | 查询条数限制 |
  | offset | Long | 查询偏移量 |
  | queryParams | QueryParams | 查询条件 |
  | queryColumns | List\<String\> | 查询列 |
  | orderByParams | OrderByParams | 排序参数 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void addOrderByParam(OrderByParam)` | 添加排序 | 惰性建 OrderByParams |
  | `void addQueryParam(QueryParam)` | 添加条件 | 惰性建 QueryParams |
  | `void addQueryColumn(String)` / `addQueryColumns(String...)` | 添加查询列 | 惰性建列表 |
  | `getQueryColumns`、`getQueryParams`、`getOrderByParams/setOrderByParams`、`getLimit/setLimit`、`getOffset/setOffset` | 属性读写 | 简单 getter/setter |

- 调用链：`JdbcStandardStore.selectList/selectOne → operator.selectList/selectOne(SelectParam)`
