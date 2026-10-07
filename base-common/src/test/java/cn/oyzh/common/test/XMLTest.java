package cn.oyzh.common.test;

import cn.oyzh.common.xml.XMLDocument;
import cn.oyzh.common.xml.XMLElement;
import cn.oyzh.common.xml.XMLReader;
import org.junit.Test;

/**
 * 测试 XML 读取工具解析 SVG 文档的性能与结果。
 *
 * @author oyzh
 * @since 2024-11-14
 */
public class XMLTest {

    @Test
    public void test1() {
        XMLReader reader = new XMLReader();
        for (int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
           XMLDocument document= reader.read(getClass().getResourceAsStream("/audit.svg"));

            XMLElement element = document.getRootElement();
            System.out.println(element);
            long end = System.currentTimeMillis();
            System.out.println("cost:" + (end - start));
        }
    }
}
