package cn.oyzh.ssh.domain;

import cn.oyzh.common.object.ObjectCopier;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.store.jdbc.Column;
import com.alibaba.fastjson2.annotation.JSONField;

import java.io.Serializable;

/**
 * ssh代理配置
 *
 * @author oyzh
 * @since 2025-04-14
 */
public class SSHProxyConfig implements Serializable, ObjectCopier<SSHProxyConfig> {

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

    @JSONField(serialize = false, deserialize = false)
    public boolean isHttpProxy() {
        return "http".equalsIgnoreCase(this.protocol);
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSocksProxy() {
        return StringUtil.equalsAnyIgnoreCase(this.protocol, "socks", "socks4", "socks5");
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSocks4Proxy() {
        return "socks4".equalsIgnoreCase(this.protocol);
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSocks5Proxy() {
        return "socks5".equalsIgnoreCase(this.protocol);
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isNoneProxy() {
        return !this.isSocksProxy() && !this.isHttpProxy();
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isPasswordAuth() {
        return "password".equalsIgnoreCase(this.authType);
    }

    @Override
    public void copy(SSHProxyConfig t1) {
        this.user = t1.getUser();
        this.host = t1.getHost();
        this.port = t1.getPort();
        this.protocol = t1.getProtocol();
        this.authType = t1.getAuthType();
        this.password = t1.getPassword();
    }
}
