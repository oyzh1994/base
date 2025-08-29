package cn.oyzh.common.util;


import java.util.Objects;

public class ObjectUtil {

    public static <T>T nullOrElse(Object value, Object object) {
        if (Objects.isNull(value)) {
            return (T) object;
        }
        return (T) value;
    }
}
