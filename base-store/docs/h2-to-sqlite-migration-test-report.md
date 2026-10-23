# H2 到 SQLite 迁移测试报告

> 被测实现：`H2SqliteMigrator`、`Dev1MigrationVerificationTest`、`JdbcDialectParityTest`、`SqliteSchemaRebuildAtomicityTest`、`ShellConnectSchemaEvolutionTest`、EasyShell `ShellStoreUtil`  
> 测试日期：2026-10-23  
> 结论：当前环境通过。迁移、状态恢复、失败回滚、H2 源保护、SQLite 动态字段与 CRUD 对齐均达到验收要求。

## 1. 测试环境

| 项目 | 值 |
|---|---|
| 操作系统 | macOS |
| Maven | 3.10.0 |
| Maven 运行 JDK | OpenJDK 27.0.1 |
| H2 | 2.4.240 |
| SQLite JDBC | 3.53.4.0 |
| SQLite CLI | 3.43.2 |
| 测试模块 | `base-store` |
| EasyShell 接入验证 | `mvn -DskipTests compile`、`ShellConnectSchemaEvolutionTest` |

## 2. 自动化测试

执行命令：

```bash
cd /Users/oyzh/IdeaProjects/oyzh/base
mvn -pl base-store test
```

结果：

```text
Tests run: 33, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

测试分布：

| 测试类 | 数量 | 结果 |
|---|---:|---|
| `H2SqliteMigratorTest` | 10 | 通过 |
| `Dev1MigrationVerificationTest` | 1 | 通过 |
| `SqliteSchemaRebuildAtomicityTest` | 1 | 通过 |
| `JdbcDialectParityTest` | 1 | 通过 |
| `SqliteStoreTest` | 4 | 通过 |
| `SqliteTest` | 3 | 通过 |
| CSV 相关测试 | 13 | 通过 |

Surefire 报告位于：

```text
base-store/target/surefire-reports/
```

## 3. 迁移边界用例

`H2SqliteMigratorTest` 覆盖的 10 个场景：

| 用例 | 验证点 | 结果 |
|---|---|---|
| `migratesOnceAndPreservesH2Source` | 真实 H2 迁移；表数/行数统计；主键、文本、布尔、BLOB、时间戳和唯一索引；H2 大小/修改时间/SHA-256 不变；pending 恢复；第二次启动不重复迁移 | 通过 |
| `newInstallWithoutH2SkipsMigration` | 无 H2 的新安装不生成 SQLite、记录或 pending；正常初始化 SQLite | 通过 |
| `existingRecordWinsEvenIfSqliteIsEmpty` | 正式记录存在时，即使 SQLite 为 0 字节也直接视为完成，不迁移、不改记录 | 通过 |
| `existingRecordWinsEvenIfSqliteIsMissing` | 正式记录存在但 SQLite 文件缺失时仍直接视为完成，不读取 H2、不创建新库 | 通过 |
| `missingRecordForNonEmptySqliteIsRegenerated` | 记录丢失但 SQLite 非空时，不覆盖 SQLite，补建 `REGENERATED` 记录；再次启动不变化 | 通过 |
| `corruptH2DoesNotBlockRecordRegeneration` | H2 文件损坏但 SQLite 非空且无记录时，仍能补建记录，证明该路径不读取 H2 内容 | 通过 |
| `stalePendingRecordIsDiscarded` | pending 的大小/SHA-256 与 SQLite 不匹配时删除 pending，再按“非空 SQLite”重建记录 | 通过 |
| `stalePendingRecordDoesNotBlockMigrationIntoEmptyTarget` | pending 过期且目标为空时删除 pending，并继续完成真实迁移 | 通过 |
| `emptySqliteWithoutRecordContinuesMigration` | SQLite 为 0 字节且无记录时继续执行迁移 | 通过 |
| `failedMigrationLeavesTargetUntouched` | H2 损坏导致迁移失败时，SQLite 保持原样，不产生正式记录/pending/临时文件，只留下错误日志 | 通过 |

## 4. 全库真实 H2 迁移与逐行值对比

`Dev1MigrationVerificationTest` 不依赖 dev1 已有的 SQLite，而是把 `~/.easyshell_dev1/db.mv.db` 复制到临时目录，重新执行完整 H2 到 SQLite 迁移，再直接对比源库与迁移库。

验证结果：

- 迁移统计为 28 张表、2539 行。
- 表名集合完全一致。
- 每张表的列名集合完全一致。
- 所有行按列读取、规范化类型、排序后逐行比较完全一致，不只比较行数。
- SQLite `PRAGMA integrity_check` 为 `ok`。
- pending 已发布为正式记录，记录中的表数、行数、`source_sha256`、`sqlite_sha256` 均与本次迁移一致。
- 迁移前后的 H2 副本 SHA-256 不变。
- 原始 `~/.easyshell_dev1/db.mv.db` 在测试前后 SHA-256 不变。

## 5. SQLite 动态字段与 CRUD 对齐

执行用例：

```text
JdbcDialectParityTest.dynamicSchemaAndCrudBehaveTheSame
```

测试方法：

- 同一套模型分别在 H2 和 SQLite 上启动。
- 旧表包含待删除列 `obsolete`，模型不含该列。
- 模型新增 `count` 列。
- `code` 列发生类型变化：H2 `INTEGER`，模型目标 `TEXT`。
- 随后执行查询、插入、更新、按主键判断存在、删除、全量查询。

验证结果：

- H2 与 SQLite 均能完成字段新增、类型修改、字段删除。
- 两条记录经历新增、更新、删除后，两端最终快照完全一致：

```text
1|updated|456|10
3|inserted|new|30
```

- 动态结构处理失败时 SQLite 使用事务回滚，不暴露半重建状态。

结论：迁移后的 SQLite 动态字段能力满足 EasyShell 当前使用方式。

`SqliteSchemaRebuildAtomicityTest` 进一步从操作器入口注入重建失败：

- 原表为 `atomic_table(id INTEGER PRIMARY KEY, name TEXT)`，包含两条数据。
- 目标模型增加非法类型列，触发 `ALTER TABLE ... RENAME` 后的 `CREATE TABLE` 失败。
- 失败后原表仍存在，列定义和两行数据完全不变。
- 不残留 `atomic_table_backup_*` 表，`PRAGMA integrity_check` 仍为 `ok`。

结论：结构重建不是“先破坏再尝试恢复”，而是由 SQLite 事务整体回滚，满足原子性要求。

## 6. ShellConnect 真实结构漂移测试

针对 `ShellConnectStore` 新增集成测试：

```text
src/test/java/cn/oyzh/easyshell/store/ShellConnectSchemaEvolutionTest.java
```

测试直接读取 `~/.easyshell_dev1/db`，先复制到临时目录，再在副本上注入结构漂移；原始 dev1 文件只读，每个测试结束时都会重新校验 SHA-256。

执行命令：

```bash
cd /Users/oyzh/IdeaProjects/oyzh/easyshell
mvn -Dtest=ShellConnectSchemaEvolutionTest test
```

结果：

```text
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

覆盖场景：

| 用例 | 注入的异常结构 | 验证结果 |
|---|---|---|
| `addsMissingColumnAndPreservesExistingRows` | 删除 `REMARK` 列 | 启动 `ShellConnectStore` 后自动补回 `REMARK TEXT`；157 行不变；其余列数据快照一致；schema 与 `ShellConnect` 模型一致 |
| `dropsObsoleteColumnAndPreservesExistingRows` | 增加 `LEGACY_EXTRA`、`LEGACY_SECOND` 两列 | 启动后两列均被删除；无 `t_connect_backup_*` 残留；157 行和原有列数据不变 |
| `restoresChangedNumericColumnTypeAndValues` | `CONNECTTIMEOUT` 从 `INTEGER` 改为 `TEXT` | 启动后恢复为 `INTEGER`；154 个非空值通过 `ShellConnectStore.selectOne()` 逐一校验一致 |
| `restoresChangedTextColumnTypeAndValues` | `REMARK` 从 `TEXT` 改为 `INTEGER`，并写入中文备注 | 启动后恢复为 `TEXT`；中文备注逐行保留，并通过模型读取一致 |
| `repairsCombinedSchemaDriftInOneStartup` | 同时缺 `REMARK`、多两个冗余列、`CONNECTTIMEOUT` 类型错误 | 一次启动全部修复；再次创建 `ShellConnectStore` 不重复重建；数据、类型、列集合、主键同时正确 |
| `shellConnectStoreCrudWorksOnRealDev1Copy` | 无结构漂移 | 从真实数据加载 157 条；更新、插入、查询存在性、删除均成功 |

每个结构变更场景还额外校验：

- 最终列数与 `ShellConnect` 模型注解列数完全一致。
- 每列类型通过 `SqliteUtil.checkSqlType` 与模型定义匹配。
- 主键仍然只有 `ID`。
- 157 行数据数量和未受影响列的快照完全一致。
- `PRAGMA integrity_check` 返回 `ok`。
- 不残留动态重建备份表。

其中数字类型恢复和复合漂移用例都额外重启了 `ShellConnectStore`，确认修复后的 schema 与模型一致，不会在第二次启动重复重建。

测试后 dev1 原文件 SHA-256 仍为：

```text
fd6870b46ef601b406843d3da2e581df7add7cb8e45be1df19a69119087aa8d1
```

结论：`ShellConnectStore` 在真实 dev1 数据副本上完成了字段新增、删除、数字/文本类型修改和复合漂移修复，且 CRUD 与数据保留行为正常。

## 7. EasyShell 接入验证

执行命令：

```bash
cd /Users/oyzh/IdeaProjects/oyzh/base
mvn -pl base-store -DskipTests install

cd /Users/oyzh/IdeaProjects/oyzh/easyshell
mvn -DskipTests compile
```

结果：

```text
base-store BUILD SUCCESS
EasyShell BUILD SUCCESS
```

接入点确认：

- `ShellStoreUtil.init()` 先调用 `H2SqliteMigrator.migrateIfNeeded(storeDatabase, storeDatabase)`。
- 随后设置 `JdbcConst.dbDialect(JdbcDialect.SQLITE)`。
- 同时显式设置 `JdbcManager.dialect = JdbcDialect.SQLITE`，避免 `JdbcManager` 提前类加载后仍使用 H2 静态默认值。
- 再调用 `JdbcManager.takeoff()`，保证连接 URL 与操作器都使用 SQLite。

## 8. 本机 dev1 数据复测

测试数据目录：

```text
/Users/oyzh/.easyshell_dev1
```

文件状态：

| 文件 | 结果 |
|---|---|
| `db.mv.db` | 保留，SHA-256 为 `b1582adbe614964b4d60d94f8d33775460de093d32d6f071c745911f0dd9f010` |
| `db` | SQLite，SHA-256 为 `fd6870b46ef601b406843d3da2e581df7add7cb8e45be1df19a69119087aa8d1` |
| `db.migration.properties` | 存在，`status=COMPLETED`，`record_origin=MIGRATED` |
| `db.migration.pending` | 不存在 |

迁移记录与 H2 源文件核对：

- 记录的 `source_sha256` 与当前 `db.mv.db` 的 SHA-256 一致。
- 记录的 `source_modified_millis=1792745596062` 与 H2 文件修改时间 `2026-10-23 16:53:16 +0800` 一致。
- 记录的 `sqlite_sha256` 与当前 SQLite 文件一致。
- EasyShell 全程未改写 H2 源文件。

SQLite 数据检查：

```text
PRAGMA integrity_check              -> ok
用户表数量                          -> 28
所有用户表总行数                    -> 2539
t_connect 行数                      -> 157
SQLite 索引对象数量                 -> 22
数据库内迁移记录表                  -> 无
```

逐表行数与 H2 对比：

| 表 | H2 行数 | SQLite 行数 |
|---|---:|---:|
| T_COLLECT | 2 | 2 |
| T_CONNECT | 157 | 157 |
| T_FILE_COLLECT | 4 | 4 |
| T_FILTER | 0 | 0 |
| T_GROUP | 95 | 95 |
| T_JUMP_CONFIG | 17 | 17 |
| T_KEY | 11 | 11 |
| T_KEY_FILTER_HISTORY | 7 | 7 |
| T_PROXY | 108 | 108 |
| T_QUERY | 67 | 67 |
| T_REDIS_COLLECT | 3 | 3 |
| T_REDIS_KEY_FILTER_HISTORY | 4 | 4 |
| T_REDIS_QUERY | 8 | 8 |
| T_SETTING | 65 | 65 |
| T_SNIPPET | 8 | 8 |
| T_SSH_CONFIG | 12 | 12 |
| T_SSL | 14 | 14 |
| T_SYNC | 0 | 0 |
| T_TERMINAL_HISTORY | 562 | 562 |
| T_TERM_HISTORY | 147 | 147 |
| T_TUNNELING_CONFIG | 4 | 4 |
| T_X11_CONFIG | 1227 | 1227 |
| T_ZK_AUTH | 3 | 3 |
| T_ZK_COLLECT | 0 | 0 |
| T_ZK_FILTER | 0 | 0 |
| T_ZK_QUERY | 0 | 0 |
| T_ZK_SASL_CONFIG | 14 | 14 |
| T_ZK_SETTING | 0 | 0 |
| **合计** | **2539** | **2539** |

结论：真实业务数据表数量、逐表行数和总行数完全一致，SQLite 完整性检查通过。

## 9. 可复现命令

```bash
mvn -pl base-store test

cd /Users/oyzh/IdeaProjects/oyzh/easyshell
mvn -Dtest=ShellConnectSchemaEvolutionTest test

sqlite3 ~/.easyshell_dev1/db 'PRAGMA integrity_check;'
sqlite3 ~/.easyshell_dev1/db \
  "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';"

shasum -a 256 ~/.easyshell_dev1/db.mv.db ~/.easyshell_dev1/db
cat ~/.easyshell_dev1/db.migration.properties
```

## 10. 未覆盖与后续建议

本次自动化测试未做进程级崩溃注入、Windows 实机测试和两个实例并发启动测试。对应风险已在设计文档第 12 节记录：

- Windows 目录 fsync 已改为尽力而为，但仍建议在 Windows 实机执行一次真实迁移。
- 当前没有跨进程迁移锁，依赖 EasyShell 单实例启动约束。
- 本项目仅面向 H2 2.x（2.4.240），不包含更早版本兼容。
- 生产迁移器只做 SQLite 完整性和行数级校验；本报告的 dev1 全量逐行比对属于集成测试，不会在每次启动时执行。

验收结论：在 macOS 当前环境、H2 2.4.240、SQLite JDBC 3.53.4.0 和本机 dev1 数据上，迁移静默自动完成、状态可观察、失败不污染目标、H2 源文件保留。`base-store` 33 项测试与 `ShellConnectStore` 6 项真实结构漂移测试全部通过；dev1 源库重新迁移后 28 张表、2539 行逐行值一致，SQLite 动态字段、结构重建回滚和 CRUD 行为与 H2 对齐，达到本次交付要求。
