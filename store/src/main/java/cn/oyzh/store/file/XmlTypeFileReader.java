package cn.oyzh.store.file;

import cn.oyzh.common.xml.XMLHelper;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;

/**
 * @author oyzh
 * @since 2024-09-03
 */
public class XmlTypeFileReader extends TypeFileReader {

    /**
     * xml读取器
     */
    private XMLEventReader reader;

    /**
     * 导入配置
     */
    private FileReadConfig config;

    private final FileColumns columns;

    public XmlTypeFileReader(FileReadConfig config, FileColumns columns) throws Exception {
        this.config = config;
        this.columns = columns;
        XMLInputFactory factory = XMLHelper.newFactory();
        this.reader = factory.createXMLEventReader(new FileInputStream(config.filePath()), config.charset());
        this.init();
    }

    @Override
    protected void init() throws Exception {
        while (this.reader.hasNext()) {
            XMLEvent event = this.reader.nextEvent();
            if (event.isStartElement()) {
                break;
            }
        }
    }

    @Override
    public FileRecord readRecord() throws Exception {
        String name = null;
        String value = null;
        FileRecord record = null;
        boolean objStart = false;
        boolean childStart = false;
        while (this.reader.hasNext()) {
            XMLEvent event = this.reader.nextEvent();
            // 读取结束
            if (event.isEndElement() && objStart && !childStart) {
                break;
            }
            // 读取开始
            if (event.isStartElement() && !objStart) {
                objStart = true;
                continue;
            }
            // 子节点开始
            if (event.isStartElement() && !childStart) {
                childStart = true;
                StartElement element = event.asStartElement();
                name = element.getName().getLocalPart();
                continue;
            }
            // 子节点结束
            if (event.isEndElement() && childStart) {
                childStart = false;
                if (record == null) {
                    record = new FileRecord();
                }
                FileColumn column = this.columns.column(name);
                record.put(column.getPosition(), value);
                name = null;
                value = null;
                continue;
            }
            // 子节点数据
            if (event.isCharacters() && name != null) {
                value = event.asCharacters().getData();
            }
        }
        return record;
    }

    @Override
    public void close() {
        try {
            if (this.reader != null) {
                this.reader.close();
            }
            this.reader = null;
            this.config = null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
