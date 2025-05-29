package cn.oyzh.common.util;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * jar工具类
 *
 * @author oyzh
 * @since 2024-12-17
 */
public class JarUtil {

    /**
     * 是否运行在jar中
     *
     * @return 结果
     */
    public static boolean isInJar() {
        // 获取当前类的保护域（ProtectionDomain）
        ProtectionDomain protectionDomain = JarUtil.class.getProtectionDomain();
        // 获取保护域的CodeSource
        CodeSource codeSource = protectionDomain.getCodeSource();
        // 获取CodeSource的Location
        URL location = codeSource.getLocation();
        // 检查URL的协议是否为"jar"
        return location.getProtocol().equals("jar");
    }
}
