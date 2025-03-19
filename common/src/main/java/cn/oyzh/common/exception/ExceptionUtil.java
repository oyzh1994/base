package cn.oyzh.common.exception;

import cn.oyzh.common.util.StringUtil;

/**
 * @author oyzh
 * @since 2024-11-08
 */
//@UtilityClass
public class ExceptionUtil {

    public static void throwRuntime(String message) throws RuntimeException {
        throw new RuntimeException(message);
    }

    public static boolean hasMessage(Throwable ex, String... message) throws RuntimeException {
        if (ex == null) {
            return false;
        }
        return StringUtil.containsAny(ex.getMessage(), message);
    }
}
