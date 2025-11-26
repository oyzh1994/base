package cn.oyzh.event;


/**
 * 事件配置
 *
 * @author oyzh
 * @since 2024/3/29
 */
public class EventConfig {

    /**
     * 是否异步
     */
    protected Boolean async;

    /**
     * 是否详细信息
     */
    protected Boolean verbose;

    public boolean isAsync() {
        return async != null && async;
    }

    public boolean isVerbose() {
        return verbose != null && verbose;
    }

    /**
     * 同步配置
     */
    public static EventConfig SYNC = new EventConfig();

    /**
     * 异步配置
     */
    public static EventConfig ASYNC = new EventConfig();

    /**
     * 默认配置
     */
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
