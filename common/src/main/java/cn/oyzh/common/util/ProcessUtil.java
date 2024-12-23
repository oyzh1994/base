package cn.oyzh.common.util;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import lombok.experimental.UtilityClass;

import java.io.File;
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
        // 运行目录
        File dir = null;
        // 重启命令
        String[] restartCommand = null;
        // 获取当前Java程序的路径和classpath
        String javaPath = System.getProperty("java.home");
        if (OSUtil.isWindows()) {
            if (!FileUtil.exist(javaPath + "/bin/javaw.exe")) {
                javaPath += "/bin/java.exe";
            } else {
                javaPath += "/bin/javaw.exe";
            }
            String classPath = System.getProperty("java.class.path");
            restartCommand = new String[]{javaPath + " -cp \"" + classPath + "\" " + mainClassName};
            // 打印命令
            JulLog.info("restartCommand:{} dir:{}", Arrays.toString(restartCommand), dir);
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
        } else if (OSUtil.isLinux()) {
            dir = new File(javaPath).getParentFile();
            if (!FileUtil.exist(javaPath + "/bin/javaw")) {
                javaPath = dir.getPath() + "/./jre/bin/java";
            } else {
                javaPath = dir.getPath() + "/./jre/bin/javaw";
            }
            String classPath = System.getProperty("java.class.path");
            restartCommand = new String[]{javaPath, "-jar", classPath};
            // 打印命令
            JulLog.info("restartCommand:{} dir:{}", Arrays.toString(restartCommand), dir);
            // 执行重启命令
            Process process = RuntimeUtil.exec(restartCommand);
            try {
                JulLog.info("exitCode:{}", process.waitFor());
                // 退出当前进程
                TaskManager.startDelay(() -> {
                    if (exitAction == null) {
                        System.exit(0);
                    } else {
                        exitAction.run();
                    }
                }, timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else if (OSUtil.isMacOS()) {
            dir = new File(javaPath).getParentFile();
            if (!FileUtil.exist(javaPath + "/bin/javaw")) {
                javaPath = dir.getPath() + "/./jre/bin/java";
            } else {
                javaPath = dir.getPath() + "/./jre/bin/javaw";
            }
            String classPath = System.getProperty("java.class.path");
            restartCommand = new String[]{javaPath, "-cp", classPath, mainClassName};
        }


    }
}
