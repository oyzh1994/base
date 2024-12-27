
package cn.oyzh.event;


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author oyzh
 * @since 2024-12-27
 */
public class EventSubscriber {

    private final Method method;

    private volatile boolean initialized;

    private final WeakReference<Object> listener;

    public EventSubscriber(Method method, Object listener) {
        this.method = method;
        this.listener = new WeakReference<>(listener);
    }

    public void doInvoke(Object event) throws InvocationTargetException, IllegalAccessException {
        if (event != null && !this.isInvalid()) {
            Method method = this.method;
            Object listener = this.listener.get();
            if (!this.initialized) {
                method.setAccessible(true);
                this.initialized = true;
            }
            method.invoke(listener, event);
        }
    }

    public boolean isAccept(Object event) {
        if (event != null && !this.isInvalid()) {
            Method method = this.method;
            Class<?> eventType = event.getClass();
            Class<?> parameterType = method.getParameterTypes()[0];
            if (parameterType == eventType) {
                return true;
            }
            if (parameterType.isAssignableFrom(eventType)) {
                return true;
            }
            return eventType.isAssignableFrom(parameterType);
        }
        return false;
    }

    public boolean isInvalid() {
        return this.listener.get() == null;
    }

    public Object getListener() {
        return this.listener.get();
    }
}
