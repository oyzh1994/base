package cn.oyzh.common.util;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.system.OSUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

/**
 * 资源工具类
 *
 * @author oyzh
 * @since 2023/02/28
 */
public class ResourceUtil {

    private ResourceUtil() {
    }

    /**
     * 获取资源
     *
     * @param url 地址
     * @return URL
     */
    public static URL getResource(String url) {
        URL u = null;
        try {
            u = ResourceUtil.class.getResource(url);
            if (u == null) {
                u = ResourceUtil.class.getClassLoader().getResource(url);
            }
            if (u == null && url.startsWith("/")) {
                u = ResourceUtil.class.getClassLoader().getResource(url.substring(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return u;
    }

    /**
     * 获取资源流
     *
     * @param url 地址
     * @return InputStream
     */
    public static InputStream getResourceAsStream(String url) {
        InputStream stream = null;
        try {
            stream = ResourceUtil.class.getResourceAsStream(url);
            if (stream == null) {
                stream = ResourceUtil.class.getClassLoader().getResourceAsStream(url);
            }
            if (stream == null && url.startsWith("/")) {
                stream = ResourceUtil.class.getClassLoader().getResourceAsStream(url.substring(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    /**
     * 转换为物理地址
     *
     * @param url 地址
     * @return 物理地址
     */
    public static String toExternalUrl(String url) {
        JulLog.info("url:{}", url);
        URL u = getResource(url);
        return u == null ? null : u.toExternalForm();
    }

    /**
     * 转换为物理文件
     *
     * @param url 地址
     * @return 物理地址
     */
    public static String toExternalFile(String url) {
        JulLog.debug("url:{}", url);
        URL u = getResource(url);
        if (u == null) {
            return null;
        }
        String externalForm = u.toExternalForm();
        if (externalForm == null) {
            return null;
        }
        if (externalForm.startsWith("file:/")) {
            externalForm = externalForm.substring(6);
        }
        return externalForm;
    }

    /**
     * 转换为物理地址
     *
     * @param urls 地址列表
     * @return 物理地址列表
     */
    public static List<String> toExternalUrl(String[] urls) {
        return toExternalUrl(Arrays.asList(urls));
    }

    /**
     * 转换为物理地址
     *
     * @param urls 地址列表
     * @return 物理地址列表
     */
    public static List<String> toExternalUrl(List<String> urls) {
        List<String> urlList = new ArrayList<>(urls.size());
        for (String url : urls) {
            urlList.add(toExternalUrl(url));
        }
        return urlList;
    }

    /**
     * 获取本地文件地址
     *
     * @param url 地址
     * @return 本地文件地址
     */
    public static String getLocalFileUrl(String url) {
        url = url.replace("\\", "/");
        if (OSUtil.isWindows()) {
            return "file:/" + url;
        }
        return "file:" + url;
    }

    /**
     * 获取路径
     *
     * @param url 地址
     * @return 本地路径
     */
    public static String getPath(String url) {
        return getPath(url, ResourceUtil.class);
    }

    /**
     * 获取路径
     *
     * @param url   地址
     * @param clazz 类
     * @return 本地路径
     */
    public static String getPath(String url, Class<?> clazz) {
        try {
            URL url1 = clazz.getResource(url);
            if (url1 != null) {
                String path = url1.toURI().getPath();
                if (OSUtil.isWindows() && path.startsWith("/")) {
                    path = path.substring(1);
                }
                return path;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 遍历资源目录下所有文件（含子目录）
     *
     * @param resourceDir 资源目录，例如 "static/images"
     * @return 相对于 resourceDir 的路径列表，例如 "logo/a.png"
     */
    public static List<String> listFiles(String resourceDir) throws IOException, URISyntaxException {
        URL url = getResource(resourceDir);
        if (url == null) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            // 开发环境：文件系统
            Path root = Paths.get(url.toURI());
            try (Stream<Path> stream = Files.walk(root)) {
                stream.filter(Files::isRegularFile)
                        .forEach(p -> result.add(root.relativize(p).toString().replace('\\', '/')));
            }
        } else if ("jar".equals(protocol)) {
            // 打包后：jar 内部
            String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
            try (JarFile jar = new JarFile(jarPath)) {
                String prefix = resourceDir.endsWith("/") ? resourceDir : resourceDir + "/";
                jar.stream()
                        .filter(e -> !e.isDirectory())
                        .map(ZipEntry::getName)
                        .filter(name -> name.startsWith(prefix))
                        .forEach(name -> result.add(name.substring(prefix.length())));
            }
        } else {
            throw new IllegalStateException("不支持的协议: " + protocol);
        }
        Collections.sort(result);
        return result;
    }
}
