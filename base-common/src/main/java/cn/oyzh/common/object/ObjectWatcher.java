package cn.oyzh.common.object;

import cn.oyzh.common.util.StringUtil;

import java.lang.ref.WeakReference;

/**
 * 对象观察者
 *
 * @author oyzh
 * @since 2025-12-05
 */
public class ObjectWatcher {

    /**
     * 名称
     */
    private final String name;

    /**
     * 被观察对象的弱引用
     */
    private final WeakReference<Object> reference;

    /**
     * 构造对象观察者
     *
     * @param node 被观察对象
     * @param name 观察者名称，为空时使用被观察对象的简单类名
     */
    public ObjectWatcher(Object node, String name) {
        this.reference = new WeakReference<>(node);
        this.name = name == null ? node.getClass().getSimpleName() : name;
    }

    /**
     * 获取被观察对象
     *
     * @return 被观察对象，已被回收时返回null
     */
    public Object getObject() {
        return this.reference.get();
    }

    /**
     * 执行清除，若被观察对象已被回收则清空弱引用
     *
     * @return 清除成功返回true，否则返回false
     */
    protected boolean doClear() {
        if (this.isEmpty()) {
            System.out.println(this.name + " is null ---------");
            this.reference.clear();
            return true;
        }
        return false;
    }

    /**
     * 判断被观察对象是否已被回收
     *
     * @return 已被回收返回true，否则返回false
     */
    protected boolean isEmpty() {
        return this.reference.get() == null;
    }

}
