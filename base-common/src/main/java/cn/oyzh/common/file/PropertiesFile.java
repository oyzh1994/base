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

    /**
     * 创建空的属性文件对象
     */
    public PropertiesFile() {
        super();
    }

    /**
     * 从类路径资源加载属性文件
     *
     * @param fileName 资源文件名
     * @throws IOException 加载失败时抛出
     */
    public PropertiesFile(String fileName) throws IOException {
        super();
        InputStream stream = ResourceUtil.getResourceAsStream(fileName);
        this.load(stream);
    }
}
