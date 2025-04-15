package cn.oyzh.ssh.jump;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.UUIDUtil;
import cn.oyzh.ssh.SSHException;
import cn.oyzh.ssh.domain.SSHConnect;
import cn.oyzh.ssh.domain.SSHJumpConfig;
import cn.oyzh.ssh.util.SSHHolder;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ssh跳板
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class SSHJumpForwarder {

    /**
     * 转发信息
     */
    private final Map<SSHJumpConfig, Session> jumpSessions = new HashMap<>();

    /**
     * 初始化ssh会话
     *
     * @throws JSchException 异常
     */
    protected Session initSession(SSHConnect connect) throws JSchException {
        Session session;
        // 登陆跳板机
        if (connect.isPasswordAuth()) {
            session = SSHHolder.getJsch().getSession(connect.getUser(), connect.getHost(), connect.getPort());
            session.setPassword(connect.getPassword());
        } else if (connect.isCertificateAuth()) {
            SSHHolder.getJsch().addIdentity(connect.getCertificatePath());
            session = SSHHolder.getJsch().getSession(connect.getUser(), connect.getHost(), connect.getPort());
        } else {
            String keyName = "ssh_key_" + UUIDUtil.uuidSimple();
            SSHHolder.getJsch().addIdentity(keyName, connect.getCertificatePriKeyyBytes(), connect.getCertificatePubKeyBytes(), null);
            session = SSHHolder.getJsch().getSession(connect.getUser(), connect.getHost(), connect.getPort());
        }
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(connect.getTimeout());
        session.connect();
        JulLog.info("ssh连接成功 connect:{}", connect);
        return session;
    }

    /**
     * 端口转发
     *
     * @param connects ssh连接信息
     * @return 转发后的本地端口
     * @throws SSHException ssh异常
     */
    public int forward(List<? extends SSHConnect> connects, SSHConnect target) throws SSHException {
        int forwardPort = -1;
        if (CollectionUtil.isNotEmpty(connects)) {
            for (int i = 0; i < connects.size(); i++) {
                try {
                    SSHConnect connect = connects.get(i);
                    SSHConnect forwardConnect;
                    if (i == connects.size() - 1) {
                        forwardConnect = target;
                    } else {
                        forwardConnect = connects.get(i + 1);
                    }
                    Session session = this.initSession(connect);
                    SSHJumpConfig config = SSHJumpConfig.from(forwardConnect);
                    int localPort = session.setPortForwardingL(0, config.getRemoteHost(), config.getRemotePort());
                    config.setLocalPort(localPort);
                    this.jumpSessions.put(config, session);
                    JulLog.info("ssh端口转发成功 本地端口:{} connect:{} config:{}", localPort, connect, config);
                    forwardPort = localPort;
                } catch (JSchException ex) {
                    throw new SSHException(ex);
                }
            }
        }
        return forwardPort;
    }

    /**
     * 销毁
     */
    public void destroy() {
        // 删除端口本地转发
        if (!this.jumpSessions.isEmpty()) {
            for (Map.Entry<SSHJumpConfig, Session> entry : this.jumpSessions.entrySet()) {
                try {
                    entry.getValue().delPortForwardingL(entry.getKey().getLocalPort());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            this.jumpSessions.clear();
        }
        JulLog.info("ssh端口转发已清理");
    }
}
