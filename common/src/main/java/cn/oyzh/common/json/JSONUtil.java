package cn.oyzh.common.json;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-09-29
 */
@UtilityClass
public class JSONUtil {

    private static final JsonArray EMPTY_ARRAY = new JsonArray();

    private static final JsonObject EMPTY_OBJECT = new JsonObject();



    /**
     * 美化
     *
     * @param obj 对象
     * @return json美化字符串
     */
    public static String toPretty(Object obj) {
        if (obj != null) {
            try {
                String jsonStr = JSONParser.INSTANCE.toJson(obj);
                return Jsoner.prettyPrint(jsonStr);
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
                return Jsoner.prettyPrint(str);
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
            return JSONParser.INSTANCE.toJson(obj);
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
            Jsoner.deserialize(json);
            return true;
        } catch (JsonException ignore) {
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
        JsonObject object = Jsoner.deserialize(json, EMPTY_OBJECT);
        return new JSONObject(object);
    }

    /**
     * 解析为json树组
     *
     * @param json json串
     * @return json树组
     */
    public static JSONArray parseArray(@NonNull String json) {
        JsonArray array = Jsoner.deserialize(json, EMPTY_ARRAY);
        return new JSONArray(array);
    }

    /**
     * 解析为bean
     *
     * @param json      json串
     * @param beanClass java类
     * @return java对象
     */
    public static <T> T toBean(String json, Class<T> beanClass) {
        try {
            JsonObject object = Jsoner.deserialize(json, EMPTY_OBJECT);
            return JSONParser.INSTANCE.toBean(object, beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 转换为java对象列表
     *
     * @param collection 对象集合
     * @param beanClass  java类
     * @param <T>        对象泛型
     * @return java对象列表
     */
    public static <T> List<T> toBeanList(Collection<?> collection, Class<T> beanClass) {
        try {
            return JSONParser.INSTANCE.toBean(collection, beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 转换为java对象列表
     *
     * @param json      json字符串
     * @param beanClass java类
     * @param <T>       对象泛型
     * @return java对象列表
     */
    public static <T> List<T> toBeanList(String json, Class<T> beanClass) {
        try {
            JsonArray array = Jsoner.deserialize(json, EMPTY_ARRAY);
            return JSONParser.INSTANCE.toBean(array, beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }
}
