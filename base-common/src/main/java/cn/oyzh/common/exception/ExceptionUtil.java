package cn.oyzh.common.exception;

import cn.oyzh.common.util.StringUtil;

/**
 * 异常工具类
 *
 * @author oyzh
 * @since 2024-11-08
 */
public class ExceptionUtil {

    /**
     * 抛出runtime异常
     *
     * @param message 消息
     */
    public static void throwRuntime(String message) throws RuntimeException {
        throw new RuntimeException(message);
    }

    /**
     * 获取信息
     *
     * @param ex 异常
     * @return 信息
     */
    public static String getMessage(Throwable ex) {
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

    /**
     * 是否包含信息
     *
     * @param ex      异常
     * @param message 消息
     * @return 结果
     */
    public static boolean hasMessage(Throwable ex, String... message) {
        return StringUtil.containsAnyIgnoreCase(getMessage(ex), message);
    }

    /**
     * 是否中断
     *
     * @param ex 异常
     * @return 结果
     */
    public static boolean isInterrupt(Throwable ex) {
        if (hasMessage(ex, "canceled", "interrupt")) {
            return true;
        }
        return false;
    }
}
