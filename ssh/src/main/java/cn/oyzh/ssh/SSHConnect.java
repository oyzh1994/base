package cn.oyzh.ssh;

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
     * 连接超时
     */
    @Column
    private int timeout = 5000;

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
}
