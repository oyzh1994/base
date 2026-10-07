package cn.oyzh.common.log;

import cn.oyzh.common.util.StringUtil;

import java.util.logging.Level;

/**
 * jul日志等级
 *
 * @author oyzh
 * @since 2024-11-15
 */
public enum JulLevel {

    /**
     * 全部等级
     */
    ALL,
    /**
     * 跟踪等级
     */
    TRACE,
    /**
     * 调试等级
     */
    DEBUG,
    /**
     * 信息等级
     */
    INFO,
    /**
     * 警告等级
     */
    WARN,
    /**
     * 错误等级
     */
    ERROR,
    /**
     * 关闭日志
     */
    OFF;

    /**
     * 转换为JUL日志等级
     *
     * @return JUL日志等级
     */
    public Level toLevel() {
        return toLevel(this);
    }

    /**
     * 将JUL日志等级转换为JulLevel
     *
     * @param level JUL日志等级
     * @return JulLevel
     */
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

    /**
     * 将JulLevel转换为JUL日志等级
     *
     * @param level JulLevel
     * @return JUL日志等级
     */
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

    /**
     * 获取JUL日志等级的名称
     *
     * @param level JUL日志等级
     * @return 名称
     */
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


    /**
     * 按名称解析JulLevel
     *
     * @param name 名称
     * @return JulLevel，无法识别时返回 null
     */
    public static JulLevel ofLevel(String name) {
        if (StringUtil.equalsIgnoreCase(name, "TRACE")) {
            return TRACE;
        }
        if (StringUtil.equalsIgnoreCase(name, "DEBUG")) {
            return DEBUG;
        }
        if (StringUtil.equalsIgnoreCase(name, "INFO")) {
            return INFO;
        }
        if (StringUtil.equalsAnyIgnoreCase(name, "WARN", "WARNING")) {
            return WARN;
        }
        if (StringUtil.endsWithAny(name, "ERROR")) {
            return ERROR;
        }
        return null;
    }

}
