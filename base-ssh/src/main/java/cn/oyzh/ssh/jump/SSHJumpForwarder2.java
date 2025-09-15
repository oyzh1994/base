package cn.oyzh.ssh.jump;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.ArrayUtil;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.ssh.SSHException;
import cn.oyzh.ssh.SSHForwarder2;
import cn.oyzh.ssh.domain.SSHConnect;
import cn.oyzh.ssh.util.SSHAgentConnectorFactory;
import cn.oyzh.ssh.util.SSHKeyUtil;
import cn.oyzh.ssh.util.SSHUtil;
import com.jcraft.jsch.JSchException;
import org.apache.sshd.client.ClientBuilder;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.auth.keyboard.UserAuthKeyboardInteractiveFactory;
import org.apache.sshd.client.auth.password.UserAuthPasswordFactory;
import org.apache.sshd.client.auth.pubkey.UserAuthPublicKeyFactory;
import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.kex.DHGClient;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.channel.ChannelFactory;
import org.apache.sshd.common.global.KeepAliveHandler;
import org.apache.sshd.common.kex.BuiltinDHFactories;
import org.apache.sshd.common.kex.KeyExchangeFactory;
import org.apache.sshd.common.session.SessionHeartbeatController;
import org.apache.sshd.common.signature.BuiltinSignatures;
import org.apache.sshd.common.signature.Signature;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.apache.sshd.core.CoreModuleProperties;
import org.eclipse.jgit.internal.transport.sshd.JGitSshClient;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.sshd.KeyPasswordProvider;

import java.security.KeyPair;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * ssh跳板转发器
 *
 * @author oyzh
 * @since 2025/07/02
 */
public class SSHJumpForwarder2 extends SSHForwarder2 {

    /**
     * 初始化客户端
     *
     * @param connect 连接
     * @return 客户端
     */
    protected JGitSshClient initClient(SSHConnect connect) throws Exception {
        // 客户端构建器
        ClientBuilder builder = new ClientBuilder();
        // 保持连接
        builder.globalRequestHandlers(List.of(KeepAliveHandler.INSTANCE));
        // 客户端
        builder.factory(JGitSshClient::new);

        // key交换工厂
        List<KeyExchangeFactory> keyExchangeFactories = ClientBuilder.setUpDefaultKeyExchanges(true);
        keyExchangeFactories.add(DHGClient.newFactory(BuiltinDHFactories.dhg1));
        keyExchangeFactories.add(DHGClient.newFactory(BuiltinDHFactories.dhg14));
        keyExchangeFactories.add(DHGClient.newFactory(BuiltinDHFactories.dhgex));
        builder.keyExchangeFactories(keyExchangeFactories);

        // 签名工厂
        List<NamedFactory<Signature>> signatureFactories = ClientBuilder.setUpDefaultSignatureFactories(true);
        for (BuiltinSignatures signature : BuiltinSignatures.values()) {
            if (!signatureFactories.contains(signature)) {
                signatureFactories.add(signature);
            }
        }
        builder.signatureFactories(signatureFactories);

        // 通道工厂
        List<ChannelFactory> channelFactories = new ArrayList<>(ClientBuilder.DEFAULT_CHANNEL_FACTORIES);
        builder.channelFactories(channelFactories);

        // 创建客户端
        JGitSshClient sshClient = (JGitSshClient) builder.build();
        // ssh agent处理
        if (connect.isSSHAgentAuth() || connect.isForwardAgent()) {
            sshClient.setAgentFactory(new SSHAgentConnectorFactory());
        }
        // 优先的认证方式
        String methods = UserAuthPasswordFactory.PASSWORD;
        // 密码
        if (connect.isPasswordAuth()) {
            methods = ArrayUtil.join(new String[]{UserAuthPasswordFactory.KB_INTERACTIVE, UserAuthPasswordFactory.PASSWORD}, ",");
            // sshClient.addPasswordIdentity(connect.getPassword());
        } else if (connect.isSSHAgentAuth()) {// ssh agent
            methods = ArrayUtil.join(new String[]{UserAuthPasswordFactory.PUBLIC_KEY, UserAuthPasswordFactory.PASSWORD, UserAuthPasswordFactory.KB_INTERACTIVE}, ",");
        } else if (connect.isCertificateAuth()) {// 证书
            methods = UserAuthPasswordFactory.PUBLIC_KEY;
            // // 加载证书
            // Iterable<KeyPair> keyPairs = SSHKeyUtil.loadKeysFromFile(connect.getCertificatePath(), connect.getCertificatePwd());
            // //  设置证书认证
            // for (KeyPair keyPair : keyPairs) {
            //     sshClient.addPublicKeyIdentity(keyPair);
            // }
        } else if (connect.isKeyAuth()) {// 密钥
            methods = UserAuthPasswordFactory.PUBLIC_KEY;
            // Iterable<KeyPair> keyPairs = SSHKeyUtil.loadKeysForStr(connect.getCertificatePriKey(), connect.getCertificatePwd());
            // //  设置证书认证
            // for (KeyPair keyPair : keyPairs) {
            //     sshClient.addPublicKeyIdentity(keyPair);
            // }
        }
        // 设置优先认证方式
        CoreModuleProperties.PREFERRED_AUTHS.set(sshClient, methods);
        // 设置认证工厂
        sshClient.setUserAuthFactories(List.of(
                UserAuthKeyboardInteractiveFactory.INSTANCE,
                UserAuthPasswordFactory.INSTANCE,
                UserAuthPublicKeyFactory.INSTANCE
        ));
        // 启动客户端
        sshClient.start();
        // 测试环境使用，生产环境需替换
        sshClient.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
        // 设置密码工厂
        sshClient.setKeyPasswordProviderFactory(() -> (KeyPasswordProvider) CredentialsProvider.getDefault());
        // 心跳
        sshClient.setSessionHeartbeat(SessionHeartbeatController.HeartbeatType.IGNORE, Duration.ofSeconds(60));
        // 其他参数
        int timeout = connect.getTimeout();
        CoreModuleProperties.SOCKET_KEEPALIVE.set(sshClient, true);
        CoreModuleProperties.ALLOW_DHG1_KEX_FALLBACK.set(sshClient, true);
        CoreModuleProperties.HEARTBEAT_INTERVAL.set(sshClient, Duration.ofSeconds(60));
        CoreModuleProperties.IO_CONNECT_TIMEOUT.set(sshClient, Duration.ofMillis(timeout));
        CoreModuleProperties.FORWARD_REQUEST_TIMEOUT.set(sshClient, Duration.ofMillis(timeout));
        // 添加到列表
        this.clients.add(sshClient);
        return sshClient;
    }

    /**
     * 初始化ssh会话
     *
     * @throws JSchException 异常
     */
    public ClientSession initSession(SSHConnect connect) throws Exception {
        // 初始化客户端
        SshClient sshClient = this.initClient(connect);

        // 超时时间
        int timeout = connect.getTimeout();
        //
        // // 由于二次验证会要求更多时间，优化下此处的验证时间
        // if (connect.isPasswordAuth() && timeout < 15000) {
        //     timeout = 15000;
        // }

        // 会话连接参数
        HostConfigEntry entry = new HostConfigEntry();
        entry.setPort(connect.getPort());
        entry.setHostName(connect.getHost());
        entry.setUsername(connect.getUser());

        // 创建会话连接
        ConnectFuture future = sshClient.connect(entry);
        // 创建会话
        ClientSession session = future.verify(timeout).getClientSession();
        // 密码
        if (connect.isPasswordAuth()) {
            session.addPasswordIdentity(connect.getPassword());
        } else if (connect.isCertificateAuth()) {// 证书
            // 加载证书
            Iterable<KeyPair> keyPairs = SSHKeyUtil.loadKeysFromFile(connect.getCertificatePath(), connect.getCertificatePwd());
            //  设置证书认证
            for (KeyPair keyPair : keyPairs) {
                session.addPublicKeyIdentity(keyPair);
            }
        } else if (connect.isKeyAuth()) {// 密钥
            Iterable<KeyPair> keyPairs = SSHKeyUtil.loadKeysForStr(connect.getCertificatePriKey(), connect.getCertificatePwd());
            //  设置证书认证
            for (KeyPair keyPair : keyPairs) {
                session.addPublicKeyIdentity(keyPair);
            }
        } else {
            throw new RuntimeException("unknow auth type");
        }
        // 认证
        session.auth().verify(timeout);
        JulLog.info("ssh连接成功 connect:{}", connect);
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
    public int forward(List<? extends SSHConnect> connects, SSHConnect target) throws SSHException {
        int forwardPort = -1;
        if (CollectionUtil.isNotEmpty(connects)) {
            for (int i = 0; i < connects.size(); i++) {
                try {
                    SSHConnect connect = connects.get(i);
                    JulLog.info("ssh跳板机连接开始 connect:{}", connect);
                    SSHConnect forwardConnect;
                    // 最后一个是目标机器
                    if (i == connects.size() - 1) {
                        forwardConnect = target;
                    } else {
                        forwardConnect = connects.get(i + 1);
                    }
                    ClientSession session = this.initSession(connect);
                    int localPort = SSHUtil.findAvailablePort();
                    int remotePort = forwardConnect.getPort();
                    SshdSocketAddress remote = new SshdSocketAddress(forwardConnect.getHost(), remotePort);
                    SshdSocketAddress address = session.startLocalPortForwarding(localPort, remote);
                    localPort = address.getPort();
                    this.sessions.add(session);
                    JulLog.info("ssh跳板机连接成功 本地端口:{} 远程端口:{} connect:{}", localPort, remotePort, connect);
                    forwardPort = localPort;
                } catch (Exception ex) {
                    throw new SSHException(ex);
                }
            }
        }
        return forwardPort;
    }
}
