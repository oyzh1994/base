package cn.oyzh.common.util;


import cn.oyzh.common.log.JulLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 计时工具类
 *
 * @author oyzh
 * @since 2025-01-01
 */
public class CostUtil {

    /**
     * 私有构造，禁止实例化
     */
    private CostUtil() {
    }

    /**
     * 耗时记录，以“调用所在的文件名.方法名”为键记录开始时间
     */
    private static final Map<String, Long> COST_RECORD = new ConcurrentHashMap<>();

    /**
     * 记录当前调用位置的起始时间
     */
    public static void record() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        String name = element.getFileName() + "." + element.getMethodName();
        COST_RECORD.put(name, System.currentTimeMillis());
    }

    /**
     * 打印当前调用位置距离上次record的耗时，并移除对应的记录
     */
    public static void printCost() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        String name = element.getFileName() + "." + element.getMethodName();
        Long start = COST_RECORD.get(name);
        if (start == null) {
            return;
        }
        String fileName = name + "#" + element.getLineNumber();
        long end = System.currentTimeMillis();
        long cost = end - start;
        JulLog.info("{}={}ms", fileName, cost);
        COST_RECORD.remove(name);
    }
}
