package cn.oyzh.store.file;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件xml工具
 *
 * <p>原先 base-store 与 fx-db 的 xml 读取器各有一份事件遍历实现，此处统一。</p>
 *
 * @author oyzh
 * @since 2026-10-19
 */
public final class FileXmlUtil {

    /**
     * 私有构造函数
     */
    private FileXmlUtil() {
    }

    /**
     * 从已定位到记录起点的读取器中读取一条记录
     *
     * <p>调用前读取器应已越过根节点的起始标签。本方法读取若干子节点，
     * 直到遇到该记录的结束标签，返回子节点名称到文本值的映射。</p>
     *
     * @param reader xml事件读取器
     * @return 记录内容，无更多记录时返回null
     * @throws XMLStreamException xml解析异常
     */
    public static Map<String, Object> readRecord(XMLEventReader reader) throws XMLStreamException {
        String name = null;
        String value = null;
        Map<String, Object> map = null;
        boolean objStart = false;
        boolean childStart = false;
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            // 记录结束
            if (event.isEndElement() && objStart && !childStart) {
                break;
            }
            // 记录开始
            if (event.isStartElement() && !objStart) {
                objStart = true;
                continue;
            }
            // 子节点开始
            if (event.isStartElement() && !childStart) {
                childStart = true;
                StartElement element = event.asStartElement();
                name = element.getName().getLocalPart();
                continue;
            }
            // 子节点结束
            if (event.isEndElement() && childStart) {
                childStart = false;
                if (map == null) {
                    map = new HashMap<>();
                }
                map.put(name, value);
                name = null;
                value = null;
                continue;
            }
            // 子节点数据
            if (event.isCharacters() && name != null) {
                value = event.asCharacters().getData();
            }
        }
        return map;
    }
}
