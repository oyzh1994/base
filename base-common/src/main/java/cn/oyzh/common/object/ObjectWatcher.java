package cn.oyzh.common.object;

import cn.oyzh.common.util.StringUtil;

import java.lang.ref.WeakReference;

/**
 * 对象观察者
 *
 * @author oyzh
 * @since 2025-12-05
 */
public class ObjectWatcher {

    /**
     * 禁用对象观察者
     */
    public static String WATCHER_DISABLE = "OBJECT_WATCHER_DISABLE";

    /**
     * 名称
     */
    private final String name;

    /**
     * 引用
     */
    private final WeakReference<Object> reference;

    public ObjectWatcher(Object node, String name) {
        this.reference = new WeakReference<>(node);
        this.name = name == null ? node.getClass().getSimpleName() : name;
    }

    /**
     * 执行清除
     * @return 结果
     */
    protected boolean doClear() {
        if (this.isEmpty()) {
            System.out.println(this.name + " is null ---------");
            this.reference.clear();
            return true;
        }
        return false;
    }

    /**
     * 是否为空
     * @return 结果
     */
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

    /**
     * 禁用
     */
    public static void disable() {
        System.setProperty(WATCHER_DISABLE, "1");
    }

    /**
     * 启用
     */
    public static void enable() {
        System.clearProperty(WATCHER_DISABLE);
    }

    /**
     * 是否禁用
     * @return 结果
     */
    public static boolean isDisabled() {
        String str = System.getProperty(WATCHER_DISABLE);
        return StringUtil.equals(str, "1");
    }

    /**
     * 是否启用
     * @return 结果
     */
    public static boolean isEnabled() {
        return !isDisabled();
    }

}
