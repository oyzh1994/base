package cn.oyzh.event;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.ReflectUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 事件注册器
 *
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
     */
    public void register(Object listener) {
        if (listener == null) {
            return;
        }
        Optional<EventSubscriber> optional = this.subscribers.parallelStream().filter(f -> f.getListener() == listener).findAny();
        if (optional.isPresent()) {
            throw new EventListenerAlreadyExistsException(listener);
        }
        Class<?> clazz = listener.getClass();
        // 寻找方法
        Method[] methods = ReflectUtil.getMethods(clazz, true, true);
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            EventSubscribe subscribe = method.getAnnotation(EventSubscribe.class);
            if (subscribe == null) {
               continue;
            }
            if (Modifier.isStatic(modifiers)
                    || Modifier.isNative(modifiers)
                    || Modifier.isAbstract(modifiers)
                    || method.getParameterCount() != 1) {
                throw new EventSubscribeInvalidException(listener, method);
            }
            EventSubscriber subscriber = new EventSubscriber(method, listener);
            this.subscribers.add(subscriber);
        }
        this.subscribers.removeIf(EventSubscriber::isInvalid);
        JulLog.info("EventSubscribe register listener:{}", listener.getClass());
    }

    /**
     * 取消注册
     *
     * @param listener 监听器
     */
    public void unregister(Object listener) {
        if (listener == null) {
            return;
        }
        // synchronized (this.subscribers) {
        this.subscribers.removeIf(s -> s.isInvalid() || Objects.equals(listener, s.getListener()));
        // }
        JulLog.info("EventSubscribe unregister listener:{}", listener.getClass());
    }

    /**
     * 获取订阅者
     *
     * @param event 事件
     * @return 订阅者列表
     */
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
