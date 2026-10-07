package cn.oyzh.common.network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SOCKS5协议握手处理器，支持用户名/密码认证
 *
 * @author oyzh
 * @since 2025-09-05
 */
public class SocksPerformHandler {

    /**
     * 代理用户名
     */
    private String proxyUsername;

    /**
     * 代理密码
     */
    private String proxyPassword;

    /**
     * 执行SOCKS5协议握手（支持认证）
     *
     * @param sock       连接通道
     * @param targetAddr 目标地址
     * @throws IOException IO异常
     */
    public void performSocksHandshake(SocketChannel sock, InetSocketAddress targetAddr) throws IOException {
        // 确定支持的认证方法
        List<Byte> authMethods = new ArrayList<>();
        authMethods.add((byte) 0x00); // 无认证

        // 如果有用户名和密码，添加用户名/密码认证方法
        if (proxyUsername != null && !proxyUsername.isEmpty() &&
                proxyPassword != null && !proxyPassword.isEmpty()) {
            authMethods.add((byte) 0x02); // 用户名/密码认证
        }

        // 构建初始握手请求
        byte[] handshake = new byte[2 + authMethods.size()];
        handshake[0] = 0x05; // SOCKS版本5
        handshake[1] = (byte) authMethods.size(); // 认证方法数量

        for (int i = 0; i < authMethods.size(); i++) {
            handshake[2 + i] = authMethods.get(i);
        }

        // 发送握手请求
        writeFully(sock, ByteBuffer.wrap(handshake));

        // 读取服务器响应 (2字节: 版本, 选择的认证方法)
        byte[] response = new byte[2];
        readFully(sock, ByteBuffer.wrap(response));

        // 检查响应版本
        if (response[0] != 0x05) {
            throw new IOException("Invalid SOCKS version in response: " + response[0]);
        }

        // 处理服务器选择的认证方法
        byte selectedMethod = response[1];

        // 如果服务器要求认证但客户端不支持
        if (selectedMethod == (byte) 0xFF) {
            throw new IOException("SOCKS proxy rejected all authentication methods");
        }

        // 处理用户名/密码认证
        if (selectedMethod == (byte) 0x02) {
            performUsernamePasswordAuth(sock);
        }
        // 如果选择无认证，继续执行
        else if (selectedMethod != (byte) 0x00) {
            throw new IOException("Unsupported SOCKS authentication method: " + selectedMethod);
        }

        // 构建连接请求
        byte[] connectRequest = buildConnectRequest(targetAddr);

        // 发送连接请求
        writeFully(sock, ByteBuffer.wrap(connectRequest));

        // 读取连接响应 (至少4字节，最多10字节)
        byte[] connectResponse = new byte[10];
        int bytesRead = readFully(sock, ByteBuffer.wrap(connectResponse), 4); // 至少读取4字节

        // 检查连接响应
        if (bytesRead < 4 || connectResponse[0] != 0x05 || connectResponse[1] != 0x00) {
            String errorMsg = getSocksErrorDescription(connectResponse[1]);
            throw new IOException("SOCKS proxy connection failed: " + errorMsg +
                    ". Response: " + Arrays.toString(Arrays.copyOf(connectResponse, bytesRead)));
        }

        // 根据地址类型处理剩余响应数据
        byte addressType = connectResponse[3];
        int extraBytes = 0;

        switch (addressType) {
            case 0x01: // IPv4地址 - 额外6字节 (4字节IP + 2字节端口)
                extraBytes = 6;
                break;
            case 0x03: // 域名 - 额外1字节长度 + 域名 + 2字节端口
                extraBytes = 1 + connectResponse[4] + 2;
                break;
            case 0x04: // IPv6地址 - 额外18字节 (16字节IP + 2字节端口)
                extraBytes = 18;
                break;
            default:
                throw new IOException("Unknown address type in SOCKS response: " + addressType);
        }

        // 如果还有额外数据需要读取
        if (bytesRead < 4 + extraBytes) {
            int remaining = 4 + extraBytes - bytesRead;
            ByteBuffer remainingBuffer = ByteBuffer.wrap(connectResponse, bytesRead, remaining);
            readFully(sock, remainingBuffer);
        }
    }

    /**
     * 执行SOCKS5用户名/密码认证
     *
     * @param sock 连接通道
     * @throws IOException IO异常
     */
    private void performUsernamePasswordAuth(SocketChannel sock) throws IOException {
        if (proxyUsername == null || proxyPassword == null) {
            throw new IOException("SOCKS proxy requires authentication but no credentials provided");
        }

        // 将用户名和密码转换为UTF-8字节数组
        byte[] usernameBytes = proxyUsername.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = proxyPassword.getBytes(StandardCharsets.UTF_8);

        // 构建认证请求
        byte[] authRequest = new byte[3 + usernameBytes.length + passwordBytes.length];
        authRequest[0] = 0x01; // 认证版本 (子协商版本)
        authRequest[1] = (byte) usernameBytes.length; // 用户名长度
        System.arraycopy(usernameBytes, 0, authRequest, 2, usernameBytes.length); // 用户名
        authRequest[2 + usernameBytes.length] = (byte) passwordBytes.length; // 密码长度
        System.arraycopy(passwordBytes, 0, authRequest, 3 + usernameBytes.length, passwordBytes.length); // 密码

        // 发送认证请求
        writeFully(sock, ByteBuffer.wrap(authRequest));

        // 读取认证响应 (2字节)
        byte[] authResponse = new byte[2];
        readFully(sock, ByteBuffer.wrap(authResponse));

        // 检查认证响应
        if (authResponse[0] != 0x01 || authResponse[1] != 0x00) {
            throw new IOException("SOCKS proxy authentication failed. Response: " +
                    Arrays.toString(authResponse));
        }
    }

    /**
     * 根据目标地址类型构建SOCKS连接请求
     *
     * @param targetAddr 目标地址
     * @return 连接请求报文
     * @throws IOException IO异常
     */
    private byte[] buildConnectRequest(InetSocketAddress targetAddr) throws IOException {
        byte[] addressBytes;
        byte addressType;

        if (targetAddr.getAddress() instanceof Inet4Address) {
            // IPv4地址
            addressType = 0x01;
            addressBytes = targetAddr.getAddress().getAddress();
        } else if (targetAddr.getAddress() instanceof Inet6Address) {
            // IPv6地址
            addressType = 0x04;
            addressBytes = targetAddr.getAddress().getAddress();
        } else {
            // 域名
            addressType = 0x03;
            String hostname = targetAddr.getHostName();
            addressBytes = new byte[1 + hostname.length()];
            addressBytes[0] = (byte) hostname.length();
            System.arraycopy(hostname.getBytes(StandardCharsets.UTF_8), 0, addressBytes, 1, hostname.length());
        }

        // 构建请求
        byte[] request = new byte[4 + addressBytes.length + 2];
        request[0] = 0x05; // SOCKS版本
        request[1] = 0x01; // CONNECT命令
        request[2] = 0x00; // 保留字节
        request[3] = addressType; // 地址类型

        // 地址
        System.arraycopy(addressBytes, 0, request, 4, addressBytes.length);

        // 端口
        int port = targetAddr.getPort();
        int offset = 4 + addressBytes.length;
        request[offset] = (byte) (port >> 8);
        request[offset + 1] = (byte) (port & 0xFF);

        return request;
    }

    /**
     * 从连接通道完整读取数据，至少读取指定数量的字节
     *
     * @param sock     连接通道
     * @param buffer   缓冲区
     * @param minBytes 最少读取的字节数
     * @return 实际读取的字节数
     * @throws IOException IO异常
     */
    private int readFully(SocketChannel sock, ByteBuffer buffer, int minBytes) throws IOException {
        int totalRead = 0;
        buffer.clear();

        while (buffer.hasRemaining() || totalRead < minBytes) {
            int read = sock.read(buffer);
            if (read < 0) {
                throw new IOException("Failed to read from socket");
            }
            if (read == 0) {
                // 非阻塞模式下可能返回0
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Read interrupted", e);
                }
            }
            totalRead += read;
        }

        return totalRead;
    }

    /**
     * 获取SOCKS错误码对应的描述信息
     *
     * @param errorCode 错误码
     * @return 错误描述
     */
    private String getSocksErrorDescription(byte errorCode) {
        switch (errorCode) {
            case 0x01: return "General failure";
            case 0x02: return "Connection not allowed by ruleset";
            case 0x03: return "Network unreachable";
            case 0x04: return "Host unreachable";
            case 0x05: return "Connection refused";
            case 0x06: return "TTL expired";
            case 0x07: return "Command not supported";
            case 0x08: return "Address type not supported";
            default: return "Unknown error (" + errorCode + ")";
        }
    }

    /**
     * 从连接通道完整读取数据
     *
     * @param sock   连接通道
     * @param buffer 缓冲区
     * @return 实际读取的字节数
     * @throws IOException IO异常
     */
    private int readFully(SocketChannel sock, ByteBuffer buffer) throws IOException {
        return readFully(sock, buffer, buffer.capacity());
    }


    /**
     * 完整地将数据写入连接通道
     *
     * @param sock   连接通道
     * @param buffer 缓冲区
     * @throws IOException IO异常
     */
    private static void writeFully(SocketChannel sock, ByteBuffer buffer) throws IOException {
        buffer.rewind();
        while (buffer.hasRemaining()) {
            int written = sock.write(buffer);
            if (written < 0) {
                throw new IOException("Failed to write to socket");
            }
        }
    }

    /**
     * 获取代理用户名
     *
     * @return 代理用户名
     */
    public String getProxyUsername() {
        return proxyUsername;
    }

    /**
     * 设置代理用户名
     *
     * @param proxyUsername 代理用户名
     */
    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    /**
     * 获取代理密码
     *
     * @return 代理密码
     */
    public String getProxyPassword() {
        return proxyPassword;
    }

    /**
     * 设置代理密码
     *
     * @param proxyPassword 代理密码
     */
    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }
}
