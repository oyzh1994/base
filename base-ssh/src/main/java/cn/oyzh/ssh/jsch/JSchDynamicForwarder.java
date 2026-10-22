package cn.oyzh.ssh.jsch;

import cn.oyzh.common.log.JulLog;
import com.jcraft.jsch.ChannelDirectTCPIP;
import com.jcraft.jsch.Session;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SOCKS4/SOCKS5 dynamic forwarding backed by JSch direct-tcpip channels.
 */
public class JSchDynamicForwarder implements AutoCloseable {

    private final Session session;

    private final ServerSocket serverSocket;

    private final Set<Connection> connections = ConcurrentHashMap.newKeySet();

    private final AtomicBoolean closed = new AtomicBoolean();

    private final int connectTimeout;

    private final Thread acceptThread;

    public JSchDynamicForwarder(Session session, String bindAddress, int port, int connectTimeout) throws IOException {
        this.session = session;
        this.connectTimeout = connectTimeout > 0 ? connectTimeout : 30_000;
        this.serverSocket = new ServerSocket();
        this.serverSocket.setReuseAddress(true);
        InetSocketAddress address = bindAddress == null || bindAddress.isBlank() || "*".equals(bindAddress)
                ? new InetSocketAddress(port)
                : new InetSocketAddress(bindAddress, port);
        this.serverSocket.bind(address);
        this.acceptThread = Thread.ofPlatform()
                .name("jsch-dynamic-forwarder-" + this.getLocalPort())
                .daemon(true)
                .unstarted(this::acceptLoop);
        this.acceptThread.start();
    }

    public int getLocalPort() {
        return this.serverSocket.getLocalPort();
    }

    private void acceptLoop() {
        while (!this.closed.get() && !this.serverSocket.isClosed()) {
            try {
                Socket socket = this.serverSocket.accept();
                socket.setTcpNoDelay(true);
                Connection connection = new Connection(socket);
                this.connections.add(connection);
                Thread.ofVirtual().name("jsch-socks-connection-" + socket.getPort()).start(() -> {
                    try {
                        connection.run();
                    } catch (IOException ex) {
                        if (!closed.get()) {
                            JulLog.debug("jsch dynamic forwarding connection closed: {}", ex.getMessage());
                        }
                    } finally {
                        this.connections.remove(connection);
                        connection.close();
                    }
                });
            } catch (SocketException ex) {
                if (!this.closed.get()) {
                    JulLog.warn("jsch dynamic forwarding accept failed", ex);
                }
            } catch (IOException ex) {
                if (!this.closed.get()) {
                    JulLog.warn("jsch dynamic forwarding accept failed", ex);
                }
            }
        }
    }

    @Override
    public void close() {
        if (!this.closed.compareAndSet(false, true)) {
            return;
        }
        try {
            this.serverSocket.close();
        } catch (IOException ignored) {
        }
        for (Connection connection : this.connections) {
            connection.close();
        }
        this.connections.clear();
        this.acceptThread.interrupt();
    }

    private final class Connection implements AutoCloseable {

        private final Socket socket;

        private volatile ChannelDirectTCPIP channel;

        private Connection(Socket socket) {
            this.socket = socket;
        }

        private void run() throws IOException {
            this.socket.setSoTimeout(JSchDynamicForwarder.this.connectTimeout);
            try {
                DataInputStream input = new DataInputStream(this.socket.getInputStream());
                int version = input.readUnsignedByte();
                if (version == 4) {
                    this.runSocks4(input);
                } else if (version == 5) {
                    this.runSocks5(input);
                } else {
                    throw new IOException("unsupported SOCKS version: " + version);
                }
            } finally {
                this.socket.setSoTimeout(0);
            }
        }

        private void runSocks4(DataInputStream input) throws IOException {
            int command = input.readUnsignedByte();
            int port = input.readUnsignedShort();
            byte[] address = readExact(input, 4);
            readUntilNull(input);
            String host;
            if (address[0] == 0 && address[1] == 0 && address[2] == 0 && address[3] == 1) {
                host = readUntilNull(input);
            } else {
                host = InetAddress.getByAddress(address).getHostAddress();
            }
            if (command != 1) {
                this.writeSocks4Reply(false);
                throw new IOException("unsupported SOCKS4 command: " + command);
            }
            try {
                this.openChannel(host, port);
            } catch (Exception ex) {
                this.writeSocks4Reply(false);
                throw new IOException("SOCKS4 connect failed: " + host + ":" + port, ex);
            }
            this.writeSocks4Reply(true);
            this.pipe();
        }

        private void runSocks5(DataInputStream input) throws IOException {
            int methods = input.readUnsignedByte();
            boolean noAuth = false;
            for (int index = 0; index < methods; index++) {
                noAuth |= input.readUnsignedByte() == 0;
            }
            OutputStream output = this.socket.getOutputStream();
            output.write(new byte[]{5, noAuth ? (byte) 0 : (byte) 0xFF});
            output.flush();
            if (!noAuth) {
                throw new IOException("SOCKS5 authentication is not supported");
            }

            int version = input.readUnsignedByte();
            int command = input.readUnsignedByte();
            input.readUnsignedByte();
            int addressType = input.readUnsignedByte();
            String host;
            if (addressType == 1) {
                host = InetAddress.getByAddress(readExact(input, 4)).getHostAddress();
            } else if (addressType == 3) {
                int length = input.readUnsignedByte();
                host = new String(readExact(input, length), StandardCharsets.UTF_8);
            } else if (addressType == 4) {
                host = InetAddress.getByAddress(readExact(input, 16)).getHostAddress();
            } else {
                this.writeSocks5Reply(8);
                throw new IOException("unsupported SOCKS5 address type: " + addressType);
            }
            int port = input.readUnsignedShort();
            if (version != 5 || command != 1) {
                this.writeSocks5Reply(7);
                throw new IOException("unsupported SOCKS5 request");
            }
            try {
                this.openChannel(host, port);
            } catch (Exception ex) {
                this.writeSocks5Reply(5);
                throw new IOException("SOCKS5 connect failed: " + host + ":" + port, ex);
            }
            this.writeSocks5Reply(0);
            this.pipe();
        }

        private void openChannel(String host, int port) throws Exception {
            if (host == null || host.isBlank() || port < 1 || port > 65535) {
                throw new IOException("invalid forwarding target: " + host + ":" + port);
            }
            ChannelDirectTCPIP channel = (ChannelDirectTCPIP) JSchDynamicForwarder.this.session.openChannel("direct-tcpip");
            channel.setHost(host);
            channel.setPort(port);
            InetSocketAddress remote = (InetSocketAddress) this.socket.getRemoteSocketAddress();
            channel.setOrgIPAddress(remote.getAddress().getHostAddress());
            channel.setOrgPort(remote.getPort());
            this.channel = channel;
            try {
                channel.connect(JSchDynamicForwarder.this.connectTimeout);
            } catch (Exception ex) {
                channel.disconnect();
                this.channel = null;
                throw ex;
            }
        }

        private void pipe() throws IOException {
            // 协商阶段使用连接超时；进入数据转发后必须允许长连接空闲。
            this.socket.setSoTimeout(0);
            ChannelDirectTCPIP channel = this.channel;
            CountDownLatch latch = new CountDownLatch(2);
            Thread.ofVirtual().start(() -> {
                try {
                    this.copy(this.socket.getInputStream(), channel.getOutputStream());
                    channel.getOutputStream().close();
                } catch (IOException ex) {
                    this.close();
                } finally {
                    latch.countDown();
                }
            });
            Thread.ofVirtual().start(() -> {
                try {
                    this.copy(channel.getInputStream(), this.socket.getOutputStream());
                    this.socket.shutdownOutput();
                } catch (IOException ex) {
                    this.close();
                } finally {
                    latch.countDown();
                }
            });
            try {
                latch.await();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IOException("SOCKS proxy pipe interrupted", ex);
            } finally {
                this.close();
            }
        }

        private void copy(InputStream input, OutputStream output) throws IOException {
            input.transferTo(output);
            output.flush();
        }

        private void writeSocks4Reply(boolean success) throws IOException {
            // SSH direct-tcpip 不暴露远端绑定地址，使用规范允许的未知地址占位。
            OutputStream output = this.socket.getOutputStream();
            output.write(new byte[]{0, success ? (byte) 90 : (byte) 91, 0, 0, 0, 0, 0, 0});
            output.flush();
        }

        private void writeSocks5Reply(int status) throws IOException {
            // SSH direct-tcpip 不返回 BND.ADDR/BND.PORT，使用 0.0.0.0:0 表示未知。
            OutputStream output = this.socket.getOutputStream();
            output.write(new byte[]{5, (byte) status, 0, 1, 0, 0, 0, 0, 0, 0});
            output.flush();
        }

        @Override
        public void close() {
            if (this.channel != null) {
                this.channel.disconnect();
            }
            try {
                this.socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private static String readUntilNull(DataInputStream input) throws IOException {
        StringBuilder result = new StringBuilder();
        int value;
        while ((value = input.read()) != 0) {
            if (value < 0) {
                throw new EOFException();
            }
            if (result.length() >= 1024) {
                throw new IOException("SOCKS field is too long");
            }
            result.append((char) value);
        }
        return result.toString();
    }

    private static byte[] readExact(DataInputStream input, int length) throws IOException {
        byte[] result = input.readNBytes(length);
        if (result.length != length) {
            throw new EOFException("incomplete SOCKS request");
        }
        return result;
    }
}
