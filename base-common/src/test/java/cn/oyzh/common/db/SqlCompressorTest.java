package cn.oyzh.common.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * SQL显示压缩测试
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class SqlCompressorTest {

    @Test
    public void compressesFormattingWhitespaceToOneLine() {
        String sql = """
                SELECT
                    id,
                    name
                FROM
                    user_table
                WHERE
                    id = 1;
                """;

        String compressed = SqlUtil.compress(sql);

        assertEquals("SELECT id, name FROM user_table WHERE id = 1;", compressed);
        assertFalse(compressed.contains("\n"));
        assertFalse(compressed.contains("\r"));
    }

    @Test
    public void removesCommentsDuringCompression() {
        String sql = """
                SELECT /* select comment */ id -- line comment
                FROM user_table # mysql comment
                """;

        String compressed = SqlUtil.compress(sql, SqlDatabase.MYSQL);

        assertEquals("SELECT id FROM user_table", compressed);
        assertFalse(compressed.contains("comment"));
        assertFalse(compressed.contains("\n"));
    }

    @Test
    public void preservesLiteralAndIdentifierText() {
        String sql = "select 'a;b -- not comment', \"c;d\", `e;f` from t";

        String compressed = SqlUtil.compress(sql, SqlDatabase.MYSQL);

        assertEquals("select 'a;b -- not comment', \"c;d\", `e;f` from t", compressed);
    }

    @Test
    public void alwaysReturnsDisplaySingleLine() {
        String compressed = SqlUtil.compress("SELECT 'line1\nline2' FROM t");

        assertEquals("SELECT 'line1 line2' FROM t", compressed);
        assertFalse(compressed.contains("\n"));
    }

    @Test
    public void compressesMultipleStatementsAndOracleQuotedLiterals() {
        String sql = """
                select q'{a;b}' from dual;
                /
                select 1;
                """;

        String compressed = SqlUtil.compress(sql, SqlDatabase.ORACLE);

        assertTrue(compressed.contains("q'{a;b}'"));
        assertTrue(compressed.contains("select 1"));
        assertFalse(compressed.contains("\n"));
    }

    @Test
    public void handlesEmptyAndCommentsOnlySql() {
        assertEquals("", SqlUtil.compress(null));
        assertEquals("", SqlUtil.compress(""));
        assertEquals("", SqlUtil.compress("-- comment only"));
        assertEquals("", SqlUtil.compress("/* comment only */"));
    }

    @Test
    public void supportsDatabaseNameOverloadAndAlias() {
        assertEquals("show tables", SqlUtil.compress("\n show \n tables \n", "MySQL"));
        assertEquals("show tables", SqlUtil.compressSql("show tables", SqlDatabase.ANSI));
    }
}
