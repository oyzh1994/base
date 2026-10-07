package cn.oyzh.common.property;

/**
 * 属性变更监听器
 *
 * @param <T> 属性值类型
 * @author oyzh
 * @since 2024-11-01
 */
public interface PropertyListener<T> {

    /**
     * 属性值变更回调
     *
     * @param oldValue 变更前的旧值
     * @param newValue 变更后的新值
     */
    void onChanged(T oldValue, T newValue);
}
