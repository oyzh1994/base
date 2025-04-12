package cn.oyzh.event;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.ReflectUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author oyzh
 * @since 2024-11-14
 */
public class EventRegister {

    /**
     * 订阅者列表
     */
    private final List<EventSubscriber> subscribers = new CopyOnWriteArrayList<>();

    /**
     * 注册
     *
     * @param listener 监听器
     * @return 结果
     */
    public boolean register(Object listener) {
        if (listener != null) {
            Optional<EventSubscriber> optional = subscribers.parallelStream().filter(f -> f.getListener() == listener).findAny();
            if (optional.isPresent()) {
                throw new EventListenerAlreadyExistsException(listener);
            }
            Class<?> clazz = listener.getClass();
            Method[] methods = ReflectUtil.getMethods(clazz, true, true);
            for (Method method : methods) {
                EventSubscribe subscribe = method.getAnnotation(EventSubscribe.class);
                if (subscribe != null) {
                    if (method.getParameterCount() == 1) {
                        EventSubscriber subscriber = new EventSubscriber(method, listener);
                        synchronized (this.subscribers) {
                            this.subscribers.add(subscriber);
                        }
                    } else {
                        JulLog.error("EventSubscribe is found, but parameterCount is not 1,class:{} method:{}", clazz, method.getName());
                    }
                }
            }
            synchronized (this.subscribers) {
                this.subscribers.removeIf(EventSubscriber::isInvalid);
            }
        }
        return false;
    }

    public boolean unregister(Object listener) {
        synchronized (this.subscribers) {
            return this.subscribers.removeIf(s -> s.isInvalid() || Objects.equals(listener, s.getListener()));
        }
    }

    public List<EventSubscriber> getSubscribers(Object event) {
        if (event == null) {
            return Collections.emptyList();
        }
        List<EventSubscriber> result = new ArrayList<>();
        synchronized (this.subscribers) {
            for (EventSubscriber subscriber : this.subscribers) {
                if (subscriber.isAccept(event)) {
                    result.add(subscriber);
                }
            }
        }
        return result;
    }

}
