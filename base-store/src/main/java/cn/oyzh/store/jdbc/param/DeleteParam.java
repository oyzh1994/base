package cn.oyzh.store.jdbc.param;


/**
 * 删除参数
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class DeleteParam {

    /**
     * 删除条数限制
     */
    private Long limit;

    /**
     * 查询条件
     */
    private QueryParams queryParams;

//    private List<OrderByParam> orderByParams;

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

//    public void addQueryParams(QueryParams queryParams) {
//        if (this.queryParams == null) {
//            this.queryParams = new QueryParams<>();
//        }
//        this.queryParams.addAll(queryParams);
//    }

//    public void addOrderByParam(OrderByParam orderByParam) {
//        if (this.orderByParams == null) {
//            this.orderByParams = new ArrayList<>();
//        }
//        this.orderByParams.add(orderByParam);
//    }

    /**
     * 获取删除条数限制
     *
     * @return 删除条数限制
     */
    public Long getLimit() {
        return limit;
    }

    /**
     * 设置删除条数限制
     *
     * @param limit 删除条数限制
     */
    public void setLimit(Long limit) {
        this.limit = limit;
    }

    /**
     * 获取查询条件
     *
     * @return 查询条件
     */
    public QueryParams getQueryParams() {
        return queryParams;
    }

    /**
     * 设置查询条件
     *
     * @param queryParams 查询条件
     */
    public void setQueryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
    }

//    public List<OrderByParam> getOrderByParams() {
//        return orderByParams;
//    }
//
//    public void setOrderByParams(List<OrderByParam> orderByParams) {
//        this.orderByParams = orderByParams;
//    }
}
