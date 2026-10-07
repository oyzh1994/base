package cn.oyzh.common.function;


import java.util.function.Consumer;

/**
 * 弱引用Consumer
 *
 * @param <T> 形参
 * @author oyzh
 * @since 2025-04-02
 */
public class WeakConsumer<T> extends WeakFunction implements Consumer<T> {

    /**
     * 被代理的Consumer
     */
    private final Consumer<T> consumer;

    /**
     * 构造弱引用Consumer
     *
     * @param obj      被弱引用的对象
     * @param consumer 被代理的Consumer
     */
    public WeakConsumer(Object obj, Consumer<T> consumer) {
        super(obj);
        this.consumer = consumer;
    }

    @Override
    public void accept(T t) {
        if (this.hasReference()) {
            this.consumer.accept(t);
        }
    }
}
