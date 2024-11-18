package cn.oyzh.common.json;

import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class JSONObject extends JsonObject {

    public JSONObject(Map<String, ?> object) {
        if (object != null) {

            this.putAll(object);
        }
    }

    public String getString(String key) {
        return super.getString(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
    }

    public int getIntValue(String key) {
        return super.getIntegerOrDefault(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return -1;
            }
        });
    }

    public Integer getInt(String key) {
        return super.getInteger(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
    }

    public long getLongValue(String key) {
        return super.getLongOrDefault(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return -1L;
            }
        });
    }

    public Long getLong(String key) {
        return super.getLong(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
    }

    public byte getByteValue(String key) {
        return super.getByteOrDefault(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return -1;
            }
        });
    }

    public Byte getByte(String key) {
        return super.getByte(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
    }

    public float getFloatValue(String key) {
        return super.getFloatOrDefault(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return -1.f;
            }
        });
    }

    public Float getFloat(String key) {
        return super.getFloat(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
    }

    public double getDoubleValue(String key) {
        return super.getDoubleOrDefault(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return -1.d;
            }
        });
    }

    public Double getDouble(String key) {
        return super.getDouble(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
    }

    public short getShortValue(String key) {
        return super.getShortOrDefault(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return -1;
            }
        });
    }

    public Short getShort(String key) {
        return super.getShort(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
    }

    public JSONObject getJSONObject(String key) {
        Object o = super.get(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
        return o instanceof JsonObject ? (JSONObject) o : null;
    }

    public JSONArray getJSONArray(String key) {
        Collection<?> collection = super.getCollection(new JsonKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return null;
            }
        });
        return new JSONArray(collection);
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
            return JSONParser.INSTANCE.toBean(this, beanClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
