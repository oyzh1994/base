package cn.oyzh.common.network;

import cn.oyzh.common.exception.InvalidParamException;
import cn.oyzh.common.function.ExceptionBiConsumer;
import cn.oyzh.common.thread.ThreadUtil;
import cn.oyzh.common.util.CollectionUtil;

import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CancellationException;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

/**
 * 网络工具类
 *
 * @author oyzh
 * @since 2025-05-26
 */
public class NetworkUtil {

    /**
     * FTP服务默认端口
     */
    public static final short FTP_PORT = 21;

    /**
     * SSH服务默认端口
     */
    public static final short SSH_PORT = 22;

    /**
     * HTTP服务默认端口
     */
    public static final short HTTP_PORT = 80;

    /**
     * VNC服务默认端口
     */
    public static final short VNC_PORT = 5900;

    /**
     * RDP服务默认端口
     */
    public static final short RDP_PORT = 3389;

    /**
     * HTTPS服务默认端口
     */
    public static final short HTTPS_PORT = 443;

    /**
     * Telnet服务默认端口
     */
    public static final short TELNET_PORT = 23;

    /**
     * RTSP服务默认端口
     */
    public static final short RTSP_PORT = 554;

    /**
     * RLogin服务默认端口
     */
    public static final short RLOGIN_PORT = 513;

    /**
     * Mysql服务默认端口
     */
    public static final short Mysql_PORT = 3306;

    /**
     * Redis服务默认端口
     */
    public static final short Redis_PORT = 6379;

    /**
     * Oracle服务默认端口
     */
    public static final short Oracle_PORT = 1521;

    /**
     * MongoDB服务默认端口
     */
    public static final short MongoDB_PORT = 27017;

    /**
     * Zookeeper服务默认端口
     */
    public static final short Zookeeper_PORT = 2281;

    /**
     * PostgreSQL服务默认端口
     */
    public static final short PostgreSQL_PORT = 5432;

    /**
     * Memcached服务默认端口
     */
    public static final short Memcached_PORT = 11211;

    /**
     * SQL Server服务默认端口
     */
    public static final short SQLServer_PORT = 1433;

    /**
     * Elasticsearch服务默认端口
     */
    public static final short Elasticsearch_PORT = 9200;

    /**
     * 表示连接被重置或提前断开的异常简单类名
     */
    private static final Set<String> RESET_EXCEPTION_SIMPLE_NAMES = Set.of(
            "NoHttpResponseException",
            "PrematureChannelClosureException"
    );

    /**
     * 连接被重置或提前断开的常见消息片段
     */
    private static final List<String> RESET_MESSAGE_FRAGMENTS = List.of(
            "connectionreset",
            "connectionwasreset",
            "connectionhasbeenreset",
            "resetbypeer",
            "networkdroppedconnectiononreset",
            "softwarecausedconnectionabort",
            "connectionaborted",
            "brokenpipe",
            "socketwriteerror",
            "unexpectedendofstream",
            "unexpectedendoffile",
            "unexpectedendofinput",
            "prematureendofstream",
            "unexpectedeof",
            "prematureeof",
            "streamendedunexpectedly",
            "incompletechunkedencoding",
            "connectionclosedprematurely",
            "prematureconnectionclose",
            "connectionwasclosedunexpectedly",
            "connectionclosedbypeer",
            "peerclosedtheconnection",
            "remoteclosedtheconnection",
            "receivedagoaway",
            "refusedstream",
            "连接被重置",
            "连接被重设",
            "连接重置",
            "重置连接",
            "对等方重置连接",
            "对端重置连接",
            "对方重置连接",
            "连接被对端重置",
            "连接被对方重置",
            "远程主机强迫关闭了一个现有的连接",
            "远程主机强制关闭了一个现有的连接",
            "远程主机强行关闭了一个现有的连接",
            "远程主机强迫关闭了一个现有连接",
            "远程主机强制关闭了一个现有连接",
            "你的主机中的软件中止了一个已建立的连接",
            "你的主机中的软件中止了一个已建立连接",
            "软件导致连接中止",
            "重置时网络连接断开",
            "管道破裂",
            "套接字写入错误",
            "套接字写错误",
            "流意外结束",
            "输入流意外结束",
            "意外的流结尾",
            "意外的输入结尾",
            "连接过早关闭",
            "连接提前关闭",
            "连接被过早关闭",
            "对端关闭了连接",
            "对方关闭了连接",
            "远端关闭了连接",
            "连接已意外关闭",
            "遠端主機強制關閉了一個現有的連線",
            "遠端主機強迫關閉了一個現有的連線",
            "您的主機中的軟體中斷了一個已建立的連線",
            "連線被重設",
            "連線被重置",
            "管道已破裂"
    );

    /**
     * Winsock连接重置相关错误码
     */
    private static final Pattern WINSOCK_RESET_CODE_PATTERN = Pattern.compile(
            "(?:wsae(?:netreset|connaborted|connreset)|(?:winerror|oserror|errno|error|错误代码|错误码|错误)"
                    + "(?:10052|10053|10054)(?![0-9]))");

    /**
     * 消息分隔符
     */
    private static final Pattern MESSAGE_SEPARATORS = Pattern.compile("[\\s\\p{Z}\\p{P}\\p{S}]+");

    /**
     * 是否为连接被重置、中止或提前断开
     *
     * @param throwable 异常
     * @return 结果
     */
    public static boolean isConnectionReset(Throwable throwable) {
        Set<Throwable> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        Throwable current = throwable;
        while (current != null && visited.add(current)) {
            if (current instanceof InterruptedException
                    || current instanceof InterruptedIOException
                    || current instanceof CancellationException) {
                return false;
            }

            if (RESET_EXCEPTION_SIMPLE_NAMES.contains(current.getClass().getSimpleName())) {
                return true;
            }

            String message = current.getMessage();
            if (message != null) {
                String compact = MESSAGE_SEPARATORS.matcher(
                        message.toLowerCase(Locale.ROOT)).replaceAll("");
                if (containsResetFragment(compact)
                        || WINSOCK_RESET_CODE_PATTERN.matcher(compact).find()
                        || isStandaloneWinsockResetCode(compact)) {
                    return true;
                }
            }
            current = current.getCause();
        }
        return false;
    }

    /**
     * 是否包含连接重置消息片段
     *
     * @param compactMessage 压缩后的消息
     * @return 结果
     */
    private static boolean containsResetFragment(String compactMessage) {
        for (String fragment : RESET_MESSAGE_FRAGMENTS) {
            if (compactMessage.contains(fragment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为独立的Winsock连接重置错误码
     *
     * @param compactMessage 压缩后的消息
     * @return 结果
     */
    private static boolean isStandaloneWinsockResetCode(String compactMessage) {
        return compactMessage.equals("10052")
                || compactMessage.equals("10053")
                || compactMessage.equals("10054")
                || compactMessage.equals("wsaenetreset")
                || compactMessage.equals("wsaeconnaborted")
                || compactMessage.equals("wsaeconnreset");
    }

    /**
     * 是否可达
     *
     * @param host    地址
     * @param port    端口
     * @param timeout 超时时间
     * @return 结果
     */
    public static boolean reachable(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            long startTime = System.currentTimeMillis();
            socket.connect(new InetSocketAddress(host, port), timeout);
            long endTime = System.currentTimeMillis();
            return endTime - startTime <= timeout;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 扫描
     *
     * @param start   开始端口
     * @param end     结束端口
     * @param host    地址
     * @param timeout 超时时间
     * @return 能联通的端口
     */
    public static List<Integer> scan(int start, int end, String host, int timeout) {
        if (start < 0 || end < 0 || end > 65535 || end < start) {
            throw new InvalidParamException("start or end param invalid");
        }
        List<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (reachable(host, i, timeout)) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * 异步扫描
     *
     * @param start          开始端口
     * @param end            结束端口
     * @param timeout        超时时间
     * @param host           地址
     * @param callback       结果回调
     * @param finishCallback 结束回调
     * @return 执行扫描任务的线程
     */
    public static Thread scanAsync(int start, int end, int timeout, String host, BiConsumer<Integer, Boolean> callback, Runnable finishCallback) {
        if (start < 0 || end < 0 || end > 65535 || end < start) {
            throw new InvalidParamException("start or end param invalid");
        }
        return ThreadUtil.start(() -> {
            try {
                for (int i = start; i <= end; i++) {
                    if (ThreadUtil.isInterrupted()) {
                        break;
                    }
                    if (reachable(host, i, timeout)) {
                        callback.accept(i, true);
                    } else {
                        callback.accept(i, false);
                    }
                }
            } finally {
                if (finishCallback != null) {
                    finishCallback.run();
                }
            }
        });
    }

    /**
     * 多线程扫描
     *
     * @param start          开始端口
     * @param end            结束端口
     * @param timeout        超时时间
     * @param threadNum      线程数量
     * @param host           地址
     * @param callback       结果回调
     * @param finishCallback 结束回调
     * @return 执行扫描任务的线程
     */
    public static Thread scanMultiple(int start, int end, int timeout, int threadNum, String host, ExceptionBiConsumer<Integer, Boolean> callback, Runnable finishCallback) {
        if (start < 0 || end < 0 || end > 65535 || end < start) {
            throw new InvalidParamException("start or end param invalid");
        }
        return ThreadUtil.start(() -> {
            // 子线程列表
            List<Thread> threads = new ArrayList<>();
            try {
                // 端口列表
                List<Integer> ports = new ArrayList<>();
                for (int i = start; i <= end; i++) {
                    ports.add(i);
                }
                // 分割集合
                List<List<Integer>> lists = CollectionUtil.splitIntoParts(ports, threadNum);
                // 等待队列
                CountDownLatch latch = new CountDownLatch(lists.size());
                // 每一个子集合都在子线程处理
                for (List<Integer> list : lists) {
                    Thread thread = ThreadUtil.start(() -> {
                        try {
                            for (Integer i : list) {
                                if (ThreadUtil.isInterrupted()) {
                                    break;
                                }
                                if (reachable(host, i, timeout)) {
                                    callback.accept(i, true);
                                } else {
                                    callback.accept(i, false);
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    });
                    threads.add(thread);
                }
                // 等待完成
                latch.await();
            } catch (Exception ex) {
                // 取消线程的执行
                threads.forEach(ThreadUtil::interrupt);
            } finally {
                if (finishCallback != null) {
                    finishCallback.run();
                }
            }
        });
    }

    /**
     * 获取可能的描述
     *
     * @param port 端口
     * @return 描述
     */
    public static String detectDesc(int port) {
        if (port == 20 || port == FTP_PORT) {
            return "FTP";
        }
        if (port == SSH_PORT) {
            return "SSH/SFTP";
        }
        if (port == TELNET_PORT) {
            return "Telnet";
        }
        if (port == 25) {
            return "SMTP";
        }
        if (port == 53) {
            return "DNS";
        }
        if (port == 67 || port == 68) {
            return "DHCP";
        }
        if (port == 69) {
            return "TFTP";
        }
        if (port == HTTP_PORT) {
            return "HTTP";
        }
        if (port == 110) {
            return "POP3";
        }
        if (port == 119) {
            return "NNTP";
        }
        if (port == 123) {
            return "NTP";
        }
        if (port == 135) {
            return "Windows RPC";
        }
        if (port == 139) {
            return "NetBIOS";
        }
        if (port == 445) {
            return "SMB/CIFS";
        }
        if (port == 143) {
            return "IMAP";
        }
        if (port == 161 || port == 162) {
            return "SNMP";
        }
        if (port == 194) {
            return "IRC";
        }
        if (port == HTTPS_PORT) {
            return "HTTPS";
        }
        if (port == 465) {
            return "SMTPS";
        }
        if (port == RLOGIN_PORT) {
            return "RLogin";
        }
        if (port == 554) {
            return "RTSP";
        }
        if (port == 587) {
            return "SMTP";
        }
        if (port == 902) {
            return "VMWare Server";
        }
        if (port == 912) {
            return "VMWare ESXi";
        }
        if (port == 993) {
            return "IMAPS";
        }
        if (port == 995) {
            return "POP3S";
        }
        if (port == 1119) {
            return "Steam";
        }
        if (port == 1801) {
            return "Microsoft BizTalk Server";
        }
        if (port == 2103) {
            return "MSMQ";
        }
        if (port == 2105) {
            return "Kerberos Encrypted Rlogin";
        }
        if (port == 2107) {
            return "Bintec Citrix SmartAuditor Server";
        }
        if (port == 2179) {
            return "Microsoft RDP for virtual machines";
        }
        if (port == 2869) {
            return "UPnP Device Host";
        }
        if (port == RDP_PORT) {
            return "RDP";
        }
        if (port == 3478) {
            return "STUN";
        }
        if (port == 4709) {
            return "WPS Office";
        }
        if (port == 5601) {
            return "Kibana";
        }
        if (port == 5800) {
            return "VNC Server";
        }
        if (port == VNC_PORT) {
            return "VNC";
        }
        if (port == 6000) {
            return "X-Server";
        }
        if (port == 7680) {
            return "Windows Update Delivery Optimization";
        }
        if (port == 8080) {
            return "Web Server";
        }
        if (port == 8081) {
            return "Everything/Web Server";
        }
        if (port == 8443) {
            return "Web Server";
        }
        if (port == Elasticsearch_PORT) {
            return "Elasticsearch";
        }
        if (port == 25565) {
            return "Minecraft";
        }
        // mssql
        if (port == SQLServer_PORT) {
            return "SQL Server";
        }
        if (port == 11433 || port == 21433 || port == 31433 || port == 41433 || port == 51433 || port == 61433) {
            return "SQL Server Suspected";
        }
        // oracle
        if (port == Oracle_PORT) {
            return "Oracle";
        }
        if (port == 11521 || port == 21521 || port == 31521 || port == 41521 || port == 51521 || port == 61521) {
            return "Oracle Suspected";
        }
        // zk
        if (port == Zookeeper_PORT) {
            return "Zookeeper";
        }
        if (port == 12181 || port == 22181 || port == 32181 || port == 42181 || port == 52181 || port == 62181) {
            return "Zookeeper Suspected";
        }
        // mysql
        if (port == Mysql_PORT) {
            return "Mysql";
        }
        if (port == 13306 || port == 23306 || port == 33306 || port == 43306 || port == 53306 || port == 63306) {
            return "Mysql Suspected";
        }
        // pg
        if (port == PostgreSQL_PORT) {
            return "PostgreSQL";
        }
        if (port == 15432 || port == 25432 || port == 35432 || port == 45432 || port == 55432 || port == 65432) {
            return "PostgreSQL Suspected";
        }
        // redis
        if (port == Redis_PORT) {
            return "Redis";
        }
        if (port == 16379 || port == 26379 || port == 36379 || port == 46379 || port == 56379) {
            return "Redis Suspected";
        }
        // 11211
        if (port == Memcached_PORT) {
            return "Memcached";
        }
        // mongo
        if (port == MongoDB_PORT) {
            return "MongoDB";
        }
        if (port == 37017 || port == 47017 || port == 57017) {
            return "MongoDB Suspected";
        }
        // dubbo
        if (port == 9090) {
            return "Dubbo Manager";
        }
        if (port == 20880) {
            return "Dubbo Provider";
        }
        if (port == 20881 || port == 20882 || port == 20883 || port == 20884 || port == 20885 || port == 20886 || port == 20887 || port == 20888 || port == 20889) {
            return "Dubbo Monitor";
        }
        return "Unknown";
    }

}
