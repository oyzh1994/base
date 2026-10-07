package cn.oyzh.common.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * SQL美化测试
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class SqlFormatterTest {

    @Test
    public void formatsClausesAndPreservesLiteralText() {
        String sql = "select a,b from t where a='x;y' and b='it''s' or c is null";

        String formatted = SqlUtil.format(sql, SqlDatabase.POSTGRESQL);

        assertTrue(formatted.startsWith("SELECT"));
        assertTrue(formatted.contains("\nFROM t"));
        assertTrue(formatted.contains("\nWHERE a = 'x;y'"));
        assertTrue(formatted.contains("b = 'it''s'"));
        assertTrue(formatted.contains("\n    OR c IS NULL"));
        assertFalse(formatted.contains("select"));
    }

    @Test
    public void formatsNestedSubqueries() {
        String sql = "select id from t where id in (select user_id from u where enabled=1)";

        String formatted = SqlUtil.format(sql, SqlDatabase.DM);

        assertTrue(formatted.contains("IN ("));
        assertTrue(formatted.contains("    SELECT user_id"));
        assertTrue(formatted.contains("    FROM u"));
        assertTrue(formatted.contains("    WHERE enabled = 1"));
    }

    @Test
    public void formatsMultipleStatements() {
        String sql = "select 1 from dual; update t set a=1 where id=2;";

        String formatted = SqlUtil.format(sql, SqlDatabase.ORACLE);

        assertTrue(formatted.contains("SELECT 1"));
        assertTrue(formatted.contains(";\n\nUPDATE t"));
        assertTrue(formatted.endsWith("WHERE id = 2"));
    }

    @Test
    public void preservesDollarQuotedAndOracleQuotedLiterals() {
        String postgres = "select $inner$a;b$inner$ as value";
        String oracle = "select q'[a;b]' as value from dual";

        assertTrue(SqlUtil.format(postgres, SqlDatabase.POSTGRESQL).contains("$inner$a;b$inner$"));
        assertTrue(SqlUtil.format(oracle, SqlDatabase.ORACLE).contains("q'[a;b]'"));
    }

    @Test
    public void formatsStringLiteralPrefixesWithoutInsertingSpaces() {
        String formatted = SqlUtil.format(
                "select N'a', E'b', X'12', _utf8'c' from t", SqlDatabase.POSTGRESQL);

        assertTrue(formatted.contains("N'a'"));
        assertTrue(formatted.contains("E'b'"));
        assertTrue(formatted.contains("X'12'"));
        assertTrue(formatted.contains("_utf8'c'"));
    }

    @Test
    public void formatsDmProcedureWithoutChangingBodyLiterals() {
        String sql = """
                create procedure p as
                begin
                    insert into t values(q'{a;b}');
                end;
                /
                select 1;
                """;

        String formatted = SqlUtil.format(sql, SqlDatabase.DM);

        assertTrue(formatted.contains("CREATE PROCEDURE p AS"));
        assertTrue(formatted.contains("INSERT INTO t"));
        assertTrue(formatted.contains("q'{a;b}'"));
        assertTrue(formatted.contains("SELECT 1"));
        assertTrue(formatted.contains("END;\n\nSELECT"));
        assertFalse(formatted.contains("END;;"));
    }
}
