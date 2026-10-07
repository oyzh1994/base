package cn.oyzh.pkg;

/**
 * 单次执行处理器，用于标记任务是否已执行
 *
 * @author oyzh
 * @since 2024/6/19
 */
public interface SingleHandler {

    /**
     * 设置是否已执行
     *
     * @param executed 是否已执行
     */
    void setExecuted(boolean executed);

    /**
     * 判断是否已执行
     *
     * @return 已执行返回 true，否则返回 false
     */
    boolean isExecuted();
}
