package cn.oyzh.common.log;

import cn.oyzh.common.date.DateHelper;
import cn.oyzh.common.util.ArrayUtil;
import cn.oyzh.common.util.StringUtil;

import java.util.logging.LogRecord;

/**
 * 日志格式化器
 *
 * @author oyzh
 * @since 2024-09-27
 */

public class JulFileFormatter extends JulFormatter {

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        // 日志时间
        builder.append(DateHelper.formatTime(record.getInstant()));

        // 日志等级
        JulLevel level = JulLevel.ofLevel(record.getLevel());
        builder.append(" ").append(level);

        // 线程id
        if (JulConst.isEnableThreadId()) {
            builder.append(" ").append(record.getLongThreadID());
        }

        // 线程名称
        if (record instanceof JulLogRecord logRecord) {
            builder.append(" [").append(logRecord.getThreadName()).append("]");
        }

        // 类名、方法名、行号
        builder.append(" ").append(record.getSourceClassName());
        builder.append(".").append(record.getSourceMethodName());
        if (record instanceof JulLogRecord logRecord) {
            builder.append("#").append(logRecord.getLineNumber());
        }

        // 消息处理
        builder.append(" : ").append(this.formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public String formatMessage(LogRecord record) {
        String message = record.getMessage();
        Throwable throwable = record.getThrown();
        Object[] args = record.getParameters();
        if (StringUtil.isNotBlank(message) && ArrayUtil.isNotEmpty(args)) {
            int index = 0;
            // 兼容其他日志库的占位符
            if (message.contains("{}")) {
                while (message.contains("{}") && index < args.length) {
                    Object arg = args[index];
                    if (arg instanceof Throwable t && record.getThrown() == null) {
                        throwable = t;
                        continue;
                    }
                    arg = this.pretreatmentArg(arg, true);
                    message = message.replace("{}", arg.toString());
                    index++;
                }
            } else {// jul类型占位符
                while (index < args.length) {
                    Object arg = args[index];
                    if (arg instanceof Throwable t && record.getThrown() == null) {
                        throwable = t;
                        continue;
                    }
                    arg = this.pretreatmentArg(arg, true);
                    message = message.replace("{" + index + "}", arg.toString());
                    index++;
                }
            }
        }
        if (throwable != null) {
            if (record instanceof JulLogRecord logRecord) {
                message = this.formatThrow(message, throwable, record.getSourceClassName(), record.getSourceMethodName(), logRecord.getLineNumber());
            } else {
                message = this.formatThrow(message, throwable, record.getSourceClassName(), record.getSourceMethodName(), -1);
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
    public String formatThrow(String message, Throwable throwable, String sourceClassName, String sourceMethodName, int lineNumber) {
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
                builder.append(ArrayUtil.last(arr)).append(".java:").append(lineNumber);
                builder.append(")");
            }
            message = builder.toString();
        }
        return message;
    }
}
