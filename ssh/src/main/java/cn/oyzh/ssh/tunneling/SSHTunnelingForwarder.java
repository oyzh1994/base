package cn.oyzh.ssh.tunneling;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.UUIDUtil;
import cn.oyzh.ssh.SSHException;
import cn.oyzh.ssh.domain.SSHConnect;
import cn.oyzh.ssh.domain.SSHTunneling;
import cn.oyzh.ssh.util.SSHHolder;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ssh跳板
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class SSHTunnelingForwarder {

    /**
     * 本地端口
     */
    private final Map<Session, List<Integer>> localPorts = new HashMap<>();

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
                        int localPort = session.setPortForwardingL(tunneling.getLocalPort(), tunneling.getRemoteHost(), tunneling.getRemotePort());
                        JulLog.info("本地端口转发成功 本地端口:{} connect:{}", localPort, tunneling);
                        List<Integer> ports = this.localPorts.computeIfAbsent(session, k -> new ArrayList<>());
                        ports.add(localPort);
                    }
                }
            } catch (JSchException ex) {
                throw new SSHException(ex);
            }
        }
    }

    /**
     * 销毁
     */
    public void destroy() {
        // 删除端口本地转发
        if (!this.localPorts.isEmpty()) {
            for (Map.Entry<Session, List<Integer>> entry : this.localPorts.entrySet()) {
                try {
                    Session session = entry.getKey();
                    for (Integer port : entry.getValue()) {
                        session.delPortForwardingL(port);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            this.localPorts.clear();
        }
        JulLog.info("ssh端口转发已清理");
    }
}
