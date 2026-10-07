package cn.oyzh.common.security;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * 信任所有证书的 X509 信任管理器。
 *
 * <p>不做任何证书校验，会无条件信任任意客户端/服务端证书，仅适用于测试或
 * 自签名证书等需要跳过证书校验的场景，生产环境请勿使用。
 *
 * @author oyzh
 * @since 2026-07-01
 */
public class TrustAllX509TrustManager implements X509TrustManager {

    /** 单例实例 */
    public static final TrustAllX509TrustManager INSTANCE = new TrustAllX509TrustManager();

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        // 不做任何检查
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        // 不做任何检查
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}