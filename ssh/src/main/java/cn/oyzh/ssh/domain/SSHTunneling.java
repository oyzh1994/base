package cn.oyzh.ssh.domain;

import cn.oyzh.store.jdbc.Column;

/**
 * ssh隧道
 *
 * @author oyzh
 * @since 2025-04-16
 */
public class SSHTunneling {

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private int localPort;

    @Column
    private String localHost;

    @Column
    private int remotePort;

    @Column
    private String remoteHost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
