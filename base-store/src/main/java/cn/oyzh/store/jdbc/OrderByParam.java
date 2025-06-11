package cn.oyzh.store.jdbc;


/**
 * orderBy参数
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class OrderByParam {

    private String name;

    private String type = "asc";

    public OrderByParam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrderByParam(String name, String type) {
        this.name = name;
        this.type = type;
    }

}
