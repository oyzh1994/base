package cn.oyzh.common.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-09-29
 */
//@UtilityClass
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
    public static JSONObject parseObject(@NonNull String json) {
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
    public static JSONArray parseArray(@NonNull String json) {
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
            return array.toBeanList(beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }
}
