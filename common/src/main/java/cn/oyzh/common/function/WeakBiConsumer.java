package cn.oyzh.common.function;


import java.util.function.BiConsumer;

/**
 * @author oyzh
 * @since 2025-04-02
 */
public class WeakBiConsumer<T, U> extends WeakFunction implements BiConsumer<T, U> {

    private final BiConsumer<T, U> consumer;

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
