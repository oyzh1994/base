package cn.oyzh.ssh.domain;

import cn.oyzh.common.object.ObjectCopier;
import cn.oyzh.store.jdbc.Column;

/**
 * ssh隧道
 *
 * @author oyzh
 * @since 2025-04-16
 */
public class SSHTunneling implements ObjectCopier<SSHTunneling> {

    /**
     * 名称
     */
    @Column
    private String name;

    /**
     * 类型
     */
    @Column
    private String type;

    /**
     * 本地端口
     */
    @Column
    private int localPort;

    /**
     * 本地地址
     */
    @Column
    private String localHost;

    /**
     * 远程端口
     */
    @Column
    private int remotePort;

    /**
     * 远程地址
     */
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

    public boolean isLocalType() {
        return "local".equals(type);
    }

    public boolean isRemoteType() {
        return "remote".equals(type);
    }

    public boolean isDynamicType() {
        return "dynamic".equals(type);
    }

    public String getLocalHostName() {
        return localHost + ":" + localPort;
    }

    public String getRemoteHostName() {
        return remoteHost + ":" + remotePort;
    }

    @Override
    public void copy(SSHTunneling t1) {
      this.name = t1.getName();
      this.type = t1.getType();
      this.localPort = t1.getLocalPort();
      this.localHost = t1.getLocalHost();
      this.remotePort = t1.getRemotePort();
      this.remoteHost = t1.getRemoteHost();
    }
}
