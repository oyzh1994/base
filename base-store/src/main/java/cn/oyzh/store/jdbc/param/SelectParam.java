package cn.oyzh.store.jdbc.param;


import java.util.ArrayList;
import java.util.List;

/**
 * 查询参数，包含查询列、查询条件、排序与分页
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class SelectParam {

//    public static final SelectParam EMPTY = new SelectParam();

    /**
     * 查询条数限制
     */
    private Long limit;

    /**
     * 查询偏移量
     */
    private Long offset;

    /**
     * 查询条件
     */
    private QueryParams queryParams;

    /**
     * 查询列
     */
    private List<String> queryColumns;

    /**
     * 排序参数
     */
    private OrderByParams orderByParams;

    /**
     * 添加排序参数
     *
     * @param orderByParam 排序参数
     */
    public void addOrderByParam(OrderByParam orderByParam) {
        if (this.orderByParams == null) {
            this.orderByParams = new OrderByParams();
        }
        this.orderByParams.add(orderByParam);
    }

    /**
     * 添加查询条件
     *
     * @param queryParam 查询条件
     */
    public void addQueryParam(QueryParam queryParam) {
        if (this.queryParams == null) {
            this.queryParams = new QueryParams();
        }
        this.queryParams.add(queryParam);
    }

    /**
     * 添加查询列
     *
     * @param column 查询列
     */
    public void addQueryColumn(String column) {
        if (this.queryColumns == null) {
            this.queryColumns = new ArrayList<>();
        }
        this.queryColumns.add(column);
    }

    /**
     * 批量添加查询列
     *
     * @param columns 查询列
     */
    public void addQueryColumns(String... columns) {
        if (columns != null) {
            for (String column : columns) {
                this.addQueryColumn(column);
            }
        }
    }

    /**
     * 获取查询列
     *
     * @return 查询列
     */
    public List<String> getQueryColumns() {
        return this.queryColumns;
    }

    /**
     * 获取查询条件
     *
     * @return 查询条件
     */
    public QueryParams getQueryParams() {
        return this.queryParams;
    }

    /**
     * 获取排序参数
     *
     * @return 排序参数
     */
    public OrderByParams getOrderByParams() {
        return orderByParams;
    }

    /**
     * 设置排序参数
     *
     * @param orderByParams 排序参数
     */
    public void setOrderByParams(OrderByParams orderByParams) {
        this.orderByParams = orderByParams;
    }

    /**
     * 获取查询条数限制
     *
     * @return 查询条数限制
     */
    public Long getLimit() {
        return limit;
    }

    /**
     * 设置查询条数限制
     *
     * @param limit 查询条数限制
     */
    public void setLimit(Long limit) {
        this.limit = limit;
    }

    /**
     * 获取查询偏移量
     *
     * @return 查询偏移量
     */
    public Long getOffset() {
        return offset;
    }

    /**
     * 设置查询偏移量
     *
     * @param offset 查询偏移量
     */
    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
