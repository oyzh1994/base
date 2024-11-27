package cn.oyzh.common.json;

import cn.oyzh.common.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-09-29
 */
@UtilityClass
public class JSONUtil {

    private static final Gson GSON = new Gson();

    private static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 美化
     *
     * @param obj 对象
     * @return json美化字符串
     */
    public static String toPretty(Object obj) {
        if (obj != null) {
            try {
                return GSON_PRETTY.toJson(obj);
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
                Object json = GSON_PRETTY.fromJson(str, Object.class);
                return GSON_PRETTY.toJson(json);
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
            return GSON.toJson(obj);
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
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement.isJsonObject() || jsonElement.isJsonArray();
        } catch (JsonSyntaxException ignore) {
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
        JsonElement element = JsonParser.parseString(json);
        if (element.isJsonObject()) {
            return new JSONObject(element.getAsJsonObject());
        }
        return null;
    }

    /**
     * 解析为json树组
     *
     * @param json json串
     * @return json树组
     */
    public static JSONArray parseArray(@NonNull String json) {
        JsonElement element = JsonParser.parseString(json);
        if (element.isJsonArray()) {
            return new JSONArray(element.getAsJsonArray());
        }
        return null;
    }

    /**
     * 解析为bean
     *
     * @param json json串
     * @return java对象
     */
    public static <T> T toBean(JsonObject json, Class<T> beanClass) {
        try {
            return GSON.fromJson(json, beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 解析为bean
     *
     * @param json json串
     * @return java对象
     */
    public static <T> T toBean(String json, Class<T> beanClass) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return GSON.fromJson(element, beanClass);
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
            JsonElement element = JsonParser.parseString(json);
            if (element.isJsonArray()) {
                List<T> list = new ArrayList<>();
                for (JsonElement obj : element.getAsJsonArray()) {
                    list.add(GSON.fromJson(obj, beanClass));
                }
                return list;
            }
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
    public static <T> List<T> toBeanList(JsonArray array, Class<T> beanClass) {
        try {
            List<T> list = new ArrayList<>();
            for (JsonElement obj : array) {
                list.add(GSON.fromJson(obj, beanClass));
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 转义显示不可见字符
     *
     * @param str 字符串
     * @return 转义后的字符串
     */
    public static String escape(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        final int len = str.length();
        final StringBuilder builder = new StringBuilder(len);
        char c;
        Character lastChar = null;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            String c1 = null;
            if (c == '"') {
                if (lastChar == null || lastChar != '\\') {
                    c1 = "\\\"";
                }
            } else if (c == '\n') {
                if (lastChar == null || lastChar != '\\') {
                    c1 = "\\n";
                }
            } else if (c == '\b') {
                if (lastChar == null || lastChar != '\\') {
                    c1 = "\\b";
                }
            } else if (c == '\t') {
                if (lastChar == null || lastChar != '\\') {
                    c1 = "\\t";
                }
            } else if (c == '\f') {
                if (lastChar == null || lastChar != '\\') {
                    c1 = "\\f";
                }
            } else if (c == '\r') {
                if (lastChar == null || lastChar != '\\') {
                    c1 = "\\r";
                }
            }
            if (c1 == null) {
                builder.append(c);
            } else {
                builder.append(c1);
            }
            lastChar = c;
        }
        return builder.toString();
    }
}
