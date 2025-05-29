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

    private static final Map<String, Long> COST_RECORD = new ConcurrentHashMap<>();

    public static void record() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        String name = element.getFileName() + "." + element.getMethodName();
        COST_RECORD.put(name, System.currentTimeMillis());
    }

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
