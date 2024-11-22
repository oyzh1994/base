package cn.oyzh.event;

import cn.oyzh.common.util.ClassUtil;
import lombok.experimental.UtilityClass;

/**
 * @author oyzh
 * @since 2024-11-14
 */
@UtilityClass
public class EventFactory {

    private static Class<? extends EventBus> eventBusClass;

    private static EventConfig syncEventConfig;

    private static EventConfig asyncEventConfig;

    private static EventConfig defaultEventConfig;

    public static void registerEventBus(Class<? extends EventBus> eventBusClass) {
        EventFactory.eventBusClass = eventBusClass;
    }

    public static void syncEventConfig(EventConfig config) {
        EventFactory.syncEventConfig = config;
    }

    public static void asyncEventConfig(EventConfig config) {
        EventFactory.asyncEventConfig = config;
    }

    public static void defaultEventConfig(EventConfig config) {
        EventFactory.defaultEventConfig = config;
    }

    public static EventConfig syncEventConfig() {
        if (syncEventConfig == null) {
            syncEventConfig = EventConfig.SYNC;
        }
        return syncEventConfig;
    }

    public static EventConfig asyncEventConfig() {
        if (asyncEventConfig == null) {
            asyncEventConfig = EventConfig.ASYNC;
        }
        return asyncEventConfig;
    }

    public static EventConfig defaultEventConfig() {
        if (defaultEventConfig == null) {
            defaultEventConfig = EventConfig.DEFAULT;
        }
        return defaultEventConfig;
    }

    public static EventBus newInstance() {
        if (eventBusClass == null) {
            eventBusClass = EventBus.class;
        }
        return ClassUtil.newInstance(eventBusClass);
    }
}
