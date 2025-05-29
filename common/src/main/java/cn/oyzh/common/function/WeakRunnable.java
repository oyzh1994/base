package cn.oyzh.common.function;


/**
 * 弱引用Runnable
 *
 * @author oyzh
 * @since 2025-04-02
 */
public class WeakRunnable extends WeakFunction implements Runnable {

    private final Runnable action;

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
