package cn.oyzh.common.db;

import java.util.List;

/**
 * SQL工具类
 *
 * @author oyzh
 * @since 2026-10-06
 */
public final class SqlUtil {

    /**
     * 私有构造，禁止实例化
     */
    private SqlUtil() {
    }

    /**
     * 拆分SQL脚本（ANSI方言）
     *
     * @param sql SQL脚本
     * @return SQL语句列表
     */
    public static List<String> split(String sql) {
        return split(sql, SqlDatabase.ANSI);
    }

    /**
     * 拆分SQL脚本
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return SQL语句列表
     */
    public static List<String> split(String sql, SqlDatabase database) {
        return SqlDialects.get(database).split(sql);
    }

    /**
     * 拆分SQL脚本
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return SQL语句列表
     */
    public static List<String> split(String sql, String database) {
        return SqlDialects.get(database).split(sql);
    }

    /**
     * 美化SQL脚本（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 美化后的SQL脚本
     */
    public static String format(String sql) {
        return format(sql, SqlDatabase.ANSI);
    }

    /**
     * 美化SQL脚本
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 美化后的SQL脚本
     */
    public static String format(String sql, SqlDatabase database) {
        return SqlDialects.get(database).format(sql);
    }

    /**
     * 美化SQL脚本
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 美化后的SQL脚本
     */
    public static String format(String sql, String database) {
        return SqlDialects.get(database).format(sql);
    }

    /**
     * 判断是否为查询语句（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 结果
     */
    public static boolean isQuery(String sql) {
        return SqlAnalyzer.isQuery(sql);
    }

    /**
     * 判断是否为查询语句
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 结果
     */
    public static boolean isQuery(String sql, SqlDatabase database) {
        return SqlAnalyzer.isQuery(sql, database);
    }

    /**
     * 判断是否为查询语句
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 结果
     */
    public static boolean isQuery(String sql, String database) {
        return SqlAnalyzer.isQuery(sql, database);
    }

    /**
     * 判断是否为查询全部字段的SQL（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 结果
     */
    public static boolean isAllFieldsQuery(String sql) {
        return SqlAnalyzer.isAllFieldsQuery(sql);
    }

    /**
     * 判断是否为查询全部字段的SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 结果
     */
    public static boolean isAllFieldsQuery(String sql, SqlDatabase database) {
        return SqlAnalyzer.isAllFieldsQuery(sql, database);
    }

    /**
     * 判断是否为查询全部字段的SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 结果
     */
    public static boolean isAllFieldsQuery(String sql, String database) {
        return SqlAnalyzer.isAllFieldsQuery(sql, database);
    }

    /**
     * 去除SQL中的注释（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 去除注释后的SQL
     */
    public static String removeComments(String sql) {
        return SqlAnalyzer.removeComments(sql);
    }

    /**
     * 去除SQL中的注释
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 去除注释后的SQL
     */
    public static String removeComments(String sql, SqlDatabase database) {
        return SqlAnalyzer.removeComments(sql, database);
    }

    /**
     * 去除SQL中的注释
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 去除注释后的SQL
     */
    public static String removeComments(String sql, String database) {
        return SqlAnalyzer.removeComments(sql, database);
    }

    /**
     * 压缩SQL（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 压缩后的SQL
     */
    public static String compress(String sql) {
        return SqlCompressor.compress(sql);
    }

    /**
     * 压缩SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 压缩后的SQL
     */
    public static String compress(String sql, SqlDatabase database) {
        return SqlCompressor.compress(sql, database);
    }

    /**
     * 压缩SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 压缩后的SQL
     */
    public static String compress(String sql, String database) {
        return SqlCompressor.compress(sql, database);
    }

    /**
     * 压缩SQL（compress的别名，ANSI方言）
     *
     * @param sql SQL脚本
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql) {
        return SqlCompressor.compressSql(sql);
    }

    /**
     * 压缩SQL（compress的别名）
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql, SqlDatabase database) {
        return SqlCompressor.compressSql(sql, database);
    }

    /**
     * 压缩SQL（compress的别名）
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql, String database) {
        return SqlCompressor.compressSql(sql, database);
    }

    /**
     * 获取SQL中的唯一一条语句，语句数量不为1时返回null
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 唯一的一条SQL语句，或null
     */
    public static String singleStatement(String sql, SqlDatabase database) {
        List<String> statements = SqlDialects.get(database).split(sql);
        return statements.size() == 1 ? statements.getFirst() : null;
    }
}
