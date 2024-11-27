package cn.oyzh.common.util;

import lombok.experimental.UtilityClass;

import java.io.File;

/**
 * 文件名称工具类
 *
 * @author oyzh
 * @since 2023/11/17
 */
@UtilityClass
public class FileNameUtil {

    /**
     * 是否sql类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public boolean isSqlType(String fileType) {
        return "sql".equalsIgnoreCase(fileType);
    }

    /**
     * 是否xml类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isXmlType(String fileType) {
        return "xml".equalsIgnoreCase(fileType);
    }

    /**
     * 是否csv类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCsvType(String fileType) {
        return "csv".equalsIgnoreCase(fileType);
    }

    /**
     * 是否html类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isHtmlType(String fileType) {
        return "html".equalsIgnoreCase(fileType);
    }

    /**
     * 是否xls类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isXlsType(String fileType) {
        return "xls".equalsIgnoreCase(fileType);
    }

    /**
     * 是否xlsx类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isXlsxType(String fileType) {
        return "xlsx".equalsIgnoreCase(fileType);
    }

    /**
     * 是否excel类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isExcelType(String fileType) {
        return isXlsType(fileType) || isXlsxType(fileType);
    }

    /**
     * 是否json类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJsonType(String fileType) {
        return "json".equalsIgnoreCase(fileType);
    }

    /**
     * 是否txt类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTxtType(String fileType) {
        return "txt".equalsIgnoreCase(fileType);
    }

    /**
     * 追加路径
     *
     * @param paths 路径列表
     * @return 新路径
     */
    public static String concat(String... paths) {
        StringBuilder builder = new StringBuilder();
        for (String path : paths) {
            builder.append(path);
            if (!path.endsWith(File.separator)) {
                builder.append(File.separator);
            }
        }
        return builder.toString();
    }

    public static String getSuffix(String fileName) {
        final int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        return fileName.substring(index + 1);
    }
}
