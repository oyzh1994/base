package cn.oyzh.common.json;


import java.util.Collections;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class JSONObject extends com.alibaba.fastjson2.JSONObject {

    public JSONObject(com.alibaba.fastjson2.JSONObject object) {
        super(object);
    }

    @Override
    public JSONObject getJSONObject(String key) {
        return new JSONObject(super.getJSONObject(key));
    }

    @Override
    public JSONArray getJSONArray(String key) {
        return new JSONArray(super.getJSONArray(key));
    }

    public <T> T getBean(String key, Class<T> beanClass) {
        com.alibaba.fastjson2.JSONObject object = this.getJSONObject(key);
        if (object != null) {
            return object.toJavaObject(beanClass);
        }
        return null;
    }

    public <T> List<T> getBeanList(String key, Class<T> beanClass) {
        com.alibaba.fastjson2.JSONArray array = this.getJSONArray(key);
        if (array != null) {
            return array.toList(beanClass);
        }
        return Collections.emptyList();
    }

    public <T> T toBean(Class<T> beanClass) {
        try {
            return this.toJavaObject(beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Integer getInt(String key) {
        return super.getInteger(key);
    }
}
