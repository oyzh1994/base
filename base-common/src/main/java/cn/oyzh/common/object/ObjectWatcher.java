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
     * 引用
     */
    private final WeakReference<Object> reference;

    public ObjectWatcher(Object node, String name) {
        this.reference = new WeakReference<>(node);
        this.name = name == null ? node.getClass().getSimpleName() : name;
    }

    /**
     * 执行清除
     * @return 结果
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
     * 是否为空
     * @return 结果
     */
    protected boolean isEmpty() {
        return this.reference.get() == null;
    }

}
