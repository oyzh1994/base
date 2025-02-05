package cn.oyzh.event;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author oyzh
 * @since 2024-11-13
 */
public class EventBus {

    @Setter
    @Getter
    @Accessors(chain = false, fluent = true)
    private Consumer<Exception> exceptionHandler;

    private final EventRegister register = new EventRegister();

    private final EventDispatcher dispatcher = new EventDispatcher();

    public boolean register(Object listener) {
        try {
            return this.register.register(listener);
        } catch (Exception ex) {
            if (this.exceptionHandler != null) {
                this.exceptionHandler.accept(ex);
            } else {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean unregister(Object listener) {
        try {
            return this.register.unregister(listener);
        } catch (Exception ex) {
            if (this.exceptionHandler != null) {
                this.exceptionHandler.accept(ex);
            } else {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public <C extends EventConfig> void post(Object event, C config, Integer delayMillis) {
        if (event != null) {
            boolean verbose = config != null && config.isVerbose();
            boolean delay = delayMillis != null && delayMillis > 0;
            boolean async = config != null && config.isAsync();
            Runnable func = () -> this.doEventPost(event, verbose);
            // 延迟、异步
            if (delay && async) {
                TaskManager.startDelay(func, delayMillis);
            } else if (delay) {// 延迟
                TaskManager.startDelay(func, delayMillis);
            } else if (async) {// 异步
                TaskManager.start(func);
            } else {// 正常执行
                func.run();
            }
        }
    }

    protected void doEventPost(Object event, boolean verbose) {
        try {
            Long startTime = null;
            // 打印日志
            if (verbose) {
                startTime = System.currentTimeMillis();
                JulLog.debug("post event[type={}] start.", event.getClass());
            }
            List<EventSubscriber> subscribers = this.register.getSubscribers(event);
            this.dispatcher.post(event, subscribers);
            // 打印日志
            if (startTime != null) {
                long endTime = System.currentTimeMillis();
                JulLog.debug("post event:[type={}] finish, cost:{}ms.", event.getClass(), (endTime - startTime));
            }
        } catch (Exception ex) {
            if (this.exceptionHandler != null) {
                this.exceptionHandler.accept(ex);
            } else {
                ex.printStackTrace();
            }
        }
    }

}
