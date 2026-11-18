package cn.oyzh.common.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void paginatesOracleAcrossServerVersions() {
        OracleSqlDialect dialect = (OracleSqlDialect) SqlDialects.get(SqlDatabase.ORACLE);

        assertTrue(dialect.supportsOffsetFetch("Oracle Database 12c Enterprise Edition Release 12.2.0.1.0"));
        assertTrue(dialect.supportsOffsetFetch("Oracle Database 19c Enterprise Edition Release 19.0.0.0.0"));
        assertTrue(dialect.supportsOffsetFetch("12.1.0.2.0"));
        assertTrue(dialect.supportsOffsetFetch("19c"));

        String modern = dialect.paginate("SELECT * FROM users;", 20, 10, true);
        assertTrue(modern.endsWith("OFFSET 20 ROWS FETCH NEXT 10 ROWS ONLY"));

        String legacy = dialect.paginate("SELECT * FROM users", 20, 10, false);
        assertTrue(legacy.contains("ROWNUM <= 30"));
        assertTrue(legacy.contains(OracleSqlDialect.PAGE_ROWNUM_ALIAS + " > 20"));
    }
}
