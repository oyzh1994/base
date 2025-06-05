package cn.oyzh.store.jdbc;


/**
 * 主键字段
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class PrimaryKeyColumn {

    private String columnName;

    private Object columnData;

    public PrimaryKeyColumn(String columnName, Object columnData) {
        this.columnName = columnName;
        this.columnData = columnData;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getColumnData() {
        return columnData;
    }

    public void setColumnData(Object columnData) {
        this.columnData = columnData;
    }
}
