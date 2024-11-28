package cn.oyzh.store.file;

import cn.oyzh.common.json.JSONObject;
import cn.oyzh.common.json.JSONUtil;
import cn.oyzh.common.util.FileUtil;
import com.alibaba.fastjson.JSONReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
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

    private FileColumns columns;

    public JsonTypeFileReader(FileReadConfig config, FileColumns columns) throws FileNotFoundException {
        this.config = config;
        this.columns = columns;
        this.reader = new JSONReader(FileUtil.getReader(config.filePath(), Charset.forName(config.charset())));
        this.init();
    }

    @Override
    protected void init() {
        // 初始化
        if (this.reader.hasNext()) {
            // if (this.config.recordLabel() == null) {
            this.reader.startArray();
            // } else {
            //     this.reader.startObject();
            //     String key = this.reader.readString();
            //     if (key.equalsIgnoreCase(this.config.recordLabel())) {
            //         this.reader.startArray();
            //     }
            // }
        }
    }

    @Override
    public FileRecord readRecord() {
        if (this.reader.hasNext()) {
            try {
                String json = this.reader.readString();
                JSONObject object = JSONUtil.parseObject(json);
                FileRecord record = new FileRecord();
                for (Map.Entry<String, Object> entry : object.entrySet()) {
                    FileColumn column = columns.column(entry.getKey());
                    record.put(column.getPosition(), entry.getValue());
                }
                return record;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        try {
            // if (this.config.recordLabel() == null) {
            this.reader.endArray();
            // this.reader.endArray();
            // } else {
            //     this.reader.endArray();
            // this.reader.endObject();
            // }
            this.reader.close();
            this.reader = null;
            this.config = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
