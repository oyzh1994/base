package cn.oyzh.common.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 倒计数闭锁，等待时忽略中断异常
 *
 * @author oyzh
 * @since 2024-11-08
 */
public class DownLatch extends CountDownLatch {

    /**
     * 构造闭锁实例
     *
     * @param count 计数值
     */
    public DownLatch(int count) {
        super(count);
    }

    @Override
    public void await() {
        try {
            super.await();
        } catch (InterruptedException ignore) {
            // ex.printStackTrace();
        }
    }

    /**
     * 在指定时间内等待计数归零
     *
     * @param timeout 超时时间，单位毫秒
     * @return 是否在超时前计数归零
     */
    public boolean await(int timeout) {
        try {
            return super.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 创建计数为1的闭锁
     *
     * @return 闭锁对象
     */
    public static DownLatch of() {
        return of(1);
    }

    /**
     * 创建指定计数的闭锁
     *
     * @param count 计数值
     * @return 闭锁对象
     */
    public static DownLatch of(int count) {
        return new DownLatch(count);
    }
}
