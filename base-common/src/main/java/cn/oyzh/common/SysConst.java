package cn.oyzh.common;

/**
 * 系统常量
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class SysConst {

    /**
     * 临时目录的系统属性名
     */
    public static String TEMP_DIR = "temp.dir";

    /**
     * 缓存目录的系统属性名
     */
    public static String CACHE_DIR = "cache.dir";

    /**
     * 存储目录的系统属性名
     */
    public static String STORE_DIR = "store.dir";

    /**
     * 项目名称的系统属性名
     */
    public static String PROJECT_NAME = "project.name";

    /**
     * 获取临时目录
     *
     * @return 临时目录
     */
    public static String tempDir() {
        return System.getProperty(TEMP_DIR);
    }

    /**
     * 设置临时目录
     *
     * @param tempDir 临时目录
     */
    public static void tempDir(String tempDir) {
        System.setProperty(TEMP_DIR, tempDir);
    }

    /**
     * 获取存储目录
     *
     * @return 存储目录
     */
    public static String storeDir() {
        return System.getProperty(STORE_DIR);
    }

    /**
     * 设置存储目录
     *
     * @param storeDir 存储目录
     */
    public static void storeDir(String storeDir) {
        System.setProperty(STORE_DIR, storeDir);
    }

    /**
     * 获取缓存目录
     *
     * @return 缓存目录
     */
    public static String cacheDir() {
        return System.getProperty(CACHE_DIR);
    }

    /**
     * 设置缓存目录
     *
     * @param cacheDir 缓存目录
     */
    public static void cacheDir(String cacheDir) {
        System.setProperty(CACHE_DIR, cacheDir);
    }

    /**
     * 获取项目名称
     *
     * @return 项目名称
     */
    public static String projectName() {
        return System.getProperty(PROJECT_NAME);
    }

    /**
     * 设置项目名称
     *
     * @param projectName 项目名称
     */
    public static void projectName(String projectName) {
        System.setProperty(PROJECT_NAME, projectName);
    }
}
