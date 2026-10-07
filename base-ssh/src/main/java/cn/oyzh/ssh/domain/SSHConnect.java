package cn.oyzh.ssh.domain;

import cn.oyzh.common.object.ObjectCopier;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.store.jdbc.Column;

import java.nio.charset.StandardCharsets;

/**
 * ssh连接信息
 *
 * @author oyzh
 * @since 2024-10-18
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
     * 客户端转发
     */
    @Column
    private boolean forwardAgent;

    /**
     * 证书路径
     */
    @Column
    private String certificatePath;

    /**
     * 证书密码
     */
    @Column
    private String certificatePwd;

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

    /**
     * 获取认证方式
     *
     * @return 认证方式
     */
    public String getAuthMethod() {
        return authMethod;
    }

    /**
     * 设置认证方式
     *
     * @param authMethod 认证方式
     */
    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    /**
     * 获取证书路径
     *
     * @return 证书路径
     */
    public String getCertificatePath() {
        return certificatePath;
    }

    /**
     * 设置证书路径
     *
     * @param certificatePath 证书路径
     */
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
     * 获取ssh用户名
     *
     * @return ssh用户名
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置ssh用户名
     *
     * @param user ssh用户名
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获取ssh密码
     *
     * @return ssh密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置ssh密码
     *
     * @param password ssh密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取连接超时，小于3000毫秒时按3000毫秒返回
     *
     * @return 连接超时，单位毫秒
     */
    public int getTimeout() {
        return timeout < 3000 ? 3000 : timeout;
    }

    /**
     * 设置连接超时，小于3000毫秒时按3000毫秒处理
     *
     * @param timeout 连接超时，单位毫秒
     */
    public void setTimeout(int timeout) {
        if (timeout < 3000) {
            timeout = 3000;
        }
        this.timeout = timeout;
    }

    /**
     * 获取连接超时，单位秒
     *
     * @return 连接超时，单位秒
     */
    public int getTimeoutSecond() {
        return this.timeout == 0 ? 0 : this.timeout / 1000;
    }

    /**
     * 获取名称，为空时返回默认名称
     *
     * @return 名称
     */
    public String getName() {
        return name == null ? "untitled" : name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取顺序
     *
     * @return 顺序
     */
    public int getOrder() {
        return order;
    }

    /**
     * 设置顺序
     *
     * @param order 顺序
     */
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
        this.certificatePwd = t1.getCertificatePwd();
        this.certificatePath = t1.getCertificatePath();
        this.certificatePriKey = t1.getCertificatePriKey();
        this.certificatePubKey = t1.getCertificatePubKey();
    }

    /**
     * 获取公钥
     *
     * @return 公钥
     */
    public String getCertificatePubKey() {
        return certificatePubKey;
    }

    /**
     * 设置公钥
     *
     * @param certificatePubKey 公钥
     */
    public void setCertificatePubKey(String certificatePubKey) {
        this.certificatePubKey = certificatePubKey;
    }

    /**
     * 获取私钥
     *
     * @return 私钥
     */
    public String getCertificatePriKey() {
        return certificatePriKey;
    }

    /**
     * 设置私钥
     *
     * @param certificatePriKey 私钥
     */
    public void setCertificatePriKey(String certificatePriKey) {
        this.certificatePriKey = certificatePriKey;
    }

    /**
     * 获取公钥的字节数组
     *
     * @return 公钥字节数组
     */
    public byte[] getCertificatePubKeyBytes() {
        return certificatePubKey == null ? null : certificatePubKey.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 获取私钥的字节数组
     *
     * @return 私钥字节数组
     */
    public byte[] getCertificatePriKeyBytes() {
        return certificatePriKey == null ? null : certificatePriKey.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 获取证书密码
     *
     * @return 证书密码
     */
    public String getCertificatePwd() {
        return certificatePwd;
    }

    /**
     * 设置证书密码
     *
     * @param certificatePwd 证书密码
     */
    public void setCertificatePwd(String certificatePwd) {
        this.certificatePwd = certificatePwd;
    }

    /**
     * 是否客户端转发
     *
     * @return 结果
     */
    public boolean isForwardAgent() {
        return forwardAgent;
    }

    /**
     * 设置客户端转发
     *
     * @param forwardAgent 客户端转发
     */
    public void setForwardAgent(boolean forwardAgent) {
        this.forwardAgent = forwardAgent;
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
                ", forwardAgent=" + forwardAgent +
                ", certificatePath='" + certificatePath + '\'' +
                ", certificatePwd='" + certificatePwd + '\'' +
                ", certificatePubKey='" + certificatePubKey + '\'' +
                ", certificatePriKey='" + certificatePriKey + '\'' +
                '}';
    }
}
