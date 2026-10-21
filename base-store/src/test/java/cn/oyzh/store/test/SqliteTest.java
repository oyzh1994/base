package cn.oyzh.store.test;

import cn.oyzh.store.jdbc.sqlite.SqliteUtil;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 测试sqlite方言工具映射。
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class SqliteTest {

    @Test
    public void mapsJavaTypesToSqliteAffinity() {
        assertEquals("integer", SqliteUtil.toSqlType(long.class));
        assertEquals("integer", SqliteUtil.toSqlType(Boolean.class));
        assertEquals("real", SqliteUtil.toSqlType(double.class));
        assertEquals("text", SqliteUtil.toSqlType(String.class));
        assertEquals("text", SqliteUtil.toSqlType(LocalDateTime.class));
        assertEquals("text", SqliteUtil.toSqlType(LocalDate.class));
        assertEquals("text", SqliteUtil.toSqlType(LocalTime.class));
        assertEquals("blob", SqliteUtil.toSqlType(byte[].class));
    }

    @Test
    public void normalizesSqliteTypeAliases() {
        assertTrue(SqliteUtil.checkSqlType("text", "VARCHAR(255)"));
        assertTrue(SqliteUtil.checkSqlType("integer", "BIGINT"));
        assertTrue(SqliteUtil.checkSqlType("real", "DOUBLE PRECISION"));
        assertTrue(SqliteUtil.checkSqlType("blob", "BINARY LARGE OBJECT"));
    }

    @Test
    public void wrapsIdentifiersAndEscapesData() {
        assertEquals("\"select\"\"table\"", SqliteUtil.wrap("select\"table"));
        assertEquals("'O''Brien'", SqliteUtil.wrapData("O'Brien"));
        assertEquals("X'0102'", SqliteUtil.wrapData(new byte[]{1, 2}));
    }
}
