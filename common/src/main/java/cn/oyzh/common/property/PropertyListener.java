package cn.oyzh.common.property;

/**
 * 属性接口
 * @author oyzh
 * @since 2024-11-01
 * @param <T> 形参
 */
public interface PropertyListener<T> {

    void onChanged(T oldValue, T newValue);
}
