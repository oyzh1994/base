package cn.oyzh.common.network;

import cn.oyzh.common.exception.InvalidParamException;
import cn.oyzh.common.thread.ThreadUtil;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author oyzh
 * @since 2025-05-26
 */
public class NetworkUtil {

    /**
     * 是否可达
     *
     * @param host    地址
     * @param port    端口
     * @param timeout 超市时间
     * @return 结果
     */
    public static boolean reachable(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            long startTime = System.currentTimeMillis();
            socket.connect(new InetSocketAddress(host, port), timeout);
            long endtime = System.currentTimeMillis();
            return endtime - startTime < timeout;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 扫描
     *
     * @param start 开始端口
     * @param end   结束端口
     * @param host  地址
     * @return 能联通的端口
     */
    public static List<Integer> scan(int start, int end, String host) {
        if (start < 0 || end < 0 || end > 65535 || end < start) {
            throw new InvalidParamException("start or end param invalid");
        }
        List<Integer> list = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (reachable(host, i, 100)) {
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
     * @param host           地址
     * @param callback       结果回调
     * @param finishCallback 结束回调
     * @return Thread
     */
    public static Thread scanAsync(int start, int end, String host, BiConsumer<Integer, Boolean> callback, Runnable finishCallback) {
        if (start < 0 || end < 0 || end > 65535 || end < start) {
            throw new InvalidParamException("start or end param invalid");
        }
        return ThreadUtil.start(() -> {
            try {
                for (int i = start; i < end; i++) {
                    if (ThreadUtil.isInterrupted()) {
                        break;
                    }
                    if (reachable(host, i, 100)) {
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
     * 获取可能的描述
     *
     * @param port 端口
     * @return 描述
     */
    public static String detectDesc(int port) {
        if (port == 20 || port == 21) {
            return "FTP";
        }
        if (port == 22) {
            return "SSH/SFTP";
        }
        if (port == 23) {
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
        if (port == 80) {
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
        if (port == 443) {
            return "HTTPS";
        }
        if (port == 465) {
            return "SMTPS";
        }
        if (port == 587) {
            return "SMTP";
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
        if (port == 2869) {
            return "UPnP Device Host";
        }
        if (port == 3389) {
            return "RDP";
        }
        if (port == 3478) {
            return "STUN";
        }
        if (port == 5601) {
            return "Kibana";
        }
        if (port == 5800) {
            return "VNC Server";
        }
        if (port == 5900) {
            return "VNC";
        }
        if (port == 6000) {
            return "X-Server";
        }
        if (port == 8080) {
            return "Web Server";
        }
        if (port == 8443) {
            return "Web Server";
        }
        if (port == 9200) {
            return "Elasticsearch";
        }
        if (port == 25565) {
            return "Minecraft";
        }
        // mssql
        if (port == 1433) {
            return "SQL Server";
        }
        if (port == 11433 || port == 21433 || port == 31433 || port == 41433 || port == 51433 || port == 61433) {
            return "SQL Server Suspected";
        }
        // oracle
        if (port == 1521) {
            return "Oracle";
        }
        if (port == 11521 || port == 21521 || port == 31521 || port == 41521 || port == 51521 || port == 61521) {
            return "Oracle Suspected";
        }
        // zk
        if (port == 2281) {
            return "Zookeeper";
        }
        if (port == 12181 || port == 22181 || port == 32181 || port == 42181 || port == 52181 || port == 62181) {
            return "Zookeeper Suspected";
        }
        // mysql
        if (port == 3306) {
            return "Mysql";
        }
        if (port == 13306 || port == 23306 || port == 33306 || port == 43306 || port == 53306 || port == 63306) {
            return "Mysql Suspected";
        }
        // pg
        if (port == 5432) {
            return "PostgreSQL";
        }
        if (port == 15432 || port == 25432 || port == 35432 || port == 45432 || port == 55432 || port == 65432) {
            return "PostgreSQL Suspected";
        }
        // redis
        if (port == 6379) {
            return "Redis";
        }
        if (port == 16379 || port == 26379 || port == 36379 || port == 46379 || port == 56379) {
            return "Redis Suspected";
        }
        // mongo
        if (port == 27017) {
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
