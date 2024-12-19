package cn.oyzh.store.jdbc;

import lombok.Data;

@Data
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
}
