package cn.oyzh.event;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-13
 */
public class EventDispatcher {

    public void post(Object event, List<EventSubscriber> subscribers) throws InvocationTargetException, IllegalAccessException {
        if (event != null && subscribers != null && !subscribers.isEmpty()) {
            for (EventSubscriber subscriber : subscribers) {
                subscriber.doInvoke(event);
            }
        }
    }
}
