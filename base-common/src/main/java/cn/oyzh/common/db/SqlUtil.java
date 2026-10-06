package cn.oyzh.common.db;

import java.util.List;

/**
 * SQL工具类
 *
 * @author oyzh
 * @since 2026/10/6
 */
public final class SqlUtil {

    private SqlUtil() {
    }

    public static List<String> split(String sql) {
        return split(sql, SqlDatabase.ANSI);
    }

    public static List<String> split(String sql, SqlDatabase database) {
        return SqlDialects.get(database).split(sql);
    }

    public static List<String> split(String sql, String database) {
        return SqlDialects.get(database).split(sql);
    }

    public static String format(String sql) {
        return format(sql, SqlDatabase.ANSI);
    }

    public static String format(String sql, SqlDatabase database) {
        return SqlDialects.get(database).format(sql);
    }

    public static String format(String sql, String database) {
        return SqlDialects.get(database).format(sql);
    }

    public static boolean isQuery(String sql) {
        return SqlAnalyzer.isQuery(sql);
    }

    public static boolean isQuery(String sql, SqlDatabase database) {
        return SqlAnalyzer.isQuery(sql, database);
    }

    public static boolean isQuery(String sql, String database) {
        return SqlAnalyzer.isQuery(sql, database);
    }

    public static boolean isAllFieldsQuery(String sql) {
        return SqlAnalyzer.isAllFieldsQuery(sql);
    }

    public static boolean isAllFieldsQuery(String sql, SqlDatabase database) {
        return SqlAnalyzer.isAllFieldsQuery(sql, database);
    }

    public static boolean isAllFieldsQuery(String sql, String database) {
        return SqlAnalyzer.isAllFieldsQuery(sql, database);
    }

    public static boolean isAllFieldQuery(String sql) {
        return SqlAnalyzer.isAllFieldQuery(sql);
    }

    public static boolean isAllFieldQuery(String sql, SqlDatabase database) {
        return SqlAnalyzer.isAllFieldQuery(sql, database);
    }

    public static boolean isAllFieldQuery(String sql, String database) {
        return SqlAnalyzer.isAllFieldQuery(sql, database);
    }

    public static boolean isSelectAll(String sql) {
        return SqlAnalyzer.isSelectAll(sql);
    }

    public static boolean isSelectAll(String sql, SqlDatabase database) {
        return SqlAnalyzer.isSelectAll(sql, database);
    }

    public static boolean isSelectAll(String sql, String database) {
        return SqlAnalyzer.isSelectAll(sql, database);
    }

    public static String removeComments(String sql) {
        return SqlAnalyzer.removeComments(sql);
    }

    public static String removeComments(String sql, SqlDatabase database) {
        return SqlAnalyzer.removeComments(sql, database);
    }

    public static String removeComments(String sql, String database) {
        return SqlAnalyzer.removeComments(sql, database);
    }

    public static String compress(String sql) {
        return SqlCompressor.compress(sql);
    }

    public static String compress(String sql, SqlDatabase database) {
        return SqlCompressor.compress(sql, database);
    }

    public static String compress(String sql, String database) {
        return SqlCompressor.compress(sql, database);
    }

    public static String compressSql(String sql) {
        return SqlCompressor.compressSql(sql);
    }

    public static String compressSql(String sql, SqlDatabase database) {
        return SqlCompressor.compressSql(sql, database);
    }

    public static String compressSql(String sql, String database) {
        return SqlCompressor.compressSql(sql, database);
    }

    public static String singleStatement(String sql, SqlDatabase database) {
        List<String> statements = SqlDialects.get(database).split(sql);
        return statements.size() == 1 ? statements.getFirst() : null;
    }
}
