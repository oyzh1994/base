package cn.oyzh.common.test;

import cn.oyzh.common.xml.XMLElement;
import cn.oyzh.common.xml.XMLReader;
import org.junit.Test;

/**
 * @author oyzh
 * @since 2024-11-14
 */
public class XMLTest {

    @Test
    public void test1() {
        XMLReader reader = new XMLReader();
        for (int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            reader.read(getClass().getResourceAsStream("/audit.svg"));
            XMLElement element = reader.getRootElement();
            System.out.println(element);
            long end = System.currentTimeMillis();
            System.out.println("cost:" + (end - start));
        }
    }
}
