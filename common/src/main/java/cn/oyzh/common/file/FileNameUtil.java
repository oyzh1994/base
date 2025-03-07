package cn.oyzh.common.file;

import cn.oyzh.common.util.StringUtil;

import java.io.File;

/**
 * @author oyzh
 * @since 2024-09-29
 */
public class FileNameUtil {

    public static boolean isType(String name, String... types) {
        if (name == null || !name.contains(".") || types == null || types.length == 0) {
            return false;
        }
        String ext = extName(name);
        return StringUtil.equalsAnyIgnoreCase(ext, types);
    }

    public static String extName(String name) {
        if (name == null || !name.contains(".")) {
            return "";
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String extName(File file) {
        return file == null ? null : extName(file.getName());
    }

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
     * 是否word类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isWordType(String fileType) {
        return isDocType(fileType) || isDocxType(fileType);
    }

    /**
     * 是否doc类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDocType(String fileType) {
        return "doc".equalsIgnoreCase(fileType);
    }

    /**
     * 是否docx类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDocxType(String fileType) {
        return "docx".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ppt类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPptType(String fileType) {
        return "ppt".equalsIgnoreCase(fileType);
    }

    /**
     * 是否pptx类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPptxType(String fileType) {
        return "pptx".equalsIgnoreCase(fileType);
    }

    /**
     * 是否pdf类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPdfType(String fileType) {
        return "pdf".equalsIgnoreCase(fileType);
    }

    /**
     * 是否jpg类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJpgType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "jpg", "jpeg");
    }

    /**
     * 是否exe类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isExeType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "exe");
    }

    /**
     * 是否mp3类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isMp3Type(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "mp3");
    }

    /**
     * 是否mp4类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isMp4Type(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "mp4");
    }

    /**
     * 是否dmg类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDmgType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "dmg");
    }

    /**
     * 是否图片类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isImageType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "jpg", "jpeg", "png", "gif", "bmp");
    }

    /**
     * 是否zip类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isZipType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "zip");
    }

    /**
     * 是否gz类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isGzType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "gz");
    }

    /**
     * 是否7z类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean is7zType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "7z");
    }

    /**
     * 是否rar类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRarType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "rar");
    }

    /**
     * 是否压缩类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCompressType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "zip", "rar", "7z", "tar.gz", "xz", "gz");
    }

    /**
     * 是否终端类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTerminalType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "bash_history", "bashrc", "profile", "zshrc");
    }

    /**
     * 是否markdown类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isMarkdownType(String fileType) {
        return "md".equalsIgnoreCase(fileType);
    }

    /**
     * 是否js类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJsType(String fileType) {
        return "js".equalsIgnoreCase(fileType);
    }

    /**
     * 是否jsp类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJspType(String fileType) {
        return "jsp".equalsIgnoreCase(fileType);
    }

    /**
     * 是否htm类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isHtmType(String fileType) {
        return "htm".equalsIgnoreCase(fileType);
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
        return isXlsType(fileType) || isXlsxType(fileType) || "excel".equalsIgnoreCase(fileType);
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
     * 是否text类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTextType(String fileType) {
        return "text".equalsIgnoreCase(fileType);
    }

    /**
     * 是否log类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isLogType(String fileType) {
        return "log".equalsIgnoreCase(fileType);
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
