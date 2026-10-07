package cn.oyzh.common.thread;

/**
 * 可抛出异常的 Runnable
 *
 * @author oyzh
 * @since 2024-10-18
 */
public interface IRunnable {

    /**
     * 执行任务
     *
     * @throws Exception 执行过程中的异常
     */
    void run() throws Exception;

}
