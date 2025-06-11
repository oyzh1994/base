package cn.oyzh.store.jdbc;


import java.util.ArrayList;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-09-26
 */
public class SelectParam {

    public static final SelectParam EMPTY = new SelectParam();

    private Long limit;

    private Long offset;

    private QueryParams queryParams;

    private List<String> queryColumns;

    private OrderByParams orderByParams;

    public void addOrderByParam(OrderByParam orderByParam) {
        if (this.orderByParams == null) {
            this.orderByParams = new OrderByParams();
        }
        this.orderByParams.add(orderByParam);
    }

    public void addQueryParam(QueryParam queryParam) {
        if (this.queryParams == null) {
            this.queryParams = new QueryParams();
        }
        this.queryParams.add(queryParam);
    }

    public void addQueryColumn(String column) {
        if (this.queryColumns == null) {
            this.queryColumns = new ArrayList<>();
        }
        this.queryColumns.add(column);
    }

    public void addQueryColumns(String... columns) {
        if (columns != null) {
            for (String column : columns) {
                this.addQueryColumn(column);
            }
        }
    }

    public List<String> getQueryColumns() {
        return this.queryColumns;
    }

    public QueryParams getQueryParams() {
        return this.queryParams;
    }

    public OrderByParams getOrderByParams() {
        return orderByParams;
    }

    public void setOrderByParams(OrderByParams orderByParams) {
        this.orderByParams = orderByParams;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
