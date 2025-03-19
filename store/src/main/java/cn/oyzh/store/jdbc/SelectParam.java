package cn.oyzh.store.jdbc;


import java.util.ArrayList;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-09-26
 */
public class SelectParam {

    public static final SelectParam EMPTY = new SelectParam();

    private List<String> queryColumns;

    private final QueryParams queryParams = new QueryParams();

    public void addQueryParam(QueryParam queryParam) {
        this.queryParams.add(queryParam);
    }

    public void addQueryColumn(String column) {
        if (this.queryColumns == null) {
            this.queryColumns = new ArrayList<>();
        }
        this.queryColumns.add(column);
    }

    public SelectParam() {

    }

    public List<String> getQueryColumns() {
        return this.queryColumns;
    }

    public QueryParams getQueryParams() {
        return this.queryParams;
    }
}
