package cn.oyzh.ssh.util;

import cn.oyzh.common.log.JulLog;
import com.jcraft.jsch.Logger;

/**
 * jsch日志对象
 *
 * @author oyzh
 * @since 2025/04/15
 */
public class JschLogger implements Logger {

    @Override
    public void log(int level, String message) {
        if (level == Logger.DEBUG) {
            JulLog.debug(message);
        } else if (level == Logger.INFO) {
            JulLog.info(message);
        } else if (level == Logger.WARN) {
            JulLog.warn(message);
        } else if (level == Logger.ERROR) {
            JulLog.error(message);
        } else {
            JulLog.error(message);
        }
    }

    @Override
    public boolean isEnabled(int level) {
        if (level == Logger.DEBUG) {
            return JulLog.isDebugEnabled();
        }
        if (level == Logger.INFO) {
            return JulLog.isInfoEnabled();
        }
        if (level == Logger.WARN) {
            return JulLog.isWarnEnabled();
        }
        if (level == Logger.ERROR) {
            return JulLog.isErrorEnabled();
        }
        return true;
    }
}