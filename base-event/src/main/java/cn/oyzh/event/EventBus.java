package cn.oyzh.event;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;

import java.util.List;
import java.util.function.Consumer;

/**
 * 事件总线
 *
 * @author oyzh
 * @since 2024-11-13
 */
public class EventBus {

    /**
     * 异常处理器
     */
    protected Consumer<Exception> exceptionHandler;

    /**
     * 事件注册器
     */
    protected final EventRegister register = new EventRegister();

    /**
     * 事件调度器
     */
    protected final EventDispatcher dispatcher = new EventDispatcher();

    /**
     * 注册监听器
     *
     * @param listener 监听器
     */
    public void register(Object listener) {
        try {
            this.register.register(listener);
        } catch (Exception ex) {
            if (this.exceptionHandler != null) {
                this.exceptionHandler.accept(ex);
            } else {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 取消注册监听器
     *
     * @param listener 监听器
     */
    public void unregister(Object listener) {
        try {
            this.register.unregister(listener);
        } catch (Exception ex) {
            if (this.exceptionHandler != null) {
                this.exceptionHandler.accept(ex);
            } else {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 发送事件
     *
     * @param event       事件
     * @param config      配置
     * @param delayMillis 延迟时间
     * @param <C>         范型
     */
    public <C extends EventConfig> void post(Object event, C config, Integer delayMillis) {
        if (event != null) {
            boolean verbose = config != null && config.isVerbose();
            boolean delay = delayMillis != null && delayMillis > 0;
            boolean async = config != null && config.isAsync();
            // 执行函数
            Runnable func = () -> this.doEventPost(event, verbose);
            // 延迟、异步
            if (delay && async) {
                TaskManager.startDelay(func, delayMillis);
            } else if (delay) {// 延迟
                TaskManager.startDelay(func, delayMillis);
            } else if (async) {// 异步
                TaskManager.startSync(func);
            } else {// 正常执行
                func.run();
            }
        }
    }

    /**
     * 执行事件发送
     *
     * @param event   事件
     * @param verbose 是否详细信息
     */
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

    /**
     * 设置异常处理器
     *
     * @param exceptionHandler 异常处理器
     */
    public void exceptionHandler(Consumer<Exception> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
}
