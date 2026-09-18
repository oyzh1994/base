package cn.oyzh.pkg.pack;

import cn.oyzh.pkg.PackOrder;
import cn.oyzh.pkg.PreHandler;
import cn.oyzh.pkg.config.PackConfig;

/**
 * @author oyzh
 * @since 2024/6/14
 */
public class StartHandler implements PreHandler {

    private int order = PackOrder.ORDER_MAX;

    public int order() {
        return order;
    }

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
