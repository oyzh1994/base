package cn.oyzh.common;

import cn.oyzh.common.log.JulLog;
import lombok.experimental.UtilityClass;

/**
 * @author oyzh
 * @since 2024-09-27
 */
@UtilityClass
public class SysConst {

    public String CACHE_DIR = "cache.dir";

    public String STORE_DIR = "store.dir";

    public String PROJECT_NAME = "project.name";

    public static String storeDir() {
        return System.getProperty(STORE_DIR);
    }

    public static void storeDir(String storeDir) {
        JulLog.info("storeDir: {}", storeDir);
        System.setProperty(STORE_DIR, storeDir);
    }

    public static String cacheDir() {
        return System.getProperty(CACHE_DIR);
    }

    public static void cacheDir(String cacheDir) {
        JulLog.info("cacheDir: {}", cacheDir);
        System.setProperty(CACHE_DIR, cacheDir);
    }

    public static String projectName() {
        return System.getProperty(PROJECT_NAME);
    }

    public static void projectName(String projectName) {
        JulLog.info("projectName: {}", projectName);
        System.setProperty(PROJECT_NAME, projectName);
    }
}
