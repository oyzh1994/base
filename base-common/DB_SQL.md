# SQL 工具设计说明

## API

```java
List<String> statements = SqlUtil.split(sql, SqlDatabase.DM);
String formatted = SqlUtil.format(sql, SqlDatabase.ORACLE);
```

也可以传入数据库名称字符串，例如 `Postgres`、`sql_server`、`达梦`。

## 架构

- `SqlDialect`：定义拆分和美化能力。
- `SqlSplitter` / `SqlFormatter`：拆分器和美化器接口。
- `AbstractSqlDialect`：方言基础实现。
- `SqlLexicalProfile`：按数据库配置字符串、注释、引号、过程体和脚本终止符规则。
- `SqlLexer`、`LexicalSqlSplitter`、`LexicalSqlFormatter`：共享词法分析、拆分和美化引擎。
- `SqlAnalyzer`：查询语句判断、全字段查询判断和注释移除。
- `AnsiSqlDialect`、`MySqlSqlDialect`、`MariaDbSqlDialect`、`PostgreSqlSqlDialect`、`OracleSqlDialect`、`SqlServerSqlDialect`、`SqliteSqlDialect`、`H2SqlDialect`、`DmSqlDialect`：各数据库方言入口。

## 拆分能力

- 忽略单引号、双引号、反引号、方括号标识符中的分隔符。
- 支持重复引号、MySQL/DM 反斜杠转义、行注释和块注释。
- 支持 PostgreSQL/DM dollar quote、Oracle/DM `q'...'`。
- 支持 MySQL/MariaDB/DM `DELIMITER`、Oracle/DM `/`、SQL Server `GO`。
- 支持嵌套括号、子查询以及存储过程/PLSQL 块中的分号。

## 美化能力

- 统一关键字大小写。
- 按 `SELECT`、`FROM`、`WHERE`、`GROUP BY`、`ORDER BY` 等子句换行。
- 对嵌套查询、`AND`/`OR`、`CASE` 和过程块进行缩进。
- 保留字符串、注释、dollar quote 和 Oracle/DM q quote 的原始字面量内容。

## 语句分析

- `SqlUtil.isQuery(sql)`：判断单条 SQL 是否为查询类语句，支持 `SELECT`、`SHOW`、`DESCRIBE`、`EXPLAIN`、`VALUES` 等。
- `SqlUtil.isAllFieldsQuery(sql)`：判断查询的顶层字段列表是否全部为 `*` 或 `表别名.*`。
- `SqlUtil.removeComments(sql)`：移除 SQL 注释，同时保留字符串、标识符、dollar quote 和 q quote 中的注释样文本。
- `SqlUtil.compress(sql)`：移除注释并把格式化空白压缩为单行，适合消息列表展示；字面量中的换行会替换为空格以保证显示单行。
- 以上方法均提供 `SqlDatabase` 和数据库名称字符串重载。

## 测试

```bash
/Users/oyzh/Documents/Tools/apache-maven-3.9.11/bin/mvn \
  -pl base-common -am \
  -Dtest=cn.oyzh.common.db.SqlCompressorTest,cn.oyzh.common.db.SqlAnalyzerTest,cn.oyzh.common.db.SqlSplitterTest,cn.oyzh.common.db.SqlFormatterTest,cn.oyzh.common.db.SqlDialectTest \
  -Dsurefire.failIfNoSpecifiedTests=false test
```
