package cn.oyzh.common.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * jul日志记录
 *
 * @author oyzh
 * @since 2024-11-15
 */
public class JulLogRecord extends LogRecord {

    private int lineNumber;

    private String threadName;

    public JulLogRecord(Level level, String msg) {
        super(level, msg);
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}
