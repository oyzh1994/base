package cn.oyzh.event;

import lombok.Getter;
import lombok.Setter;

/**
 * @author oyzh
 * @since 2024/3/29
 */
@Setter
public class EventConfig {

    private Boolean async;

    @Getter
    private Integer delay;

    private Boolean verbose;

    public boolean isAsync() {
        return async != null && async;
    }

    public boolean isDelay() {
        return delay != null && delay > 0;
    }

    public boolean isVerbose() {
        return verbose != null && verbose;
    }

    public static EventConfig DEFAULT = new EventConfig();

    public static EventConfig ASYNC = new EventConfig();

    static {
        DEFAULT.async = false;
        DEFAULT.verbose = true;

        ASYNC.async = true;
        ASYNC.verbose = true;
    }
}
