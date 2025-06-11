package cn.oyzh.common.object;

/**
 * 对象比较器
 *
 * @author oyzh
 * @since 2023/04/24
 * @param <T> 参数
 */
public interface ObjectComparator<T> {

    /**
     * 比较对象
     *
     * @param t1 待比较对象
     * @return 结果
     */
    boolean compare(T t1);
}
