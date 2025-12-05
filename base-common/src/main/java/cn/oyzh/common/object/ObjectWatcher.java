package cn.oyzh.common.object;

import java.lang.ref.WeakReference;

/**
 *
 * @author oyzh
 * @since 2025-12-05
 */
public class ObjectWatcher {

    private final String name;

    private final WeakReference<Object> reference;

    public ObjectWatcher(Object node, String name) {
        this.reference = new WeakReference<>(node);
        this.name = name == null ? node.getClass().getSimpleName() : name;
    }

    protected boolean doClear() {
        if (this.isEmpty()) {
            System.out.println(this.name + " is null ---------");
            this.reference.clear();
            return true;
        }
        return false;
    }

    protected boolean isEmpty() {
        return this.reference.get() == null;
    }

    public static ObjectWatcher watch(Object node) {
        ObjectWatcher watcher = new ObjectWatcher(node, null);
        ObjectWatcherFactory.INSTANCE.push(watcher);
        return watcher;
    }

    public static ObjectWatcher watch(Object node, String name) {
        ObjectWatcher watcher = new ObjectWatcher(node, name);
        ObjectWatcherFactory.INSTANCE.push(watcher);
        return watcher;
    }

}
