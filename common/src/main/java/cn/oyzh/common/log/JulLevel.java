package cn.oyzh.common.log;

import java.util.logging.Level;

/**
 * 日志等级
 *
 * @author oyzh
 * @since 2024-11-15
 */
public enum JulLevel {

    ALL,
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
    OFF;

    public Level toLevel() {
        return toLevel(this);
    }

    public static JulLevel ofLevel(Level level) {
        if (level == Level.FINEST || level == Level.FINER) {
            return TRACE;
        }
        if (level == Level.FINE || level == Level.CONFIG) {
            return DEBUG;
        }
        if (level == Level.INFO) {
            return INFO;
        }
        if (level == Level.WARNING) {
            return WARN;
        }
        if (level == Level.SEVERE) {
            return ERROR;
        }
        if (level == Level.OFF) {
            return OFF;
        }
        return ALL;
    }

    public static Level toLevel(JulLevel level) {
        if (level == TRACE) {
            return Level.FINEST;
        }
        if (level == DEBUG) {
            return Level.CONFIG;
        }
        if (level == INFO) {
            return Level.INFO;
        }
        if (level == WARN) {
            return Level.WARNING;
        }
        if (level == ERROR) {
            return Level.SEVERE;
        }
        if (level == OFF) {
            return Level.OFF;
        }
        if (level == ALL) {
            return Level.ALL;
        }
        return null;
    }

    public static String nameOfLevel(Level level) {
        if (level == Level.FINEST || level == Level.FINER) {
            return "TRACE";
        }
        if (level == Level.FINE || level == Level.CONFIG) {
            return "DEBUG";
        }
        if (level == Level.INFO) {
            return "INFO";
        }
        if (level == Level.WARNING) {
            return "WARN";
        }
        if (level == Level.SEVERE) {
            return "ERROR";
        }
        return "UNKNOWN";
    }

}
