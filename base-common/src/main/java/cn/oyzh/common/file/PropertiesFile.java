package cn.oyzh.common.file;

import cn.oyzh.common.util.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性类文件
 *
 * @author oyzh
 * @since 2024-09-04
 */
public class PropertiesFile extends Properties {

    public PropertiesFile() {
        super();
    }

    public PropertiesFile(String fileName) throws IOException {
        super();
        InputStream stream = ResourceUtil.getResourceAsStream(fileName);
        this.load(stream);
    }
}
