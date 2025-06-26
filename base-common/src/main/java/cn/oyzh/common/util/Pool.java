package cn.oyzh.common.util;

import cn.oyzh.common.exception.InvalidParamException;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.ThreadUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 池
 *
 * @param <T> 类型
 * @author oyzh
 * @since 2025-06-25
 */
public abstract class Pool<T> {

    /**
     * 最小值
     */
    private int minSize;

    /**
     * 最大值
     */
    private int maxSize;

    /**
     * 对象列表
     */
    private List<T> list;

    /**
     * 如果没有可用对象，则一直等待接用
     */
    private boolean waitingBorrow;

    public Pool(int minSize, int maxSize) {
        this.setMaxSize(maxSize);
        this.setMinSize(minSize);
    }

    public boolean isWaitingBorrow() {
        return waitingBorrow;
    }

    public void setWaitingBorrow(boolean waitingBorrow) {
        this.waitingBorrow = waitingBorrow;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        if (minSize > this.maxSize) {
            throw new InvalidParamException("minSize");
        }
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        if (maxSize < this.minSize) {
            throw new InvalidParamException("maxSize");
        }
        this.maxSize = maxSize;
    }

    public List<T> list() {
        if (this.list == null) {
            this.list = new CopyOnWriteArrayList<>();
        }
        return this.list;
    }

    public void list(List<T> list) {
        this.list = list;
    }

    /**
     * 清除列表
     */
    public void clear() {
        if (this.list != null) {
            this.list.clear();
        }
    }

    /**
     * 列表是否为空
     *
     * @return 结果
     */
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(this.list);
    }

    /**
     * 获取列表长度
     *
     * @return 列表长度
     */
    public int size() {
        return CollectionUtil.size(this.list);
    }

    /**
     * 池子是否满了
     *
     * @return 结果
     */
    public boolean isFull() {
        return this.size() >= this.getMaxSize();
    }

    /**
     * 初始化
     */
    protected synchronized void init() {
        int failCount = 0;
        while (this.size() < this.getMinSize()) {
            T obj = null;
            try {
                obj = this.newObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (obj == null) {
                JulLog.warn("newObject is null");
                if (failCount++ > 3) {
                    break;
                }
                continue;
            }
            this.list().add(obj);
        }
    }

    /**
     * 创建新对象
     *
     * @return 新对象
     * @throws Exception 异常
     */
    protected abstract T newObject() throws Exception;

    /**
     * 归还对象
     *
     * @param t 对象
     */
    public void returnObject(T t) {
        if (t == null || this.list == null || this.isFull()) {
            return;
        }
        synchronized (this.listLock) {
            this.list.add(t);
        }
        System.out.println("object:" + t + " is returned, size:" + this.size());
    }

    /**
     * 数据锁
     */
    private final Object listLock = new Object();

    /**
     * 借用对象
     *
     * @return 对象
     */
    public T borrowObject() {
        T obj = null;
        try {
            synchronized (this.listLock) {
                if (this.isEmpty()) {
                    this.init();
                }
                if (this.isEmpty() && this.waitingBorrow) {
                    int count = 0;
                    JulLog.info("waiting for borrow...");
                    while (this.isEmpty()) {
                        ThreadUtil.sleep(5);
                        if (count++ > 1000) {
                            break;
                        }
                    }
                    if (this.isEmpty()) {
                        JulLog.warn("borrow fail...");
                    }
                }
                if (!this.isEmpty()) {
                    obj = this.list().removeFirst();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("object:" + obj + " is borrowed, size:" + this.size());
        }
        return obj;
    }
}
