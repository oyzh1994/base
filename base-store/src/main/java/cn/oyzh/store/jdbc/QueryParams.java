package cn.oyzh.store.jdbc;

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

    public void add(String name, Object data) {
        this.add(new QueryParam(name, data));
    }

    public void add(String name, Object data, String operator) {
        this.add(new QueryParam(name, data));
    }

    public static QueryParams of(QueryParam param) {
        QueryParams params = new QueryParams();
        params.add(param);
        return params;
    }
}
