package cn.oyzh.common.system;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import cn.oyzh.common.thread.ThreadUtil;
import cn.oyzh.common.util.NumberUtil;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 系统工具类
 *
 * @author oyzh
 * @since 2023/04/05
 */
public class SystemUtil {

    /**
     * 移除可选属性
     */
    @Deprecated
    public static void removeOptionalProperties() {
        Properties properties = System.getProperties();
        properties.remove("java.vendor");
        properties.remove("user.script");
        properties.remove("user.variant");
        properties.remove("java.vm.vendor");
        properties.remove("java.vendor.url");
        properties.remove("sun.os.patch.level");
        properties.remove("jansi.passthrough");
        properties.remove("java.vendor.url.bug");
        properties.remove("java.vm.specification.name");
        properties.remove("debugger.agent.enable.coroutines");
    }

    private static MemoryMXBean memoryMXBean;

    private static MemoryMXBean getMemoryMXBean() {
        if (memoryMXBean == null) {
            memoryMXBean = ManagementFactory.getMemoryMXBean();
        }
        return memoryMXBean;
    }

    private static ClassLoadingMXBean classLoadingMXBean;

    private static ClassLoadingMXBean getClassLoadingMXBean() {
        if (classLoadingMXBean == null) {
            classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        }
        return classLoadingMXBean;
    }

    /**
     * 执行gc
     */
    public static void gc() {
        try {
            // 获取 MXBean 实例
            MemoryMXBean memoryMXBean = getMemoryMXBean();
            // OperatingSystemMXBean systemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            // double l1 = systemMXBean.getCommittedVirtualMemorySize();
            double l2 = heapMemoryUsage.getCommitted() + nonHeapMemoryUsage.getCommitted();
            // double l3 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // gc之前
            double usedMemory = l2;
            // double usedMemory = (l1 + l2 + l3) / 2.7;
            double m1 = NumberUtil.scale(usedMemory / 1024 / 1024, 2);
            ClassLoadingMXBean classLoadingMXBean = getClassLoadingMXBean();
            long currentLoaded = classLoadingMXBean.getLoadedClassCount();
            long totalLoaded = classLoadingMXBean.getTotalLoadedClassCount();
            long unloaded = classLoadingMXBean.getUnloadedClassCount();
            JulLog.info("gc之前预估使用内存:{}Mb, 总加载/已加载/已卸载类:{}/{}/{}", m1, totalLoaded, currentLoaded, unloaded);
            memoryMXBean.gc();

            // gc之后
            // l1 = systemMXBean.getCommittedVirtualMemorySize();
            l2 = heapMemoryUsage.getCommitted() + nonHeapMemoryUsage.getCommitted();
            // l3 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            usedMemory = l2;
            // usedMemory = (l1 + l2 + l3) / 2.7;
            m1 = NumberUtil.scale(usedMemory / 1024 / 1024, 2);
            currentLoaded = classLoadingMXBean.getLoadedClassCount();
            totalLoaded = classLoadingMXBean.getTotalLoadedClassCount();
            unloaded = classLoadingMXBean.getUnloadedClassCount();
            JulLog.info("gc之后预估使用内存:{}Mb, 总加载/已加载/已卸载类:{}/{}/{}", m1, totalLoaded, currentLoaded, unloaded);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 定期gc
     *
     * @param interval 间隔，单位毫秒
     */
    public static void gcInterval(int interval) {
        JulLog.debug("gc interval in {}ms", interval);
        // TaskManager.cancelInterval("gc:task");
        // TaskManager.startInterval("gc:task", SystemUtil::gc, interval);
        TaskManager.startInterval(SystemUtil::gc, interval);
    }

    /**
     * gc标志位
     */
    private static final AtomicBoolean GC_FLAG = new AtomicBoolean(false);

    /**
     * 延迟gc
     */
    public static void gcLater() {
        ThreadUtil.start(() -> {
            if (GC_FLAG.get()) {
                return;
            }
            try {
                GC_FLAG.set(true);
                SystemUtil.gc();
            } finally {
                GC_FLAG.set(false);
            }
        });
    }

    /**
     * 获取已用内存，单位mb
     *
     * @return 已用内存
     */
    public static double getUsedMemory() {
        MemoryMXBean mxBean = getMemoryMXBean();
        MemoryUsage heapMemoryUsage = mxBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = mxBean.getNonHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed() + nonHeapMemoryUsage.getUsed();
        return usedMemory / 1024.0 / 1024;
    }

    /**
     * 获取临时目录
     *
     * @return 临时目录
     */
    public static String tmpdir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 获取用户目录
     *
     * @return 用户目录
     */
    public static String userHome() {
        return System.getProperty("user.home");
    }

    /**
     * 获取用户目录
     *
     * @return 用户目录
     */
    public static String userDir() {
        return System.getProperty("user.dir");
    }

    /**
     * 打开系统目录
     *
     * @param path 文件路径
     */
    public static void openFolderViaCommand(String path) {
        ProcessBuilder builder = new ProcessBuilder();
        try {
            if (OSUtil.isWindows()) {
                // Windows：使用 explorer 打开目录
                builder.command("explorer", path);
            } else if (OSUtil.isMacOS()) {
                // macOS：使用 open 命令
                builder.command("open", path);
            } else if (OSUtil.isLinux()) {
                // Linux/Unix：使用 xdg-open
                builder.command("xdg-open", path);
            }
            builder.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
