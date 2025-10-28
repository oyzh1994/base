package cn.oyzh.common.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

import java.util.Collections;
import java.util.List;

/**
 * json工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class JSONUtil {

    /**
     * 美化
     *
     * @param obj 对象
     * @return json美化字符串
     */
    public static String toPretty(Object obj) {
        if (obj != null) {
            try {
                return JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 美化
     *
     * @param str 对象
     * @return json美化字符串
     */
    public static String toPretty(String str) {
        if (str != null) {
            try {
                Object json = JSON.parse(str);
                return JSON.toJSONString(json, JSONWriter.Feature.PrettyFormat);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     // * 取消美化
     // *
     // * @param str 对象
     // * @return json取消美化字符串
     // */
    //public static String unPretty(String str) {
    //    if (str != null) {
    //        StringBuilder builder = new StringBuilder();
    //        char[] chars = str.toCharArray();
    //        for (char aChar : chars) {
    //            if (aChar != '\r' && aChar != '\t' && aChar != '\n') {
    //                builder.append(aChar);
    //            }
    //        }
    //        return builder.toString();
    //    }
    //    return null;
    //}

    /**
     * 压缩
     *
     * @param json 对象
     * @return json压缩字符串
     */
    public static String toCompress(String json) {
        StringBuilder result = new StringBuilder();
        boolean inString = false;
        boolean escaping = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escaping) {
                // 当前字符是转义序列的一部分
                result.append(c);
                escaping = false;
                continue;
            }
            if (c == '\\') {
                // 开始转义序列
                result.append(c);
                escaping = true;
                continue;
            }
            if (c == '\"') {
                // 遇到引号
                result.append(c);
                inString = !inString; // 切换字符串状态
                continue;
            }
            if (inString) {
                // 在字符串内，保留所有字符
                result.append(c);
            } else {
                // 在字符串外，保留所有非空白字符和必要的结构字符
                // 只去除真正的格式空白（空格、制表符、换行、回车）
                if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    /**
     * 转换为json字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    public static String toJson(Object obj) {
        try {
            return JSON.toJSONString(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否json字符串
     *
     * @param json json字符串
     * @return 结果
     */
    public static boolean isJson(String json) {
        if (json != null && !json.isBlank() && (json.contains("{") || json.contains("["))) {
            try {
                return JSON.isValid(json);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析为json对象
     *
     * @param json json串
     * @return json对象
     */
    public static JSONObject parseObject(String json) {
        com.alibaba.fastjson2.JSONObject object = com.alibaba.fastjson2.JSONObject.parseObject(json);
        if (object != null) {
            return new JSONObject(object);
        }
        return new JSONObject();
    }

    /**
     * 解析为json树组
     *
     * @param json json串
     * @return json树组
     */
    public static JSONArray parseArray(String json) {
        com.alibaba.fastjson2.JSONArray array = com.alibaba.fastjson2.JSONArray.parseArray(json);
        if (array != null) {
            return new JSONArray(array);
        }
        return new JSONArray();
    }


    /**
     * 解析为bean
     *
     * @param json json串
     * @return java对象
     */
    public static <T> T toBean(String json, Class<T> beanClass) {
        try {
            com.alibaba.fastjson2.JSONObject object = com.alibaba.fastjson2.JSONObject.parseObject(json);
            return object.toJavaObject(beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 转换为java对象列表
     *
     * @param json json字符串
     * @param <T>  对象泛型
     * @return java对象列表
     */
    public static <T> List<T> toBeanList(String json, Class<T> beanClass) {
        try {
            com.alibaba.fastjson2.JSONArray array = com.alibaba.fastjson2.JSONArray.parseArray(json);
            return array.toJavaList(beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 转换为java对象列表
     *
     * @param array json字符串
     * @param <T>   对象泛型
     * @return java对象列表
     */
    public static <T> List<T> toBeanList(JSONArray array, Class<T> beanClass) {
        try {
            return array.toList(beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }
}
