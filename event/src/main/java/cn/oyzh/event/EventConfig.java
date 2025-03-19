package cn.oyzh.event;


/**
 * @author oyzh
 * @since 2024/3/29
 */
//@Setter
public class EventConfig {

    protected Boolean async;

    protected Boolean verbose;

    public boolean isAsync() {
        return async != null && async;
    }

    public boolean isVerbose() {
        return verbose != null && verbose;
    }

    public static EventConfig SYNC = new EventConfig();

    public static EventConfig ASYNC = new EventConfig();

    public static EventConfig DEFAULT = new EventConfig();

    static {
        SYNC.async = true;
        SYNC.verbose = true;

        ASYNC.async = true;
        ASYNC.verbose = true;

        DEFAULT.async = false;
        DEFAULT.verbose = true;
    }
}
