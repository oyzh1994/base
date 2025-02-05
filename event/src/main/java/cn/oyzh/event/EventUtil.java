package cn.oyzh.event;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

/**
 * 事件工具类
 *
 * @author oyzh
 * @since 2023/4/10
 */
@UtilityClass
public class EventUtil {

    /**
     * 事件总线对象
     */
    private static final EventBus EVENT_BUS = EventFactory.newInstance();

    /**
     * 异常处理器
     *
     * @param exceptionHandler 异常处理器
     */
    public static void exceptionHandler(Consumer<Exception> exceptionHandler) {
        EVENT_BUS.exceptionHandler(exceptionHandler);
    }

    /**
     * 注册
     *
     * @param listener 监听器
     * @return 结果
     */
    public static boolean register(EventListener listener) {
        return EVENT_BUS.register(listener);
    }

    /**
     * 取消注册
     *
     * @param listener 监听器
     * @return 结果
     */
    public static boolean unregister(EventListener listener) {
        return EVENT_BUS.unregister(listener);
    }

    /**
     * 发送事件
     *
     * @param event 事件
     */
    public static void post(Event<?> event) {
        post(event, EventFactory.defaultEventConfig(), null);
    }

    /**
     * 发送同步步事件
     *
     * @param event 事件
     */
    public static void postSync(Event<?> event) {
        post(event, EventFactory.syncEventConfig(), null);
    }

    /**
     * 发送异步事件
     *
     * @param event 事件
     */
    public static void postAsync(Event<?> event) {
        post(event, EventFactory.asyncEventConfig(), null);
    }

    /**
     * 发送事件
     *
     * @param event 事件
     * @param delay 延迟时间
     */
    public static void postDelay(Event<?> event, int delay) {
        post(event, EventFactory.defaultEventConfig(), delay);
    }

    /**
     * 发送事件
     *
     * @param event       事件
     * @param config      配置
     * @param delayMillis 延迟毫秒数
     */
    public static void post(Event<?> event, EventConfig config, Integer delayMillis) {
        EVENT_BUS.post(event, config, delayMillis);
    }
}


