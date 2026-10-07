package cn.oyzh.common.property;

import java.lang.ref.WeakReference;

/**
 * 对象属性
 *
 * @param <T> 形参
 * @author oyzh
 * @since 2024-11-01
 */
public class ObjectProperty<T> {

    /**
     * 属性值的弱引用
     */
    private WeakReference<T> reference;

    /**
     * 属性变更监听器
     */
    private PropertyListener<T> listener;

    /**
     * 设置属性变更监听器
     *
     * @param listener 属性变更监听器
     */
    public void setListener(PropertyListener<T> listener) {
        this.listener = listener;
    }

    /**
     * 构造对象属性
     */
    public ObjectProperty() {
        this(null, null);
    }

    /**
     * 构造对象属性
     *
     * @param listener 属性变更监听器
     */
    public ObjectProperty(PropertyListener<T> listener) {
        this(null, listener);
    }

    /**
     * 构造对象属性
     *
     * @param value    属性值
     * @param listener 属性变更监听器
     */
    public ObjectProperty(T value, PropertyListener<T> listener) {
        this.reference = new WeakReference<>(value);
        this.listener = listener;
    }

    /**
     * 获取属性值
     *
     * @return 属性值，已被回收或未设置时返回null
     */
    public T get() {
        return this.reference == null ? null : this.reference.get();
    }

    /**
     * 设置属性值，若存在监听器则先回调变更事件
     *
     * @param value 属性值
     */
    public void set(T value) {
        if (this.listener != null) {
            this.listener.onChanged(this.get(), value);
        }
        this.reference = new WeakReference<>(value);
    }
}
