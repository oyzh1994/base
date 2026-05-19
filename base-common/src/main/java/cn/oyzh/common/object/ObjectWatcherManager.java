package cn.oyzh.common.object;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import cn.oyzh.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oyzh
 * @since 2025-12-05
 */
public class ObjectWatcherManager {

    /**
     * 禁用对象观察者
     */
    public static String WATCHER_DISABLE = "OBJECT_WATCHER_DISABLE";

    /**
     * 观察者
     */
    private static final List<ObjectWatcher> WATCHERS = new ArrayList<>();

    static {
        if (ObjectWatcherManager.isDisabled()) {
            JulLog.warn("Object watcher is disabled.");
        } else {
            JulLog.info("Object watcher is enabled.");
            TaskManager.startInterval(ObjectWatcherManager::doWatch, 3000, 0);
        }
    }

    /**
     * 执行观察
     */
    private static void doWatch() {
        try {
            // 空的观察者
            List<ObjectWatcher> emptyWatchers = null;
            for (ObjectWatcher watcher : WATCHERS) {
                if (watcher.doClear()) {
                    if (emptyWatchers == null) {
                        emptyWatchers = new ArrayList<>();
                    }
                    emptyWatchers.add(watcher);
                } else {
                    System.out.println("watcher=" + watcher.getObject());
                }
            }
            // 执行移除
            if (emptyWatchers != null) {
                WATCHERS.removeAll(emptyWatchers);
            }
            if (!WATCHERS.isEmpty()) {
                System.gc();
                System.out.println("watchers.size=" + WATCHERS.size());
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 推送观察者
     *
     * @param watcher 观察者
     */
    public static void push(ObjectWatcher watcher) {
        if (watcher != null) {
            System.out.println("add watcher=" + watcher.getObject());
            WATCHERS.add(watcher);
        }
    }

    /**
     * 观察对象
     *
     * @param object 对象
     * @return 对象观察者
     */
    public static ObjectWatcher watch(Object object) {
        return watch(object, null);
    }

    /**
     * 观察对象
     *
     * @param object 对象
     * @return 对象观察者
     */
    public static ObjectWatcher watch(Object object, String name) {
        if (ObjectWatcherManager.isEnabled() && object != null) {
            ObjectWatcher watcher = new ObjectWatcher(object, name);
            ObjectWatcherManager.push(watcher);
            return watcher;
        }
        return null;
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
     *
     * @return 结果
     */
    public static boolean isDisabled() {
        String str = System.getProperty(WATCHER_DISABLE);
        return StringUtil.equals(str, "1");
    }

    /**
     * 是否启用
     *
     * @return 结果
     */
    public static boolean isEnabled() {
        return !isDisabled();
    }
}
