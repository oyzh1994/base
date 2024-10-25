package cn.oyzh.common.exception;


/**
 * @author oyzh
 * @since 2024-09-26
 */
public class PropertyNotFoundException extends RuntimeException {

    public PropertyNotFoundException(String propertyName) {
        super("Property not found: " + propertyName);
    }
}
