package cn.oyzh.ssh.domain;


/**
 * ssh转发信息
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class SSHForwardConfig {

    /**
     * 远程端口
     */
    private int port;

    /**
     * 远程地址
     */
    private String host;

    /**
     * 本地地址
     */
    private String localHost;

    /**
     * 本地端口
     */
    private int localPort;

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

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }
}
