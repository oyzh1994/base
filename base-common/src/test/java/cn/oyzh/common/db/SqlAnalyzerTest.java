package cn.oyzh.common.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * SQL语句分析测试
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class SqlAnalyzerTest {

    @Test
    public void detectsQueryStatements() {
        assertTrue(SqlUtil.isQuery("select * from t"));
        assertTrue(SqlUtil.isQuery("SELECT id FROM t WHERE id = 1"));
        assertTrue(SqlUtil.isQuery("show dbs"));
        assertTrue(SqlUtil.isQuery("SHOW TABLES"));
        assertTrue(SqlUtil.isQuery("describe t"));
        assertTrue(SqlUtil.isQuery("explain select * from t"));
        assertTrue(SqlUtil.isQuery("values (1, 2)"));
        assertTrue(SqlUtil.isQuery("with cte as (select 1) select * from cte"));
        assertTrue(SqlUtil.isQuery("pragma table_info(t)", SqlDatabase.SQLITE));
        assertTrue(SqlUtil.isQuery("show tables", "MySQL"));
    }

    @Test
    public void rejectsNonQueryAndMultiStatementSql() {
        assertFalse(SqlUtil.isQuery("insert into t values (1)"));
        assertFalse(SqlUtil.isQuery("update t set a = 1"));
        assertFalse(SqlUtil.isQuery("delete from t"));
        assertFalse(SqlUtil.isQuery("create table t (id int)"));
        assertFalse(SqlUtil.isQuery("select * into target from source"));
        assertFalse(SqlUtil.isQuery("with cte as (select 1) update t set a = 1"));
        assertFalse(SqlUtil.isQuery("select 1; select 2"));
        assertFalse(SqlUtil.isQuery(""));
        assertFalse(SqlUtil.isQuery("-- comment only"));
    }

    @Test
    public void detectsAllFieldQueries() {
        assertTrue(SqlUtil.isAllFieldsQuery("select * from t"));
        assertTrue(SqlUtil.isAllFieldsQuery("SELECT DISTINCT * FROM t"));
        assertTrue(SqlUtil.isAllFieldsQuery("select t.* from t"));
        assertTrue(SqlUtil.isAllFieldsQuery("select schema.t.* from schema.t"));
        assertTrue(SqlUtil.isAllFieldsQuery("select a.*, b.* from a join b on a.id = b.id"));
        assertTrue(SqlUtil.isAllFieldsQuery("select * from (select id from t) nested"));
        assertTrue(SqlUtil.isAllFieldsQuery("select *"));
        assertTrue(SqlUtil.isAllFieldsQuery("select top 10 * from t", SqlDatabase.SQL_SERVER));
        assertTrue(SqlUtil.isSelectAll("select * from t"));
        assertTrue(SqlUtil.isAllFieldsQuery("select * from t", "DM"));
    }

    @Test
    public void rejectsPartialAndMixedProjections() {
        assertFalse(SqlUtil.isAllFieldsQuery("select id from t"));
        assertFalse(SqlUtil.isAllFieldsQuery("select count(*) from t"));
        assertFalse(SqlUtil.isAllFieldsQuery("select *, id from t"));
        assertFalse(SqlUtil.isAllFieldsQuery("select id, * from t"));
        assertFalse(SqlUtil.isAllFieldsQuery("select * as all_columns from t"));
        assertFalse(SqlUtil.isAllFieldsQuery("select * into target from source"));
        assertFalse(SqlUtil.isAllFieldsQuery("select * from t; select id from t"));
        assertFalse(SqlUtil.isAllFieldQuery("select id from t"));
    }

    @Test
    public void removesCommentsButPreservesLiteralCommentText() {
        String sql = """
                SELECT '--literal', "#literal" FROM t -- line comment
                WHERE /* keep ; */ id = 1 # mysql comment
                """;

        String cleaned = SqlUtil.removeComments(sql, SqlDatabase.MYSQL);

        assertFalse(cleaned.contains("-- line"));
        assertFalse(cleaned.contains("# mysql"));
        assertFalse(cleaned.contains("/*"));
        assertTrue(cleaned.contains("'--literal'"));
        assertTrue(cleaned.contains("\"#literal\""));
        assertEquals("SELECT '--literal', \"#literal\" FROM t WHERE id = 1",
                cleaned.replaceAll("\\s+", " ").strip());
    }

    @Test
    public void removesNestedAndOracleQuotedComments() {
        String nested = SqlUtil.removeComments(
                "SELECT /* outer /* nested */ still */ 1", SqlDatabase.POSTGRESQL);
        String oracle = SqlUtil.removeComments(
                "select q'{-- literal; /* literal */}' from dual -- tail", SqlDatabase.ORACLE);

        assertEquals("SELECT 1", nested.replaceAll("\\s+", " ").strip());
        assertFalse(oracle.contains("-- tail"));
        assertTrue(oracle.contains("q'{-- literal; /* literal */}'"));
    }

    @Test
    public void handlesNullAndEmptyInput() {
        assertEquals("", SqlUtil.removeComments(null));
        assertEquals("", SqlUtil.removeComments(""));
        assertFalse(SqlUtil.isQuery(null));
        assertFalse(SqlUtil.isAllFieldsQuery(null));
    }
}
