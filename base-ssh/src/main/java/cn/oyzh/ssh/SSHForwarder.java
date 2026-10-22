package cn.oyzh.ssh;

import cn.oyzh.common.log.JulLog;
import com.jcraft.jsch.Session;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ssh转发器
 *
 * @author oyzh
 * @since 2025/04/16
 */
public class SSHForwarder implements AutoCloseable {

    /**
     * 会话列表
     */
    protected final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    /**
     * 本对象创建并拥有的会话
     */
    private final Set<Session> ownedSessions = ConcurrentHashMap.newKeySet();

    /**
     * 端口转发注册表
     */
    private final Map<Session, Set<Forwarding>> forwardings = new ConcurrentHashMap<>();

    /**
     * 自定义转发资源，例如 JSch 不直接支持的动态转发
     */
    private final Set<AutoCloseable> resources = ConcurrentHashMap.newKeySet();

    protected void registerSession(Session session, boolean owned) {
        this.sessions.add(session);
        if (owned) {
            this.ownedSessions.add(session);
        }
    }

    protected void registerLocal(Session session, String bindAddress, int port) {
        this.registerForwarding(session, new Forwarding(ForwardingType.LOCAL, bindAddress, port));
    }

    protected void registerRemote(Session session, String bindAddress, int port) {
        this.registerForwarding(session, new Forwarding(ForwardingType.REMOTE, bindAddress, port));
    }

    protected void registerDynamic(Session session, String bindAddress, int port) {
        this.registerForwarding(session, new Forwarding(ForwardingType.DYNAMIC, bindAddress, port));
    }

    protected void registerResource(AutoCloseable resource) {
        this.resources.add(resource);
    }

    private void registerForwarding(Session session, Forwarding forwarding) {
        this.sessions.add(session);
        this.forwardings.computeIfAbsent(session, key -> ConcurrentHashMap.newKeySet()).add(forwarding);
    }

    /**
     * 当前转发器持有的会话是否全部可用。
     *
     * @return 是否可用
     */
    public synchronized boolean isAlive() {
        if (this.sessions.isEmpty()) {
            return false;
        }
        for (Session session : this.sessions) {
            if (!session.isConnected()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 销毁
     */
    public synchronized void destroy() {
        for (AutoCloseable resource : this.resources) {
            try {
                resource.close();
            } catch (Exception ex) {
                JulLog.warn("ssh forwarding resource cleanup failed", ex);
            }
        }
        this.resources.clear();
        for (Session session : this.sessions) {
            if (!session.isConnected()) {
                continue;
            }
            for (Forwarding forwarding : this.forwardings.getOrDefault(session, Set.of())) {
                try {
                    switch (forwarding.type()) {
                        case LOCAL -> {
                            if (forwarding.bindAddress() == null) {
                                session.delPortForwardingL(forwarding.port());
                            } else {
                                session.delPortForwardingL(forwarding.bindAddress(), forwarding.port());
                            }
                        }
                        case REMOTE -> session.delPortForwardingR(forwarding.bindAddress(), forwarding.port());
                        case DYNAMIC -> {
                        }
                    }
                } catch (Exception ex) {
                    if (ex.getMessage() != null && ex.getMessage().contains("is not registered")) {
                        JulLog.debug("ssh port forwarding was already removed: {}", ex.getMessage());
                    } else {
                        JulLog.warn("ssh port forwarding cleanup failed", ex);
                    }
                }
            }
            if (this.ownedSessions.contains(session)) {
                session.disconnect();
            }
        }
        this.forwardings.clear();
        this.ownedSessions.clear();
        this.sessions.clear();
        JulLog.info("ssh端口转发已清理");
    }

    @Override
    public void close() {
        this.destroy();
    }

    private enum ForwardingType {
        LOCAL,
        REMOTE,
        DYNAMIC
    }

    private record Forwarding(ForwardingType type, String bindAddress, int port) {
    }
}
