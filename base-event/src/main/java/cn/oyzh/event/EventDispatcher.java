package cn.oyzh.event;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 事件调度器
 *
 * @author oyzh
 * @since 2024-11-13
 */
public class EventDispatcher {

    public void post(Object event, List<EventSubscriber> subscribers) throws InvocationTargetException, IllegalAccessException {
        if (event != null) {
            if (CollectionUtil.isNotEmpty(subscribers)) {
                for (EventSubscriber subscriber : subscribers) {
                    subscriber.doInvoke(event);
                }
            } else {
                JulLog.warn("subscribers is empty, post event:{} fail!", event.getClass().getName());
            }
        }
    }
}
