package cn.oyzh.common.test;

import org.junit.Test;

import java.util.Properties;

public class OSTest {

    @Test
    public void test1() {
        Properties properties = System.getProperties();
        for (Object o : properties.keySet()) {
            System.out.println(o + "=" + System.getProperty(o.toString()));
        }
    }
}
