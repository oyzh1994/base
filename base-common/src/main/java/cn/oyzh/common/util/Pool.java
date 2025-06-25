package cn.oyzh.common.util;

import cn.oyzh.common.exception.InvalidParamException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 迟
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

    public Pool(int minSize, int maxSize) {
        this.setMaxSize(maxSize);
        this.setMinSize(minSize);
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

    public boolean isEmpty() {
        return CollectionUtil.isEmpty(this.list);
    }

    public int size() {
        return CollectionUtil.size(this.list);
    }

    /**
     * 初始化
     *
     * @throws Exception 异常
     */
    protected synchronized void init() throws Exception {
        while (this.size() < this.getMinSize()) {
            this.list().add(this.newObject());
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
        if (t == null || this.list == null || this.size() >= this.getMaxSize()) {
            return;
        }
        this.list.add(t);
        System.out.println("object:" + t + " is returned, size:" + this.size());
    }

    /**
     * 借用对象
     *
     * @return 对象
     */
    public T borrowObject() {
        try {
            if (this.isEmpty()) {
                this.init();
            }
            if (!this.isEmpty()) {
                return this.list().removeFirst();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
