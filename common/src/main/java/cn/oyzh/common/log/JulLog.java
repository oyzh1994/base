package cn.oyzh.common.log;


import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * jul日志
 *
 * @author oyzh
 * @since 2024-09-27
 */
@UtilityClass
public class JulLog {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger("JulLog");

    static {
        System.setProperty("jansi.passthrough", "true");
        JulLog.setLevel(JulLevel.DEBUG);
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(new JulConsoleHandler());
    }

    public static void setLevel(JulLevel level) {
        if (level != null) {
            LOGGER.setLevel(level.toLevel());
        }
    }

    public static void trace(String format, Object... args) {
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(format(Level.FINEST, format, args));
        }
    }

    public static void trace(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(format(Level.FINEST, format, throwable));
        }
    }

    public static void debug(String format, Object... args) {
        if (LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.log(format(Level.CONFIG, format, args));
        }
    }

    public static void debug(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.log(format(Level.CONFIG, format, throwable));
        }
    }

    public static void info(String format, Object... args) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(format(Level.INFO, format, args));
        }
    }

    public static void info(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(format(Level.INFO, format, throwable));
        }
    }

    public static void warn(String format, Object... args) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(format(Level.WARNING, format, args));
        }
    }

    public static void warn(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(format(Level.WARNING, format, throwable));
        }
    }

    public static void error(String format, Object... args) {
        if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(format(Level.SEVERE, format, args));
        }
    }

    public static void error(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(format(Level.SEVERE, format, throwable));
        }
    }

    private static JulLogRecord format(Level level, String format, Object... args) {
        return format(level, format, null, args);
    }

    private static JulLogRecord format(Level level, String format, Throwable throwable) {
        return format(level, format, throwable, null);
    }

    private static JulLogRecord format(Level level, String format, Throwable throwable, Object... args) {
        // 获取线程和堆栈信息
        Thread thread = Thread.currentThread();
        StackTraceElement[] trace = thread.getStackTrace();
        StackTraceElement element = trace[4];
        // 日志对象
        JulLogRecord logRecord = new JulLogRecord(level, format);
        // 参数
        logRecord.setParameters(args);
        // 异常
        logRecord.setThrown(throwable);
        // 时间
        logRecord.setInstant(Instant.now());
        // 线程名称
        logRecord.setThreadName(thread.getName());
        // 线程id
        logRecord.setLongThreadID(thread.threadId());
        // 行号
        logRecord.setLineNumber(element.getLineNumber());
        // class名称
        logRecord.setSourceClassName(element.getClassName());
        // 方法名
        logRecord.setSourceMethodName(element.getMethodName());
        return logRecord;
    }

    public static boolean isTraceEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() >= JulLevel.TRACE.ordinal();
    }

    public static boolean isDebugEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() >= JulLevel.DEBUG.ordinal();
    }

    public static boolean isInfoEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() >= JulLevel.INFO.ordinal();
    }

    public static boolean isWarnEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() >= JulLevel.WARN.ordinal();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
