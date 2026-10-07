package cn.oyzh.store.jdbc.param;


/**
 * 查询参数
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class QueryParam {

    /**
     * 列名称
     */
    private String name;

    /**
     * 数据值
     */
    private Object data;

    /**
     * 运算符，默认等于
     */
    private String operator = "=";

    /**
     * 构造查询参数
     */
    public QueryParam() {

    }

    /**
     * 构造查询条件，数据为空时使用 is null
     *
     * @param name 列名称
     * @param data 数据值
     */
    public QueryParam(String name, Object data) {
        this.name = name;
        this.data = data;
        if (data == null) {
            this.operator = " is null";
        }
    }

    /**
     * 构造查询条件
     *
     * @param name     列名称
     * @param data     数据值
     * @param operator 运算符
     */
    public QueryParam(String name, Object data, String operator) {
        this.name = name;
        this.data = data;
        this.operator = operator;
    }

    /**
     * 构建查询条件，数据为空时使用 is null
     *
     * @param name 列名称
     * @param data 数据值
     * @return 查询条件
     */
    public static QueryParam of(String name, Object data) {
        return new QueryParam(name, data);
    }

    /**
     * 构建查询条件
     *
     * @param name     列名称
     * @param data     数据值
     * @param operator 运算符
     * @return 查询条件
     */
    public static QueryParam of(String name, Object data, String operator) {
        return new QueryParam(name, data, operator);
    }

    /**
     * 获取列名称
     *
     * @return 列名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置列名称
     *
     * @param name 列名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取数据值
     *
     * @return 数据值
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置数据值
     *
     * @param data 数据值
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 获取运算符
     *
     * @return 运算符
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置运算符
     *
     * @param operator 运算符
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
}
