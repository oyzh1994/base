package cn.oyzh.common.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author oyzh
 * @since 2024/7/1
 */
@UtilityClass
public class CollectionUtil {

    public static <T> T indexOf(Collection<T> collection, int index) {
        if (collection != null && !collection.isEmpty() && index > 0 && index < collection.size()) {
            int i = 0;
            for (T t : collection) {
                if (i++ == index) {
                    return t;
                }
            }
        }
        return null;
    }

    public static <T> T get(List<T> list, int index) {
        if (list != null && !list.isEmpty() && index > 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public static <T> T get(Collection<T> list, int index) {
        if (list != null && !list.isEmpty() && index > 0 && index < list.size()) {
            int i = 0;
            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (i++ == index) {
                    return iterator.next();
                }
            }
        }
        return null;
    }

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <T> List<List<T>> split(Collection<T> collection, int limit) {
        if (collection == null) {
            return Collections.emptyList();
        }
        if (collection.size() <= limit) {
            return Collections.singletonList(new ArrayList<>(collection));
        }
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < collection.size(); i += limit) {
            int end = Math.min(i + limit, collection.size());
            result.add(new ArrayList<>(new ArrayList<>(collection).subList(i, end)));
        }
        return result;
    }

    public static boolean contains(List<?> list, Object t) {
        return list != null && list.contains(t);
    }

    public static <T> T getFirst(List<T> list) {
        if (list != null && !list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    public static <T> T getFirst(Collection<T> collection) {
        if (collection != null && !collection.isEmpty()) {
            for (T t : collection) {
                return t;
            }
        }
        return null;
    }

    public static <T> T getLast(List<T> list) {
        if (list != null && !list.isEmpty()) {
            return list.getLast();
        }
        return null;
    }

    public static String join(Collection<String> collection, String lineSeparator) {
        if (collection == null || lineSeparator == null) {
            return null;
        }
        return String.join(lineSeparator, collection);
    }

    public static List<String> removeBlank(List<String> elements) {
        elements.removeIf(StringUtil::isBlank);
        return elements;
    }

    public static <T> T removeRandom(Map<?, T> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        int size = map.size();
        Random random = new Random();
        int index = random.nextInt(size);
        Object key = get(map.keySet(), index);
        return map.remove(key);
    }

    /**
     * 移除随机元素
     *
     * @param list 集合
     * @param <T>  泛型
     * @return 随机元素
     */
    public static <T> T removeRandom(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        Random random = new Random();
        int index = random.nextInt(size);
        T value = get(list, index);
        list.remove(value);
        return value;
    }

    /**
     * 获取随机元素
     *
     * @param list 集合
     * @param <T>  泛型
     * @return 随机元素
     */
    public static <T> T getRandom(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        Random random = new Random();
        int index = random.nextInt(size);
        return get(list, index);
    }

    public static int size(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return 0;
        }
        return collection.size();
    }
}
