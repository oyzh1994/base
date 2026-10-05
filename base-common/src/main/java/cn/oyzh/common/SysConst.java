package cn.oyzh.common;

/**
 * 系统常量
 *
 * @author oyzh
 * @since 2024-09-27
 */
public class SysConst {

    public static String TEMP_DIR = "temp.dir";

    public static String CACHE_DIR = "cache.dir";

    public static String STORE_DIR = "store.dir";

    public static String PROJECT_NAME = "project.name";

    public static String tempDir() {
        return System.getProperty(TEMP_DIR);
    }

    public static void tempDir(String tempDir) {
        System.setProperty(TEMP_DIR, tempDir);
    }

    public static String storeDir() {
        return System.getProperty(STORE_DIR);
    }

    public static void storeDir(String storeDir) {
        System.setProperty(STORE_DIR, storeDir);
    }

    public static String cacheDir() {
        return System.getProperty(CACHE_DIR);
    }

    public static void cacheDir(String cacheDir) {
        System.setProperty(CACHE_DIR, cacheDir);
    }

    public static String projectName() {
        return System.getProperty(PROJECT_NAME);
    }

    public static void projectName(String projectName) {
        System.setProperty(PROJECT_NAME, projectName);
    }
}
