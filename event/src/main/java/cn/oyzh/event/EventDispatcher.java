package cn.oyzh.event;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-13
 */
public class EventDispatcher {

    public void post(Object event, List<EventSubscriber> subscribers) throws InvocationTargetException, IllegalAccessException {
        if (event != null && CollectionUtil.isNotEmpty(subscribers)) {
            for (EventSubscriber subscriber : subscribers) {
                subscriber.doInvoke(event);
            }
        } else {
            JulLog.warn("event is null or subscribers is empty, post event:{} fail!", event == null ? "null" : event.getClass().getName());
        }
    }
}
