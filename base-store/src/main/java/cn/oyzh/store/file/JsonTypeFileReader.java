package cn.oyzh.store.file;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.util.IOUtil;
import com.alibaba.fastjson2.JSONReader;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * json类型文件读取器
 *
 * @author oyzh
 * @since 2024-09-03
 */
public class JsonTypeFileReader extends TypeFileReader {

    /**
     * json读取器
     */
    private JSONReader reader;

    /**
     * 导入配置
     */
    private FileReadConfig config;

    /**
     * 字段列表
     */
    private FileColumns columns;

    public JsonTypeFileReader(FileReadConfig config, FileColumns columns) throws FileNotFoundException {
        this.config = config;
        this.columns = columns;
        this.reader = JSONReader.of(FileUtil.getReader(config.filePath(), Charset.forName(config.charset())));
        this.init();
    }

    @Override
    protected void init() {
        // 初始化：进入数组上下文
        if (!this.reader.isEnd()) {
            this.reader.startArray();
        }
    }

    @Override
    public FileRecord readRecord() {
        // 到达流末尾或数组末尾（]）时返回 null
        if (this.reader.isEnd() || this.reader.nextIfMatch(']')) {
            return null;
        }
        try {
            // 直接读取一个 json 对象
            Map<String, Object> object = this.reader.readObject();
            // 消费元素间的逗号分隔符
            this.reader.nextIfComma();
            if (object == null) {
                return null;
            }
            FileRecord record = new FileRecord();
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                FileColumn column = this.columns.column(entry.getKey());
                if (column != null) {
                    record.put(column.getPosition(), entry.getValue());
                }
            }
            return record;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        try {
            IOUtil.close(this.reader);
            this.reader = null;
            this.config = null;
            this.columns.clear();
            this.columns = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
