package cn.oyzh.common.util;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

/**
 * @author oyzh
 * @since 2024-12-17
 */
@UtilityClass
public class ProcessUtil {

    /**
     * 重启应用
     *
     * @param mainClass  主类
     * @param timeout    超时时间
     * @param exitAction 退出操作
     */
    public static void restartApplication(Class<?> mainClass, int timeout, Runnable exitAction) {
        restartApplication(mainClass.getName(), timeout, exitAction);
    }

    /**
     * 重启应用
     *
     * @param mainClassName 主类
     * @param timeout       超时时间
     * @param exitAction    退出操作
     */
    public static void restartApplication(String mainClassName, int timeout, Runnable exitAction) {
        // 获取当前Java程序的路径和classpath
        // 重启命令
        String[] restartCommand = null;
        String javaPath = System.getProperty("java.home");
        if (OSUtil.isWindows()) {
            if (!FileUtil.exist(javaPath + "/bin/javaw.exe")) {
                javaPath += "/bin/java.exe";
            } else {
                javaPath += "/bin/javaw.exe";
            }
            String classPath = System.getProperty("java.class.path");
            restartCommand = new String[]{javaPath + " -cp \"" + classPath + "\" " + mainClassName};
        } else if (OSUtil.isLinux()) {
            if (!FileUtil.exist(javaPath + "/bin/javaw")) {
                javaPath += "/bin/java";
            } else {
                javaPath += "/bin/javaw";
            }
            String classPath = System.getProperty("java.class.path");
            // restartCommand = new String[]{javaPath, "-cp", classPath, mainClassName};
            if (JarUtil.isInJar()) {
                // restartCommand = new String[]{javaPath, "-jar", classPath};
                restartCommand = new String[]{javaPath, "-jar", "/home/oyzh/Desktop/EasyZK_2.0.0/" + classPath};
            } else {
                restartCommand = new String[]{javaPath, "-cp", classPath, mainClassName};
            }
        } else if (OSUtil.isMacOS()) {
            if (!FileUtil.exist(javaPath + "/bin/javaw")) {
                javaPath += "/bin/java";
            } else {
                javaPath += "/bin/javaw";
            }
            String classPath = System.getProperty("java.class.path");
            restartCommand = new String[]{javaPath, "-cp", classPath, mainClassName};
        }
        // if (JarUtil.isInJar()) {
        //     restartCommand = javaPath + " -jar \"" + "/home/oyzh/Desktop/EasyZK_2.0.0/" + classPath + "\"";
        // } else {
        //     restartCommand = javaPath + " -cp \"" + classPath + "\" " + mainClassName;
        // }
        // 打印命令
        JulLog.info("restartCommand:{}", Arrays.toString(restartCommand));
        // 执行重启命令
        RuntimeUtil.exec(restartCommand);
        // 退出当前进程
        TaskManager.startDelay(() -> {
            if (exitAction == null) {
                System.exit(0);
            } else {
                exitAction.run();
            }
        }, timeout);
    }
}
