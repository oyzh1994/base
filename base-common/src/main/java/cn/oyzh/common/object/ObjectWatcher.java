package cn.oyzh.common.object;

import cn.oyzh.common.thread.ThreadUtil;

import java.lang.ref.WeakReference;

/**
 *
 * @author oyzh
 * @since 2025-12-05
 */
public class ObjectWatcher {
    /**
     * tableview
     */
    private final WeakReference<Object> reference;

    private final String name;

    public ObjectWatcher(Object node, String name) {
        this.reference = new WeakReference<>(node);
        this.name = name == null ? node.getClass().getSimpleName() : name;
        this.doWatch();
    }

    protected void doWatch() {
        ThreadUtil.start(() -> {
            while (true) {
                if (this.reference.get() == null) {
                    System.out.println(this.name + " is null ---------");
                    break;
                } else {
                    ThreadUtil.sleep(3000);
                }
            }
        });
    }

    public static ObjectWatcher watch(Object node) {
        return new ObjectWatcher(node, null);
    }

    public static ObjectWatcher watch(Object node, String name) {
        return new ObjectWatcher(node, name);
    }

}
