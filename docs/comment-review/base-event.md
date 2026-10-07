# base-event

> 模块：base-event　包：cn.oyzh.event
> 说明：轻量级事件总线实现，基于注解 `@EventSubscribe` 反射注册监听方法，支持同步/异步/延迟派发；监听器以弱引用持有，避免内存泄漏。

## Event
> 包：cn.oyzh.event

- 职责：泛型事件载体，封装事件数据 `data` 与附加数据 `extra`。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | data | D | 事件数据（泛型） |
  | extra | Object | 额外附加数据 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `Event()` / `Event(D)` / `Event(D, Object)` | 三种构造 | 委托 `this(data, extra)` 赋值 |
  | `D data()` | 获取数据 | 直接返回字段 |
  | `Event<D> data(D)` | 设置数据 | 赋值并返回 `this`，支持链式 |
  | `Object extra()` | 获取额外数据 | 直接返回字段 |
  | `Event<D> extra(Object)` | 设置额外数据 | 赋值并返回 `this` |

- 调用链：`EventUtil.post(event) → EventBus.post → EventDispatcher.post → EventSubscriber.doInvoke`

## EventBus
> 包：cn.oyzh.event

- 职责：事件总线核心，负责监听器注册/注销与事件派发，统一收口异常处理。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | exceptionHandler | Consumer\<Exception\> | 异常处理器（volatile） |
  | register | EventRegister | 事件注册器（final） |
  | dispatcher | EventDispatcher | 事件调度器（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void register(EventListener)` | 注册监听器 | 委托 `register.register`，异常交 `exceptionHandler` 或 `printStackTrace` |
  | `void unregister(EventListener)` | 注销监听器 | 委托 `register.unregister`，异常同样收口 |
  | `<C extends EventConfig> void post(Object, C, Integer)` | 发送事件 | 依据 `isAsync`/`delayMillis` 组合，选择 `TaskManager.startDelay`/`startSync`/`ThreadUtil.sleep`+直接运行 |
  | `void doEventPost(Object, boolean)` | 真正派发 | `register.getSubscribers(event)` 取订阅者，`dispatcher.post` 调用；verbose 时输出耗时日志 |
  | `void exceptionHandler(Consumer<Exception>)` | 设置异常处理器 | 赋值 |

- 调用链：`EventBus.post → doEventPost → EventRegister.getSubscribers → EventDispatcher.post → EventSubscriber.doInvoke`

## EventConfig
> 包：cn.oyzh.event

- 职责：事件派发配置，控制是否异步与是否输出详细日志。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | async | Boolean | 是否异步 |
  | verbose | Boolean | 是否输出详细日志 |
  | SYNC / ASYNC / DEFAULT | EventConfig | 三个内置配置常量（静态块初始化） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `boolean isAsync()` | 是否异步 | `async != null && async` |
  | `boolean isVerbose()` | 是否详细 | `verbose != null && verbose` |

- 调用链：`EventUtil.postSync → EventFactory.syncEventConfig → EventConfig.SYNC`

## EventDispatcher
> 包：cn.oyzh.event

- 职责：事件调度器，遍历订阅者执行调用。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | — | — | 无 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void post(Object, List<EventSubscriber>)` | 派发事件 | 集合非空则逐个 `subscriber.doInvoke(event)`；为空则 `JulLog.warn` |

- 调用链：`EventBus.doEventPost → EventDispatcher.post → EventSubscriber.doInvoke`

## EventFactory
> 包：cn.oyzh.event

- 职责：事件总线与配置的工厂，提供单例配置与总线实例创建。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | eventBusClass | Class\<? extends EventBus\> | 事件总线类 |
  | syncEventConfig / asyncEventConfig / defaultEventConfig | volatile EventConfig | 同步/异步/默认配置 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void registerEventBus(Class)` | 设置总线类 | 赋值静态字段 |
  | `void syncEventConfig/asyncEventConfig/defaultEventConfig(EventConfig)` | 设置对应配置 | 赋值静态字段 |
  | `EventConfig syncEventConfig()/asyncEventConfig()/defaultEventConfig()` | 获取对应配置 | 双检锁，为空时取 `EventConfig.SYNC/ASYNC/DEFAULT` |
  | `EventBus newInstance()` | 创建总线实例 | 为空时默认 `EventBus.class`，`ClassUtil.newInstance` 反射实例化 |

- 调用链：`EventUtil` 静态初始化 → `EventFactory.newInstance() → ClassUtil.newInstance(EventBus.class)`

## EventFormatter
> 包：cn.oyzh.event

- 职责：事件消息格式化接口。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `String eventFormat()` | 格式化消息 | 抽象方法，由实现类实现 |

- 调用链：`实现类.eventFormat()`（供日志/展示调用）

## EventListener
> 包：cn.oyzh.event

- 职责：监听器标记接口，提供默认注册/注销方法。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `default void register()` | 注册自身 | `EventUtil.register(this)` |
  | `default void unregister()` | 注销自身 | `EventUtil.unregister(this)` |

- 调用链：`EventListener.register → EventUtil.register → EventBus.register`

## EventListenerAlreadyExistsException
> 包：cn.oyzh.event

- 职责：监听器重复注册时抛出的运行时异常。
- 字段：无（继承 RuntimeException）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `EventListenerAlreadyExistsException(Object)` | 构造异常 | 消息拼接 "Event listener ... already exists" |

- 调用链：`EventRegister.register → throw EventListenerAlreadyExistsException`

## EventRegister
> 包：cn.oyzh.event

- 职责：事件注册器，扫描监听器方法上的 `@EventSubscribe` 构建订阅者集合。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | subscribers | List\<EventSubscriber\> | 订阅者列表（CopyOnWriteArrayList） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void register(EventListener)` | 注册监听器 | 校验重复后，`ReflectUtil.getMethods` 遍历方法，取 `@EventSubscribe` 且参数为 1；非法（static/native/abstract）抛 `EventSubscribeInvalidException`；构建 `EventSubscriber` 加入列表，最后 `removeIf(isInvalid)` |
  | `void unregister(EventListener)` | 注销监听器 | `removeIf` 移除失效或监听器相等的订阅者 |
  | `List<EventSubscriber> getSubscribers(Object)` | 获取匹配订阅者 | 遍历列表，`subscriber.isAccept(event)` 过滤 |

- 调用链：`EventBus.register → EventRegister.register → ReflectUtil.getMethods`；`EventBus.doEventPost → EventRegister.getSubscribers`

## EventSubscribe
> 包：cn.oyzh.event

- 职责：方法级注解，标记事件订阅方法（`@Target(METHOD)`、RUNTIME）。
- 字段：无（注解）
- 方法：无

- 调用链：`EventRegister.register → method.getAnnotation(EventSubscribe.class)`

## EventSubscribeInvalidException
> 包：cn.oyzh.event

- 职责：订阅方法签名非法时抛出的运行时异常。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `EventSubscribeInvalidException(Object, Method)` | 构造异常 | 消息包含监听器与无效方法名 |

- 调用链：`EventRegister.register → throw EventSubscribeInvalidException`

## EventSubscriber
> 包：cn.oyzh.event

- 职责：单个订阅方法封装，弱引用持有监听器并负责事件匹配与反射调用。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | method | Method | 订阅方法 |
  | listener | WeakReference\<Object\> | 监听器弱引用 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `EventSubscriber(Method, Object)` | 构造订阅者 | 保存方法，包装弱引用 |
  | `void doInvoke(Object)` | 调用方法 | 非失效时 `method.setAccessible(true)` 后 `method.invoke(listener, event)` |
  | `boolean isAccept(Object)` | 是否接受事件 | 参数类型 `==` 事件类型或 `isAssignableFrom` |
  | `boolean isInvalid()` | 是否失效 | `listener.get() == null` |
  | `Object getListener()` | 获取监听器 | 弱引用解引用 |

- 调用链：`EventDispatcher.post → EventSubscriber.doInvoke`；`EventRegister.getSubscribers → EventSubscriber.isAccept`

## EventUtil
> 包：cn.oyzh.event

- 职责：事件工具门面，静态持有全局 EventBus 并暴露常用发送方法。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | EVENT_BUS | EventBus | 全局事件总线（`EventFactory.newInstance()` 创建，final static） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void exceptionHandler(Consumer<Exception>)` | 设置异常处理器 | 委托 `EVENT_BUS.exceptionHandler` |
  | `void register(EventListener)` / `unregister(EventListener)` | 注册/注销 | 委托 EVENT_BUS |
  | `void post(Event<?>)` | 默认发送 | `post(event, defaultEventConfig(), null)` |
  | `void postSync(Event<?>)` | 同步发送 | 使用 `syncEventConfig()` |
  | `void postAsync(Event<?>)` | 异步发送 | 使用 `asyncEventConfig()` |
  | `void postDelay(Event<?>, int)` | 延迟发送 | 使用 `defaultEventConfig()` + delay |
  | `void post(Event<?>, EventConfig, Integer)` | 通用发送 | 委托 `EVENT_BUS.post` |

- 调用链：`EventUtil.post → EventBus.post → EventBus.doEventPost → EventDispatcher.post`
