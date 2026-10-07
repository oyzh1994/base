package cn.oyzh.common.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * SQL方言测试
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class SqlDialectTest {

    @Test
    public void resolvesDatabaseAliases() {
        assertEquals(SqlDatabase.POSTGRESQL, SqlDialects.parse("Postgres"));
        assertEquals(SqlDatabase.SQL_SERVER, SqlDialects.parse("sql_server"));
        assertEquals(SqlDatabase.DM, SqlDialects.parse("达梦"));
        assertEquals(SqlDatabase.ANSI, SqlDialects.parse(null));
    }

    @Test
    public void providesEveryDialect() {
        for (SqlDatabase database : SqlDatabase.values()) {
            SqlDialect dialect = SqlDialects.get(database);
            assertNotNull(dialect);
            assertNotNull(dialect.getSplitter());
            assertNotNull(dialect.getFormatter());
            assertEquals(database, dialect.getDatabase());
        }
    }
}
