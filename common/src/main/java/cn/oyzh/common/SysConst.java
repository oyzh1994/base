package cn.oyzh.common;

import lombok.experimental.UtilityClass;

/**
 * @author oyzh
 * @since 2024-09-27
 */
@UtilityClass
public class SysConst {

    public String CACHE_DIR = "cache.dir";

    public String STORE_DIR = "store.dir";

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
}
