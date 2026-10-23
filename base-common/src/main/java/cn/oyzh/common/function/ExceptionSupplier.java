package cn.oyzh.common.function;

/**
 * 异常Supplier
 *
 * @param <T> 形参
 * @author oyzh
 * @since 2025-05-16
 */
public interface ExceptionSupplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;

}
