package cn.oyzh.ssh.tunneling;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.ssh.SSHException;
import cn.oyzh.ssh.SSHForwarder;
import cn.oyzh.ssh.SSHForwarder2;
import cn.oyzh.ssh.domain.SSHTunneling;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.util.net.SshdSocketAddress;

import java.io.IOException;
import java.util.List;

/**
 * ssh隧道转发器
 *
 * @author oyzh
 * @since 2025/07/02
 */
public class SSHTunnelingForwarder2 extends SSHForwarder2 {

    /**
     * 端口转发
     *
     * @param tunnelings ssh隧道信息
     * @param session    ssh回话
     * @throws SSHException ssh异常
     */
    public void forward(List<? extends SSHTunneling> tunnelings, ClientSession session) throws SSHException {
        if (CollectionUtil.isNotEmpty(tunnelings)) {
            try {
                for (SSHTunneling tunneling : tunnelings) {
                    if (tunneling.isLocalType()) {
                        int remotePort = tunneling.getRemotePort();
                        SshdSocketAddress local = new SshdSocketAddress(tunneling.getLocalHost(), tunneling.getLocalPort());
                        SshdSocketAddress remote = new SshdSocketAddress(tunneling.getRemoteHost(), tunneling.getRemotePort());
                        SshdSocketAddress address = session.startLocalPortForwarding(local, remote);
                        JulLog.info("本地端口转发成功 本地端口:{} 远程端口:{} tunneling:{}", address.getPort(), remotePort, tunneling);
                    } else if (tunneling.isRemoteType()) {
                        int localPort = tunneling.getLocalPort();
                        int remotePort = tunneling.getRemotePort();
                        SshdSocketAddress local = new SshdSocketAddress(tunneling.getLocalHost(), tunneling.getLocalPort());
                        SshdSocketAddress remote = new SshdSocketAddress(tunneling.getRemoteHost(), tunneling.getRemotePort());
                        session.startRemotePortForwarding(remote, local);
                        JulLog.info("远程端口转发成功 本地端口:{} 远程端口:{} tunneling:{}", localPort, remotePort, tunneling);
                    } else if (tunneling.isDynamicType()) {
                        int localPort = tunneling.getLocalPort();
                        SshdSocketAddress local = new SshdSocketAddress(tunneling.getLocalHost(), tunneling.getLocalPort());
                        session.startDynamicPortForwarding(local);
                        JulLog.info("动态端口转发成功 本地端口:{} tunneling:{}", localPort, tunneling);
                    }
                    this.sessions.add(session);
                }
            } catch (IOException ex) {
                throw new SSHException(ex);
            }
        }
    }
}
