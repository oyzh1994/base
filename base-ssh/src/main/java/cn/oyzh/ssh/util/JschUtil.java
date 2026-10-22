package cn.oyzh.ssh.util;

import cn.oyzh.common.system.OSUtil;
import cn.oyzh.ssh.domain.SSHProxyConfig;
import com.jcraft.jsch.AgentConnector;
import com.jcraft.jsch.AgentIdentityRepository;
import com.jcraft.jsch.AgentProxyException;
import com.jcraft.jsch.Proxy;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.ProxySOCKS4;
import com.jcraft.jsch.ProxySOCKS5;
import com.jcraft.jsch.PageantConnector;
import com.jcraft.jsch.SSHAgentConnector;
import com.jcraft.jsch.WindowsSSHAgentConnector;

import java.nio.file.Path;

/**
 * @author oyzh
 * @since 2025-07-03
 */
public class JschUtil {

    public static final String PAGEANT_IDENTITY_AGENT = "\\\\.\\pageant";

    public static final String UNIX_IDENTITY_AGENT = "SSH_AUTH_SOCK";

    public static String defaultIdentityAgent() {
        return OSUtil.isWindows() ? PAGEANT_IDENTITY_AGENT : UNIX_IDENTITY_AGENT;
    }

    /**
     * 初始化代理证书仓库
     *
     * @return AgentIdentityRepository
     * @throws AgentProxyException 异常
     */
    public static AgentIdentityRepository initAgentIdentityRepository() throws AgentProxyException {
        return initAgentIdentityRepository(defaultIdentityAgent());
    }

    public static AgentIdentityRepository initAgentIdentityRepository(String identityAgent) throws AgentProxyException {
        AgentConnector connector;
        if (OSUtil.isWindows()) {
            if (identityAgent == null || identityAgent.isBlank() || PAGEANT_IDENTITY_AGENT.equals(identityAgent)) {
                connector = new PageantConnector();
            } else {
                connector = new WindowsSSHAgentConnector(identityAgent);
            }
        } else if (identityAgent == null || identityAgent.isBlank() || UNIX_IDENTITY_AGENT.equals(identityAgent)) {
            String socketFile = SSHUtil.getSSHAgentSockFile();
            connector = socketFile == null || socketFile.isBlank()
                    ? new SSHAgentConnector()
                    : new SSHAgentConnector(Path.of(socketFile));
        } else {
            connector = new SSHAgentConnector(Path.of(identityAgent));
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
        if (proxyConfig == null) {
            return null;
        }
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
        } else if (proxyConfig.isSocksProxy()) {
            // 兼容历史配置：protocol=socks 在旧实现中按 SOCKS5 处理。
            ProxySOCKS5 socks5 = new ProxySOCKS5(proxyConfig.getHost(), proxyConfig.getPort());
            if (proxyConfig.isPasswordAuth()) {
                socks5.setUserPasswd(proxyConfig.getUser(), proxyConfig.getPassword());
            }
            proxy = socks5;
        }
        return proxy;
    }
}
