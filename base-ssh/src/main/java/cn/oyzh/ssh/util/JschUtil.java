package cn.oyzh.ssh.util;

import cn.oyzh.common.system.OSUtil;
import cn.oyzh.ssh.domain.SSHProxyConfig;
import com.jcraft.jsch.AgentConnector;
import com.jcraft.jsch.AgentIdentityRepository;
import com.jcraft.jsch.AgentProxyException;
import com.jcraft.jsch.PageantConnector;
import com.jcraft.jsch.Proxy;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.ProxySOCKS4;
import com.jcraft.jsch.ProxySOCKS5;
import com.jcraft.jsch.SSHAgentConnector;

/**
 * @author oyzh
 * @since 2025-07-03
 */
public class JschUtil {

    /**
     * 初始化代理证书仓库
     *
     * @return AgentIdentityRepository
     * @throws AgentProxyException 异常
     */
    public static AgentIdentityRepository initAgentIdentityRepository() throws AgentProxyException {
        AgentConnector connector;
        if (OSUtil.isWindows()) {
            connector = new PageantConnector();
        } else {
            connector = new SSHAgentConnector();
        }
        if (!connector.isAvailable()) {
            throw new AgentProxyException("AgentConnector is not available");
        }
        return new AgentIdentityRepository(connector);
    }

    /**
     * 初始化代理
     *
     * @param proxyConfig 代理配置
     * @return 代理对象
     */
    public static Proxy newProxy(SSHProxyConfig proxyConfig) {
        Proxy proxy = null;
        if (proxyConfig.isHttpProxy()) {
            ProxyHTTP http = new ProxyHTTP(proxyConfig.getHost(), proxyConfig.getPort());
            if (proxyConfig.isPasswordAuth()) {
                http.setUserPasswd(proxyConfig.getUser(), proxyConfig.getPassword());
            }
            proxy = http;
        } else if (proxyConfig.isSocks4Proxy()) {
            ProxySOCKS4 socks4 = new ProxySOCKS4(proxyConfig.getHost(), proxyConfig.getPort());
            if (proxyConfig.isPasswordAuth()) {
                socks4.setUserPasswd(proxyConfig.getUser(), proxyConfig.getPassword());
            }
            proxy = socks4;
        } else if (proxyConfig.isSocks5Proxy()) {
            ProxySOCKS5 socks5 = new ProxySOCKS5(proxyConfig.getHost(), proxyConfig.getPort());
            if (proxyConfig.isPasswordAuth()) {
                socks5.setUserPasswd(proxyConfig.getUser(), proxyConfig.getPassword());
            }
            proxy = socks5;
        }
        return proxy;
    }
}
