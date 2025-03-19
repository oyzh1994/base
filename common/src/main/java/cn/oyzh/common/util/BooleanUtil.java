package cn.oyzh.common.util;

/**
 * @author oyzh
 * @since 2024-09-29
 */
//@UtilityClass
public class BooleanUtil {

    public static boolean isTrue(Boolean bool) {
        return bool != null && bool;
    }

    public static boolean isFalse(Boolean bool) {
        return bool != null && !bool;
    }
}
