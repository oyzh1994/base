package cn.oyzh.store.file;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
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
        Map<String, Object> map = new HashMap<>();
        String name = null;
        StringBuilder value = null;
        boolean recordStart = false;
        boolean childStart = false;
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            // 记录结束
            if (event.isEndElement()) {
                if (recordStart && !childStart) {
                    break;
                }
                if (childStart) {
                    map.put(name, value == null ? null : value.toString());
                    name = null;
                    value = null;
                    childStart = false;
                }
                continue;
            }
            // 记录开始
            if (event.isStartElement()) {
                if (!recordStart) {
                    recordStart = true;
                    continue;
                }
                if (!childStart) {
                    childStart = true;
                    name = event.asStartElement().getName().getLocalPart();
                    value = new StringBuilder();
                }
                continue;
            }
            // 子节点数据。实体引用可能被拆成多个字符事件，必须追加而不是覆盖。
            if (event.isCharacters() && childStart) {
                value.append(event.asCharacters().getData());
            }
        }
        return map.isEmpty() ? null : map;
    }
}
