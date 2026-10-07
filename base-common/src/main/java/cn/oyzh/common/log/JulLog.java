package cn.oyzh.common.log;


import cn.oyzh.common.util.JarUtil;

import java.io.File;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * jul日志对象
 *
 * @author oyzh
 * @since 2024-09-27
 */
public class JulLog {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger("JulLog");

    static {
        try {
            System.setProperty("jansi.passthrough", "true");
            LOGGER.setUseParentHandlers(false);
            // 日志等级
            JulLog.setLevel(JulUtil.getLogLevel());
            // 控制台日志
            if (!JarUtil.isInJar()) {
                LOGGER.addHandler(new JulConsoleHandler());
            }
            // 文件日志
            LOGGER.addHandler(new JulFileHandler(JulUtil.getLogFile()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置日志等级
     *
     * @param level 日志等级
     */
    public static void setLevel(JulLevel level) {
        if (level != null) {
            LOGGER.setLevel(level.toLevel());
        }
    }

    /**
     * 输出跟踪等级日志
     *
     * @param format 消息格式
     * @param args   参数
     */
    public static void trace(String format, Object... args) {
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(record(Level.FINEST, format, args));
        }
    }

    /**
     * 输出跟踪等级日志
     *
     * @param format    消息格式
     * @param throwable 异常
     */
    public static void trace(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(record(Level.FINEST, format, throwable));
        }
    }

    /**
     * 输出调试等级日志
     *
     * @param format 消息格式
     * @param args   参数
     */
    public static void debug(String format, Object... args) {
        if (LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.log(record(Level.CONFIG, format, args));
        }
    }

    /**
     * 输出调试等级日志
     *
     * @param format    消息格式
     * @param throwable 异常
     */
    public static void debug(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.log(record(Level.CONFIG, format, throwable));
        }
    }

    /**
     * 输出信息等级日志
     *
     * @param format 消息格式
     * @param args   参数
     */
    public static void info(String format, Object... args) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(record(Level.INFO, format, args));
        }
    }

    /**
     * 输出信息等级日志
     *
     * @param format    消息格式
     * @param throwable 异常
     */
    public static void info(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(record(Level.INFO, format, throwable));
        }
    }

    /**
     * 输出警告等级日志
     *
     * @param format 消息格式
     * @param args   参数
     */
    public static void warn(String format, Object... args) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(record(Level.WARNING, format, args));
        }
    }

    /**
     * 输出警告等级日志
     *
     * @param format    消息格式
     * @param throwable 异常
     */
    public static void warn(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(record(Level.WARNING, format, throwable));
        }
    }

    /**
     * 输出错误等级日志
     *
     * @param format 消息格式
     * @param args   参数
     */
    public static void error(String format, Object... args) {
        if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(record(Level.SEVERE, format, args));
        }
    }

    /**
     * 输出错误等级日志
     *
     * @param format    消息格式
     * @param throwable 异常
     */
    public static void error(String format, Throwable throwable) {
        if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(record(Level.SEVERE, format, throwable));
        }
    }

    /**
     * 构建日志记录
     *
     * @param level  日志等级
     * @param format 消息格式
     * @param args   参数
     * @return 日志记录
     */
    private static JulLogRecord record(Level level, String format, Object... args) {
        return record(level, format, null, args);
    }

    /**
     * 构建日志记录，包含异常与堆栈信息
     *
     * @param level     日志等级
     * @param format    消息格式
     * @param throwable 异常
     * @param args      参数
     * @return 日志记录
     */
    private static JulLogRecord record(Level level, String format, Throwable throwable, Object... args) {
        // 获取线程和堆栈信息
        Thread thread = Thread.currentThread();
        StackTraceElement[] trace = thread.getStackTrace();
        StackTraceElement element = trace[Math.min(4, trace.length - 1)];
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
        if (JulConst.isEnableThreadId()) {
            logRecord.setLongThreadID(thread.threadId());
        }
        // 行号
        logRecord.setLineNumber(element.getLineNumber());
        // class名称
        logRecord.setSourceClassName(element.getClassName());
        // 方法名
        logRecord.setSourceMethodName(element.getMethodName());
        return logRecord;
    }

    /**
     * 是否启用跟踪等级日志
     *
     * @return 结果
     */
    public static boolean isTraceEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() >= JulLevel.TRACE.ordinal();
    }

    /**
     * 是否启用调试等级日志
     *
     * @return 结果
     */
    public static boolean isDebugEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() <= JulLevel.DEBUG.ordinal();
    }

    /**
     * 是否启用信息等级日志
     *
     * @return 结果
     */
    public static boolean isInfoEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() <= JulLevel.INFO.ordinal();
    }

    /**
     * 是否启用警告等级日志
     *
     * @return 结果
     */
    public static boolean isWarnEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() <= JulLevel.WARN.ordinal();
    }

    /**
     * 是否启用错误等级日志
     *
     * @return 结果
     */
    public static boolean isErrorEnabled() {
        JulLevel level = JulLevel.ofLevel(LOGGER.getLevel());
        return level.ordinal() <= JulLevel.ERROR.ordinal();
    }

    /**
     * 获取日志对象
     *
     * @return 日志对象
     */
    public static Logger getLogger() {
        return LOGGER;
    }
}
