package cn.oyzh.common.xml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * xml文档
 *
 * @author oyzh
 * @since 2024-11-14
 */
public class XMLDocument {

    /**
     * 根节点
     */
    private XMLElement root;

    /**
     * 读取器
     */
    private XMLEventReader reader;

    XMLDocument(XMLEventReader reader) {
        this.reader = reader;
    }

    /**
     * 获取根节点
     *
     * @return 根节点
     */
    public XMLElement getRootElement() {
        if (this.root == null) {
            try {
                this.root = parse(this.reader);
                this.reader.close();
                this.reader = null;
            } catch (XMLStreamException ex) {
                throw new RuntimeException(ex);
            }
        }
        return this.root;
    }

    /**
     * 解析文档
     *
     * @param reader 读取器
     * @return 结果
     * @throws XMLStreamException 异常
     */
    static XMLElement parse(XMLEventReader reader) throws XMLStreamException {
        // 当前等级
        int level = 0;
        // 根节点
        XMLElement root = null;
        // 当前节点
        XMLElement element = null;
        // 节点集合
        Map<XMLElement, Integer> nodes = new HashMap<>();
        // 读取节点
        while (reader.hasNext()) {
            // 事件
            XMLEvent event = reader.nextEvent();
            // 文档开始
            if (event.isStartDocument()) {
                continue;
            }
            // 属性
            if (event.isAttribute()) {
                continue;
            }
            // 命名空间
            if (event.isNamespace()) {
                continue;
            }
            // 实体引用
            if (event.isEntityReference()) {
                continue;
            }
            // 文档结束
            if (event.isEndDocument()) {
                break;
            }
            // 节点开始
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                element = new XMLElement();
                // 标签名称
                element.setTagName(startElement.getName().getLocalPart());
                // 属性
                Iterator<Attribute> iterator = startElement.getAttributes();
                while (iterator.hasNext()) {
                    Attribute attribute = iterator.next();
                    element.attributes().put(attribute.getName().getLocalPart(), attribute.getValue());
                }
                // 根节点
                if (++level == 1) {
                    root = element;
                } else {// 子节点
                    nodes.put(element, level);
                }
            }
            // 文本内容
            if (event.isCharacters() && element != null) {
                Characters characters = event.asCharacters();
                if (!characters.isCData() && !characters.isWhiteSpace()) {
                    element.appendText(characters.getData());
                }
            }
            // 节点结束
            if (event.isEndElement()) {
                if (--level == 0) {
                    break;
                }
            }
        }

        // 遍历数据列表，为节点添加子节点
        for (XMLElement subEle : nodes.keySet()) {
            int subLevel = nodes.get(subEle);
            // 一级节点
            if (subLevel == 2 && root != null) {
                root.elements().add(subEle);
            } else {// 二级或其他
                for (XMLElement parentEle : nodes.keySet()) {
                    int parentLevel = nodes.get(parentEle);
                    if (parentLevel == subLevel - 1) {
                        parentEle.elements().add(subEle);
                        break;
                    }
                }
            }
        }

        // 返回根节点
        return root;
    }
}
