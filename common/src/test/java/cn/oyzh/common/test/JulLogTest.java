package cn.oyzh.common.test;

import cn.oyzh.common.log.JulLog;
import org.junit.Test;

/**
 * @author oyzh
 * @since 2024-11-15
 */
public class JulLogTest {

    @Test
    public void test() {
        JulLog.trace("trace");
        JulLog.debug("debug");
        JulLog.info("info");
        JulLog.warn("warn");
        JulLog.error("error");
        JulLog.error("error1-{}", "1");
        JulLog.error("error2-{}", 2);
        JulLog.error("error3-{}", new Object());
        JulLog.error("{}={}ms", "1", 2L);
        JulLog.info("{}={}ms", "1", 2L);

        try {
            System.out.println(1 / 0);
        } catch (Exception ex) {
            JulLog.error("error3", ex);
        }
    }
}
