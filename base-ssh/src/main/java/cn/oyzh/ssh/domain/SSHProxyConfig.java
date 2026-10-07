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

    /**
     * 获取连接地址
     *
     * @return 连接地址
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置连接地址
     *
     * @param host 连接地址
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 获取连接端口
     *
     * @return 连接端口
     */
    public int getPort() {
        return port;
    }

    /**
     * 设置连接端口
     *
     * @param port 连接端口
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置用户名
     *
     * @param user 用户名
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取认证类型
     *
     * @return 认证类型
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * 设置认证类型
     *
     * @param authType 认证类型
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    /**
     * 获取代理协议
     *
     * @return 代理协议
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * 设置代理协议
     *
     * @param protocol 代理协议
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * 是否http代理
     *
     * @return 结果
     */
    @JSONField(serialize = false, deserialize = false)
    public boolean isHttpProxy() {
        return "http".equalsIgnoreCase(this.protocol);
    }

    /**
     * 是否socks代理，包含socks、socks4、socks5
     *
     * @return 结果
     */
    @JSONField(serialize = false, deserialize = false)
    public boolean isSocksProxy() {
        return StringUtil.equalsAnyIgnoreCase(this.protocol, "socks", "socks4", "socks5");
    }

    /**
     * 是否socks4代理
     *
     * @return 结果
     */
    @JSONField(serialize = false, deserialize = false)
    public boolean isSocks4Proxy() {
        return "socks4".equalsIgnoreCase(this.protocol);
    }

    /**
     * 是否socks5代理
     *
     * @return 结果
     */
    @JSONField(serialize = false, deserialize = false)
    public boolean isSocks5Proxy() {
        return "socks5".equalsIgnoreCase(this.protocol);
    }

    /**
     * 是否未使用代理
     *
     * @return 结果
     */
    @JSONField(serialize = false, deserialize = false)
    public boolean isNoneProxy() {
        return !this.isSocksProxy() && !this.isHttpProxy();
    }

    /**
     * 是否密码认证
     *
     * @return 结果
     */
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
