package cn.oyzh.store.jdbc.param;

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

    /**
     * 添加排序参数
     *
     * @param name 列名称
     * @param type 排序类型
     */
    public void add(String name, String type) {
        this.add(new OrderByParam(name, type));
    }

    /**
     * 构建排序参数集合
     *
     * @param param 排序参数
     * @return 排序参数集合
     */
    public static OrderByParams of(OrderByParam param) {
        OrderByParams params = new OrderByParams();
        params.add(param);
        return params;
    }
}
