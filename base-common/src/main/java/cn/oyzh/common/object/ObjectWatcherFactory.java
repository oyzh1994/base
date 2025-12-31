package cn.oyzh.common.object;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oyzh
 * @since 2025-12-05
 */
public class ObjectWatcherFactory {

    /**
     * 观察者
     */
    private final List<ObjectWatcher> watchers = new ArrayList<>();

    public ObjectWatcherFactory() {
        if (ObjectWatcher.isDisabled()) {
            JulLog.warn("Object watcher is disabled.");
        } else {
            JulLog.info("Object watcher is enabled.");
            ThreadUtil.startVirtual(this::doWatch);
        }
    }

    /**
     * 执行观察
     */
    private void doWatch() {
        while (true) {
            try {
                // 空的观察者
                List<ObjectWatcher> emptyWatchers = null;
                for (ObjectWatcher watcher : this.watchers) {
                    if (watcher.doClear()) {
                        if (emptyWatchers == null) {
                            emptyWatchers = new ArrayList<>();
                        }
                        emptyWatchers.add(watcher);
                    }
                }
                // 执行移除
                if (emptyWatchers != null) {
                    this.watchers.removeAll(emptyWatchers);
                }
                if (!this.watchers.isEmpty()) {
                    System.gc();
                }
                ThreadUtil.sleep(3000);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 推送观察者
     * @param watcher 观察者
     */
    public void push(ObjectWatcher watcher) {
        if (watcher != null) {
            this.watchers.add(watcher);
        }
    }

    /**
     * 当前实例
     */
    public final static ObjectWatcherFactory INSTANCE = new ObjectWatcherFactory();
}
