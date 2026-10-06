package cn.oyzh.common.db;

/**
 * MySQL SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class MySqlSqlDialect extends AbstractSqlDialect {

    public MySqlSqlDialect() {
        super(SqlDatabase.MYSQL, SqlLexicalProfile.MYSQL);
    }
}
