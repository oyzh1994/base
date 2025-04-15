package cn.oyzh.ssh.domain;


import java.util.ArrayList;
import java.util.List;

/**
 * ssh转发信息
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class SSHJumpConfig   {

    /**
     * 远程端口
     */
    private int remotePort;

    /**
     * 远程地址
     */
    private String remoteHost;

    /**
     * 本地端口
     */
    private int localPort;

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    @Override
    public String toString() {
        return "SSHJumpConfig{" +
                "remotePort=" + remotePort +
                ", remoteHost='" + remoteHost + '\'' +
                ", localPort=" + localPort +
                '}';
    }

    public static SSHJumpConfig from(SSHConnect connect) {
        SSHJumpConfig config = new SSHJumpConfig();
        config.setRemoteHost(connect.getHost());
        config.setRemotePort(connect.getPort());
        return config;
    }

}
