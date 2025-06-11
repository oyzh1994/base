package cn.oyzh.ssh.domain;

import cn.oyzh.common.object.ObjectCopier;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.store.jdbc.Column;

/**
 * ssh连接信息
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class SSHConnect implements ObjectCopier<SSHConnect> {

    /**
     * 名称
     */
    @Column
    private String name;

    /**
     * 顺序
     */
    @Column
    private int order;

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

    /**
     * 公钥
     */
    @Column
    private String certificatePubKey;

    /**
     * 私钥
     */
    @Column
    private String certificatePriKey;

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

    /**
     * 是否密码认证
     *
     * @return 结果
     */
    public boolean isPasswordAuth() {
        return StringUtil.isBlank(this.authMethod) || StringUtil.equalsIgnoreCase(this.authMethod, "password");
    }

    /**
     * 是否证书认证
     *
     * @return 结果
     */
    public boolean isCertificateAuth() {
        return StringUtil.equalsIgnoreCase(this.authMethod, "certificate");
    }

    /**
     * 是否密钥认证
     *
     * @return 结果
     */
    public boolean isKeyAuth() {
        return StringUtil.equalsIgnoreCase(this.authMethod, "key");
    }

    /**
     * 是否ssh agent认证
     *
     * @return 结果
     */
    public boolean isSSHAgentAuth() {
        return StringUtil.equalsIgnoreCase(this.authMethod, "sshAgent");
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
        return timeout < 3000 ? 3000 : timeout;
    }

    public void setTimeout(int timeout) {
        if (timeout < 3000) {
            timeout = 3000;
        }
        this.timeout = timeout;
    }

    public int getTimeoutSecond() {
        return this.timeout == 0 ? 0 : this.timeout / 1000;
    }

    public String getName() {
        return name == null ? "untitled" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void copy(SSHConnect t1) {
        this.name = t1.getName();
        this.host = t1.getHost();
        this.port = t1.getPort();
        this.user = t1.getUser();
        this.order = t1.getOrder();
        this.timeout = t1.getTimeout();
        this.password = t1.getPassword();
        this.authMethod = t1.getAuthMethod();
        this.certificatePath = t1.getCertificatePath();
    }

    public String getCertificatePubKey() {
        return certificatePubKey;
    }

    public void setCertificatePubKey(String certificatePubKey) {
        this.certificatePubKey = certificatePubKey;
    }

    public String getCertificatePriKey() {
        return certificatePriKey;
    }

    public void setCertificatePriKey(String certificatePriKey) {
        this.certificatePriKey = certificatePriKey;
    }

    public byte[] getCertificatePubKeyBytes() {
        return certificatePubKey == null ? null : certificatePubKey.getBytes();
    }

    public byte[] getCertificatePriKeyyBytes() {
        return certificatePriKey == null ? null : certificatePriKey.getBytes();
    }

    @Override
    public String toString() {
        return "SSHConnect{" +
                "name='" + name + '\'' +
                ", order=" + order +
                ", port=" + port +
                ", host='" + host + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", timeout=" + timeout +
                ", authMethod='" + authMethod + '\'' +
                ", certificatePath='" + certificatePath + '\'' +
                ", certificatePubKey='" + certificatePubKey + '\'' +
                ", certificatePriKey='" + certificatePriKey + '\'' +
                '}';
    }
}
