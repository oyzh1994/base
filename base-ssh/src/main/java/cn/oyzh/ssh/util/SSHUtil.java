package cn.oyzh.ssh.util;


import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.system.OSUtil;
import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.ssh.domain.SSHProxyConfig;
import com.jcraft.jsch.AgentConnector;
import com.jcraft.jsch.AgentIdentityRepository;
import com.jcraft.jsch.AgentProxyException;
import com.jcraft.jsch.PageantConnector;
import com.jcraft.jsch.Proxy;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.ProxySOCKS4;
import com.jcraft.jsch.ProxySOCKS5;
import com.jcraft.jsch.SSHAgentConnector;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


/**
 * ssh工具类
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class SSHUtil {

    /**
     * 排除端口
     */
    private final static Set<Integer> EXCLUDED_PORT = new HashSet<>();

    static {
        EXCLUDED_PORT.add(21);
        EXCLUDED_PORT.add(22);
        EXCLUDED_PORT.add(23);
        EXCLUDED_PORT.add(25);
        EXCLUDED_PORT.add(53);
        EXCLUDED_PORT.add(80);
        EXCLUDED_PORT.add(99);
        EXCLUDED_PORT.add(110);
        EXCLUDED_PORT.add(113);
        EXCLUDED_PORT.add(119);
        EXCLUDED_PORT.add(135);
        EXCLUDED_PORT.add(137);
        EXCLUDED_PORT.add(138);
        EXCLUDED_PORT.add(139);
        EXCLUDED_PORT.add(143);
        EXCLUDED_PORT.add(161);
        EXCLUDED_PORT.add(163);
        EXCLUDED_PORT.add(389);
        EXCLUDED_PORT.add(443);
        EXCLUDED_PORT.add(445);
        EXCLUDED_PORT.add(1433);
        EXCLUDED_PORT.add(2181);
        EXCLUDED_PORT.add(3306);
        EXCLUDED_PORT.add(3389);
        EXCLUDED_PORT.add(6379);
        EXCLUDED_PORT.add(8080);
    }

    /**
     * 寻找可用的端口
     *
     * @return 结果
     */
    public static int findAvailablePort() {
        // 默认从10000-12000区间里面找
        return findAvailablePort(null, 10_000, 12_000);
    }

    /**
     * 寻找可用的端口
     *
     * @param excludes 排除的列表
     * @return 结果
     */
    public static int findAvailablePort(Set<Integer> excludes) {
        // 默认从10000-12000区间里面找
        return findAvailablePort(excludes, 10_000, 12_000);
    }

    /**
     * 寻找可用的端口
     *
     * @param excludes 排除的列表
     * @param start    开始端口
     * @param end      结束端口
     * @return 可用端口，-1代表未找到
     */
    public static int findAvailablePort(Set<Integer> excludes, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (excludes != null && excludes.contains(i)) {
                continue;
            }
            if (EXCLUDED_PORT.contains(i)) {
                continue;
            }
            if (isPortAvailable(i, 100)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 检查本地端口是否可用
     *
     * @param port    端口
     * @param timeout 超时
     * @return 结果
     */
    public static boolean isPortAvailable(int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("127.0.0.1", port), timeout);
            // 端口被占用
            return false;
        } catch (IOException ignored) {
        }
        // 端口未被占用
        return true;
    }

//    /**
//     * 转换为pem格式
//     *
//     * @param keyType    密钥类型
//     * @param privateKey 密钥
//     * @return 结果
//     */
//    public static String formatPem(String keyType, String privateKey) {
//        StringBuilder pem = new StringBuilder();
//        pem.append("-----BEGIN ").append(keyType).append("-----\n");
//        int lineLength = 64;
//        for (int i = 0; i < privateKey.length(); i += lineLength) {
//            pem.append(privateKey, i, Math.min(i + lineLength, privateKey.length())).append("\n");
//        }
//        pem.append("-----END ").append(keyType).append("-----");
//        return pem.toString();
//    }
//
//    /**
//     * 转换为pem格式
//     *
//     * @param keyType    密钥类型
//     * @param privateKey 密钥
//     * @return 结果
//     */
//    public static String formatPem1(String keyType, String privateKey) {
//        StringBuilder pem = new StringBuilder();
//        pem.append("-----BEGIN ").append(keyType).append("-----\n");
//        pem.append(privateKey).append("\n");
//        pem.append("-----END ").append(keyType).append("-----");
//        return pem.toString();
//    }

    /**
     * 初始化代理
     *
     * @param proxyConfig 代理配置
     * @return 代理对象
     */
    public static Proxy newProxy(SSHProxyConfig proxyConfig) {
        Proxy proxy = null;
        if (proxyConfig.isHttpProxy()) {
            ProxyHTTP http = new ProxyHTTP(proxyConfig.getHost(), proxyConfig.getPort());
            if (proxyConfig.isPasswordAuth()) {
                http.setUserPasswd(proxyConfig.getUser(), proxyConfig.getPassword());
            }
            proxy = http;
        } else if (proxyConfig.isSocks4Proxy()) {
            ProxySOCKS4 socks4 = new ProxySOCKS4(proxyConfig.getHost(), proxyConfig.getPort());
            if (proxyConfig.isPasswordAuth()) {
                socks4.setUserPasswd(proxyConfig.getUser(), proxyConfig.getPassword());
            }
            proxy = socks4;
        } else if (proxyConfig.isSocks5Proxy()) {
            ProxySOCKS5 socks5 = new ProxySOCKS5(proxyConfig.getHost(), proxyConfig.getPort());
            if (proxyConfig.isPasswordAuth()) {
                socks5.setUserPasswd(proxyConfig.getUser(), proxyConfig.getPassword());
            }
            proxy = socks5;
        }
        return proxy;
    }

    /**
     * 移除ansi字符
     *
     * @param output 内容
     * @return 结果
     */
    public static String removeAnsi(String output) {
        if (StringUtil.isBlank(output)) {
            return output;
        }
        String ansiRegex = "\u001B\\[[;\\d]*[ -/]*[@-~]";
        output = output.replaceAll(ansiRegex, "");
        return output;
    }

    /**
     * 移除控制字符
     *
     * @param output 内容
     * @return 结果
     */
    public static String removeControl(String output) {
        if (StringUtil.isBlank(output)) {
            return output;
        }
        if (output.contains("\b")) {
            output = output.replace("\b", "");
        }
        if (output.contains("\\a")) {
            output = output.replace("\\a", "");
        }
        if (output.contains("\r")) {
            output = output.replace("\r", "");
        }
        if (output.contains("\t")) {
            output = output.replace("\t", "");
        }
        if (output.contains("\n")) {
            output = output.replace("\n", "");
        }
        return output;
    }

    /**
     * 初始化代理证书仓库
     *
     * @return AgentIdentityRepository
     * @throws AgentProxyException 异常
     */
    public static AgentIdentityRepository initAgentIdentityRepository() throws AgentProxyException {
        AgentConnector connector;
        if (OSUtil.isWindows()) {
            connector = new PageantConnector();
        } else {
            connector = new SSHAgentConnector();
        }
        if (!connector.isAvailable()) {
            throw new AgentProxyException("AgentConnector is not available");
        }
        return new AgentIdentityRepository(connector);
    }

    /**
     * 获取ssh agent的sock文件
     *
     * @return 结果
     */
    public static String getSSHAgentSockFile() {
        String sockFile = null;
        String file = System.getenv("SSH_AUTH_SOCK");
        boolean findMore = false;
        if (FileUtil.exist(file)) {
            // 注意，这个SSH_AUTH_SOCK值未必准确，可能需要深入查找
            String res = RuntimeUtil.execForStr("ssh-add -l");
            if (StringUtil.contains(res, "The agent has no identities")) {
                findMore = true;
            } else {
                sockFile = file;
            }
        } else {
            findMore = true;
        }
        // 寻找更深层次的文件
        if (findMore && OSUtil.isMacOS()) {
            String tmpdir = System.getenv("TMPDIR");
            File tmp = new File(tmpdir);
            if (tmp.exists() && tmp.isDirectory()) {
                File[] files2 = tmp.listFiles();
                if (files2 != null) {
                    f1:
                    for (File file1 : files2) {
                        if (file1.isDirectory() && file1.getName().startsWith("ssh-")) {
                            File[] files3 = file1.listFiles();
                            if (files3 != null) {
                                for (File file2 : files3) {
                                    sockFile = file2.getPath();
                                    break f1;
                                }
                            }
                        }
                    }
                }
            }
        }
        return sockFile;
    }
}
