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

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
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
     * 获取类型
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取本地端口
     *
     * @return 本地端口
     */
    public int getLocalPort() {
        return localPort;
    }

    /**
     * 设置本地端口
     *
     * @param localPort 本地端口
     */
    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    /**
     * 获取本地地址
     *
     * @return 本地地址
     */
    public String getLocalHost() {
        return localHost;
    }

    /**
     * 设置本地地址
     *
     * @param localHost 本地地址
     */
    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }

    /**
     * 获取远程端口
     *
     * @return 远程端口
     */
    public int getRemotePort() {
        return remotePort;
    }

    /**
     * 设置远程端口
     *
     * @param remotePort 远程端口
     */
    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    /**
     * 获取远程地址
     *
     * @return 远程地址
     */
    public String getRemoteHost() {
        return remoteHost;
    }

    /**
     * 设置远程地址
     *
     * @param remoteHost 远程地址
     */
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    /**
     * 是否本地转发
     *
     * @return 结果
     */
    public boolean isLocalType() {
        return "local".equals(type);
    }

    /**
     * 是否远程转发
     *
     * @return 结果
     */
    public boolean isRemoteType() {
        return "remote".equals(type);
    }

    /**
     * 是否动态转发
     *
     * @return 结果
     */
    public boolean isDynamicType() {
        return "dynamic".equals(type);
    }

    /**
     * 获取本地地址端口字符串
     *
     * @return 本地地址端口字符串
     */
    public String getLocalHostName() {
        return localHost + ":" + localPort;
    }

    /**
     * 获取远程地址端口字符串
     *
     * @return 远程地址端口字符串
     */
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
