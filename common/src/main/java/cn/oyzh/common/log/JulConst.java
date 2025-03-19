package cn.oyzh.common.log;

import cn.oyzh.common.util.StringUtil;
import lombok.experimental.UtilityClass;

/**
 * @author oyzh
 * @since 2024-11-19
 */
//@UtilityClass
public class JulConst {

    public static final String ENABLE_THREAD_ID = "jul.enable.thread.id";

    public static void enableThreadId() {
        System.setProperty(ENABLE_THREAD_ID, "true");
    }

    public static void disableThreadId() {
        System.clearProperty(ENABLE_THREAD_ID);
    }

    public static boolean isEnableThreadId() {
        String prop = System.getProperty(ENABLE_THREAD_ID);
        return StringUtil.equals(prop, "true");
    }
}
