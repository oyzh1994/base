package cn.oyzh.common.thread;

/**
 * 线程扩展，中断后仍能被持续判断为已中断
 *
 * @author oyzh
 * @since 2024/6/7
 */
public class ThreadExt extends Thread {

    /**
     * 是否已结束
     */
    private Boolean finish;

    /**
     * 构造线程扩展实例
     *
     * @param task 任务
     */
    public ThreadExt(Runnable task) {
        super(task);
    }

    @Override
    public void interrupt() {
        this.finish = true;
        super.interrupt();
    }

    @Override
    public boolean isInterrupted() {
        return (this.finish != null && this.finish) || super.isInterrupted();
    }
}
