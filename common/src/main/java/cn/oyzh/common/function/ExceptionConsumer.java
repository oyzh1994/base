package cn.oyzh.common.function;

/**
 * 异常BiConsumer
 *
 * @param <T> 形参
 * @author oyzh
 * @since 2025-05-16
 */
public interface ExceptionConsumer<T> {

    /**
     * 接受参数
     *
     * @param t 参数1
     * @throws Exception 异常
     */
    void accept(T t) throws Exception;

}
