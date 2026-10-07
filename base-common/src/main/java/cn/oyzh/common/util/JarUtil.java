package cn.oyzh.common.util;

import cn.oyzh.common.compress.CompressUtil;
import cn.oyzh.common.file.FileNameUtil;
import cn.oyzh.common.file.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * jar工具类
 *
 * @author oyzh
 * @since 2024-12-17
 */
public class JarUtil {

    /**
     * 私有构造，禁止实例化
     */
    private JarUtil() {
    }

    /**
     * 获取JAR目录路径
     *
     * @return JAR所在目录路径，非JAR运行时返回null
     */
    public static String getJarDir() {
        String jarPath = getJarPath();
        if (jarPath != null) {
            return jarPath.substring(0, jarPath.lastIndexOf("/"));
        }
        return null;
    }

    /**
     * 获取JAR文件路径
     *
     * @return JAR文件路径，非JAR运行时返回null
     */
    public static String getJarPath() {
        String path = null;
        try {
            CodeSource source = JarUtil.class.getProtectionDomain().getCodeSource();
            if (source != null) {
                URL url = source.getLocation();
                String jarPath = URLDecoder.decode(
                        url.getPath(),
                        StandardCharsets.UTF_8
                );
                path = new File(jarPath).getAbsolutePath();
                if (path.contains("file:/") && path.contains("/!")) {
                    path = path.substring(path.indexOf("file:/") + 5, path.indexOf("/!"));
                } else if (path.contains("nested:/") && path.contains("/!")) {
                    path = path.substring(path.indexOf("nested:/") + 7, path.indexOf("/!"));
                } else {
                    path = null;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return path;
    }

    /**
     * 是否运行在jar中的缓存结果
     */
    private static Boolean isInJar;

    /**
     * 判断是否运行在jar中
     *
     * @return 运行在jar中返回true，否则返回false
     */
    public static boolean isInJar() {
        if (isInJar == null) {
            synchronized (JarUtil.class) {
                try {
                    // 获取当前类的保护域（ProtectionDomain）
                    ProtectionDomain protectionDomain = JarUtil.class.getProtectionDomain();
                    // 获取保护域的CodeSource
                    CodeSource codeSource = protectionDomain.getCodeSource();
                    if (codeSource != null) {
                        // 获取CodeSource的Location
                        URL location = codeSource.getLocation();
                        // 检查URL的协议是否为"jar"
                        isInJar = location.getProtocol().equals("jar");
                    }
                } catch (Exception ex) {
                    String className = JarUtil.class.getName().replace('.', '/') + ".class";
                    String classPath = JarUtil.class.getResource("/" + className).toString();
                    isInJar = classPath.startsWith("jar:");
                }
            }
        }
        return isInJar;
    }

    /**
     * 判断JAR文件中是否包含class文件
     *
     * @param jarFile jar文件路径
     * @return 包含class文件返回true，否则返回false
     * @throws IOException 读取jar文件失败时抛出
     */
    public static boolean hasClass(String jarFile) throws IOException {
        if (!FileUtil.exists(jarFile)) {
            throw new RuntimeException("jarFile " + jarFile + " is not exist.");
        }
        if (!FileUtil.isFile(jarFile)) {
            throw new RuntimeException("jarFile " + jarFile + " is not file.");
        }
        try (
                FileInputStream fis = new FileInputStream(jarFile);
                JarInputStream jarIn = new JarInputStream(fis)
        ) {
            while (true) {
                ZipEntry entry = jarIn.getNextJarEntry();
                if (entry == null) {
                    break;
                }
                if (isClass(entry.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 解压jar
     *
     * @param src     源文件
     * @param destDir 目标目录
     * @return 解压后的目标目录文件
     */
    public static File unJar(String src, String destDir) {
        if (!FileUtil.exists(src)) {
            throw new RuntimeException("src " + src + " is not exist.");
        }
        if (!FileUtil.isFile(src)) {
            throw new RuntimeException("src " + src + " is not file.");
        }
        if (FileUtil.exists(destDir) && !FileUtil.isDirectory(destDir)) {
            throw new RuntimeException("destDir " + destDir + " is not dir.");
        }
        return CompressUtil.unzip(src, destDir);
    }


    /**
     * 判断文件是否jar
     *
     * @param file 文件
     * @return 是jar文件返回true，否则返回false
     */
    public static boolean isJar(File file) {
        if (file == null || !file.isFile() || !file.exists()) {
            return false;
        }
        return isJar(file.getName());
    }

    /**
     * 判断名称是否为jar
     *
     * @param name 名称
     * @return 是jar返回true，否则返回false
     */
    public static boolean isJar(String name) {
        return FileNameUtil.isJarType(FileNameUtil.extName(name));
    }

    /**
     * 判断文件是否class文件
     *
     * @param file 文件
     * @return 是class文件返回true，否则返回false
     */
    public static boolean isClass(File file) {
        if (file == null || !file.isFile() || !file.exists()) {
            return false;
        }
        return isClass(file.getName());
    }

    /**
     * 判断名称是否为class文件
     *
     * @param name 名称
     * @return 是class文件返回true，否则返回false
     */
    public static boolean isClass(String name) {
        return FileNameUtil.isClassType(FileNameUtil.extName(name));
    }
}
