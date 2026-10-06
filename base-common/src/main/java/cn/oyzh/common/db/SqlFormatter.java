package cn.oyzh.common.db;

/**
 * SQL美化器
 *
 * @author oyzh
 * @since 2026/10/6
 */
public interface SqlFormatter {

    /**
     * 美化单条SQL语句
     *
     * @param sql SQL语句
     * @return 美化后的SQL
     */
    String format(String sql);
}
