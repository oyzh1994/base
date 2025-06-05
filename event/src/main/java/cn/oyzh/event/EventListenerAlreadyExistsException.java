package cn.oyzh.event;

/**
 * 事件监听器已存在异常
 *
 * @author oyzh
 * @since 2024-11-18
 */
public class EventListenerAlreadyExistsException extends RuntimeException {

    public EventListenerAlreadyExistsException(Object listener) {
        super("Listener " + listener + " already exists");
    }
}
