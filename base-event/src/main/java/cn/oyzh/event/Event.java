package cn.oyzh.event;


/**
 * 事件
 *
 * @author oyzh
 * @since 2023/4/10
 */
public class Event<D> {

    /**
     * 数据
     */
    private D data;

    /**
     * 额外数据
     */
    private Object extra;

    /**
     * 构造事件，数据和额外数据均为空
     */
    public Event() {
        this(null, null);
    }

    /**
     * 构造事件
     *
     * @param data 数据
     */
    public Event(D data) {
        this(data, null);
    }

    /**
     * 构造事件
     *
     * @param data  数据
     * @param extra 额外数据
     */
    public Event(D data, Object extra) {
        this.data = data;
        this.extra = extra;
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    public D data() {
        return data;
    }

    /**
     * 设置数据
     *
     * @param data 数据
     * @return 当前事件
     */
    public Event<D> data(D data) {
        this.data = data;
        return this;
    }

    /**
     * 获取额外数据
     *
     * @return 额外数据
     */
    public Object extra() {
        return extra;
    }

    /**
     * 设置额外数据
     *
     * @param extra 额外数据
     * @return 当前事件
     */
    public Event<D> extra(Object extra) {
        this.extra = extra;
        return this;
    }
}
