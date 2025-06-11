package cn.oyzh.store.jdbc;

import java.util.ArrayList;

/**
 * orderBy参数集合
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class OrderByParams extends ArrayList<OrderByParam> {

    @Override
    public boolean add(OrderByParam orderByParam) {
        if (orderByParam == null) {
            return false;
        }
        return super.add(orderByParam);
    }

    public void add(String name, String type) {
        this.add(new OrderByParam(name, type));
    }

    public static OrderByParams of(OrderByParam param) {
        OrderByParams params = new OrderByParams();
        params.add(param);
        return params;
    }
}
