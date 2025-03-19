package cn.oyzh.common.dto;

/**
 * 友好信息对象
 *
 * @author oyzh
 * @since 2020/3/26
 */
//@Data
//@Accessors(fluent = true, chain = true)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public T getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(T originalValue) {
        this.originalValue = originalValue;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public Object getFriendlyValue() {
        return friendlyValue;
    }

    public void setFriendlyValue(Object friendlyValue) {
        this.friendlyValue = friendlyValue;
    }

    public Object friendlyValue() {
        return this.friendlyValue;
    }

    public Object value() {
        return this.value;
    }

    public void name(String name) {
        this.name = name;
    }

    public void friendlyValue(Object friendlyValue) {
        this.friendlyValue = friendlyValue;
    }

    public void value(Object value) {
        this.value = value;
    }

    public void friendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
