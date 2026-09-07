package cn.oyzh.common.util;

import cn.oyzh.common.system.OSUtil;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author oyzh
 * @since 2026-09-07
 */
public class CoverUtil {

    /**
     * 获取项目类路径
     *
     * @param projectPath 项目路径
     * @return 结果
     * @throws Exception 异常
     */
    public static String getClassesPath(String projectPath) throws Exception {
        String f = projectPath;
        if (projectPath.contains("test-classes")) {
            f = projectPath.substring(0, f.indexOf("test-classes"));
        } else if (projectPath.contains("classes")) {
            f = projectPath.substring(0, f.indexOf("classes"));
        }
        if (f.startsWith("file:/")) {
            if (OSUtil.isWindows()) {
                f = f.substring(6);
            } else {
                f = f.substring(5);
            }
        }
        return f+"classes";
    }

    /**
     * 获取项目类
     *
     * @param projectPath 项目路径
     * @return 结果
     * @throws Exception 异常
     */
    public static List<Class<?>> getClasses(String projectPath) throws Exception {
        String pkName = "";
        String f = getClassesPath(projectPath);
        List<Class<?>> list = new ArrayList<>();
        ClassUtil.findClassesInDirectory(new File(f), pkName, list, (Predicate<Class<?>>) aClass -> {
            if (Modifier.isAbstract(aClass.getModifiers()) || !Modifier.isPublic(aClass.getModifiers())) {
                return false;
            }
            return true;
        });
        return list;
    }
}
