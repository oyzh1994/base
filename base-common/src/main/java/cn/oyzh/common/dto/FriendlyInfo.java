package cn.oyzh.common.dto;

/**
 * 友好信息对象
 *
 * @param <T> 原始值类型
 * @author oyzh
 * @since 2020/3/26
 */
public class FriendlyInfo<T> {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private Object value;

    /**
     * 原始值
     */
    private T originalValue;

    /**
     * 友好名称
     */
    private String friendlyName;

    /**
     * 友好值
     */
    private Object friendlyValue;

    /**
     * 获取名称
     *
     * @param friendly 友好化的
     * @return 名称
     */
    public String getName(boolean friendly) {
        return friendly ? this.friendlyName : this.name;
    }

    /**
     * 获取值
     *
     * @param friendly 友好化的
     * @return 值
     */
    public Object getValue(boolean friendly) {
        return friendly ? this.friendlyValue : this.value;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取原始值
     *
     * @return 原始值
     */
    public T getOriginalValue() {
        return originalValue;
    }

    /**
     * 设置原始值
     *
     * @param originalValue 原始值
     */
    public void setOriginalValue(T originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * 获取友好名称
     *
     * @return 友好名称
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * 设置友好名称
     *
     * @param friendlyName 友好名称
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * 获取友好值
     *
     * @return 友好值
     */
    public Object getFriendlyValue() {
        return friendlyValue;
    }

    /**
     * 设置友好值
     *
     * @param friendlyValue 友好值
     */
    public void setFriendlyValue(Object friendlyValue) {
        this.friendlyValue = friendlyValue;
    }

    /**
     * 获取友好值
     *
     * @return 友好值
     */
    public Object friendlyValue() {
        return this.friendlyValue;
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public Object value() {
        return this.value;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void name(String name) {
        this.name = name;
    }

    /**
     * 设置友好值
     *
     * @param friendlyValue 友好值
     */
    public void friendlyValue(Object friendlyValue) {
        this.friendlyValue = friendlyValue;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void value(Object value) {
        this.value = value;
    }

    /**
     * 设置友好名称
     *
     * @param friendlyName 友好名称
     */
    public void friendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
