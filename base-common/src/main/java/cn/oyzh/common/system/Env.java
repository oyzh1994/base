package cn.oyzh.common.system;

/**
 * 环境
 *
 * @author oyzh
 * @since 2026-10-11
 */
public enum Env {
    DEV, PROD, UNKOWN;

    private static final Env CURRENT = resolve();

    private static Env resolve() {
        // 优先级：系统属性 > 环境变量 > 默认
        String v = System.getProperty("app.env");
        if (v == null) {
            v = System.getenv("APP_ENV");
        }
        if (v == null) {
            return UNKOWN;
        }
        return switch (v.toLowerCase()) {
            case "dev", "develop", "development" -> DEV;
            case "prod", "production" -> PROD;
            default -> UNKOWN;
        };
    }

    public static Env current() {
        return CURRENT;
    }

    public static boolean isDev() {
        return CURRENT == DEV;
    }

    public static boolean isProd() {
        return CURRENT == PROD;
    }
}