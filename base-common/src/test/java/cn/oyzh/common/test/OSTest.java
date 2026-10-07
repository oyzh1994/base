package cn.oyzh.common.test;

import org.junit.Test;

import java.util.Properties;

/**
 * 打印当前 JVM 的操作系统相关系统属性。
 *
 * @author oyzh
 * @since 2025-03-02
 */
public class OSTest {

    @Test
    public void test1() {
        Properties properties = System.getProperties();
        for (Object o : properties.keySet()) {
            System.out.println(o + "=" + System.getProperty(o.toString()));
        }
    }
}
