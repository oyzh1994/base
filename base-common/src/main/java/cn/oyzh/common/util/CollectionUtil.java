package cn.oyzh.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 集合工具类
 *
 * @author oyzh
 * @since 2024/7/1
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

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

    /**
     * 获取数据
     *
     * @param list  集合
     * @param index 索引
     * @param <T>   泛型
     * @return 数据
     */
    public static <T> T get(List<T> list, int index) {
        if (list != null && !list.isEmpty() && index >= 0 && index < list.size()) {
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

    /**
     * 分割集合
     *
     * @param collection 集合
     * @param limit      限制数量
     * @param <T>        集合列表
     * @return 分割后的集合
     */
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

    /**
     * 将集合均分成 n 个子集合，允许余数均匀分布在前几个子集合中。
     * 例如：12个元素分成5份 -> [3, 3, 2, 2, 2]
     *
     * @param originalList 原始集合
     * @param n            份数
     */
    public static <T> List<List<T>> splitIntoParts(List<T> originalList, int n) {
        if (originalList == null || originalList.isEmpty()) {
            return Collections.emptyList();
        }
        if (n <= 0) {
            throw new IllegalArgumentException("子集合数量 n 必须为正整数");
        }
        if (n > originalList.size()) {
            n = originalList.size(); // 若 n 超过集合大小，调整为集合大小
        }

        List<List<T>> result = new ArrayList<>(n);
        int size = originalList.size();
        int baseSize = size / n;
        int remainder = size % n;

        int index = 0;
        for (int i = 0; i < n; i++) {
            int currentSize = baseSize + (i < remainder ? 1 : 0);
            result.add(originalList.subList(index, index + currentSize));
            index += currentSize;
        }
        return result;
    }

    /**
     * 将集合按固定大小分割成多个子集合，最后一个子集合可能不足 size。
     * 例如：12个元素按 size=5 分割 -> [5, 5, 2]
     *
     * @param originalList 原始集合
     * @param size         大小
     */
    public static <T> List<List<T>> splitBySize(List<T> originalList, int size) {
        if (originalList == null || originalList.isEmpty()) {
            return Collections.emptyList();
        }
        if (size <= 0) {
            throw new IllegalArgumentException("子集合大小必须为正整数");
        }

        return IntStream.range(0, (originalList.size() + size - 1) / size)
                .mapToObj(i -> originalList.subList(i * size, Math.min((i + 1) * size, originalList.size())))
                .collect(Collectors.toList());
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

    public static void removeBlank(List<String> elements) {
        elements.removeIf(StringUtil::isBlank);
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

    /**
     * 创建一个新的ArrayList
     *
     * @param <T> 范型
     * @return ArrayList
     */
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 排序
     *
     * @param collection 集合
     * @param comparator 比较器
     * @param <T>        范型
     * @return 排序后的集合
     */
    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(collection);
        list.sort(comparator);
        return list;
    }
}
