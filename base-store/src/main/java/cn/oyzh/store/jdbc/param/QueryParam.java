package cn.oyzh.store.jdbc.param;


/**
 * 查询参数
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class QueryParam {

    private String name;

    private Object data;

    private String operator = "=";

    public QueryParam() {

    }

    public QueryParam(String name, Object data) {
        this.name = name;
        this.data = data;
        if (data == null) {
            this.operator = " is null";
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
