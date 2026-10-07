package cn.oyzh.common.xml;

import javax.xml.stream.XMLInputFactory;

/**
 * xml辅助类
 *
 * @author oyzh
 * @since 2024-11-28
 */
public class XMLHelper {

    /**
     * 创建禁用了DTD支持的XML输入工厂，以避免外部实体注入风险
     *
     * @return XML输入工厂
     */
    public static XMLInputFactory newFactory() {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        return factory;
    }
}
