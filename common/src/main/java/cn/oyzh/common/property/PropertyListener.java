package cn.oyzh.common.property;

/**
 * @author oyzh
 * @since 2024-11-01
 */
public interface PropertyListener<T> {

    void onChanged(T oldValue, T newValue);
}
