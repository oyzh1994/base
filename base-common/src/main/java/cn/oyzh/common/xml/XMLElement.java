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

    /**
     * 获取属性映射，为空时初始化
     *
     * @return 属性映射
     */
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

    /**
     * 获取子节点列表，为空时初始化
     *
     * @return 子节点列表
     */
    public List<XMLElement> elements() {
        if (this.elements == null) {
            this.elements = new ArrayList<>();
        }
        return  this.elements;
    }

    /**
     * 获取指定标签名子节点的迭代器
     *
     * @param tagName 标签名
     * @return 子节点迭代器
     */
    public Iterator<XMLElement> elementIterator(String tagName) {
        return this.elements(tagName).iterator();
    }

    /**
     * 获取指定标签名的子节点列表，标签名为null时返回全部子节点
     *
     * @param tagName 标签名
     * @return 子节点列表
     */
    public List<XMLElement> elements(String tagName) {
        if (tagName != null && this.elements != null) {
            return this.elements.parallelStream().filter(e -> StringUtil.equals(e.tagName, tagName)).toList();
        }
        return this.elements;
    }

    /**
     * 获取第一个匹配标签名的子节点
     *
     * @param tagName 标签名
     * @return 子节点，未找到返回null
     */
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

    /**
     * 获取指定属性的值
     *
     * @param attributeName 属性名
     * @return 属性值，不存在返回null
     */
    public String attributeValue(String attributeName) {
        if (attributeName != null && this.attributes != null) {
            return this.attributes.get(attributeName);
        }
        return null;
    }

    /**
     * 追加文本内容
     *
     * @param text 文本
     */
    public void appendText(String text) {
        if (this.text == null) {
            this.text = text;
        } else {
            this.text += text;
        }
    }

    /**
     * 设置标签名称
     *
     * @param tagName 标签名称
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
