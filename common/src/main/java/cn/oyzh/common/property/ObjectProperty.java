package cn.oyzh.common.property;

import lombok.Setter;

import java.lang.ref.WeakReference;

/**
 * @author oyzh
 * @since 2024-11-01
 */
public class ObjectProperty<T> {

    private WeakReference<T> reference;

    @Setter
    private PropertyListener<T> listener;

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
