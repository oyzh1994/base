package cn.oyzh.store.jdbc;

import lombok.Data;

/**
 * @author oyzh
 * @since 2024-09-26
 */
@Data
public class OrderByParam {

    private String name;

    private String type = "asc";

    public OrderByParam(String name) {
        this.name = name;
    }

    public OrderByParam(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
