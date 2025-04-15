package cn.oyzh.ssh.domain;

import cn.oyzh.common.util.StringUtil;
import cn.oyzh.store.jdbc.Column;

/**
 * ssh连接信息
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class SSHConnect {

    /**
     * 连接端口，默认22
     */
    @Column
    private int port = 22;

    /**
     * 连接地址
     */
    @Column
    private String host;

    /**
     * ssh用户名
     */
    @Column
    private String user;

    /**
     * ssh密码
     */
    @Column
    private String password;

    /**
     * 连接超时，单位毫秒
     */
    @Column
    private int timeout = 5000;

    /**
     * 认证方式
     */
    @Column
    private String authMethod;

    /**
     * 证书路径
     */
    @Column
    private String certificatePath;

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public boolean isPasswordAuth() {
        return StringUtil.isBlank(this.authMethod) || StringUtil.equalsAnyIgnoreCase(this.authMethod, "password");
    }

    public boolean isCertificateAuth() {
        return  StringUtil.equalsAnyIgnoreCase(this.authMethod, "certificate");
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeoutSecond() {
        return this.timeout == 0 ? 0 : this.timeout / 1000;
    }
}
