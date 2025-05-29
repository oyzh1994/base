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

    private final Consumer<T> consumer;

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
