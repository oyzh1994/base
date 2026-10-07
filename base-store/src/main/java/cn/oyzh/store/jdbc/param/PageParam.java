package cn.oyzh.store.jdbc.param;


/**
 * 分页参数
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class PageParam {

    /**
     * 每页条数
     */
    private long limit;

    /**
     * 起始位置
     */
    private long start;

    /**
     * 查询条件
     */
    private final QueryParams queryParams = new QueryParams();

    /**
     * 构造分页参数
     */
    public PageParam() {

    }

    /**
     * 构造分页参数
     *
     * @param limit 每页条数
     * @param start 起始位置
     */
    public PageParam(final long limit, final long start) {
        this.limit = limit;
        this.start = start;
    }

    /**
     * 添加查询条件
     *
     * @param queryParam 查询条件
     */
    public void addQueryParam(QueryParam queryParam) {
        this.queryParams.add(queryParam);
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
     * 获取每页条数
     *
     * @return 每页条数
     */
    public long getLimit() {
        return this.limit;
    }

    /**
     * 获取起始位置
     *
     * @return 起始位置
     */
    public long getStart() {
        return this.start;
    }
}
