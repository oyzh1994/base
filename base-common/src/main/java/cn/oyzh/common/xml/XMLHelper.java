package cn.oyzh.common.xml;

import javax.xml.stream.XMLInputFactory;

/**
 * xml辅助类
 * @author oyzh
 * @since 2024-11-28
 */
public class XMLHelper {

    public static XMLInputFactory newFactory() {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        return factory;
    }
}
