package cn.oyzh.common.log;

import java.util.logging.Formatter;

/**
 * @author oyzh
 * @since 2024-11-21
 */
public abstract class JulFormatter extends Formatter {

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
