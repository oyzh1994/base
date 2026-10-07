package cn.oyzh.common.db;

/**
 * 达梦数据库SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class DmSqlDialect extends AbstractSqlDialect {

    /**
     * 构造达梦数据库SQL方言实例
     */
    public DmSqlDialect() {
        super(SqlDatabase.DM, SqlLexicalProfile.DM);
    }
}
