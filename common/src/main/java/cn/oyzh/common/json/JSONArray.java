package cn.oyzh.common.json;

import com.github.cliftonlabs.json_simple.JsonArray;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class JSONArray extends JsonArray {

    public JSONArray(Collection<?> array) {
        if (array != null) {

            this.addAll(array);
        }
    }

    public <T> List<T> toBeanList(Class<T> beanClass) {
        try {
            return JSONParser.INSTANCE.toBean(this, beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

}
