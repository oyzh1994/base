package cn.oyzh.common.system;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import cn.oyzh.common.thread.ThreadUtil;
import cn.oyzh.common.util.NumberUtil;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Properties;

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

    /**
     * 执行gc
     */
    public static void gc() {
        try {
            // 获取 MXBean 实例
            MemoryMXBean mxBean = getMemoryMXBean();
            OperatingSystemMXBean systemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            MemoryUsage heapMemoryUsage = mxBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = mxBean.getNonHeapMemoryUsage();

            double l1 = systemMXBean.getCommittedVirtualMemorySize();
            double l2 = heapMemoryUsage.getCommitted() + nonHeapMemoryUsage.getCommitted();
            double l3 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // gc之前
            double usedMemory = (l1 + l2 + l3) / 3;
            double m1 = NumberUtil.scale(usedMemory / 1024 / 1024, 2);
            JulLog.info("gc之前预估使用内存:{}Mb", m1);
            mxBean.gc();

            // gc之后
            l1 = systemMXBean.getCommittedVirtualMemorySize();
            l2 = heapMemoryUsage.getCommitted() + nonHeapMemoryUsage.getCommitted();
            l3 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            usedMemory = (l1 + l2 + l3) / 3;
            m1 = NumberUtil.scale(usedMemory / 1024 / 1024, 2);
            JulLog.info("gc之后预估使用内存:{}Mb", m1);
            System.out.println("l1:" + l1);
            System.out.println("l2:" + l2);
            System.out.println("l3:" + l3);
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
     * 延迟gc
     */
    public static void gcLater() {
        ThreadUtil.startVirtual(SystemUtil::gc);
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
}
