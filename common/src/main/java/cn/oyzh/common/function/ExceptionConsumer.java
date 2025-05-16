package cn.oyzh.common.function;

/**
 * @author oyzh
 * @since 2025-05-16
 */
public interface ExceptionConsumer<T> {

    void accept(T t) throws Exception;

}
