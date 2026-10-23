# 进程单实例锁设计与测试报告

> 实现：`cn.oyzh.common.system.ProcessInstanceLock`  
> 接入：EasyShell `EasyShellApp.main()`  
> 日期：2026-10-23

## 1. 目标

替代原 H2 数据库文件锁提供的重复启动保护。应用启动时创建并锁定实例文件，第二个实例检测到锁后读取持有者 PID 并拒绝启动；进程被强杀后，操作系统自动释放文件锁，下一次启动可正常恢复。

EasyShell 锁文件位置：

```text
<storePath>/easyshell.lock
```

生产默认是 `~/.easyshell/easyshell.lock`，开发默认是 `~/.easyshell_dev/easyshell.lock`。

## 2. 文件格式

获取锁成功后写入：

```text
pid=<当前进程PID>
started_at=<ISO-8601启动时间>
```

写入后执行 `force(true)`，保证其他进程读取时能看到完整内容。锁文件只是 PID 的可观察快照，真正的互斥仍由操作系统文件锁保证。

## 3. 启动判定

| 场景 | 判定 | 结果 |
|---|---|---|
| 锁文件不存在 | `tryLock()` 成功 | `ACQUIRED`，写入当前 PID，允许启动 |
| 锁文件存在但未锁定 | `tryLock()` 成功 | `ACQUIRED`，覆盖旧 PID，允许启动 |
| 其他进程持有锁，PID 存活 | 锁失败，读取 PID 并校验进程 | `ALREADY_RUNNING`，拒绝启动 |
| 其他进程持有锁，PID 已死亡或不可读 | 锁失败且无法确认存活持有者 | `LOCKED`，保守拒绝启动 |
| 同一 JVM 已持有锁 | 进程内锁路径登记命中 | `ALREADY_RUNNING`，不打开第二个通道 |
| 进程被强杀 | 操作系统释放文件锁，PID 内容可能残留 | 下次 `tryLock()` 成功并覆盖旧内容 |

`LOCKED` 不会自动放行，原因是无法区分“陈旧锁”和“持锁进程尚未写完 PID”。进程被正常强杀时文件锁会由操作系统释放，不会落入该分支；该分支主要防御异常文件系统或锁服务故障。

## 4. 关闭语义

正常关闭时：

1. 持有文件锁读取并确认锁文件中的 PID 属于当前进程。
2. 在仍持锁时尝试删除锁文件。
3. 释放文件锁并关闭通道。

删除必须发生在释放锁之前，否则可能出现：旧实例释放锁后，新实例立即获取并写入 PID，旧实例再把新实例的锁文件删除，造成第三个实例可重复启动。

Windows 可能不允许删除已打开的锁文件。删除失败会被静默忽略，`close()` 不会向调用方抛出异常；文件锁仍会随通道关闭而释放，遗留 PID 在下次启动时会被覆盖。因此删除是“尽力而为”，不是状态判定前提，也不影响程序退出。

实现还避免在持锁期间通过另一个文件描述符重新打开锁文件。POSIX `fcntl` 记录锁可能在同进程关闭该文件的任意描述符时被释放，因此：

- 同一 JVM 的重复获取在打开通道前由进程内路径登记拦截。
- 关闭时读取 PID 复用当前 `FileChannel`，不重新 `Files.readString`。
- 测试会启动真实外部进程再次尝试，确认同 JVM 的重复检查没有释放原锁。

## 5. EasyShell 接入

`EasyShellApp.main()` 在存储初始化、JavaFX 启动和事件总线初始化之前调用实例检查。重复启动时只记录日志并提示“程序已启动，请不要重复打开”，随后直接返回。

正常退出路径：

- `main()` 的 `finally` 兜底释放锁。
- `stop()` 在客户端检测、X11、JDBC 存储和缓存清理完成后释放锁。
- `ProcessInstanceLock.close()` 幂等，因此两条路径重复调用安全。

实例锁必须先于 `ShellStoreUtil.init()` 获取，这样 H2 到 SQLite 迁移和新库初始化都不会被两个实例并发执行。

## 6. 边界与限制

| 边界 | 结论 |
|---|---|
| 首次启动 | 自动创建父目录和锁文件 |
| 正常退出 | 尽力删除锁文件 |
| 进程崩溃或被强杀 | 操作系统释放锁，下次可启动 |
| 锁文件残留且 PID 已死 | 可直接获取并覆盖 |
| 锁文件存在但内容为空/损坏且无人持锁 | 可直接获取并覆盖 |
| 同 JVM 重复获取 | 判定为已运行 |
| 锁文件被人工删除后再次启动 | 可启动；运行中人工删除会破坏互斥，禁止此操作 |
| 网络文件系统 | 依赖底层文件锁实现，不建议将存储目录放到不可靠的 NFS/SMB 上 |
| 跨主机 | 不支持，仅提供本机互斥 |

程序运行期间手工删除锁文件属于不受支持的运维操作，可能导致重复启动。需要清理时必须在确认应用已退出后删除。

## 7. 测试报告

测试类：`cn.oyzh.common.test.ProcessInstanceLockTest`  
执行命令：

```bash
mvn -pl base-common -Dtest=ProcessInstanceLockTest test
```

结果：

```text
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

| 用例 | 验证点 | 结果 |
|---|---|---|
| `acquiresLockWritesPidAndDeletesOnClose` | 获取锁、写入 PID、正常关闭删除文件 | 通过 |
| `secondAcquireReportsRunningProcess` | 同一 JVM 第二次获取被拒绝，不删除持有者文件，外部进程仍无法获取原锁 | 通过 |
| `lockCanBeReacquiredAfterClose` | 关闭后可重新获取 | 通过 |
| `stalePidFileIsOverwritten` | 死 PID 残留文件可覆盖 | 通过 |
| `emptyLockFileCanBeAcquired` | 空锁文件可正常获取 | 通过 |
| `retainedLockFileCanBeReused` | 保留锁文件模式可重复使用 | 通过 |
| `deleteFailureOnCloseDoesNotThrowAndReleasesLock` | 锁文件路径被替换为非空目录导致删除失败时，关闭不抛异常且进程内锁登记被释放 | 通过 |
| `overlappingJvmLockIsReportedRunningWithoutPid` | 非本工具创建的同 JVM 重叠锁且 PID 未写入时仍判定已运行 | 通过 |
| `externalLockWithDeadPidIsReportedLocked` | 外部进程持锁但 PID 不可信时保守返回 `LOCKED` | 通过 |
| `killedProcessLockCanBeAcquiredAgain` | 真实子进程强杀后锁被系统释放并可恢复启动 | 通过 |

EasyShell 侧已执行：

```bash
mvn -DskipTests compile
mvn -Dtest=ShellConnectSchemaEvolutionTest test
```

编译成功，表结构演进 6 项测试通过。

## 8. 验收结论

实例锁不依赖 H2 文件，能够覆盖正常启动、重复启动、正常退出、强杀恢复、死 PID 残留、空/损坏锁文件和同 JVM 重入等边界。EasyShell 在数据库初始化前完成互斥检查，满足 SQLite 迁移后的单实例启动要求。
