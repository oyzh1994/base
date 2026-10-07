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

    /**
     * 日志所在行号
     */
    private int lineNumber;

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 构造日志记录
     *
     * @param level 日志等级
     * @param msg   日志消息
     */
    public JulLogRecord(Level level, String msg) {
        super(level, msg);
    }

    /**
     * 获取日志所在行号
     *
     * @return 日志所在行号
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * 设置日志所在行号
     *
     * @param lineNumber 日志所在行号
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * 获取线程名称
     *
     * @return 线程名称
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * 设置线程名称
     *
     * @param threadName 线程名称
     */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}
