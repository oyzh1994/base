package cn.oyzh.store.jdbc;

import cn.oyzh.common.log.JulLog;
import lombok.experimental.UtilityClass;

/**
 * db常量
 *
 * @author oyzh
 * @since 2024-09-27
 */
@UtilityClass
public class JdbcConst {

    /**
     * db文件
     */
    public String DB_FILE = "db.file";

    /**
     * db方言
     */
    public String DB_DIALECT = "db.dialect";

    /**
     * db缓存大小
     */
    public String DB_CACHE_SIZE = "db.cache.size";

    /*
     * db缓存类型
     */
    public String DB_CACHE_TYPE = "db.cache.type";

    /*
     * db页大小
     */
    public String DB_PAGE_SIZE = "db.page.size";

    public static void dbFile(String dbFile) {
        JulLog.info("dbFile: {}", dbFile);
        System.setProperty(DB_FILE, dbFile);
    }

    public static String dbFile() {
        return System.getProperty(DB_FILE);
    }

    public static void dbDialect(JdbcDialect dialect) {
        JulLog.info("dialect: {}", dialect);
        System.setProperty(DB_DIALECT, dialect.toString());
    }

    public static String dbDialect() {
        return System.getProperty(DB_DIALECT);
    }

    public static void dbCacheSize(int dbCacheSize) {
        JulLog.info("dbCacheSize: {}", dbCacheSize);
        System.setProperty(DB_CACHE_SIZE, dbCacheSize + "");
    }

    public static String dbCacheSize() {
        return System.getProperty(DB_CACHE_SIZE);
    }

    public static void dbPageSize(int dbPageSize) {
        JulLog.info("dbPageSize: {}", dbPageSize);
        System.setProperty(DB_PAGE_SIZE, dbPageSize + "");
    }

    public static String dbPageSize() {
        return System.getProperty(DB_PAGE_SIZE);
    }
}
