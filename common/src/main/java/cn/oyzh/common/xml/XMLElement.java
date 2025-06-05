package cn.oyzh.common.xml;

import cn.oyzh.common.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * xml节点
 *
 * @author oyzh
 * @since 2024-11-14
 */
public class XMLElement {

    /**
     * 文本内容
     */
    private String text;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 属性
     */
    private Map<String, String> attributes;

    public Map<String, String> attributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }
        return attributes;
    }

    /**
     * 子节点
     */
    private List<XMLElement> elements;

    public List<XMLElement> elements() {
        if (this.elements == null) {
            this.elements = new ArrayList<>();
        }
        return elements;
    }

    public Iterator<XMLElement> elementIterator(String tagName) {
        if (tagName != null && this.elements != null) {
            List<XMLElement> elementList = this.elements.parallelStream().filter(e -> StringUtil.equals(e.tagName, tagName)).toList();
            return elementList.iterator();
        }
        return null;
    }

    public XMLElement element(String tagName) {
        if (tagName != null && this.elements != null) {
            for (XMLElement element : this.elements) {
                if (StringUtil.equals(element.tagName, tagName)) {
                    return element;
                }
            }
        }
        return null;
    }

    public String attributeValue(String attributeName) {
        if (attributeName != null && this.attributes != null) {
            return this.attributes.get(attributeName);
        }
        return null;
    }

    public void appendText(String text) {
        if (this.text == null) {
            this.text = text;
        } else {
            this.text += text;
        }
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
