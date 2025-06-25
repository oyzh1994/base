package cn.oyzh.common.util;

import cn.oyzh.common.exception.InvalidParamException;
import cn.oyzh.common.thread.ThreadUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 竞争器
 *
 * @author oyzh
 * @since 2025-06-25
 */
public class Competitor {

    /**
     * 允许的最大竞争数量
     */
    private final int max;

    /**
     * 竞争对象列表
     */
    private final List<Object> list;

    public Competitor() {
        this(1);
    }

    public Competitor(int max) {
        if (max <= 0) {
            throw new InvalidParamException("max");
        }
        this.max = max;
        this.list = new CopyOnWriteArrayList<>();
    }

    /**
     * 尝试锁定
     *
     * @param obj 锁定对象
     * @return 结果
     */
    public boolean tryLock(Object obj) {
        while (this.list.size() >= this.max) {
            ThreadUtil.sleep(5);
        }
        synchronized (this.list) {
            this.list.add(obj);
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
        if (this.list.contains(obj)) {
            synchronized (this.list) {
                this.list.remove(obj);
            }
            return true;
        }
        return false;
    }
}
