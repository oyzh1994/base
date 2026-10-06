package cn.oyzh.common.db;

/**
 * MariaDB SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class MariaDbSqlDialect extends AbstractSqlDialect {

    public MariaDbSqlDialect() {
        super(SqlDatabase.MARIADB, SqlLexicalProfile.MYSQL);
    }
}
