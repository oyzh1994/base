package cn.oyzh.common.util;

import java.util.Base64;

/**
 *
 * @author oyzh
 * @since 2025-10-10
 */
public class HttpUtil {

    /**
     * basic认证
     *
     * @param username 用户名
     * @param password 密码
     * @return basic认证
     */
    public static String basic(String username, String password) {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encodedAuth;
    }
}
