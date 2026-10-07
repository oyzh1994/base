package cn.oyzh.common.function;


/**
 * 弱引用Runnable
 *
 * @author oyzh
 * @since 2025-04-02
 */
public class WeakRunnable extends WeakFunction implements Runnable {

    /**
     * 被代理的Runnable
     */
    private final Runnable action;

    /**
     * 构造弱引用Runnable
     *
     * @param obj    被弱引用的对象
     * @param action 被代理的Runnable
     */
    public WeakRunnable(Object obj, Runnable action) {
        super(obj);
        this.action = action;
    }

    @Override
    public void run() {
        if (this.hasReference()) {
            this.action.run();
        }
    }
}
