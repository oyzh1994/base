package cn.oyzh.common.xml;

import lombok.Getter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-11-14
 */
public class XMLElement {

    @Getter
    private String tagName;

    @Getter
    private String text;

    @Getter
    private final Map<String, String> attributes = new HashMap<>();

    private final List<XMLElement> elements = new ArrayList<>();

    private int nodeLevel;

    public static XMLElement parse(XMLEventReader reader) throws XMLStreamException {
        int level = 0;
        XMLElement root = null;
        List<XMLElement> list = new ArrayList<>();
        XMLElement element = null;
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            // 读取开始
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                element = new XMLElement();
                element.nodeLevel = ++level;
                element.tagName = startElement.getName().getLocalPart();
                Iterator<Attribute> iterator = startElement.getAttributes();
                while (iterator.hasNext()) {
                    Attribute attribute = iterator.next();
                    element.attributes.put(attribute.getName().getLocalPart(), attribute.getValue());
                }
                if (level == 1) {
                    root = element;
                }
                list.add(element);
            }
            // 文本内容
            if (event.isCharacters()) {
                Characters characters = event.asCharacters();
                if (!characters.isCData() && !characters.isWhiteSpace()) {
                    if (element != null) {
                        element.text = characters.getData();
                    }
                }
            }
            // 节点结束
            if (event.isEndElement()) {
                if (--level == 0) {
                    break;
                }
            }
            // 文档结束
            if (event.isEndDocument()) {
                break;
            }
        }

        // 遍历数据列表，为节点添加子节点
        for (XMLElement subEle : list) {
            int subLevel = subEle.nodeLevel;
            for (XMLElement parentEle : list) {
                if (parentEle.nodeLevel == subLevel - 1) {
                    parentEle.elements.add(subEle);
                    break;
                }
            }
        }
        return root;
    }

}
