package cn.oyzh.common.util;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import cn.oyzh.common.thread.ThreadUtil;
import lombok.experimental.UtilityClass;

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
@UtilityClass
public class SystemUtil {

    /**
     * 移除可选属性
     */
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

    /**
     * 执行gc
     */
    public static void gc() {
        try {
            // 获取 MemoryMXBean 实例
            MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
            // 获取堆内存信息
            MemoryUsage heapMemoryUsage = mxBean.getHeapMemoryUsage();
            // 获取非堆内存信息
            MemoryUsage nonHeapMemoryUsage = mxBean.getNonHeapMemoryUsage();
            long usedMemory = heapMemoryUsage.getUsed() + nonHeapMemoryUsage.getUsed();
            JulLog.info("gc之前预估使用内存:{}Mb", usedMemory / 1024 / 1024.0);
            System.gc();
            usedMemory = heapMemoryUsage.getUsed() + nonHeapMemoryUsage.getUsed();
            JulLog.info("gc之后预估使用内存:{}Mb", usedMemory / 1024 / 1024.0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 定期gc
     */
    public static void gcInterval(int interval) {
        JulLog.debug("gc interval in {}ms", interval);
        TaskManager.cancelInterval("gc:task");
        TaskManager.startInterval("gc:task", SystemUtil::gc, interval);
    }

    /**
     * 延迟gc
     */
    public static void gcLater() {
        ThreadUtil.startVirtual(SystemUtil::gc);
    }

    public static double getUsedMemory() {
        MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = mxBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = mxBean.getNonHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed() + nonHeapMemoryUsage.getUsed();
        return usedMemory / 1024.0 / 1024;
    }
}
