package cn.oyzh.common.json;


import java.util.Collections;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class JSONArray extends com.alibaba.fastjson2.JSONArray {

    public JSONArray(com.alibaba.fastjson2.JSONArray array) {
        super(array);
    }

    @Override
    public JSONObject getJSONObject(int index) {
        return new JSONObject(super.getJSONObject(index));
    }

    public <T> List<T> toBeanList(Class<T> beanClass) {
        try {
            return super.toJavaList(beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }
}
