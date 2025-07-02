package cn.oyzh.ssh;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.IOUtil;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.util.net.SshdSocketAddress;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ssh转发器
 *
 * @author oyzh
 * @since 2025/07/02
 */
public class SSHForwarder2 implements AutoCloseable {

    /**
     * 客户端列表
     */
    protected final Set<SshClient> clients = new HashSet<>();

    /**
     * 会话列表
     */
    protected final Set<ClientSession> sessions = new HashSet<>();

    @Override
    public void close() {
        // 删除端口转发
        for (ClientSession session : this.sessions) {
            try {
                // 删除本地端口转发
                List<Map.Entry<SshdSocketAddress, SshdSocketAddress>> localForwardsBindings = session.getLocalForwardsBindings();
                for (Map.Entry<SshdSocketAddress, SshdSocketAddress> entry : localForwardsBindings) {
                    try {
                        session.stopLocalPortForwarding(entry.getKey());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                // 删除远程端口转发
                List<Map.Entry<Integer, SshdSocketAddress>> remoteForwardsBindings = session.getRemoteForwardsBindings();
                for (Map.Entry<Integer, SshdSocketAddress> entry : remoteForwardsBindings) {
                    try {
                        session.stopRemotePortForwarding(entry.getValue());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.sessions.clear();
        // 清理客户端
        for (SshClient client : clients) {
            IOUtil.close(client);
        }
        this.clients.clear();
        JulLog.info("ssh端口转发已清理");
    }
}
