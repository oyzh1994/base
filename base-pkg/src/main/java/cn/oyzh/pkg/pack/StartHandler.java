package cn.oyzh.pkg.pack;

import cn.oyzh.pkg.PackOrder;
import cn.oyzh.pkg.PreHandler;
import cn.oyzh.pkg.config.PackConfig;

/**
 * 起始处理器，记录打包开始时间
 *
 * @author oyzh
 * @since 2024/6/14
 */
public class StartHandler implements PreHandler {

    private int order = PackOrder.ORDER_MAX;

    /**
     * 获取排序
     *
     * @return 排序
     */
    public int order() {
        return order;
    }

    /**
     * 设置排序
     *
     * @param order 排序
     */
    public void order(int order) {
        this.order = order;
    }

    @Override
    public boolean unique() {
        return true;
    }

    @Override
    public void handle(PackConfig packConfig) throws Exception {
        packConfig.putProperty("startTime", System.currentTimeMillis());
    }

    @Override
    public String name() {
        return "起始处理";
    }
}
