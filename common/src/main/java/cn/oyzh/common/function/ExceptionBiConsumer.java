package cn.oyzh.common.function;

/**
 * @author oyzh
 * @since 2025-05-26
 */
public interface ExceptionBiConsumer<T, U> {

    void accept(T t, U u) throws Exception;

}
