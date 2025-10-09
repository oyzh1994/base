package cn.oyzh.common.file;

import cn.oyzh.common.util.StringUtil;

import java.io.File;

/**
 * 文件名工具类
 *
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

    /**
     * 获取扩展名
     *
     * @param name 名称
     * @return 扩展名
     */
    public static String extName(String name) {
        if (name == null || !name.contains(".")) {
            return "";
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * 移除扩展名
     *
     * @param name 名称
     * @return 结果
     */
    public static String removeExtName(String name) {
        String extName = FileNameUtil.extName(name);
        if (extName.isEmpty()) {
            return name;
        }
        return name.substring(0, name.lastIndexOf("."));
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
    public static boolean isSqlType(String fileType) {
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
     * 是否perl类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPerlType(String fileType) {
        return "pl".equalsIgnoreCase(fileType);
    }

    /**
     * 是否php类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPhpType(String fileType) {
        return "php".equalsIgnoreCase(fileType);
    }

    /**
     * 是否rust类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRustType(String fileType) {
        return "rs".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ruby类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRubyType(String fileType) {
        return "rb".equalsIgnoreCase(fileType);
    }

    /**
     * 是否dart类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDartType(String fileType) {
        return "dart".equalsIgnoreCase(fileType);
    }

    /**
     * 是否actionscript类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isActionScriptType(String fileType) {
        return "actionscript".equalsIgnoreCase(fileType);
    }

    /**
     * 是否scala类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isScalaType(String fileType) {
        return "scala".equalsIgnoreCase(fileType);
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
     * 是否apk类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isApkType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "apk");
    }

    /**
     * 是否3gp类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean is3gpType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "3gp");
    }

    /**
     * 是否webp类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isWebpType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "webp");
    }

    /**
     * 是否wbmp类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isWbmpType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "wbmp");
    }


    /**
     * 是否wps类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isWpsType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "wps");
    }


    /**
     * 是否rpm类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRpmType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "rpm");
    }

    /**
     * 是否amr类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isAmrType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "amr");
    }

    /**
     * 是否jar类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJarType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "jar");
    }

    /**
     * 是否tar类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTarType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "tar");
    }

    /**
     * 是否swf类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isSwfType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "swf");
    }

    /**
     * 是否rmvb类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRmvbType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "rmvb");
    }

    /**
     * 是否bmp类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isBmpType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "bmp");
    }

    /**
     * 是否flv类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isFlvType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "flv");
    }

    /**
     * 是否pcm类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPcmType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "pcm");
    }

    /**
     * 是否wma类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isWmaType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "wma");
    }

    /**
     * 是否webm类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isWebmType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "webm");
    }

    /**
     * 是否flac类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isFlacType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "flac");
    }

    /**
     * 是否ogg类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isOggType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "ogg");
    }

    /**
     * 是否aac类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isAacType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "aac");
    }

    /**
     * 是否m4a类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isM4aType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "m4a");
    }

    /**
     * 是否kmk类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isKmkType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "kmk");
    }

    /**
     * 是否icns类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isIcnsType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "icns");
    }

    /**
     * 是否cs类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCsType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "cs");
    }

    /**
     * 是否kt类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isKtType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "kt");
    }

    /**
     * 是否asm类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isAsmType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "asm");
    }

    /**
     * 是否proto类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isProtobufType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "proto");
    }

    /**
     * 是否less类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isLessType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "less");
    }

    /**
     * 是否lua类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isLuaType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "lua");
    }

    /**
     * 是否ts类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTsType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "ts");
    }

    /**
     * 是否gz类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isGzType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "gz", "tgz");
    }

    /**
     * 是否xz类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isXzType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "xz");
    }

    /**
     * 是否gif类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isGifType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "gif");
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
        return StringUtil.equalsAnyIgnoreCase(fileType, "zip", "rar", "7z", "tar.gz", "xz", "gz", "tgz");
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
     * 是否css类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCssType(String fileType) {
        return "css".equalsIgnoreCase(fileType);
    }

    /**
     * 是否properties类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPropertiesType(String fileType) {
        return "properties".equalsIgnoreCase(fileType);
    }

    /**
     * 是否conf类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isConfType(String fileType) {
        return "conf".equalsIgnoreCase(fileType);
    }

    /**
     * 是否bat类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isBatType(String fileType) {
        return "bat".equalsIgnoreCase(fileType);
    }

    /**
     * 是否yaml类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isYamlType(String fileType) {
        return "yaml".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ini类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isIniType(String fileType) {
        return "ini".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ico类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isIcoType(String fileType) {
        return "ico".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ttf类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTtfType(String fileType) {
        return "ttf".equalsIgnoreCase(fileType);
    }

    /**
     * 是否svg类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isSvgType(String fileType) {
        return "svg".equalsIgnoreCase(fileType);
    }

    /**
     * 是否py类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPyType(String fileType) {
        return "py".equalsIgnoreCase(fileType);
    }

    /**
     * 是否bin类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isBinType(String fileType) {
        return "bin".equalsIgnoreCase(fileType);
    }

    /**
     * 是否psd类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPsdType(String fileType) {
        return "psd".equalsIgnoreCase(fileType);
    }

    /**
     * 是否yml类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isYmlType(String fileType) {
        return "yml".equalsIgnoreCase(fileType);
    }

    /**
     * 是否sh类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isShType(String fileType) {
        return "sh".equalsIgnoreCase(fileType);
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
     * 是否so类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isSoType(String fileType) {
        return "so".equalsIgnoreCase(fileType);
    }

    /**
     * 是否dylib类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDylibType(String fileType) {
        return "dylib".equalsIgnoreCase(fileType);
    }

    /**
     * 是否dll类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDllType(String fileType) {
        return "dll".equalsIgnoreCase(fileType);
    }

    /**
     * 是否iso类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isIsoType(String fileType) {
        return "iso".equalsIgnoreCase(fileType);
    }

    /**
     * 是否rss类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRssType(String fileType) {
        return "rss".equalsIgnoreCase(fileType);
    }

    /**
     * 是否asp类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isAspType(String fileType) {
        return "asp".equalsIgnoreCase(fileType);
    }

    /**
     * 是否aspx类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isAspxType(String fileType) {
        return "aspx".equalsIgnoreCase(fileType);
    }

    /**
     * 是否wav类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isWavType(String fileType) {
        return "wav".equalsIgnoreCase(fileType);
    }

    /**
     * 是否rtf类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRtfType(String fileType) {
        return "rtf".equalsIgnoreCase(fileType);
    }

    /**
     * 是否mkv类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isMkvType(String fileType) {
        return "mkv".equalsIgnoreCase(fileType);
    }


    /**
     * 是否dot类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDotType(String fileType) {
        return "dot".equalsIgnoreCase(fileType);
    }

    /**
     * 是否rm类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRmType(String fileType) {
        return "rm".equalsIgnoreCase(fileType);
    }

    /**
     * 是否mov类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isMovType(String fileType) {
        return "mov".equalsIgnoreCase(fileType);
    }

    /**
     * 是否cmd类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCmdType(String fileType) {
        return "cmd".equalsIgnoreCase(fileType);
    }

    /**
     * 是否srt类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isSrtType(String fileType) {
        return "srt".equalsIgnoreCase(fileType);
    }

    /**
     * 是否rtf类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isStfType(String fileType) {
        return "srt".equalsIgnoreCase(fileType);
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
     * 是否jsonc类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJsoncType(String fileType) {
        return "jsonc".equalsIgnoreCase(fileType);
    }


    /**
     * 是否jsonl类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJsonlType(String fileType) {
        return "jsonl".equalsIgnoreCase(fileType);
    }

    /**
     * 是否java类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJavaType(String fileType) {
        return "java".equalsIgnoreCase(fileType);
    }

    /**
     * 是否plist类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPlistType(String fileType) {
        return "plist".equalsIgnoreCase(fileType);
    }

    /**
     * 是否vb类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isVbType(String fileType) {
        return "vb".equalsIgnoreCase(fileType);
    }

    /**
     * 是否war类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isWarType(String fileType) {
        return "war".equalsIgnoreCase(fileType);
    }

    /**
     * 是否tsx类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTsxType(String fileType) {
        return "tsx".equalsIgnoreCase(fileType);
    }

    /**
     * 是否vbs类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isVbsType(String fileType) {
        return "vbs".equalsIgnoreCase(fileType);
    }

    /**
     * 是否vbs类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isVimType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "vim", "vimrc", "gvimrc", "nvimrc", "_vimrc", "vmb", "ideavimrc");
    }

    /**
     * 是否python类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPythonType(String fileType) {
        return "py".equalsIgnoreCase(fileType);
    }

    /**
     * 是否c类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "c", "h");
    }

    /**
     * 是否cpp类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCppType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "cpp", "hpp", "hxx");
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
     * 是否tex类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTexType(String fileType) {
        return "tex".equalsIgnoreCase(fileType);
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
     * 是否bz2类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isBz2Type(String fileType) {
        return "bz2".equalsIgnoreCase(fileType);
    }

    /**
     * 是否lzma类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isLzmaType(String fileType) {
        return "lzma".equalsIgnoreCase(fileType);
    }

    /**
     * 是否lz类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isLzType(String fileType) {
        return "lz".equalsIgnoreCase(fileType);
    }

    /**
     * 是否zst类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isZstType(String fileType) {
        return "zst".equalsIgnoreCase(fileType);
    }

    /**
     * 是否lzo类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isLzoType(String fileType) {
        return "lzo".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ds_store类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDsstoreType(String fileType) {
        return "ds_store".equalsIgnoreCase(fileType);
    }

    /**
     * 是否pyc类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPycType(String fileType) {
        return "pyc".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ocx类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isOcxType(String fileType) {
        return "ocx".equalsIgnoreCase(fileType);
    }

    /**
     * 是否inf类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isInfType(String fileType) {
        return "inf".equalsIgnoreCase(fileType);
    }

    /**
     * 是否gradle类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isGradleType(String fileType) {
        return "gradle".equalsIgnoreCase(fileType);
    }

    /**
     * 是否epub类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isEpubType(String fileType) {
        return "epub".equalsIgnoreCase(fileType);
    }

    /**
     * 是否db类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDbType(String fileType) {
        return "db".equalsIgnoreCase(fileType);
    }

    /**
     * 是否config类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isConfigType(String fileType) {
        return "config".equalsIgnoreCase(fileType);
    }

    /**
     * 是否com类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isComType(String fileType) {
        return "com".equalsIgnoreCase(fileType);
    }

    /**
     * 是否class类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isClassType(String fileType) {
        return "class".equalsIgnoreCase(fileType);
    }

    /**
     * 是否chm类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isChmType(String fileType) {
        return "chm".equalsIgnoreCase(fileType);
    }

    /**
     * 是否cfg类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCfgType(String fileType) {
        return "cfg".equalsIgnoreCase(fileType);
    }

    /**
     * 是否cer类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCerType(String fileType) {
        return "cer".equalsIgnoreCase(fileType);
    }

    /**
     * 是否clojure类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isClojureType(String fileType) {
        return "clj".equalsIgnoreCase(fileType);
    }

    /**
     * 是否coffeescript类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCoffeeScriptType(String fileType) {
        return "coffee".equalsIgnoreCase(fileType);
    }

    /**
     * 是否cuda类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isCudaType(String fileType) {
        return "cu".equalsIgnoreCase(fileType);
    }

    /**
     * 是否diff类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDiffType(String fileType) {
        return "diff".equalsIgnoreCase(fileType);
    }

    /**
     * 是否dockerfile类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isDockerfileType(String fileType) {
        return "dockerfile".equalsIgnoreCase(fileType);
    }

    /**
     * 是否erlang类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isErlangType(String fileType) {
        return "erl".equalsIgnoreCase(fileType);
    }

    /**
     * 是否fsharp类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isFsharpType(String fileType) {
        return "fs".equalsIgnoreCase(fileType);
    }

    /**
     * 是否go类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isGoType(String fileType) {
        return "go".equalsIgnoreCase(fileType);
    }

    /**
     * 是否groovy类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isGroovyType(String fileType) {
        return "groovy".equalsIgnoreCase(fileType);
    }

    /**
     * 是否Handlebars类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isHandlebarsType(String fileType) {
        return "hbs".equalsIgnoreCase(fileType);
    }

    /**
     * 是否hlsl类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isHlslType(String fileType) {
        return "hlsl".equalsIgnoreCase(fileType);
    }

    /**
     * 是否julia类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isJuliaType(String fileType) {
        return "julia".equalsIgnoreCase(fileType);
    }

    /**
     * 是否kconfig类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isKconfigType(String fileType) {
        return "kconfig".equalsIgnoreCase(fileType);
    }

    /**
     * 是否latex类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isLatexType(String fileType) {
        return "ltx".equalsIgnoreCase(fileType);
    }

    /**
     * 是否makefile类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isMakefileType(String fileType) {
        return "makefile".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ObjectiveC类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isObjectiveCType(String fileType) {
        return "m".equalsIgnoreCase(fileType);
    }

    /**
     * 是否ObjectiveCPP类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isObjectiveCPPType(String fileType) {
        return "mm".equalsIgnoreCase(fileType);
    }

    /**
     * 是否Powershell类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPowershellType(String fileType) {
        return "ps1".equalsIgnoreCase(fileType);
    }

    /**
     * 是否pug类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isPugType(String fileType) {
        return "pug".equalsIgnoreCase(fileType);
    }

    /**
     * 是否r类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRType(String fileType) {
        return "r".equalsIgnoreCase(fileType);
    }

    /**
     * 是否razor类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRazorType(String fileType) {
        return "razor".equalsIgnoreCase(fileType);
    }

    /**
     * 是否Restructuredtext类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isRestructuredtextType(String fileType) {
        return "rst".equalsIgnoreCase(fileType);
    }

    /**
     * 是否scss类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isScssType(String fileType) {
        return "scss".equalsIgnoreCase(fileType);
    }

    /**
     * 是否shaderlab类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isShaderlabType(String fileType) {
        return "shader".equalsIgnoreCase(fileType);
    }

    /**
     * 是否swift类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isSwiftType(String fileType) {
        return "swift".equalsIgnoreCase(fileType);
    }

    /**
     * 是否twig类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isTwigType(String fileType) {
        return "twig".equalsIgnoreCase(fileType);
    }

    /**
     * 是否xsl类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isXslType(String fileType) {
        return "xsl".equalsIgnoreCase(fileType);
    }

    /**
     * 是否asciidoc类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isAsciidocType(String fileType) {
        return StringUtil.equalsAnyIgnoreCase(fileType, "ad", "asc", "adoc", "asciidoc");
    }

    /**
     * 是否bibtex类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isBibtexType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "bib");
    }

    /**
     * 是否GitRebase类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isGitRebaseType(String fileType) {
        return StringUtil.equalsIgnoreCase(fileType, "git-rebase-todo");
    }

    /**
     * 是否SearchResult类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isSearchResultType(String fileType) {
        return "code-search".equalsIgnoreCase(fileType);
    }

    /**
     * 是否sas类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isSasType(String fileType) {
        return "sas".equalsIgnoreCase(fileType);
    }

    /**
     * 是否gitignore类型
     *
     * @param fileType 文件类型
     * @return 结果
     */
    public static boolean isGitIgnoreType(String fileType) {
        return "gitignore".equalsIgnoreCase(fileType);
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
            if (!path.startsWith(File.separator)&&!builder.toString().endsWith(File.separator)) {
                builder.append(File.separator);
            }
            builder.append(path);
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
