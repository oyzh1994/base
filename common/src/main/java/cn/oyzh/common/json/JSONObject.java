package cn.oyzh.common.json;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class JSONObject {

    private final JsonObject object;

    public JSONObject(JsonObject object) {
        this.object = object;
    }

    public boolean containsKey(String key) {
        return this.object.has(key);
    }

    public String getString(String key) {
        JsonElement element = object.get(key);
        return element == null ? null : element.getAsString();
    }

    public int getIntValue(String key) {
        JsonElement element = object.get(key);
        return element == null ? -1 : element.getAsInt();
    }

    public Integer getInt(String key) {
        JsonElement element = object.get(key);
        if (element == null) {
            return null;
        }
        Number number = element.getAsNumber();
        return number == null ? null : number.intValue();
    }

    public long getLongValue(String key) {
        JsonElement element = object.get(key);
        return element == null ? -1 : element.getAsLong();
    }

    public Long getLong(String key) {
        JsonElement element = object.get(key);
        if (element == null) {
            return null;
        }
        Number number = element.getAsNumber();
        return number == null ? null : number.longValue();
    }

    public byte getByteValue(String key) {
        JsonElement element = object.get(key);
        return element == null ? -1 : element.getAsByte();
    }

    public Byte getByte(String key) {
        JsonElement element = object.get(key);
        if (element == null) {
            return null;
        }
        Number number = element.getAsNumber();
        return number == null ? null : number.byteValue();
    }

    public float getFloatValue(String key) {
        JsonElement element = object.get(key);
        return element == null ? -1 : element.getAsFloat();
    }

    public Float getFloat(String key) {
        JsonElement element = object.get(key);
        if (element == null) {
            return null;
        }
        Number number = element.getAsNumber();
        return number == null ? null : number.floatValue();
    }

    public double getDoubleValue(String key) {
        JsonElement element = object.get(key);
        return element == null ? -1 : element.getAsDouble();
    }

    public Double getDouble(String key) {
        JsonElement element = object.get(key);
        if (element == null) {
            return null;
        }
        Number number = element.getAsNumber();
        return number == null ? null : number.doubleValue();
    }

    public short getShortValue(String key) {
        JsonElement element = object.get(key);
        return element == null ? -1 : element.getAsShort();
    }

    public Short getShort(String key) {
        JsonElement element = object.get(key);
        if (element == null) {
            return null;
        }
        Number number = element.getAsNumber();
        return number == null ? null : number.shortValue();
    }

    public JSONObject getJSONObject(String key) {
        JsonElement element = object.get(key);
        return element.isJsonObject() ? new JSONObject(element.getAsJsonObject()) : null;
    }

    public JSONArray getJSONArray(String key) {
        JsonElement element = object.get(key);
        return element.isJsonArray() ? new JSONArray(element.getAsJsonArray()) : null;
    }

    public <T> T getBean(String key, Class<T> beanClass) {
        JSONObject object = this.getJSONObject(key);
        if (object != null) {
            return object.toBean(beanClass);
        }
        return null;
    }

    public <T> List<T> getBeanList(String key, Class<T> beanClass) {
        JSONArray array = this.getJSONArray(key);
        if (array != null) {
            return array.toBeanList(beanClass);
        }
        return Collections.emptyList();
    }

    public <T> T toBean(Class<T> beanClass) {
        try {
            return JSONUtil.toBean(this.object, beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
