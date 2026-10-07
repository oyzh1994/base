# cn.oyzh.common.thread

> 说明：`ProcessExecBuilder` 整文件被注释掉，属死代码，未纳入本审查。

## ThreadUtil

- 职责：线程工具类，提供平台/虚拟线程的创建、批量提交、结果收集、休眠与中断控制。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| executor | volatile static ExecutorService | 业务线程池（缓存线程池） |
| virtualExecutor | volatile static ExecutorService | 虚拟线程执行器（每任务一虚拟线程） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ExecutorService executor()` | 获取业务线程池 | 双重检查加锁，`Executors.newCachedThreadPool()` |
| `ExecutorService virtualExecutor()` | 获取虚拟线程执行器 | 双重检查加锁，`Executors.newVirtualThreadPerTaskExecutor()` |
| `Thread startVirtual(Runnable)` | 启动虚拟线程 | `Thread.ofVirtual().start(task)` |
| `void submitVirtual(List<Runnable>)` | 虚拟线程同步执行任务列表 | `virtualExecutor().submit` 后逐个 `future.get()` |
| `List<V> invokeVirtual(List<Callable<V>>)` | 虚拟线程执行并收集结果 | `virtualExecutor().invokeAll(tasks)` 后 `future.get()` |
| `V invokeVirtual(Callable<V>)` | 单任务版 | 委托列表版并取首个 |
| `Thread start(Runnable)` | 启动平台线程 | 构造 `ThreadExt` 并 `start()` |
| `Thread newThread(Runnable)` | 创建平台线程 | `new ThreadExt(task)` |
| `Thread newThreadVirtual(Runnable)` | 创建虚拟线程 | `Thread.ofVirtual().unstarted(task)` |
| `Thread start(Runnable, long)` | 延迟启动线程 | 通过 `ExecutorUtil.start(thread::start, delay)` |
| `void submit(List<Runnable>)` | 平台线程同步执行任务列表 | `executor().submit` 后 `future.get()` |
| `void submit(Runnable)` | 单任务版 | 委托列表版 |
| `void submitAsync(List<Runnable>)` | 平台线程异步执行 | 逐个 `executor().submit`，不等待 |
| `void submitAsync(Runnable)` | 单任务版 | 委托列表版 |
| `void submitSmart(List<Runnable>)` | 智能分发执行 | 按 CPU 核数：≥8 用平台线程同步、≥4 用虚拟线程、否则当前线程顺序执行 |
| `List<V> invoke(List<Callable<V>>)` | 平台线程执行并收集结果 | `executor().invokeAll` 后收集非 null 结果 |
| `V invoke(Callable<V>)` | 单任务版 | 委托列表版并取首个 |
| `void sleep(long)` | 休眠 | `Thread.sleep`，吞掉中断异常 |
| `boolean isInterrupted()` | 当前线程是否中断 | `Thread.currentThread().isInterrupted()` |
| `boolean isInterrupted(Thread)` | 指定线程是否中断 | 线程为 null 视为已中断 |
| `boolean isAlive(Thread)` | 线程是否活跃 | `thread != null && thread.isAlive()` |
| `void interrupt(Thread)` | 中断线程 | 未中断时调用 `interrupt()` |
| `void join(Thread)` | 等待线程结束 | 存活时 `thread.join()` |
| `void await(CountDownLatch)` | 等待闭锁 | `latch.await()`，忽略中断 |

- 调用链：`ThreadUtil.submitSmart → RuntimeUtil.processorCount → submitVirtual/submit/任务顺序执行`
- 调用链：`ThreadUtil.start(Runnable,long) → ExecutorUtil.start(thread::start, delay)`

## ExecutorUtil

- 职责：定时任务执行工具类，封装 `ScheduledExecutorService` 的提交、延迟、定时与取消。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| executor | volatile static ScheduledExecutorService | 定时执行器（核心池大小 = CPU 核数 * 2） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ScheduledExecutorService executor()` | 获取执行器 | 双重检查加锁创建 |
| `Future<?> submit(Runnable)` | 提交任务 | `executor().submit(task)` |
| `ScheduledFuture<?> start(Runnable, long)` | 延迟执行一次 | `schedule(task, delay, MILLISECONDS)` |
| `ScheduledFuture<?> start(Runnable, long, long)` | 定时周期执行 | `scheduleAtFixedRate(task, delay, period, MILLISECONDS)` |
| `ScheduledFuture<V> invoke(Callable<V>, long)` | 延迟执行并返回结果 | `schedule(task, delay, MILLISECONDS)` |
| `void cancel(Future<?>)` | 取消任务 | 非空时 `task.cancel(false)` |
| 静态块 | 注册关闭钩子 | `RuntimeUtil.addShutdownHook` 中 `executor.shutdown()` |

- 调用链：`ExecutorUtil.start(Runnable,long,long) → ScheduledExecutorService.scheduleAtFixedRate`

## DownLatch

- 职责：继承 `CountDownLatch` 的倒计数闭锁，等待时忽略中断异常。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | 继承父类 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `DownLatch(int)` | 构造 | `super(count)` |
| `void await()` | 等待归零 | `super.await()`，吞掉 `InterruptedException` |
| `boolean await(int)` | 限时等待 | `super.await(timeout, MILLISECONDS)` |
| `DownLatch of()` | 创建计数 1 闭锁 | 委托 `of(1)` |
| `DownLatch of(int)` | 创建指定计数闭锁 | `new DownLatch(count)` |

- 调用链：`DownLatch.of → DownLatch(int) → CountDownLatch(int)`

## IRunnable

- 职责：可抛出异常的 Runnable 接口（`run()` 声明 `throws Exception`）。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void run()` | 执行任务 | 声明 `throws Exception`，由实现类定义 |

- 调用链：`Task.onStart → IRunnable.run`

## ThreadExt

- 职责：线程扩展类，被中断后即使中断标志被清除仍能持续判定为已中断。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| finish | Boolean | 是否已结束/中断标记 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ThreadExt(Runnable)` | 构造 | `super(task)` |
| `void interrupt()` | 中断 | 先置 `finish=true`，再 `super.interrupt()` |
| `boolean isInterrupted()` | 是否中断 | `finish` 为真或 `super.isInterrupted()` |

- 调用链：`ThreadUtil.start → ThreadExt(构造) → Thread.start`

## ThreadLocalUtil

- 职责：线程本地变量工具类，基于单个 `ThreadLocal` 存放一张 map，实现按线程隔离的键值存取。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| LOCAL | ThreadLocal<Object> | 线程本地存储容器（内部为 ThreadLocalMap） |
| ThreadLocalMap extends HashMap<String,Object> | 内部类 | 每线程一张的键值表 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void setVal(String, Object)` | 设置线程本地变量 | 首次调用时创建 ThreadLocalMap 存入 LOCAL，再 `put` |
| `void removeVal(String)` | 移除变量 | 同上获取容器后 `remove` |
| `T getVal(String)` | 获取变量 | 容器存在时 `map.get(key)` |
| `boolean hasVal(String)` | 是否存在 | 容器存在时 `containsKey` |

- 调用链：`ThreadLocalUtil.setVal → ThreadLocal.get/set → ThreadLocalMap.put`

## Task

- 职责：任务封装，支持开始、成功、结束、异常四个阶段回调。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| start | IRunnable | 开始操作 |
| finish | IRunnable | 结束操作 |
| success | IRunnable | 成功操作 |
| error | Consumer<Exception> | 错误操作 |
| exception | Exception | 执行过程中捕获的异常 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void onStart()` | 执行开始回调 | start 非空则 `start.run()` |
| `void onSuccess()` | 执行成功回调 | success 非空则 `success.run()` |
| `void onFinish()` | 执行结束回调 | finish 非空则 `finish.run()` |
| `void onError(Exception)` | 执行异常回调 | error 非空则 `error.accept(ex)` |
| `void run()` | 任务主流程（final） | 依次 `onStart→onSuccess`；异常时记录并 `onError`；finally 中 `onFinish` |
| `boolean hasException()` | 是否存在异常 | `exception != null` |
| `void throwRuntimeException()` | 抛出运行时异常 | 有异常时 `throw new RuntimeException(exception)` |
| 各字段 getter/setter | 访问器 | 读写 start/finish/success/error/exception |

- 调用链：`Task.run → onStart → onSuccess → onFinish`（异常分支：`onError`）

## TaskBuilder

- 职责：`Task` 构建器，链式设置各阶段回调。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| start | IRunnable | 开始业务 |
| finish | IRunnable | 结束业务 |
| success | IRunnable | 成功业务 |
| error | Consumer<Exception> | 异常处理 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `TaskBuilder from(Task)` | 从已有任务复制回调 | 复制 task 的 start/error/finish/success |
| `TaskBuilder onStart(IRunnable)` | 设置开始业务 | 赋值并返回 this |
| `TaskBuilder onSuccess(IRunnable)` | 设置成功业务 | 赋值并返回 this |
| `TaskBuilder onFinish(IRunnable)` | 设置结束业务 | 赋值并返回 this |
| `TaskBuilder onError(Consumer<Exception>)` | 设置异常业务 | 赋值并返回 this |
| `Task build()` | 构建任务 | 新建 Task 并注入各回调 |
| `TaskBuilder newBuilder()` | 创建构建器 | `new TaskBuilder()` |

- 调用链：`TaskBuilder.newBuilder → onStart/onSuccess/onFinish/onError → build → Task`

## TaskManager

- 职责：任务管理器，统一通过 ExecutorUtil 提交同步/异步/延迟/定时/超时任务。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | （历史键值缓存字段已注释） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `Future<?> startSync(Runnable)` | 同步提交 | `ExecutorUtil.submit(task)` |
| `Future<?> startAsync(Runnable)` | 异步执行 | `CompletableFuture.runAsync(task, ExecutorUtil.executor())` |
| `Future<?> startDelay(Runnable, int)` | 延迟执行 | `ExecutorUtil.start(task, delay)` |
| `Future<?> startInterval(Runnable, int)` | 定时执行 | `ExecutorUtil.start(task, 0, interval)` |
| `Future<?> startInterval(Runnable, int, int)` | 带延迟定时执行 | `ExecutorUtil.start(task, delay, interval)` |
| `Future<?> startTimeout(Runnable, int)` | 超时任务 | `CompletableFuture.runAsync` 后 `future.get(timeout)`，超时则 `cancel(true)` |
| `void cancel(Future<?>)` | 取消任务 | 未完成时 `cancel(true)`，吞掉异常 |

- 调用链：`TaskManager.startAsync → CompletableFuture.runAsync → ExecutorUtil.executor`
- 调用链：`TaskManager.startTimeout → CompletableFuture.get(timeout) → Future.cancel`

## ProcessExecResult

- 职责：进程执行结果承载对象。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| input | String | 标准输出内容 |
| error | String | 错误输出内容 |
| exitCode | Integer | 进程退出码 |
| timedOut | boolean | 是否已超时 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String getInput()` / `void setInput(String)` | 标准输出读写 | 标准访问器 |
| `String getError()` / `void setError(String)` | 错误输出读写 | 标准访问器 |
| `Integer getExitCode()` / `void setExitCode(Integer)` | 退出码读写 | 标准访问器 |
| `boolean isTimedOut()` / `void setTimedOut(boolean)` | 超时读写 | 标准访问器 |
| `boolean isSuccess()` | 是否成功 | `exitCode == 0` |
| `String toString()` | 字符串描述 | 拼接 output/error/exitCode/timedOut |

- 调用链：`ProcessExecResult.isSuccess → exitCode 判定`

# cn.oyzh.common.function

## ExceptionConsumer

- 职责：可抛出异常的消费型函数式接口（`accept(T) throws Exception`）。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void accept(T)` | 接受参数 | 声明 `throws Exception` |

- 调用链：`ExceptionConsumer.accept`

## ExceptionBiConsumer

- 职责：可抛出异常的双参消费型函数式接口（`accept(T,U) throws Exception`）。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void accept(T, U)` | 接受两个参数 | 声明 `throws Exception` |

- 调用链：`ExceptionBiConsumer.accept`

## WeakFunction

- 职责：弱引用功能基类，持有目标对象的弱引用并提供存活判定。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| reference | WeakReference<Object> | 对象的弱引用 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `WeakFunction(Object)` | 构造 | `new WeakReference<>(obj)` |
| `boolean hasReference()` | 目标是否存活 | `reference.get() != null` |

- 调用链：`WeakConsumer.accept → WeakFunction.hasReference`

## WeakConsumer

- 职责：弱引用 `Consumer`，仅当被弱引用对象存活时才转发调用。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| consumer | Consumer<T> | 被代理的 Consumer |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `WeakConsumer(Object, Consumer<T>)` | 构造 | `super(obj)` 并保存 consumer |
| `void accept(T)` | 接受参数 | `hasReference()` 为真才执行 `consumer.accept(t)` |

- 调用链：`WeakConsumer.accept → WeakFunction.hasReference → Consumer.accept`

## WeakBiConsumer

- 职责：弱引用 `BiConsumer`，仅当对象存活时才转发调用。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| consumer | BiConsumer<T,U> | 被代理的 BiConsumer |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `WeakBiConsumer(Object, BiConsumer<T,U>)` | 构造 | `super(obj)` 并保存 consumer |
| `void accept(T, U)` | 接受两参数 | `hasReference()` 为真才执行 `consumer.accept(t,u)` |

- 调用链：`WeakBiConsumer.accept → WeakFunction.hasReference → BiConsumer.accept`

## WeakRunnable

- 职责：弱引用 `Runnable`，仅当对象存活时才执行。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| action | Runnable | 被代理的 Runnable |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `WeakRunnable(Object, Runnable)` | 构造 | `super(obj)` 并保存 action |
| `void run()` | 执行 | `hasReference()` 为真才执行 `action.run()` |

- 调用链：`WeakRunnable.run → WeakFunction.hasReference → Runnable.run`
