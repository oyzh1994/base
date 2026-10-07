package cn.oyzh.event;


/**
 * 事件配置
 *
 * @author oyzh
 * @since 2024-11-01
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

    /**
     * 是否异步
     *
     * @return 是否异步
     */
    public boolean isAsync() {
        return async != null && async;
    }

    /**
     * 是否输出详细日志
     *
     * @return 是否输出详细日志
     */
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
        SYNC.async = false;
        SYNC.verbose = true;

        ASYNC.async = true;
        ASYNC.verbose = true;

        DEFAULT.async = false;
        DEFAULT.verbose = true;
    }
}
