
package cn.oyzh.event;


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 事件订阅器
 *
 * @author oyzh
 * @since 2024-12-27
 */
public class EventSubscriber {

    /**
     * 方法
     */
    private final Method method;

    /**
     * 监听器
     */
    private final WeakReference<Object> listener;

    public EventSubscriber(Method method, Object listener) {
        this.method = method;
        this.listener = new WeakReference<>(listener);
    }

    /**
     * 调用方法
     *
     * @param event 事件
     * @throws InvocationTargetException 异常
     * @throws IllegalAccessException    异常
     */
    public void doInvoke(Object event) throws InvocationTargetException, IllegalAccessException {
        if (event != null && !this.isInvalid()) {
            Method method = this.method;
            Object listener = this.listener.get();
            if (!method.canAccess(listener)) {
                method.setAccessible(true);
            }
            method.invoke(listener, event);
        }
    }

    /**
     * 是否接受
     *
     * @param event 事件
     * @return 结果
     */
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

    /**
     * 是否无效
     *
     * @return 结果
     */
    public boolean isInvalid() {
        return this.listener.get() == null;
    }

    /**
     * 获取监听器
     *
     * @return 监听器
     */
    public Object getListener() {
        return this.listener.get();
    }
}
