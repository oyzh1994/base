package cn.oyzh.ssh.jump;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.ssh.SSHException;
import cn.oyzh.ssh.SSHForwarder;
import cn.oyzh.ssh.domain.SSHConnect;
import cn.oyzh.ssh.jsch.JSchPasswordUserInfo;
import cn.oyzh.ssh.util.JschUtil;
import cn.oyzh.ssh.util.SSHHolder;
import com.jcraft.jsch.AgentProxyException;
import com.jcraft.jsch.Identity;
import com.jcraft.jsch.IdentityRepository;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ssh跳板转发器
 *
 * @author oyzh
 * @since 2025/04/15
 */
public class SSHJumpForwarder extends SSHForwarder {

    /**
     * 初始化ssh会话
     *
     * @throws JSchException 异常
     */
    public Session initSession(SSHConnect connect) throws JSchException, AgentProxyException {
        return this.initSession(connect, connect.getHost(), connect.getPort());
    }

    private Session initSession(SSHConnect connect, String host, int port) throws JSchException, AgentProxyException {
        JSch jsch = SSHHolder.newJsch();
        Session session;
        if (connect.isPasswordAuth()) {
            session = jsch.getSession(connect.getUser(), host, port);
            session.setPassword(connect.getPassword());
            session.setUserInfo(new JSchPasswordUserInfo(connect.getPassword()));
        } else if (connect.isCertificateAuth()) {
            jsch.addIdentity(connect.getCertificatePath(), connect.getCertificatePwd());
            session = jsch.getSession(connect.getUser(), host, port);
            session.setUserInfo(new JSchPasswordUserInfo(null, connect.getCertificatePwd()));
        } else if (connect.isSSHAgentAuth()) {
            IdentityRepository repository = JschUtil.initAgentIdentityRepository();
            if (CollectionUtil.isEmpty(repository.getIdentities())) {
                throw new AgentProxyException("identities is empty");
            }
            jsch.setIdentityRepository(repository);
            for (Identity identity : repository.getIdentities()) {
                JulLog.info("Identity: {}", identity.getName());
            }
            session = jsch.getSession(connect.getUser(), host, port);
        } else if (connect.isKeyAuth()) {
            String keyName = "jump_key_" + System.identityHashCode(connect);
            byte[] passphrase = connect.getCertificatePwd() == null
                    ? null
                    : connect.getCertificatePwd().getBytes(StandardCharsets.UTF_8);
            jsch.addIdentity(keyName, connect.getCertificatePriKeyBytes(), connect.getCertificatePubKeyBytes(), passphrase);
            session = jsch.getSession(connect.getUser(), host, port);
            session.setUserInfo(new JSchPasswordUserInfo(null, connect.getCertificatePwd()));
        } else {
            throw new JSchException("unknown auth type");
        }
        try {
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("TCPKeepAlive", "yes");
            session.setServerAliveInterval(60_000);
            session.setServerAliveCountMax(3);
            session.setTimeout(connect.getTimeout());
            session.connect(connect.getTimeout());
        } catch (JSchException ex) {
            session.disconnect();
            throw ex;
        }
        this.registerSession(session, true);
        JulLog.info("ssh jump connection established");
        return session;
    }

    /**
     * 端口转发
     *
     * @param connects ssh连接信息
     * @param target   目标连接
     * @return 转发后的本地端口
     * @throws SSHException ssh异常
     */
    public synchronized int forward(List<? extends SSHConnect> connects, SSHConnect target) throws SSHException {
        int forwardPort = -1;
        if (CollectionUtil.isNotEmpty(connects)) {
            try {
                int hopPort = -1;
                for (int i = 0; i < connects.size(); i++) {
                    SSHConnect connect = connects.get(i);
                    SSHConnect forwardConnect;
                    if (i == connects.size() - 1) {
                        forwardConnect = target;
                    } else {
                        forwardConnect = connects.get(i + 1);
                    }
                    Session session = hopPort < 0
                            ? this.initSession(connect)
                            : this.initSession(connect, "127.0.0.1", hopPort);
                    int remotePort = forwardConnect.getPort();
                    int localPort = session.setPortForwardingL(0, forwardConnect.getHost(), remotePort);
                    this.registerLocal(session, null, localPort);
                    JulLog.info("ssh jump forwarding established, hop:{}, localPort:{}, remotePort:{}", i + 1, localPort, remotePort);
                    if (i == connects.size() - 1) {
                        forwardPort = localPort;
                    } else {
                        hopPort = localPort;
                    }
                }
            } catch (JSchException | AgentProxyException | RuntimeException ex) {
                this.destroy();
                throw new SSHException(ex);
            }
        }
        return forwardPort;
    }
}
