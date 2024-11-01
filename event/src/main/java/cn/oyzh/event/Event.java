package cn.oyzh.event;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 事件
 *
 * @author oyzh
 * @since 2023/4/10
 */
@Data
@Accessors(fluent = true, chain = false)
public class Event<D> {

    /**
     * 数据
     */
    private D data;

    /**
     * 额外数据
     */
    private Object extra;

    public Event() {
        this(null, null);
    }

    public Event(D data) {
        this(data, null);
    }

    public Event(D data, Object extra) {
        this.data = data;
        this.extra = data;
    }

}
