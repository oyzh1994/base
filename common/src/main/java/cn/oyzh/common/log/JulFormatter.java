package cn.oyzh.common.log;

import cn.oyzh.common.util.ArrayUtil;
import cn.oyzh.common.util.StringUtil;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author oyzh
 * @since 2024-11-21
 */
public abstract class JulFormatter extends Formatter {

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
            } else if (message.contains("{0}")) {// jul类型占位符
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
            } else if (message.contains("%")) {// string模板占位符
                message = String.format(message, args);
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
     * 格式化异常
     *
     * @param message          消息
     * @param throwable        异常
     * @param sourceClassName  消息类
     * @param sourceMethodName 消息方法
     * @param lineNumber       消息行号
     * @return 结果
     */
    public abstract String formatThrow(String message, Throwable throwable, String sourceClassName, String sourceMethodName, int lineNumber);

    protected String pretreatmentArg(Object arg, boolean escape) {
        if (arg == null) {
            return "null";
        }
        if (arg instanceof Number obj) {
            return obj.toString();
        }
        String string = arg.toString();
        if (escape) {
            StringBuilder builder = new StringBuilder();
            for (char c : string.toCharArray()) {
                switch (c) {
                    case '\\':
                        builder.append("\\\\"); // 反斜杠
                        break;
                    case '\'':
                        builder.append("\\'"); // 单引号
                        break;
                    case '\"':
                        builder.append("\\\""); // 双引号
                        break;
                    default: // 其他字符保持不变
                        builder.append(c);
                        break;
                }
            }
            string = builder.toString();
        }
        return string;
    }
}
