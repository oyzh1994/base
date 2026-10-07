package cn.oyzh.common.function;


import java.lang.ref.WeakReference;

/**
 * 弱引用Function
 *
 * @author oyzh
 * @since 2025-04-02
 */
public class WeakFunction {

    /**
     * 对象的弱引用
     */
    private final WeakReference<Object> reference;

    /**
     * 构造弱引用Function
     *
     * @param obj 被弱引用的对象
     */
    public WeakFunction(Object obj) {
        this.reference = new WeakReference<>(obj);
    }

    /**
     * 判断被弱引用的对象是否仍然存活
     *
     * @return 存活返回true，否则返回false
     */
    public boolean hasReference() {
        return this.reference.get() != null;
    }


}
