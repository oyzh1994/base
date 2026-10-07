package cn.oyzh.common.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * jul文件日志处理器
 *
 * @author oyzh
 * @since 2024-11-15
 */
public class JulFileHandler extends StreamHandler {

    /**
     * 构造文件日志处理器，以追加方式写入日志文件
     *
     * @param logFile 日志文件
     * @throws FileNotFoundException 文件不存在异常
     */
    public JulFileHandler(File logFile) throws FileNotFoundException {
        super(new FileOutputStream(logFile, true), new JulFileFormatter());
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
