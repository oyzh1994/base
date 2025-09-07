package cn.oyzh.common.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author oyzh
 * @since 2025-09-05
 */
public class ProxyUtil {

    /**
     * 实现SOCKS5协议握手（包括认证）
     *
     * @param proxySocket   连接
     * @param targetHost    目标连接
     * @param targetPort    目标端口
     * @param proxyUser     代理用户
     * @param proxyPassword 代理米啊么
     */
    public static void socks5Handshake(Socket proxySocket,
                                       String targetHost,
                                       int targetPort,
                                       String proxyUser,
                                       String proxyPassword
    ) throws IOException {
        // if (StringUtil.isAnyBlank(targetHost, proxyUser, proxyPassword)) {
        //     return;
        // }
        if (proxyUser == null) {
            proxyUser = "";
        }
        if (proxyPassword == null) {
            proxyPassword = "";
        }
        byte[] buffer = new byte[1024];
        int len;

        // 步骤1：发送认证方法协商
        // 格式：[版本(0x05)][方法数][方法列表(0x00=无认证, 0x02=密码认证)]
        buffer[0] = 0x05;
        buffer[1] = 0x02; // 支持2种方法
        buffer[2] = 0x00; // 无认证
        buffer[3] = 0x02; // 密码认证
        proxySocket.getOutputStream().write(buffer, 0, 4);

        // 步骤2：接收代理选择的认证方法
        len = proxySocket.getInputStream().read(buffer);
        if (len < 2 || buffer[0] != 0x05) {
            throw new IOException("SOCKS5协议版本不支持");
        }

        // 步骤3：如果需要密码认证，发送用户名密码
        if (buffer[1] == 0x02) {
            // 认证格式：[版本(0x01)][用户名长度][用户名][密码长度][密码]
            byte[] userBytes = proxyUser.getBytes(StandardCharsets.UTF_8);
            byte[] passBytes = proxyPassword.getBytes(StandardCharsets.UTF_8);
            int authLen = 1 + 1 + userBytes.length + 1 + passBytes.length;
            buffer[0] = 0x01;
            buffer[1] = (byte) userBytes.length;
            System.arraycopy(userBytes, 0, buffer, 2, userBytes.length);
            buffer[2 + userBytes.length] = (byte) passBytes.length;
            System.arraycopy(passBytes, 0, buffer, 3 + userBytes.length, passBytes.length);
            proxySocket.getOutputStream().write(buffer, 0, authLen);

            // 验证认证结果
            len = proxySocket.getInputStream().read(buffer);
            if (len < 2 || buffer[0] != 0x01 || buffer[1] != 0x00) {
                throw new IOException("SOCKS5代理认证失败");
            }
        } else if (buffer[1] != 0x00) {
            // 不支持的认证方法
            throw new IOException("不支持的SOCKS5认证方法：" + buffer[1]);
        }

        // 步骤4：发送连接请求（目标主机和端口）
        // 格式：[版本(0x05)][命令(0x01=CONNECT)][保留位(0x00)][地址类型][地址][端口]
        byte[] hostBytes = targetHost.getBytes(StandardCharsets.UTF_8);
        int reqLen = 4 + 1 + hostBytes.length + 2; // 固定头(4) + 域名长度(1) + 域名 + 端口(2)
        buffer[0] = 0x05;
        buffer[1] = 0x01; // CONNECT命令
        buffer[2] = 0x00; // 保留位
        buffer[3] = 0x03; // 地址类型：域名
        buffer[4] = (byte) hostBytes.length; // 域名长度
        System.arraycopy(hostBytes, 0, buffer, 5, hostBytes.length);
        // 端口（大端序）
        buffer[5 + hostBytes.length] = (byte) (targetPort >> 8);
        buffer[6 + hostBytes.length] = (byte) (targetPort & 0xFF);
        proxySocket.getOutputStream().write(buffer, 0, reqLen);

        // 步骤5：验证连接响应
        len = proxySocket.getInputStream().read(buffer);
        if (len < 3 || buffer[1] != 0x00) {
            throw new IOException("SOCKS5连接目标服务器失败，错误码：" + buffer[1]);
        }
    }

    /**
     * socks握手
     *
     * @param sock          连接通道
     * @param addr          地址
     * @param proxyUser     代理用户
     * @param proxyPassword 代理密码
     * @throws IOException 异常
     */
    public static void socks5Handshake(SocketChannel sock,
                                       InetSocketAddress addr,
                                       String proxyUser,
                                       String proxyPassword) throws IOException {
        SocksPerformHandler socksPerformHandler = new SocksPerformHandler();
        socksPerformHandler.setProxyUsername(proxyUser);
        socksPerformHandler.setProxyPassword(proxyPassword);
        socksPerformHandler.performSocksHandshake(sock, addr);
    }

    /**
     * 是否需要代理
     *
     * @param proxy 代理对象
     * @return 结果
     */
    public static boolean isNeedProxy(Proxy proxy) {
        return proxy != null && proxy.type() != Proxy.Type.DIRECT;
    }
}
