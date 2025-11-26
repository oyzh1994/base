package cn.oyzh.event;

import cn.oyzh.common.util.ClassUtil;

/**
 * 事件工厂
 *
 * @author oyzh
 * @since 2024-11-14
 */
public class EventFactory {

    /**
     * 事件总线类
     */
    private static Class<? extends EventBus> eventBusClass;

    /**
     * 同步配置
     */
    private static EventConfig syncEventConfig;

    /**
     * 异步配置
     */
    private static EventConfig asyncEventConfig;

    /**
     * 默认配置
     */
    private static EventConfig defaultEventConfig;

    /**
     * 设置事件总线类
     *
     * @param eventBusClass 事件总线类
     */
    public static void registerEventBus(Class<? extends EventBus> eventBusClass) {
        EventFactory.eventBusClass = eventBusClass;
    }

    /**
     * 设置同步配置
     *
     * @param config 配置
     */
    public static void syncEventConfig(EventConfig config) {
        EventFactory.syncEventConfig = config;
    }

    /**
     * 设置异步配置
     *
     * @param config 配置
     */
    public static void asyncEventConfig(EventConfig config) {
        EventFactory.asyncEventConfig = config;
    }

    /**
     * 设置默认配置
     *
     * @param config 配置
     */
    public static void defaultEventConfig(EventConfig config) {
        EventFactory.defaultEventConfig = config;
    }

    /**
     * 获取同步配置
     *
     * @return 结果
     */
    public static EventConfig syncEventConfig() {
        if (syncEventConfig == null) {
            syncEventConfig = EventConfig.SYNC;
        }
        return syncEventConfig;
    }

    /**
     * 获取异步配置
     *
     * @return 结果
     */
    public static EventConfig asyncEventConfig() {
        if (asyncEventConfig == null) {
            asyncEventConfig = EventConfig.ASYNC;
        }
        return asyncEventConfig;
    }

    /**
     * 获取默认配置
     *
     * @return 结果
     */
    public static EventConfig defaultEventConfig() {
        if (defaultEventConfig == null) {
            defaultEventConfig = EventConfig.DEFAULT;
        }
        return defaultEventConfig;
    }

    /**
     * 事件总线实例
     *
     * @return 结果
     */
    public static EventBus newInstance() {
        if (eventBusClass == null) {
            eventBusClass = EventBus.class;
        }
        return ClassUtil.newInstance(eventBusClass);
    }
}
