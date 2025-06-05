package cn.oyzh.store.jdbc;


/**
 * 删除参数
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class DeleteParam {

    private Long limit;

    private QueryParams queryParams;

//    private List<OrderByParam> orderByParams;

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

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

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
