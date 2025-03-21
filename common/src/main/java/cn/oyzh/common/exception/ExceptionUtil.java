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

    public static String getMessage(Throwable ex) throws RuntimeException {
        if (ex == null) {
            return null;
        }
        Throwable t = ex;
        while (t.getCause() != null) {
            t = t.getCause();
        }
        String msg = t.getMessage();
        if (msg == null || msg.isBlank()) {
            return t.toString();
        }
        return msg;
    }

    public static boolean hasMessage(Throwable ex, String... message) throws RuntimeException {
        return StringUtil.containsAny(getMessage(ex), message);
    }
}
