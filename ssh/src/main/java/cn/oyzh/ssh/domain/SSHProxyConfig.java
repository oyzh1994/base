package cn.oyzh.ssh.domain;

import cn.oyzh.store.jdbc.Column;

import java.io.Serializable;

/**
 * @author oyzh
 * @since 2025-04-14
 */
public class SSHProxyConfig implements Serializable {

    /**
     * 代理协议
     */
    @Column
    private String protocol;

    /**
     * 连接地址
     */
    @Column
    private String host;

    /**
     * 连接端口
     */
    @Column
    private int port;

    /**
     * 认证类型
     */
    @Column
    private String authType;

    /**
     * 用户名
     */
    @Column
    private String user;

    /**
     * 密码
     */
    @Column
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isHttpProxy() {
        return "http".equalsIgnoreCase(this.protocol);
    }

    public boolean isSocks4Proxy() {
        return "socks4".equalsIgnoreCase(this.protocol);
    }

    public boolean isSocks5Proxy() {
        return "socks5".equalsIgnoreCase(this.protocol);
    }

    public boolean isPasswordAuth() {
        return "password".equalsIgnoreCase(this.authType);
    }
}
