package cn.oyzh.common.exception;


/**
 * 属性未找到异常
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class PropertyNotFoundException extends RuntimeException {

    /**
     * 创建属性未找到异常
     *
     * @param propertyName 未找到的属性名
     */
    public PropertyNotFoundException(String propertyName) {
        super("Property not found: " + propertyName);
    }
}
