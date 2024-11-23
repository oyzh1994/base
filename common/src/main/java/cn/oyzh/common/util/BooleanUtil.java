package cn.oyzh.common.util;

import lombok.experimental.UtilityClass;

/**
 * @author oyzh
 * @since 2024-09-29
 */
@UtilityClass
public class BooleanUtil {

    public static boolean isTrue(Boolean bool) {
        return bool != null && bool;
    }
}
