# H2 到 SQLite 静默迁移方案

> 模块：`base-store`  
> 实现：`cn.oyzh.store.jdbc.migration.H2SqliteMigrator`  
> 接入：EasyShell `ShellStoreUtil.init()`  
> 版本：`H2_TO_SQLITE_V1`  
> 日期：2026-10-23

## 1. 目标

EasyShell 的默认存储从 H2 切换为 SQLite。对于已经存在 H2 数据的老用户，应用启动时自动、静默地完成一次迁移，用户不感知、不确认、不手工操作。

核心约束：

- 检测到旧 H2 数据时自动迁移到 SQLite。
- 迁移成功后留下外置记录，后续启动不再读取或迁移 H2。
- 迁移失败只记录错误日志，不弹出迁移错误，不删除、不修改原 H2 数据文件。
- 数据迁移必须整体成功或整体失败，不能把一部分表暴露为目标 SQLite 数据库。
- 迁移后的 SQLite 必须继续支持项目原有的动态字段新增、类型修改、字段删除和 CRUD 行为。
- 迁移状态要能直接在磁盘上观察，不使用数据库内部迁移表。

## 2. 非目标

- 不升级或改写 H2 源文件。
- 不迁移 H2 的全部数据库对象，只迁移项目实际使用的 PUBLIC 表结构、数据和二级索引。
- 不提供交互式迁移向导、迁移进度界面或迁移成功提示。
- 不解决多个应用实例同时启动时的跨进程迁移互斥，调用方必须继续遵守单实例启动约束。

## 3. 文件约定

设 H2 基础路径为 `<base>`，SQLite 目标为 `<sqlite>`。EasyShell 中两者分别是：

```text
<userHome>/.easyshell_dev1/db.mv.db   H2 源文件
<userHome>/.easyshell_dev1/db          SQLite 目标文件
```

迁移涉及的文件：

| 文件 | 作用 | 生命周期 |
|---|---|---|
| `<base>.mv.db` | H2 2.x 源数据库，只读打开 | 永久保留，不删除、不修改 |
| `<base>.h2.db` | H2 旧后缀源数据库，兼容查找 | 永久保留，不删除、不修改 |
| `<sqlite>` | 正式 SQLite 数据库 | 迁移成功后由应用使用 |
| `<sqlite>.migration.properties` | 外置成功记录 | 成功后长期存在；被删除后可按规则重建 |
| `<sqlite>.migration.pending` | 目标文件已替换、正式记录尚未发布的临时记录 | 正常完成后被原子重命名为正式记录 |
| `<sqlite>.migration-*.tmp` | 迁移期间的临时 SQLite 文件 | 正常完成或普通异常时清理；硬崩溃可能残留 |

查找 H2 源文件时优先 `.mv.db`，其次 `.h2.db`。EasyShell 传入的是不带后缀的基础路径。

## 4. 启动决策顺序

`migrateIfNeeded(base, sqlite)` 按以下固定优先级执行，顺序不能调整：

| 优先级 | 磁盘状态 | 动作 | 读取 H2 内容 | 检查 SQLite 是否为空 |
|---|---|---|---|---|
| 1 | 正式记录文件存在 | 直接视为已完成，返回 `NOT_NEEDED` | 否 | 否 |
| 2 | 无正式记录；pending 存在且格式、迁移标识、SQLite 大小和 SHA-256 全部匹配 | 将 pending 原子发布为正式记录，返回 `NOT_NEEDED` | 否 | 否 |
| 3 | 无正式记录；pending 存在但指纹不匹配 | 删除失效 pending，继续后续判断 | 否 | 是 |
| 4 | 无正式记录；SQLite 是大小大于 0 的普通文件 | 生成 `record_origin=REGENERATED` 的正式记录，返回 `NOT_NEEDED` | 否，只读取 H2 文件元信息 | 否，按“非空即已完成”处理 |
| 5 | 无正式记录；SQLite 不存在或大小为 0；H2 文件存在 | 执行完整迁移，返回 `MIGRATED` | 是，只读 | 是 |
| 6 | 无正式记录；SQLite 不存在或大小为 0；H2 文件不存在 | 不迁移，返回 `NOT_NEEDED`，交给应用首次初始化 SQLite | 否 | 是 |
| 7 | 迁移或记录重建抛异常 | 返回 `FAILED`，只写错误日志 | 视阶段而定 | 不适用 |

关键语义：

- **正式记录文件是最高优先级事实来源。** 即使 SQLite 是空文件、损坏文件或文件不存在，只要正式记录存在，就不会再次迁移。
- **非空 SQLite 在没有正式记录时视为“迁移已完成但记录丢失”。** 只重建记录，不读取 H2 内容，避免每次启动都解析大 H2 文件。
- **空 SQLite 视为上次迁移未完成。** 只有这种情况下才允许继续执行迁移。
- **新安装没有 H2 文件时不做任何迁移。** 不创建记录文件，由 `JdbcManager` 正常初始化新的 SQLite。
- pending 恢复必须校验 SQLite 的大小和 SHA-256，不能用“文件存在”直接发布过期记录。

## 5. 迁移流程

```text
1. 校验 H2 源文件存在，且与 SQLite 目标不是同一路径
2. 计算 H2 源文件指纹：大小、修改时间、SHA-256
3. 在 SQLite 同目录创建临时文件 <sqlite>.migration-*.tmp
4. 以只读方式打开 H2，连接临时 SQLite
5. 开启 SQLite 事务，读取 PUBLIC 表结构
6. 逐表建表、复制数据、创建二级索引
7. 提交事务
8. 执行 PRAGMA integrity_check 和总行数校验
9. 再次计算 H2 指纹，必须与迁移开始时完全一致
10. fsync 临时 SQLite 文件
11. 写入 pending 记录，记录目标文件大小和 SHA-256
12. 原子替换临时 SQLite 与正式 SQLite
13. 原子发布 pending 为正式记录
14. 尽力 fsync 父目录
```

迁移期间 H2 使用只读连接，源文件指纹在前后各检查一次。如果源文件在迁移中被外部进程修改，本次迁移失败，目标文件不会被替换。

## 6. 原子性与崩溃恢复

SQLite 数据先完整写入同目录临时文件，所有建表、数据写入和索引创建都在同一个事务中完成。正式 SQLite 只在事务提交、完整性检查、行数检查和 H2 指纹复核全部通过后才被原子替换。

| 崩溃/失败时点 | 正式 SQLite | pending | 正式记录 | 下次启动行为 |
|---|---|---|---|---|
| 临时文件构建中 | 原样 | 无 | 无 | 重新迁移；残留临时文件不参与判断 |
| pending 已写、目标未替换 | 原样 | 指纹不匹配 | 无 | 删除 pending，重新迁移 |
| 目标已替换、记录未发布 | 新数据 | 指纹匹配 | 无 | 发布 pending，不重复迁移 |
| 正式记录已发布 | 新数据 | 无 | 存在 | 直接使用 SQLite |

发布顺序是“目标 SQLite 先替换，正式记录后发布”。因此不存在“正式记录已存在但目标还是旧文件”的正常崩溃窗口。反过来，目标已替换但记录未发布时，pending 能把状态补齐。

目录 fsync 在部分平台不可用，尤其是 Windows。该步骤现在是最佳努力：失败只写 WARN 日志，不会把已经成功发布的迁移误报为失败。

## 7. 迁移记录格式

记录使用 Java `Properties` 格式，文件示例：

```properties
record_format=H2_TO_SQLITE_V1
migration_id=H2_TO_SQLITE_V1
status=COMPLETED
record_origin=MIGRATED
source_file=/Users/oyzh/.easyshell_dev1/db.mv.db
source_size=1634304
source_modified_millis=1792745596062
source_sha256=b1582adbe614964b4d60d94f8d33775460de093d32d6f071c745911f0dd9f010
h2_version=2.4.240
sqlite_file=db
sqlite_size=491520
sqlite_sha256=fd6870b46ef601b406843d3da2e581df7add7cb8e45be1df19a69119087aa8d1
table_count=28
row_count=2539
migrated_at=2026-10-23T09:45:43.160493Z
```

字段说明：

| 字段 | 说明 |
|---|---|
| `record_format` / `migration_id` | 迁移协议版本，当前均为 `H2_TO_SQLITE_V1` |
| `status` | 固定为 `COMPLETED` |
| `record_origin` | `MIGRATED` 表示真实迁移；`REGENERATED` 表示根据已有非空 SQLite 补建记录 |
| `source_file` | H2 源文件路径；补建记录时可能为空 |
| `source_size` / `source_modified_millis` / `source_sha256` | H2 快照指纹，用于审计 |
| `h2_version` | 通过 `H2VERSION()` 读取的源版本 |
| `sqlite_file` | 目标文件名 |
| `sqlite_size` / `sqlite_sha256` | 迁移完成时目标 SQLite 指纹；补建记录时为 `NOT_RECORDED` |
| `table_count` / `row_count` | 迁移统计；补建记录时为 `NOT_RECORDED` |
| `migrated_at` | 迁移完成时间 |
| `recovered_at` | 补建记录时间，仅 `REGENERATED` 记录存在 |

正式记录存在时不会解析记录内容，也不会用记录中的哈希重新校验 SQLite。记录只作为“已完成”的外置标记和审计信息。

## 8. 结构与数据迁移范围

已迁移：

- `PUBLIC` schema 下的普通表。
- 列名、SQLite 类型亲和性、`NOT NULL` 约束。
- 主键及其列顺序。
- 非主键的普通索引和唯一索引。
- 表数据，包括字符串、整数、浮点、布尔、BLOB、CLOB、日期时间等常见 JDBC 值。

不会迁移：

- 外键、级联规则。
- 默认值、identity/auto increment、序列。
- CHECK 约束。
- 视图、触发器、存储过程、注释。
- H2 私有类型和复杂对象类型。

类型映射：

| H2/JDBC 类型 | SQLite 类型 |
|---|---|
| BOOLEAN、BIT、TINYINT、SMALLINT、INTEGER、BIGINT | `INTEGER` |
| REAL、FLOAT、DOUBLE | `REAL` |
| DECIMAL、NUMERIC | `REAL`（存在精度风险，见第 12 节） |
| BINARY、VARBINARY、LONGVARBINARY、BLOB | `BLOB` |
| CHAR、VARCHAR、CLOB、TEXT、日期时间及其他默认类型 | `TEXT` 或按名称归一化后的亲和性 |

迁移后的数据校验包括：

- SQLite `PRAGMA integrity_check` 必须返回 `ok`。
- 所有迁移表的总行数必须与复制行数一致。
- 单元测试额外校验主键、布尔值、BLOB、时间戳和唯一索引。
- `Dev1MigrationVerificationTest` 会复制本机 dev1 的 H2 源库重新迁移，再按表、按列、按行比较 H2 与 SQLite 的规范化值；当前 28 张表、2539 行全部一致。

## 9. 迁移后的动态字段能力

迁移完成后 EasyShell 使用 `JdbcDialect.SQLITE`。`JdbcManager.dialect` 在 `ShellStoreUtil.init()` 中显式同步为 SQLite，避免 `JdbcManager` 提前类加载时仍停留在 H2 默认值。

SQLite 的动态结构处理由 `SqliteStandardOperator` 和 `SqliteUtil.rebuildTable` 完成：

- 启动时读取现有列和主键，与模型定义比较。
- 发现新增列、删除列、类型变化或主键变化时，在事务内重建表。
- 重建时只复制目标模型和旧表共同存在的列，因此新增列有默认空值，删除列被丢弃，保留列数据不变。
- 任一 DDL/DML 失败会回滚，不留下半重建表。

`JdbcDialectParityTest` 用同一套模型分别跑 H2 和 SQLite，覆盖了旧列删除、新列增加、列类型变化、插入、更新、按主键查询和删除，最终数据快照完全一致。

`ShellConnectSchemaEvolutionTest` 进一步使用真实 dev1 SQLite 副本和实际 `ShellConnectStore`，覆盖缺列回补、双冗余列删除、数字/文本列类型回正、复合结构漂移一次修复，以及 157 行真实数据上的 CRUD。每个场景都校验最终 schema 与 `ShellConnect` 模型一致、主键仍为 `ID`、完整性检查和原文件哈希不变。

`SqliteSchemaRebuildAtomicityTest` 通过注入非法建表 SQL，让动态重建在 `RENAME` 之后、`CREATE` 阶段失败，验证整段 DDL/DML 事务回滚：原表仍存在、列定义和两行数据不变、无备份表残留、`integrity_check=ok`。

## 10. 失败策略与日志

迁移失败时：

- 不创建正式记录。
- 不替换正式 SQLite；普通失败路径会删除临时文件、SQLite sidecar 和 pending。
- H2 源文件保持不变。
- `H2SqliteMigrator` 使用 `JulLog.error` 记录源、目标和异常信息。

EasyShell 当前不会因为迁移失败阻止启动，后续继续按 SQLite 初始化存储。这是“失败只留错误日志”的产品约定，但也带来第 12 节描述的状态歧义风险。

可观察日志包括：

```text
H2 to SQLite migration started
H2 to SQLite migration completed
H2 to SQLite migration failed
Stale H2 to SQLite pending migration record discarded
H2 to SQLite migration record regenerated
H2 to SQLite pending migration record recovered
Directory fsync skipped
```

## 11. 运维观察与排查

查看是否已完成迁移：

```bash
cat ~/.easyshell_dev1/db.migration.properties
ls -l ~/.easyshell_dev1/db.migration.pending
```

查看 SQLite 完整性、表数量和行数：

```bash
sqlite3 ~/.easyshell_dev1/db 'PRAGMA integrity_check;'
sqlite3 ~/.easyshell_dev1/db \
  "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';"
```

查看文件指纹：

```bash
shasum -a 256 ~/.easyshell_dev1/db.mv.db ~/.easyshell_dev1/db
```

记录文件中的 `source_sha256` 和 `sqlite_sha256` 可用于确认源文件未被改写、SQLite 未被迁移后替换。

## 12. 边界审查结论与设计风险

已通过实现和测试闭合的边界：

| 边界 | 结论 |
|---|---|
| 新安装，无 H2 文件 | 不迁移、不创建记录，由首次 SQLite 初始化接管 |
| 正式记录存在，SQLite 为空或缺失 | 正式记录优先，直接视为已完成，不再读取 H2 |
| 正式记录被删除，SQLite 非空 | 视为迁移已完成但记录丢失，补建 `REGENERATED` 记录，不覆盖 SQLite |
| 正式记录被删除，SQLite 为 0 字节 | 视为上次迁移未完成，继续执行迁移 |
| 目标已替换，记录尚未发布 | 下次启动校验 pending 中的大小和 SHA-256 后发布记录 |
| pending 过期或与目标不匹配 | 删除 pending；目标为空则重迁，目标非空则补建记录 |
| H2 源文件损坏但 SQLite 非空 | 补建记录不需要读取 H2 内容，不阻塞启动 |
| 迁移中途异常 | 临时 SQLite 和 pending 被清理，正式目标不替换，无正式记录 |
| 动态结构重建中途异常 | 事务回滚，原表和原数据保留，无半重建表 |

仍需明确接受或后续处理的风险，按优先级排列：

1. **失败后继续启动会掩盖旧数据。** 如果迁移失败，EasyShell 继续启动并新建 SQLite schema，下次启动会因 SQLite 非空而生成 `REGENERATED` 记录，不再自动尝试 H2。这是当前“失败只留日志、不阻塞产品”的直接代价；发布前应监控 `H2 to SQLite migration failed`，必要时人工移走新建 SQLite 和记录后重试。
2. **没有跨进程迁移锁。** 两个实例同时首次启动时可能同时迁移，依赖 EasyShell 的单实例启动约束；如未来允许多实例，需要增加文件锁或进程间互斥。
3. **记录文件按存在性判定，不解析内容。** 空文件、损坏文件或手写的任意文件只要存在，都会被视为迁移完成。这是明确的产品语义。
4. **记录存在但 SQLite 缺失时不会重新迁移。** 必须人工恢复 SQLite，或归档/删除正式记录后重新评估。
5. **非空 SQLite 一律视为已完成。** “非空”只看文件大小，因此只有空表结构、损坏文件或应用在迁移失败后新建的数据库，也会被认定为已完成并补建记录。
6. **生产迁移的内容校验是行数级。** 迁移器运行时保证完整性、总行数和源文件指纹不变；逐表逐行全值比对由 dev1 集成测试完成，不会在生产启动时为每次迁移额外计算全库业务哈希。
7. **结构范围有限。** 外键、默认值、identity、视图、触发器、CHECK 等不会迁移。EasyShell 当前表结构不依赖这些对象，但通用 `base-store` 使用者需要额外评估。
8. **DECIMAL/NUMERIC 映射为 REAL。** 高精度小数可能发生精度变化；当前 EasyShell 模型未使用 BigDecimal，但通用库使用者需要关注。
9. **硬崩溃可能留下临时文件。** `<sqlite>.migration-*.tmp` 不参与状态判断，也不会在启动时全局清理，需要时可人工删除。
10. **动态重建会丢失二级索引。** 这是现有 SQLite 动态改表实现的性能边界，不影响字段和数据逻辑；后续如需保持索引，可在重建流程中重建索引。
11. **只面向项目实际使用的 H2 2.x。** 迁移器按 H2 `2.4.240` 可读格式处理，不提供更早版本兼容或升级路径。

## 13. 回滚原则

迁移设计保留 H2 源文件，因此回滚的基础是“回退到仍使用 H2 的应用版本”，而不是反向导出 SQLite：

1. 停止应用。
2. 备份 SQLite 文件、正式记录和 pending 文件。
3. 回退到使用 H2 的应用版本，确认其数据库路径仍指向原 H2 基础路径。
4. 保留 `db.mv.db` 原文件；不要删除、改写或重新初始化 H2 源文件。
5. 如果旧版本会误读 SQLite 同名文件，应将 SQLite 文件移到备份目录，而不是覆盖 H2。

不建议通过手工修改迁移记录来强制重跑迁移。若确需重跑，应先完整备份并移走现有 SQLite、正式记录和 pending，再确认 H2 源文件指纹未变化。
