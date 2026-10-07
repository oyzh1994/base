package cn.oyzh.store.jdbc.param;


/**
 * orderBy参数
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class OrderByParam {

    /**
     * 列名称
     */
    private String name;

    /**
     * 排序类型，默认升序
     */
    private String type = "asc";

    /**
     * 构造orderBy参数
     *
     * @param name 列名称
     */
    public OrderByParam(String name) {
        this.name = name;
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
     * 获取排序类型
     *
     * @return 排序类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置排序类型
     *
     * @param type 排序类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 构造orderBy参数
     *
     * @param name 列名称
     * @param type 排序类型
     */
    public OrderByParam(String name, String type) {
        this.name = name;
        this.type = type;
    }

}
