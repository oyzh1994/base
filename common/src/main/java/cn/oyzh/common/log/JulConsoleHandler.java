package cn.oyzh.common.log;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * 控制台日志处理器
 *
 * @author oyzh
 * @since 2024-11-15
 */
public class JulConsoleHandler extends StreamHandler {

    public JulConsoleHandler() {
        super(System.out, new JulLogFormatter());
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        flush();
    }

    @Override
    public void close() {
        flush();
    }
}
