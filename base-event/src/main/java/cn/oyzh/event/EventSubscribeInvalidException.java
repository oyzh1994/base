package cn.oyzh.event;

import java.lang.reflect.Method;

/**
 * 事件定义者无效异常
 *
 * @author oyzh
 * @since 2024-11-18
 */
public class EventSubscribeInvalidException extends RuntimeException {

    public EventSubscribeInvalidException(Object listener, Method method) {
        super("Event subscribe " + listener + "@" + method.getName() + " is invalid");
    }
}
