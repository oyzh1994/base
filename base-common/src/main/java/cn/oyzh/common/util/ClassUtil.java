package cn.oyzh.common.util;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class ClassUtil {

    /**
     * 私有构造，禁止实例化
     */
    private ClassUtil() {
    }

    /**
     * 创建实例，使用public无参构造方法
     *
     * @param clazz 类
     * @param <T>   类型
     * @return 实例对象，无可用构造方法时返回null
     */
    public static <T> T newInstance(Class<T> clazz) {
        if (clazz != null) {
            try {
                Constructor<?>[] constructors = clazz.getConstructors();
                // 寻找一个public、无参的构造方法去实例化
                for (Constructor<?> constructor : constructors) {
                    if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterCount() == 0) {
                        return (T) constructor.newInstance();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取类的所有接口
     *
     * @param clazz 类
     * @return 接口列表
     */
    public static List<Class<?>> getInterfaces(Class<?> clazz) {
        Set<Class<?>> interfaces = new HashSet<>();
        do {
            getInterfaces(clazz, interfaces);
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
        return new ArrayList<>(interfaces);
    }

    /**
     * 获取接口
     *
     * @param clazz      类
     * @param interfaces 接口列表
     */
    private static void getInterfaces(Class<?> clazz, Set<Class<?>> interfaces) {
        for (Class<?> aClass : clazz.getInterfaces()) {
            interfaces.add(aClass);
            getInterfaces(aClass, interfaces);
        }
    }

    /**
     * 扫描指定包下的所有类
     *
     * @param packageName 包名
     * @param predicate   类过滤器，可为null表示不过滤
     * @return 扫描到的类列表
     * @throws ClassNotFoundException 类加载失败时抛出
     * @throws IOException            读取资源失败时抛出
     */
    public static List<Class<?>> scanClasses(String packageName, Predicate<Class<?>> predicate) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        String packagePath = packageName.replace(".", "/");
        Enumeration<URL> resources;
        resources = Thread.currentThread().getContextClassLoader().getResources(packagePath);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
                findClassesInDirectory(new File(filePath), packageName, classes, predicate);
            } else if ("jar".equals(protocol)) {
                JarFile jar = ((JarURLConnection) resource.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.charAt(0) != '/') {
                        continue;
                    }
                    if (name.startsWith(packagePath) && name.length() > packagePath.length() + 1) {
                        String className = name.substring(packagePath.length() + 1, name.lastIndexOf('.')).replace('/', '.');
                        Class<?> clazz = Class.forName(packageName + '.' + className);
                        if (predicate == null || predicate.test(clazz)) {
                            classes.add(clazz);
                        }
                    }
                }
            }
        }
        return classes;
    }

    /**
     * 递归查找目录下的所有类
     *
     * @param directory   目录
     * @param packageName 当前包名
     * @param classes     结果类列表
     * @param predicate   类过滤器，可为null表示不过滤
     * @throws ClassNotFoundException 类加载失败时抛出
     */
    public static void findClassesInDirectory(File directory, String packageName, List<Class<?>> classes, Predicate<Class<?>> predicate) throws ClassNotFoundException {
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }
        File[] files = directory.listFiles(file -> (file.isFile() && file.getName().endsWith(".class") || file.isDirectory()));
        if (files != null) {
            for (File file : files) {
                String fName = file.getName();
                if (file.isFile()) {
                    String nName;
                    if (StringUtil.isBlank(packageName)) {
                        nName = fName.substring(0, fName.length() - 6);
                    } else {
                        nName = packageName + "." + fName.substring(0, fName.length() - 6);
                    }
                    Class<?> clazz = Class.forName(nName);
                    if (predicate == null || predicate.test(clazz)) {
                        classes.add(clazz);
                    }
                } else {
                    String nName;
                    if (StringUtil.isBlank(packageName)) {
                        nName = fName;
                    } else {
                        nName = packageName + "." + fName;
                    }
                    findClassesInDirectory(file, nName, classes, predicate);
                }
            }
        }
    }

    /**
     * 根据全限定名加载类
     *
     * @param typeName 全限定类名
     * @return 类对象，加载失败时返回null
     */
    public static Class<?> forName(String typeName) {
        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 是否原始类型
     *
     * @param clazz 类
     * @return 结果
     */
    public static boolean isPrimitiveType(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz == boolean.class || clazz == int.class || clazz == byte.class || clazz == short.class
                || clazz == char.class || clazz == double.class || clazz == float.class || clazz == long.class;
    }
}
