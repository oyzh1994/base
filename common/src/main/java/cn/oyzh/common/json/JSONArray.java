package cn.oyzh.common.json;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class JSONArray implements Iterable<JsonElement>{

    private JsonArray array;

    public JSONArray(JsonArray array) {
        this.array = array;
    }

    public <T> List<T> toBeanList(Class<T> beanClass) {
        try {
            return JSONUtil.toBeanList(this.array, beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return array.iterator();
    }
}
