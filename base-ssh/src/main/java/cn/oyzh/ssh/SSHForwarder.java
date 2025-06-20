package cn.oyzh.ssh;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.StringUtil;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.HashSet;
import java.util.Set;

/**
 * ssh转发器
 *
 * @author oyzh
 * @since 2025/04/16
 */
public class SSHForwarder {

    /**
     * 会话列表
     */
    protected final Set<Session> sessions = new HashSet<>();

    /**
     * 销毁
     */
    public void destroy() {
        // 删除端口转发
        for (Session session : this.sessions) {
            try {
                // 删除本地端口转发
                String[] localPorts = session.getPortForwardingL();
                for (String port : localPorts) {
                    if (!StringUtil.contains(port, ":")) {
                        continue;
                    }
                    int localPort = Integer.parseInt(port.split(":")[0]);
                    session.delPortForwardingL(localPort);
                }
                // 删除远程端口转发
                String[] remotePorts = session.getPortForwardingR();
                for (String port : remotePorts) {
                    if (!StringUtil.contains(port, ":")) {
                        continue;
                    }
                    int remotePort = Integer.parseInt(port.split(":")[0]);
                    session.delPortForwardingR(remotePort);
                }
            } catch (JSchException ex) {
                ex.printStackTrace();
            }
        }
        this.sessions.clear();
        JulLog.info("ssh端口转发已清理");
    }
}
