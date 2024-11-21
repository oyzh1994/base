package cn.oyzh.common.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * 文件日志处理器
 *
 * @author oyzh
 * @since 2024-11-15
 */
public class JulFileHandler extends StreamHandler {

    public JulFileHandler(File logFile) throws FileNotFoundException {
        super(new FileOutputStream(logFile, true), new JulFileLogFormatter());
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
