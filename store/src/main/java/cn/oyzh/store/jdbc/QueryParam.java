package cn.oyzh.store.jdbc;

import lombok.Data;

/**
 * @author oyzh
 * @since 2024-09-26
 */
@Data
public class QueryParam {

    private String name;

    private Object data;

    private String operator = "=";

    public QueryParam() {

    }

    public QueryParam(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public QueryParam(String name, Object data, String operator) {
        this.name = name;
        this.data = data;
        this.operator = operator;
    }

    public static QueryParam of(String name, Object data) {
        return new QueryParam(name, data);
    }

    public static QueryParam of(String name, Object data, String operator) {
        return new QueryParam(name, data, operator);
    }
}
