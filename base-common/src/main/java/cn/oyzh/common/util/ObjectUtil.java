package cn.oyzh.common.util;


import java.util.Objects;

/**
 * 对象工具类
 *
 * @author oyzh
 * @since 2025-08-29
 */
public class ObjectUtil {

    /**
     * 若第一个对象为null则返回第二个对象，否则返回第一个对象
     *
     * @param value  优先返回的对象
     * @param object 备选对象
     * @param <T>    返回值类型
     * @return value不为null时返回value，否则返回object
     */
    public static <T>T nullOrElse(Object value, Object object) {
        if (Objects.isNull(value)) {
            return (T) object;
        }
        return (T) value;
    }
}
