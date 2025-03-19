package cn.oyzh.common.log;

import lombok.Getter;
import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author oyzh
 * @since 2024-11-15
 */
public class JulLogRecord extends LogRecord {

//    @Setter
//    @Getter
    private int lineNumber;

//    @Setter
//    @Getter
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
