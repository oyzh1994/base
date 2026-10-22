package cn.oyzh.ssh.tunneling;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.ssh.SSHException;
import cn.oyzh.ssh.SSHForwarder;
import cn.oyzh.ssh.domain.SSHTunneling;
import cn.oyzh.ssh.jsch.JSchDynamicForwarder;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public synchronized void forward(List<? extends SSHTunneling> tunnelings, Session session) throws SSHException {
        if (CollectionUtil.isNotEmpty(tunnelings)) {
            try {
                for (SSHTunneling tunneling : tunnelings) {
                    if (tunneling.isLocalType()) {
                        int remotePort = tunneling.getRemotePort();
                        int localPort = session.setPortForwardingL(tunneling.getLocalHost(), tunneling.getLocalPort(), tunneling.getRemoteHost(), remotePort);
                        this.registerLocal(session, tunneling.getLocalHost(), localPort);
                        JulLog.info("local forwarding established, localPort:{}, remotePort:{}", localPort, remotePort);
                    } else if (tunneling.isRemoteType()) {
                        int localPort = tunneling.getLocalPort();
                        Set<String> existing = new HashSet<>(List.of(session.getPortForwardingR()));
                        session.setPortForwardingR(tunneling.getRemoteHost(), tunneling.getRemotePort(), tunneling.getLocalHost(), localPort);
                        int remotePort = this.resolveRemotePort(session, existing, tunneling.getRemotePort(), tunneling.getLocalHost(), localPort);
                        this.registerRemote(session, tunneling.getRemoteHost(), remotePort);
                        JulLog.info("remote forwarding established, localPort:{}, remotePort:{}", localPort, remotePort);
                    } else if (tunneling.isDynamicType()) {
                        JSchDynamicForwarder dynamicForwarder = new JSchDynamicForwarder(session, tunneling.getLocalHost(), tunneling.getLocalPort(), 30_000);
                        this.registerResource(dynamicForwarder);
                        int localPort = dynamicForwarder.getLocalPort();
                        JulLog.info("dynamic forwarding established, localPort:{}", localPort);
                    }
                }
                this.registerSession(session, false);
            } catch (JSchException | IOException | RuntimeException ex) {
                this.destroy();
                throw new SSHException(ex);
            }
        }
    }

    private int resolveRemotePort(Session session, Set<String> existing, int requestedPort, String host, int localPort) throws JSchException {
        if (requestedPort != 0) {
            return requestedPort;
        }
        String suffix = ":" + host + ":" + localPort;
        for (String forwarding : session.getPortForwardingR()) {
            if (!existing.contains(forwarding) && forwarding.endsWith(suffix)) {
                return Integer.parseInt(forwarding.substring(0, forwarding.indexOf(':')));
            }
        }
        throw new JSchException("allocated remote port was not found");
    }
}
