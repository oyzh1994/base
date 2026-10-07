package cn.oyzh.store.jdbc.param;

import java.util.ArrayList;

/**
 * 查询参数列表
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class QueryParams extends ArrayList<QueryParam> {

    @Override
    public boolean add(QueryParam queryParam) {
        if (queryParam == null) {
            return false;
        }
        return super.add(queryParam);
    }

    /**
     * 添加查询条件
     *
     * @param name 列名称
     * @param data 数据值
     */
    public void add(String name, Object data) {
        this.add(new QueryParam(name, data));
    }

    /**
     * 添加查询条件
     *
     * @param name     列名称
     * @param data     数据值
     * @param operator 运算符
     */
    public void add(String name, Object data, String operator) {
        this.add(new QueryParam(name, data, operator));
    }

    /**
     * 构建查询条件集合
     *
     * @param param 查询条件
     * @return 查询条件集合
     */
    public static QueryParams of(QueryParam param) {
        QueryParams params = new QueryParams();
        params.add(param);
        return params;
    }
}
