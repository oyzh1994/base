package cn.oyzh.common.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author oyzh
 * @since 2024-11-08
 */
public class DownLatch extends CountDownLatch {

    public DownLatch(int count) {
        super(count);
    }

    @Override
    public void await() {
        try {
            super.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static DownLatch of() {
        return of(1);
    }

    public static DownLatch of(int count) {
        return new DownLatch(count);
    }
}
