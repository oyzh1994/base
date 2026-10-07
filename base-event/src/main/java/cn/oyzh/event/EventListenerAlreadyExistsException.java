package cn.oyzh.event;

/**
 * 事件监听器已存在异常
 *
 * @author oyzh
 * @since 2024-11-18
 */
public class EventListenerAlreadyExistsException extends RuntimeException {

    /**
     * 构造异常
     *
     * @param listener 已存在的监听器
     */
    public EventListenerAlreadyExistsException(Object listener) {
        super("Event listener " + listener + " already exists");
    }
}
