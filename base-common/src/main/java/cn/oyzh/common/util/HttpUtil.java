package cn.oyzh.common.util;

import java.util.Base64;

/**
 * HTTP工具类
 *
 * @author oyzh
 * @since 2025-10-10
 */
public class HttpUtil {

    /**
     * 生成Basic认证头
     *
     * @param username 用户名
     * @param password 密码
     * @return Basic认证头字符串
     */
    public static String basic(String username, String password) {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encodedAuth;
    }
}
