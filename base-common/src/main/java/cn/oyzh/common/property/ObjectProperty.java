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

    private WeakReference<T> reference;

    private PropertyListener<T> listener;

    public void setListener(PropertyListener<T> listener) {
        this.listener = listener;
    }

    public ObjectProperty() {
        this(null, null);
    }

    public ObjectProperty(PropertyListener<T> listener) {
        this(null, listener);
    }

    public ObjectProperty(T value, PropertyListener<T> listener) {
        this.reference = new WeakReference<>(value);
        this.listener = listener;
    }

    public T get() {
        return this.reference == null ? null : this.reference.get();
    }

    public void set(T value) {
        if (this.listener != null) {
            this.listener.onChanged(this.get(), value);
        }
        this.reference = new WeakReference<>(value);
    }
}
