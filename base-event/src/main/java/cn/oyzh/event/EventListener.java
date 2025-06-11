package cn.oyzh.event;

/**
 * 事件监听接口
 *
 * @author oyzh
 * @since 2024/3/29
 */
public interface EventListener {

    /**
     * 注册监听器
     */
    default void register() {
        EventUtil.register(this);
    }

    /**
     * 取消注册监听器
     *
     * @return 结果
     */
    default boolean unregister() {
        return EventUtil.unregister(this);
    }
}
