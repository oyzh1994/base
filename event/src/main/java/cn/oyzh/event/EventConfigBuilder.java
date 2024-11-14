package cn.oyzh.event;

/**
 * 事件配置构建器
 *
 * @author oyzh
 * @since 2024/03/30
 */
public class EventConfigBuilder {

    private boolean async;

    private boolean verbose;

    public EventConfigBuilder async(boolean async) {
        this.async = async;
        return this;
    }

    public EventConfigBuilder verbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    public EventConfig build() {
        EventConfig config = new EventConfig();
        config.setAsync(async);
        config.setVerbose(verbose);
        return config;
    }

    public static EventConfigBuilder newBuilder() {
        return new EventConfigBuilder();
    }
}
