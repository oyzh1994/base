package cn.oyzh.store.file;

import cn.oyzh.common.util.FileNameUtil;
import lombok.experimental.UtilityClass;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

/**
 * @author oyzh
 * @since 2024-11-27
 */
@UtilityClass
public class FileHelper {

    /**
     * 初始化写入器
     *
     * @param fileType 文件类型
     * @param config   配置
     * @param columns  字段列表
     * @return 文件写入器
     * @throws Exception 异常
     */
    public static TypeFileWriter initWriter(String fileType, FileWriteConfig config, FileColumns columns) throws Exception {
        if (FileNameUtil.isExcelType(fileType)) {
            return new ExcelTypeFileWriter(config, columns);
        }
        if (FileNameUtil.isHtmlType(fileType)) {
            return new HtmlTypeFileWriter(config, columns);
        }
        if (FileNameUtil.isJsonType(fileType)) {
            return new JsonTypeFileWriter(config, columns);
        }
        if (FileNameUtil.isXmlType(fileType)) {
            return new XmlTypeFileWriter(config, columns);
        }
        if (FileNameUtil.isCsvType(fileType)) {
            return new CsvTypeFileWriter(config, columns);
        }
        if (FileNameUtil.isTxtType(fileType)) {
            return new TxtTypeFileWriter(config, columns);
        }
        return null;
    }

    /**
     * 初始化读取器
     *
     * @param fileType 文件类型
     * @param config   配置
     * @param columns  字段列表
     * @return 文件写入器
     * @throws Exception 异常
     */
    public static TypeFileReader initReader(String fileType, FileReadConfig config, FileColumns columns) throws Exception {
        if (FileNameUtil.isExcelType(fileType)) {
            return new ExcelTypeFileReader(config, columns);
        }
        if (FileNameUtil.isJsonType(fileType)) {
            return new JsonTypeFileReader(config, columns);
        }
        if (FileNameUtil.isXmlType(fileType)) {
            return new XmlTypeFileReader(config, columns);
        }
        if (FileNameUtil.isCsvType(fileType)) {
            return new CsvTypeFileReader(config, columns);
        }
        if (FileNameUtil.isTxtType(fileType)) {
            return new TxtTypeFileReader(config, columns);
        }
        return null;
    }
}
