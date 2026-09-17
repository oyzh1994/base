package cn.oyzh.common.log;

import java.io.UnsupportedEncodingException;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * 控制台日志处理器
 *
 * @author oyzh
 * @since 2024-11-15
 */
public class JulConsoleHandler extends StreamHandler {

    public JulConsoleHandler() throws UnsupportedEncodingException {
        super(System.out, new JulConsoleFormatter());
        if (System.console() != null) {
            this.setEncoding(System.console().charset().name());
        } else {
            this.setEncoding(System.getProperty("stdout.encoding"));
        }
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        super.flush();
    }

    @Override
    public void close() {
        super.flush();
        super.close();
    }
}
