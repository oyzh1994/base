# cn.oyzh.common.object

## DestroyUtil

- 职责：对象销毁工具类，对实现 `Destroyable` 的对象统一执行销毁。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void destroy(Object)` | 销毁单个对象 | 判断 `obj instanceof Destroyable`，是则调用 `destroyable.destroy()` |
| `void destroy(Collection<?>)` | 销毁集合中的所有对象 | 集合非空时遍历，逐个调用 `destroy(Object)` |

- 调用链：`DestroyUtil.destroy(Collection) → DestroyUtil.destroy(Object) → Destroyable.destroy()`

## Destroyable

- 职责：可销毁对象接口，约定销毁行为。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void destroy()` | 执行销毁 | 由实现类定义销毁逻辑 |

- 调用链：`DestroyUtil.destroy → Destroyable.destroy`

## ObjectComparator

- 职责：对象比较器接口，用于对单个对象做判定/过滤。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `boolean compare(T)` | 比较对象 | 由实现类返回比较结果 |

- 调用链：`ObjectComparator.compare`（由调用方在遍历/过滤中回调）

## ObjectCopier

- 职责：对象复制器接口，将源对象内容复制到当前对象。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void copy(T)` | 复制对象 | 由实现类定义复制逻辑 |

- 调用链：`ObjectCopier.copy`

## ObjectWatcher

- 职责：对象观察者，通过弱引用持有被观察对象并判定其是否被 GC 回收。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| name | String | 观察者名称，默认取被观察对象简单类名 |
| reference | WeakReference<Object> | 被观察对象的弱引用 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ObjectWatcher(Object, String)` | 构造观察者 | 建立弱引用；name 为空时取 `node.getClass().getSimpleName()` |
| `Object getObject()` | 获取被观察对象 | 返回 `reference.get()`，已回收时返回 null |
| `boolean doClear()` | 执行清除 | 若 `isEmpty()` 为真则打印日志、`reference.clear()` 并返回 true |
| `boolean isEmpty()` | 是否已被回收 | 返回 `reference.get() == null` |

- 调用链：`ObjectWatcherManager.doWatch → ObjectWatcher.doClear → ObjectWatcher.isEmpty`

## ObjectWatcherManager

- 职责：对象观察者管理器，维护观察者列表、用虚拟线程周期性检测对象是否被回收。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| WATCHER_DISABLE | String | 禁用观察者的系统属性名，值常量 "OBJECT_WATCHER_DISABLE" |
| WATCHERS | List<ObjectWatcher> | 已注册的观察者列表（静态） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void init()` | 初始化任务 | 禁用时打告警；否则打印启用日志并通过 `ThreadUtil.startVirtual` 启动 `doWatch` |
| `void doWatch()` | 执行观察循环 | 死循环中加锁遍历 WATCHERS，收集 `doClear()` 为真的观察者并移除，非空时触发 `System.gc()`，每轮 `ThreadUtil.sleep(3000)` |
| `void push(ObjectWatcher)` | 推送观察者 | 非空则加入 WATCHERS |
| `ObjectWatcher watch(Object)` | 观察对象 | 委托 `watch(object, null)` |
| `ObjectWatcher watch(Object, String)` | 观察对象 | 启用且对象非空时，先去重查找，再加入并返回；否则返回 null |
| `void disable()` | 禁用 | 设置系统属性为 "1" 并调用 `init()` |
| `void enable()` | 启用 | 清除系统属性并调用 `init()` |
| `boolean isDisabled()` | 是否禁用 | 系统属性等于 "1" |
| `boolean isEnabled()` | 是否启用 | `!isDisabled()` |

- 调用链：`ObjectWatcherManager.watch → push → WATCHERS.add`
- 调用链：`ObjectWatcherManager.doWatch → ObjectWatcher.doClear → WATCHERS.removeAll`

# cn.oyzh.common.bean

## BeanUtil

- 职责：Bean 反射工具类，基于 getter/setter 读写对象属性，支持递归查找父类。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `T getValue(Object, String)` | 获取属性值 | 委托 `getValue(bean, name, true)` |
| `T getValue(Object, String, boolean)` | 获取属性值 | 校验 bean/name 非空（否则抛 `InvalidParamException`）；找不到 getter 抛 `PropertyNotFoundException`；`method.invoke(bean)` 后强转返回 |
| `Method getGetterMethod(Class<?>, String, boolean)` | 查找 getter 方法 | 先找 `getXxx`，再找 `isXxx`；未找到且 callSuper 时递归查父类（到 Object 止） |
| `void setValue(Object, String, Object)` | 设置属性值 | 委托 `setValue(bean, name, value, true)` |
| `void setValue(Object, String, Object, boolean)` | 设置属性值 | 校验非空；查找 setter 失败抛 `PropertyNotFoundException`；`method.invoke(bean, value)` |
| `Method getSetterMethod(Class<?>, String, Class<?>, boolean)` | 查找 setter 方法 | 遍历 `clazz.getMethods()` 匹配 `setXxx` 单参方法，按参数类型/包装类型兼容性选择；未找到且 callSuper 时递归父类 |

- 调用链：`BeanUtil.getValue → getGetterMethod → Method.invoke`
- 调用链：`BeanUtil.setValue → getSetterMethod → Method.invoke`

# cn.oyzh.common.property

## ObjectProperty

- 职责：对象属性容器，弱引用持有属性值，值变更时回调监听器。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| reference | WeakReference<T> | 属性值的弱引用 |
| listener | PropertyListener<T> | 属性变更监听器 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `ObjectProperty()` | 构造 | 委托 `ObjectProperty(null, null)` |
| `ObjectProperty(PropertyListener<T>)` | 构造 | 委托 `ObjectProperty(null, listener)` |
| `ObjectProperty(T, PropertyListener<T>)` | 构造 | 建立弱引用并设置监听器 |
| `T get()` | 获取属性值 | `reference == null` 返回 null，否则 `reference.get()` |
| `void setListener(PropertyListener<T>)` | 设置监听器 | 直接赋值 |
| `void set(T)` | 设置属性值 | 若监听器非空先回调 `onChanged(oldValue, value)`，再重建弱引用 |

- 调用链：`ObjectProperty.set → PropertyListener.onChanged`

## PropertyListener

- 职责：属性变更监听器接口。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void onChanged(T, T)` | 属性值变更回调 | 参数为旧值、新值 |

- 调用链：`ObjectProperty.set → PropertyListener.onChanged`

# cn.oyzh.common.cache

> 说明：`CacheHelper` 整文件被注释掉，属死代码，未纳入本审查。

## Cache

- 职责：缓存通用接口。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `V get(K)` | 获取缓存值 | 由实现类定义 |
| `void put(K, V)` | 写入缓存 | 由实现类定义 |
| `void remove(K)` | 移除缓存 | 由实现类定义 |
| `void clear()` | 清空缓存 | 由实现类定义 |
| `boolean containsKey(K)` | 是否包含键 | 由实现类定义 |

- 调用链：`CacheUtil.newWeakCache → WeakCache implements Cache`

## CacheUtil

- 职责：缓存工厂工具类，创建弱引用缓存与定时缓存。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `CacheUtil()` | 私有构造 | 禁止实例化 |
| `WeakCache<K,V> newWeakCache()` | 创建弱引用缓存 | `new WeakCache<>()` |
| `TimedCache<K,V> newTimedCache(long)` | 创建定时缓存 | `new TimedCache<>(timeout)` |

- 调用链：`CacheUtil.newTimedCache → TimedCache(构造)`

## TimedCache

- 职责：基于 `ConcurrentHashMap` 的定时缓存，缓存项超过存活时间后失效。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| timeout | long | 存活时间（毫秒），≤0 表示不过期 |
| cache | Map<K, TimedValue<V>> | 缓存数据（静态内部类 TimedValue 含 value 与 putTime） |
| TimedValue.value | V | 缓存值（静态内部类字段） |
| TimedValue.putTime | Long | 写入时间（静态内部类字段） |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `TimedCache(long)` | 构造 | 保存 timeout，初始化 ConcurrentHashMap |
| `V get(K)` | 获取缓存值 | 命中后 `checkTimeout` 判断，超时则移除并返回 null |
| `void put(K, V)` | 写入缓存 | 构造 TimedValue；timeout>0 时记录 putTime；写入 map |
| `void remove(K)` / `void clear()` | 移除/清空 | 直接操作 map |
| `boolean containsKey(K)` | 是否包含有效键 | 值为空返回 false，否则返回 `!checkTimeout(value)` |
| `boolean checkTimeout(TimedValue<V>)` | 检查是否超时 | timeout>0 且 `当前时间 - putTime > timeout` 返回 true |

- 调用链：`TimedCache.get → TimedCache.checkTimeout → Map.remove`

## WeakCache

- 职责：基于 `ConcurrentHashMap` + 弱引用的缓存，值被回收后自动失效。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| cache | Map<K, WeakReference<V>> | 缓存数据 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `WeakCache()` | 构造 | 初始化 ConcurrentHashMap |
| `V get(K)` | 获取缓存值 | 引用为空返回 null；`ref.get()==null` 时移除并返回 null |
| `void put(K, V)` | 写入缓存 | 存入 `new WeakReference<>(value)` |
| `void remove(K)` / `void clear()` | 移除/清空 | 直接操作 map |
| `boolean containsKey(K)` | 是否包含有效键 | 引用非空且 `ref.get() != null` |

- 调用链：`CacheUtil.newWeakCache → WeakCache(构造)`
