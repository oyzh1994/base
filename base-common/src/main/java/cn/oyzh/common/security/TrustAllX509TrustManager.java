package cn.oyzh.common.security;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 *
 * @author oyzh
 * @since 2026-07-01
 */
public class TrustAllX509TrustManager implements X509TrustManager {

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