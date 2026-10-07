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
     * 池中对象的最小数量
     */
    private int minSize;

    /**
     * 池中对象的最大数量
     */
    private int maxSize;

    /**
     * 池中的对象列表
     */
    private List<T> list;

    /**
     * 没有可用对象时是否一直等待借用
     */
    private boolean waitingBorrow;

    /**
     * 构造池
     *
     * @param minSize 最小对象数量
     * @param maxSize 最大对象数量
     */
    public Pool(int minSize, int maxSize) {
        if (minSize < 0) {
            throw new InvalidParamException("minSize");
        }
        if (maxSize < 0) {
            throw new InvalidParamException("maxSize");
        }
        if (minSize > maxSize) {
            throw new InvalidParamException("minSize");
        }
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    /**
     * 是否一直等待借用
     *
     * @return 结果
     */
    public boolean isWaitingBorrow() {
        return waitingBorrow;
    }

    /**
     * 设置是否一直等待借用
     *
     * @param waitingBorrow 是否一直等待借用
     */
    public void setWaitingBorrow(boolean waitingBorrow) {
        this.waitingBorrow = waitingBorrow;
    }

    /**
     * 获取池中对象的最小数量
     *
     * @return 池中对象的最小数量
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * 设置池中对象的最小数量
     *
     * @param minSize 池中对象的最小数量
     */
    public void setMinSize(int minSize) {
        if (minSize > this.maxSize) {
            throw new InvalidParamException("minSize");
        }
        if (minSize < 0) {
            throw new InvalidParamException("minSize");
        }
        this.minSize = minSize;
    }

    /**
     * 获取池中对象的最大数量
     *
     * @return 池中对象的最大数量
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * 设置池中对象的最大数量
     *
     * @param maxSize 池中对象的最大数量
     */
    public void setMaxSize(int maxSize) {
        if (maxSize < this.minSize) {
            throw new InvalidParamException("maxSize");
        }
        if (maxSize < 0) {
            throw new InvalidParamException("maxSize");
        }
        this.maxSize = maxSize;
    }

    /**
     * 获取对象列表，列表未初始化时创建
     *
     * @return 对象列表
     */
    public List<T> list() {
        if (this.list == null) {
            this.list = new CopyOnWriteArrayList<>();
        }
        return this.list;
    }

    /**
     * 设置对象列表
     *
     * @param list 对象列表
     */
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
        JulLog.debug("object:{} is returned, size:{}" ,t, this.size());
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
           JulLog.debug("object:{} is borrowed, size:{}",obj,  this.size());
        }
        return obj;
    }
}
