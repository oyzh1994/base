package cn.oyzh.common.log;

import cn.oyzh.common.date.DateHelper;
import cn.oyzh.common.util.ArrayUtil;
import cn.oyzh.common.util.StringUtil;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 日志格式化器
 *
 * @author oyzh
 * @since 2024-09-27
 */

public class JulLogFormatter extends Formatter {
    /**
     * 重置
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * 黑色
     */
    public static final String ANSI_BLACK = "\u001B[30m";

    /**
     * 红色
     */
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * 绿色
     */
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * 黄色
     */
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * 蓝色
     */
    public static final String ANSI_BLUE = "\u001B[34m";

    /**
     * 紫色
     */
    public static final String ANSI_PURPLE = "\u001B[35m";

    /**
     * 青蓝
     */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * 白色
     */
    public static final String ANSI_WHITE = "\u001B[37m";

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        // 日志时间
        builder.append(DateHelper.formatDateTime(record.getInstant()));

        // 日志等级
        JulLevel level = JulLevel.ofLevel(record.getLevel());
        if (level == JulLevel.TRACE || level == JulLevel.DEBUG || level == JulLevel.INFO) {
            builder.append(ANSI_GREEN);
            builder.append(" ").append(level);
            builder.append(ANSI_RESET);
        } else if (level == JulLevel.WARN) {
            builder.append(ANSI_YELLOW);
            builder.append(" ").append(level);
            builder.append(ANSI_RESET);
        } else if (level == JulLevel.ERROR) {
            builder.append(ANSI_RED);
            builder.append(" ").append(level);
            builder.append(ANSI_RESET);
        }

        // 线程id
        builder.append(ANSI_PURPLE);
        builder.append(" ").append(record.getLongThreadID());
        builder.append(ANSI_RESET);

        // 线程名称
        if (record instanceof JulLogRecord logRecord) {
            builder.append(" --- ");
            builder.append(ANSI_CYAN);
            builder.append("[").append(logRecord.getThreadName()).append("] ");
            builder.append(ANSI_RESET);
        }

        // 类名、方法名、行号
        builder.append(ANSI_BLUE);
        builder.append(" ").append(record.getSourceClassName());
        builder.append(".").append(record.getSourceMethodName());
        if (record instanceof JulLogRecord logRecord) {
            builder.append("#").append(logRecord.getLineNumber());
        }
        builder.append(ANSI_RESET);

        // 具体消息
        builder.append(" : ");
        // 异常处理
        if (record.getThrown() != null) {
            if (record instanceof JulLogRecord logRecord) {
                builder.append(this.formatMessage(record.getMessage(), record.getThrown(), record.getSourceClassName(), record.getSourceMethodName(), logRecord.getLineNumber()));
            } else {
                builder.append(this.formatMessage(record.getMessage(), record.getThrown(), record.getSourceClassName(), record.getSourceMethodName(), -1));
            }
        } else {// 消息处理
            builder.append(this.formatMessage(record.getMessage(), record.getParameters()));
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * 格式化消息
     *
     * @param message 消息
     * @param args    参数
     * @return 结果
     */
    private String formatMessage(String message, Object[] args) {
        if (StringUtil.isNotBlank(message) && ArrayUtil.isNotEmpty(args)) {
            int index = 0;
            // 兼容其他日志库的占位符
            if (message.contains("{}")) {
                while (message.contains("{}") && index < args.length) {
                    Object arg = args[index];
                    arg = arg == null ? "null" : arg;
                    message = message.replaceFirst("\\{}", ANSI_WHITE + arg + ANSI_RESET);
                    index++;
                }
            } else {// jul类型占位符
                while (index < args.length) {
                    Object arg = args[index];
                    arg = arg == null ? "null" : arg;
                    message = message.replaceFirst("\\{" + index + "}", ANSI_WHITE + arg + ANSI_RESET);
                    index++;
                }
            }
        }
        return message;
    }

    /**
     * 格式化消息
     *
     * @param message          消息
     * @param throwable        异常
     * @param sourceClassName  消息类
     * @param sourceMethodName 消息方法
     * @param lineNumber       消息行号
     * @return 结果
     */
    private String formatMessage(String message, Throwable throwable, String sourceClassName, String sourceMethodName, int lineNumber) {
        if (StringUtil.isNotBlank(message) && throwable != null) {
            StringBuilder builder = new StringBuilder(message);
            builder.append("\n");
            builder.append(throwable.getClass().getName());
            builder.append(" : ");
            builder.append(throwable.getMessage());
            builder.append("\n");
            builder.append(" at ").append(sourceClassName).append(".").append(sourceMethodName);
            if (lineNumber != -1) {
                String[] arr = sourceClassName.split("\\.");
                builder.append("(");
                builder.append(ANSI_BLUE);
                builder.append(ArrayUtil.last(arr)).append(".java:").append(lineNumber);
                builder.append(ANSI_RESET);
                builder.append(")");
            }
            message = builder.toString();
        }
        return message;
    }
}
