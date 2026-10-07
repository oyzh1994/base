package cn.oyzh.common.log;

import cn.oyzh.common.util.StringUtil;

/**
 * jul常量
 *
 * @author oyzh
 * @since 2024-11-19
 */
public class JulConst {

    /**
     * 线程id启用属性名
     */
    public static final String ENABLE_THREAD_ID = "jul.enable.thread.id";

    /**
     * 启用线程id输出
     */
    public static void enableThreadId() {
        System.setProperty(ENABLE_THREAD_ID, "true");
    }

    /**
     * 禁用线程id输出
     */
    public static void disableThreadId() {
        System.clearProperty(ENABLE_THREAD_ID);
    }

    /**
     * 是否启用线程id输出
     *
     * @return 结果
     */
    public static boolean isEnableThreadId() {
        String prop = System.getProperty(ENABLE_THREAD_ID);
        return StringUtil.equals(prop, "true");
    }
}
