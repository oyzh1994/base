package cn.oyzh.common.property;

import lombok.Setter;

/**
 * @author oyzh
 * @since 2024-11-01
 */
public class ObjectProperty<T> {

    private T value;

    @Setter
    private PropertyListener<T> listener;

    public ObjectProperty( ) {
    }
    public ObjectProperty(T value, PropertyListener<T> listener) {
        this.value = value;
        this.listener = listener;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        if (this.listener != null) {
            this.listener.onChanged(this.value, value);
        }
        this.value = value;
    }
}
