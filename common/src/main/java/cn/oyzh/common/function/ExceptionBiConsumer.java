package cn.oyzh.common.function;

/**
 * 异常BiConsumer
 *
 * @param <T> 形参1
 * @param <U> 形参2
 * @author oyzh
 * @since 2025-05-26
 */
public interface ExceptionBiConsumer<T, U> {

    /**
     * 接受参数
     *
     * @param t 参数1
     * @param u 参数2
     * @throws Exception 异常
     */
    void accept(T t, U u) throws Exception;

}
