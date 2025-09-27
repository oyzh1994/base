package cn.oyzh.store.jdbc.param;


/**
 * 分页参数
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class PageParam {

    private long limit;

    private long start;

    private final QueryParams queryParams = new QueryParams();

    public PageParam() {

    }

    public PageParam(final long limit, final long start) {
        this.limit = limit;
        this.start = start;
    }

    public void addQueryParam(QueryParam queryParam) {
        this.queryParams.add(queryParam);
    }

    public QueryParams getQueryParams() {
        return this.queryParams;
    }

    public long getLimit() {
        return this.limit;
    }

    public long getStart() {
        return this.start;
    }
}
