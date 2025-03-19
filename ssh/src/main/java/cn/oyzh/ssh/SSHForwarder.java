package cn.oyzh.ssh;

import cn.oyzh.common.log.JulLog;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ssh转发器
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class SSHForwarder {

    /**
     * ssh会话
     */
    private Session session;

    /**
     * ssh连接信息
     */
    private final SSHConnect connect;

    /**
     * 转发信息
     */
    private final List<SSHForwardConfig> forwardInfos = new ArrayList<>();

    public SSHForwarder( SSHConnect connect) {
        this.connect = connect;
    }

    /**
     * 初始化ssh会话
     *
     * @throws JSchException 异常
     */
    protected void initSession() throws JSchException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();
            // 登陆跳板机
            this.session = jsch.getSession(this.connect.getUser(), this.connect.getHost(), this.connect.getPort());
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.setTimeout(this.connect.getTimeout());
            this.session.setPassword(this.connect.getPassword());
            this.session.connect();
            JulLog.info("ssh连接成功 connectInfo:{}", this.connect);
        }
    }

    /**
     * 获取转发的端口列表
     *
     * @return 转发的端口列表
     */
    protected Set<Integer> getForwardPorts() {
        return this.forwardInfos.parallelStream().map(SSHForwardConfig::getLocalPort).collect(Collectors.toSet());
    }

    /**
     * 是否已连接
     *
     * @return 结果
     */
    public boolean isConnected() {
        return this.session != null && this.session.isConnected();
    }

    /**
     * 端口转发
     *
     * @param forwardInfo 转发信息
     * @return 转发后的本地端口
     * @throws SSHException ssh异常
     */
    public int forward(SSHForwardConfig forwardInfo) throws SSHException {
        if (forwardInfo != null) {
            int localPort = SSHUtil.findAvailablePort(this.getForwardPorts());
            if (localPort == -1) {
                throw new SSHException("未找到可用端口");
            }
            try {
                this.initSession();
                this.session.setPortForwardingL(localPort, forwardInfo.getHost(), forwardInfo.getPort());
                forwardInfo.setLocalPort(localPort);
                this.forwardInfos.add(forwardInfo);
                JulLog.info("ssh端口转发成功 localPort:{} forwardInfo:{}", localPort, forwardInfo);
                return localPort;
            } catch (JSchException ex) {
                throw new SSHException(ex);
            }
        }
        return -1;
    }

    /**
     * 销毁
     */
    public void destroy() {
        // 删除端口本地转发
        if (!this.forwardInfos.isEmpty() && this.isConnected()) {
            for (SSHForwardConfig forwardInfo : this.forwardInfos) {
                try {
                    this.session.delPortForwardingL(forwardInfo.getLocalPort());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            this.forwardInfos.clear();
        }
        JulLog.info("ssh端口转发已清理");
    }
}
