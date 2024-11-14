package cn.oyzh.common.xml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-14
 */
public class XMLReader {

    /**
     * xml读取器
     */
    private XMLEventReader reader;

    public void read(InputStream stream) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
            this.reader = factory.createXMLEventReader(stream);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void read(InputStream stream, String encoding) {
        try {
            this.reader = XMLInputFactory.newInstance().createXMLEventReader(stream, encoding);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    public XMLElement getRootElement() {
        try {
            return XMLElement.parse(this.reader);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }
}
