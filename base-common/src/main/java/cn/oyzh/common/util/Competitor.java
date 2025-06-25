package cn.oyzh.common.util;

import cn.oyzh.common.thread.ThreadUtil;

/**
 * 竞争器
 *
 * @author oyzh
 * @since 2025-06-25
 */
public class Competitor {

    /**
     * 当前对象
     */
    private transient Object obj;

    /**
     * 尝试锁定
     *
     * @param obj 锁定对象
     * @return 结果
     */
    public boolean tryLock(Object obj) {
        while (this.obj != null) {
            ThreadUtil.sleep(5);
        }
        synchronized (this) {
            this.obj = obj;
        }
        return true;
    }

    /**
     * 释放
     *
     * @param obj 锁定对象
     * @return 结果
     */
    public boolean release(Object obj) {
        if (obj == this.obj) {
            synchronized (this) {
                this.obj = null;
            }
            return true;
        }
        return false;
    }
}
