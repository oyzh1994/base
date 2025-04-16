package cn.oyzh.ssh.tunneling;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.ssh.SSHException;
import cn.oyzh.ssh.SSHForwarder;
import cn.oyzh.ssh.domain.SSHTunneling;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.List;

/**
 * ssh隧道转发器
 *
 * @author oyzh
 * @since 2025/04/16
 */
public class SSHTunnelingForwarder extends SSHForwarder {

    /**
     * 端口转发
     *
     * @param tunnelings ssh隧道信息
     * @param session    ssh回话
     * @throws SSHException ssh异常
     */
    public void forward(List<? extends SSHTunneling> tunnelings, Session session) throws SSHException {
        if (CollectionUtil.isNotEmpty(tunnelings)) {
            try {
                for (SSHTunneling tunneling : tunnelings) {
                    if (tunneling.isLocalType()) {
                        int remotePort = tunneling.getRemotePort();
                        int localPort = session.setPortForwardingL(tunneling.getLocalHost(), tunneling.getLocalPort(), tunneling.getRemoteHost(), remotePort);
                        JulLog.info("本地端口转发成功 本地端口:{} 远程端口:{} tunneling:{}", localPort, remotePort, tunneling);
                    } else if (tunneling.isRemoteType()) {
                        int localPort = tunneling.getLocalPort();
                        int remotePort = tunneling.getRemotePort();
                        session.setPortForwardingR(tunneling.getLocalHost(), localPort, tunneling.getRemoteHost(), remotePort);
                        JulLog.info("远程端口转发成功 本地端口:{} 远程端口:{} tunneling:{}", localPort, remotePort, tunneling);
                    } else if (tunneling.isDynamicType()) {
                        int localPort = tunneling.getLocalPort();
                        localPort = session.setPortForwardingL(tunneling.getLocalHost(), localPort, "0.0.0.0", 0);
                        JulLog.info("动态端口转发成功 本地端口:{} tunneling:{}", localPort, tunneling);
                    }
                    this.sessions.add(session);
                }
            } catch (JSchException ex) {
                throw new SSHException(ex);
            }
        }
    }
}
