package cn.oyzh.common.xml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;

/**
 * xml读取器
 *
 * @author oyzh
 * @since 2024-11-14
 */
public class XMLReader {

    /**
     * xml读取器
     */
    private XMLEventReader reader;

    /**
     * 读取流
     *
     * @param stream 流
     * @return XMLDocument
     */
    public XMLDocument read(InputStream stream) {
        if (stream != null) {
            try {
                XMLInputFactory factory = XMLHelper.newFactory();
                this.reader = factory.createXMLEventReader(stream);
                return new XMLDocument(this.reader);
            } catch (XMLStreamException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    /**
     * 读取流
     *
     * @param stream   流
     * @param encoding 字符编码
     * @return XMLDocument
     */
    public XMLDocument read(InputStream stream, String encoding) {
        if (stream != null) {
            try {
                XMLInputFactory factory = XMLInputFactory.newInstance();
                factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
                this.reader = factory.createXMLEventReader(stream, encoding);
                return new XMLDocument(this.reader);
            } catch (XMLStreamException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
}
