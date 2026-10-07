package cn.oyzh.common.db;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * SQL拆分测试
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class SqlSplitterTest {

    @Test
    public void splitsCommonStatementsAndIgnoresLiteralSemicolons() {
        String sql = """
                INSERT INTO t(name, note) VALUES ('a;b', 'it''s; ok');
                UPDATE t SET name = 'slash\\;value' WHERE id = 1;
                DELETE FROM t WHERE note = 'last; value';
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.MYSQL);

        assertEquals(3, statements.size());
        assertTrue(statements.get(0).startsWith("INSERT INTO"));
        assertTrue(statements.get(1).startsWith("UPDATE"));
        assertTrue(statements.get(2).startsWith("DELETE FROM"));
    }

    @Test
    public void splitsNestedSubqueriesWithoutBreakingParentheses() {
        String sql = """
                SELECT id
                FROM (
                    SELECT id
                    FROM (
                        SELECT id FROM t WHERE note = 'a;b' AND id IN (SELECT user_id FROM u)
                    ) nested_2
                    WHERE enabled = 1
                ) nested_1;
                UPDATE t SET enabled = 0 WHERE id = 2;
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.POSTGRESQL);

        assertEquals(2, statements.size());
        assertTrue(statements.get(0).contains("SELECT id FROM t"));
        assertTrue(statements.get(0).contains("(SELECT user_id FROM u)"));
        assertTrue(statements.get(1).startsWith("UPDATE"));
    }

    @Test
    public void ignoresLineCommentsAndNestedBlockComments() {
        String sql = """
                /* outer; /* nested; */ still outer; */ SELECT '(literal; value)';
                -- comment; with semicolon
                UPDATE t SET value = 1;
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.POSTGRESQL);

        assertEquals(2, statements.size());
        assertTrue(statements.get(0).startsWith("/* outer;"));
        assertTrue(statements.get(0).contains("'(literal; value)'"));
        assertTrue(statements.get(1).contains("-- comment; with semicolon"));
    }

    @Test
    public void supportsMySqlDelimiterDirectiveAndProcedureBody() {
        String sql = """
                DELIMITER $$
                CREATE PROCEDURE test_proc()
                BEGIN
                    SELECT 'value; $$ literal';
                    UPDATE t SET value = 2;
                END$$
                DELIMITER ;
                SELECT 3;
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.MYSQL);

        assertEquals(2, statements.size());
        assertFalse(statements.get(0).contains("DELIMITER"));
        assertFalse(statements.get(0).endsWith("$$"));
        assertTrue(statements.get(0).contains("SELECT 'value; $$ literal'"));
        assertEquals("SELECT 3", statements.get(1));
    }

    @Test
    public void preservesDelimiterTextInsideLiteralsAndComments() {
        String sql = """
                /*
                DELIMITER $$
                */
                SELECT 'DELIMITER $$; value;' AS value;
                UPDATE t SET value = 2;
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.MYSQL);

        assertEquals(2, statements.size());
        assertTrue(statements.get(0).contains("DELIMITER $$"));
        assertTrue(statements.get(0).contains("'DELIMITER $$; value;'"));
        assertTrue(statements.get(1).startsWith("UPDATE"));
    }

    @Test
    public void supportsEscapedDoubleQuotedLiteral() {
        String sql = "SELECT \"a\\\\\\\"; b\"; SELECT 2;";

        List<String> statements = SqlUtil.split(sql, SqlDatabase.MYSQL);

        assertEquals(2, statements.size());
        assertTrue(statements.get(0).contains("\\\""));
        assertEquals("SELECT 2", statements.get(1));
    }

    @Test
    public void supportsPostgreSqlDollarQuotedBodies() {
        String sql = """
                CREATE FUNCTION test_func() RETURNS void AS $outer$
                BEGIN
                    PERFORM nested($inner$ SELECT ';'; $inner$);
                END;
                $outer$;
                SELECT 2;
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.POSTGRESQL);

        assertEquals(2, statements.size());
        assertTrue(statements.get(0).startsWith("CREATE FUNCTION"));
        assertTrue(statements.get(0).contains("$inner$ SELECT ';'"));
        assertEquals("SELECT 2", statements.get(1));
    }

    @Test
    public void supportsOracleQQuotesAndSlashTerminator() {
        String sql = """
                CREATE OR REPLACE PROCEDURE test_proc AS
                    value VARCHAR2(100);
                BEGIN
                    value := q'[a;b]' || q'{c;d}' || q'<e;f>' || q'!g;h!';
                    INSERT INTO t VALUES (value);
                END;
                /
                SELECT 1;
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.ORACLE);

        assertEquals(2, statements.size());
        assertTrue(statements.get(0).contains("q'[a;b]'"));
        assertTrue(statements.get(0).contains("q'!g;h!'"));
        assertTrue(statements.get(0).endsWith("END;"));
        assertEquals("SELECT 1", statements.get(1));
    }

    @Test
    public void supportsSqlServerGoBatches() {
        String sql = """
                CREATE PROCEDURE test_proc AS
                BEGIN
                    SELECT 'a;b';
                    UPDATE t SET value = 1;
                END;
                GO -- next batch
                SELECT 2;
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.SQL_SERVER);

        assertEquals(2, statements.size());
        assertTrue(statements.get(0).contains("SELECT 'a;b'"));
        assertFalse(statements.get(0).contains("GO"));
        assertEquals("SELECT 2", statements.get(1));
    }

    @Test
    public void supportsDmPlSqlAndQQuotes() {
        String sql = """
                CREATE OR REPLACE PROCEDURE test_proc AS
                    value VARCHAR2(100);
                BEGIN
                    SELECT q'{a;b}' INTO value FROM dual;
                    INSERT INTO t VALUES (value);
                END;
                /
                SELECT 1;
                """;

        List<String> statements = SqlUtil.split(sql, SqlDatabase.DM);

        assertEquals(2, statements.size());
        assertTrue(statements.get(0).contains("q'{a;b}'"));
        assertTrue(statements.get(0).endsWith("END;"));
        assertEquals("SELECT 1", statements.get(1));
    }

    @Test
    public void ignoresEmptyAndTrailingSegments() {
        List<String> statements = SqlUtil.split(" ;;\nSELECT 1;;\n ", SqlDatabase.ANSI);

        assertEquals(List.of("SELECT 1"), statements);
    }
}
