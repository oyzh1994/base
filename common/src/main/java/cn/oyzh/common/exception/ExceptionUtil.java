package cn.oyzh.common.exception;

import lombok.experimental.UtilityClass;

/**
 * @author oyzh
 * @since 2024-11-08
 */
@UtilityClass
public class ExceptionUtil {

    public static void throwRuntime(String message) throws RuntimeException {
        throw new RuntimeException(message);
    }
}
