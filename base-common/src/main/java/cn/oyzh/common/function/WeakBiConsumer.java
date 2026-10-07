package cn.oyzh.common.function;


import java.util.function.BiConsumer;

/**
 * 弱引用BiConsumer
 *
 * @param <T> 形参1
 * @param <U> 形参2
 * @author oyzh
 * @since 2025-04-02
 */
public class WeakBiConsumer<T, U> extends WeakFunction implements BiConsumer<T, U> {

    /**
     * 被代理的BiConsumer
     */
    private final BiConsumer<T, U> consumer;

    /**
     * 构造弱引用BiConsumer
     *
     * @param obj      被弱引用的对象
     * @param consumer 被代理的BiConsumer
     */
    public WeakBiConsumer(Object obj, BiConsumer<T, U> consumer) {
        super(obj);
        this.consumer = consumer;
    }

    @Override
    public void accept(T t, U u) {
        if (this.hasReference()) {
            this.consumer.accept(t, u);
        }
    }
}
