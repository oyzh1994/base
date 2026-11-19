package cn.oyzh.store.test;

import cn.oyzh.store.file.FileXmlUtil;
import org.junit.Test;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * 测试公共 XML 记录读取。
 *
 * @author oyzh
 * @since 2026-11-20
 */
public class FileXmlUtilTest {

    @Test
    public void appendsSplitCharacterEvents() throws Exception {
        XMLEventReader reader = positionedReader("""
                <records>
                    <row><name>a&quot;b&amp;c</name></row>
                    <row><name>c&amp;d</name></row>
                </records>
                """);
        Map<String, Object> first = FileXmlUtil.readRecord(reader);
        assertEquals("a\"b&c", first.get("name"));
        Map<String, Object> second = FileXmlUtil.readRecord(reader);
        assertEquals("c&d", second.get("name"));
        assertNull(FileXmlUtil.readRecord(reader));
        reader.close();
    }

    private static XMLEventReader positionedReader(String xml) throws Exception {
        XMLEventReader reader = XMLInputFactory.newInstance()
                .createXMLEventReader(new StringReader(xml));
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                break;
            }
        }
        return reader;
    }
}
