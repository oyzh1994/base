package cn.oyzh.common.util;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * jar工具类
 *
 * @author oyzh
 * @since 2024-12-17
 */
public class JarUtil {

    private JarUtil() {
    }

    /**
     * 获取 JAR 目录路径
     */
    public static String getJarDir() {
        String jarPath = getJarPath();
        if (jarPath != null) {

            return jarPath.substring(0, jarPath.lastIndexOf("/"));
        }
        return null;
    }

    /**
     * 获取 JAR 文件路径
     */
    public static String getJarPath() {
        try {
            URL url = JarUtil.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation();
            String jarPath = URLDecoder.decode(
                    url.getFile(),
                    StandardCharsets.UTF_8.name()
            );
            String path = new File(jarPath).getAbsolutePath();
            path = path.substring(path.indexOf("file:/"), path.indexOf("!/"));
            path = path.substring(5);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
