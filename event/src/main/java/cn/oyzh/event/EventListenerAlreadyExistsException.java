package cn.oyzh.event;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class EventListenerAlreadyExistsException extends RuntimeException {

    public EventListenerAlreadyExistsException(Object listener) {
        super("Listener " + listener + " already exists");
    }
}
