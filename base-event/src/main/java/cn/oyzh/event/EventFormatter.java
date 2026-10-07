package cn.oyzh.event;

/**
 * 事件格式化器
 *
 * @author oyzh
 * @since 2024-11-01
 */
public interface EventFormatter {

    /**
     * 格式化消息
     *
     * @return 格式化后的消息
     */
    String eventFormat();

}
