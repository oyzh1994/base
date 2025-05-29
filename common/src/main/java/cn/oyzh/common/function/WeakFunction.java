package cn.oyzh.common.function;


import java.lang.ref.WeakReference;

/**
 * 弱引用Function
 * @author oyzh
 * @since 2025-04-02
 */
public class WeakFunction {

    private final WeakReference<Object> reference;

    public WeakFunction(Object obj) {
        this.reference = new WeakReference<>(obj);
    }

    public boolean hasReference() {
        return this.reference.get() != null;
    }


}
