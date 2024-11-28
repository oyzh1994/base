package cn.oyzh.common.xml;

import lombok.experimental.UtilityClass;

import javax.xml.stream.XMLInputFactory;

/**
 * @author oyzh
 * @since 2024-11-28
 */
@UtilityClass
public class XMLHelper {

    public static XMLInputFactory newFactory() {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        return factory;
    }
}
