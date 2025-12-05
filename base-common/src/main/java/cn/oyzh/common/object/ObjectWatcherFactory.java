package cn.oyzh.common.object;

import cn.oyzh.common.thread.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oyzh
 * @since 2025-12-05
 */
public class ObjectWatcherFactory {

    private final List<ObjectWatcher> watchers = new ArrayList<>();

    public ObjectWatcherFactory() {
        ThreadUtil.startVirtual(this::doWatch);
    }

    private void doWatch() {
        while (true) {
            try {
                List<ObjectWatcher> emptyWatchers = null;
                for (ObjectWatcher watcher : this.watchers) {
                    if (watcher.doClear()) {
                        if (emptyWatchers == null) {
                            emptyWatchers = new ArrayList<>();
                        }
                        emptyWatchers.add(watcher);
                    }
                }
                if (emptyWatchers != null) {
                    this.watchers.removeAll(emptyWatchers);
                }
                System.gc();
                ThreadUtil.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void push(ObjectWatcher watcher) {
        this.watchers.add(watcher);
    }

    public final static ObjectWatcherFactory INSTANCE = new ObjectWatcherFactory();
}
