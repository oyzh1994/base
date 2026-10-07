package cn.oyzh.store.jdbc;


/**
 * 主键字段
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class PrimaryKeyColumn {

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列数据
     */
    private Object columnData;

    /**
     * 构造主键字段
     *
     * @param columnName 列名称
     * @param columnData 列数据
     */
    public PrimaryKeyColumn(String columnName, Object columnData) {
        this.columnName = columnName;
        this.columnData = columnData;
    }

    /**
     * 获取列名称
     *
     * @return 列名称
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 设置列名称
     *
     * @param columnName 列名称
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * 获取列数据
     *
     * @return 列数据
     */
    public Object getColumnData() {
        return columnData;
    }

    /**
     * 设置列数据
     *
     * @param columnData 列数据
     */
    public void setColumnData(Object columnData) {
        this.columnData = columnData;
    }
}
