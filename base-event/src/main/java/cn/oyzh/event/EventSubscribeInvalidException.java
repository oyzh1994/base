package cn.oyzh.event;

import java.lang.reflect.Method;

/**
 * 事件订阅无效异常
 *
 * @author oyzh
 * @since 2024-11-18
 */
public class EventSubscribeInvalidException extends RuntimeException {

    /**
     * 构造异常
     *
     * @param listener 监听器
     * @param method   无效的订阅方法
     */
    public EventSubscribeInvalidException(Object listener, Method method) {
        super("Event subscribe " + listener + "@" + method.getName() + " is invalid");
    }
}
