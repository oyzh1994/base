package cn.oyzh.common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 数组工具类
 *
 * @author oyzh
 * @since 2022/5/7
 */
public class ArrayUtil {

    /**
     * 私有构造，禁止实例化
     */
    private ArrayUtil() {
    }

    /**
     * 获取首个数据
     *
     * @param arr 数组
     * @param <T> 数据类型
     * @return 数据
     */
    public static <T> T first(T[] arr) {
        if (arr != null && arr.length > 0) {
            return arr[0];
        }
        return null;
    }

    /**
     * 获取最后一个数据
     *
     * @param arr 数组
     * @param <T> 数据类型
     * @return 数据
     */
    public static <T> T last(T[] arr) {
        if (arr != null && arr.length > 0) {
            return arr[arr.length - 1];
        }
        return null;
    }

    /**
     * 合并两个数组
     *
     * @param arr1 数组1
     * @param arr2 数组2
     * @param <T>  数据类型
     * @return 合并后的新数组
     */
    public static <T> T[] append(T[] arr1, T[] arr2) {
        int len1 = arr1.length;
        int len2 = arr2.length;
        T[] result = Arrays.copyOf(arr1, len1 + len2);
        System.arraycopy(arr2, 0, result, len1, len2);
        return result;
    }

    /**
     * 判断数组是否为空
     *
     * @param arr 数组
     * @param <T> 数据类型
     * @return 为null或长度为0时返回true
     */
    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * 获取指定索引处的元素
     *
     * @param arr   数组
     * @param index 索引
     * @param <T>   数据类型
     * @return 对应元素，索引越界时返回null
     */
    public static <T> T indexOf(T[] arr, int index) {
        if (index < 0 || index >= arr.length) {
            return null;
        }
        return arr[index];
    }

    /**
     * 判断数组是否不为空
     *
     * @param arr 数组
     * @param <T> 数据类型
     * @return 不为null且长度大于0时返回true
     */
    public static <T> boolean isNotEmpty(T[] arr) {
        return !isEmpty(arr);
    }

    /**
     * 数组转字符串
     *
     * @param arr 数组
     * @param <T> 数据类型
     * @return 数组的字符串表示，空数组返回空字符串
     */
    public static <T> String toString(T[] arr) {
        if (isEmpty(arr)) {
            return "";
        }
        return Arrays.toString(arr);
    }

    /**
     * 判断数组是否包含指定元素
     *
     * @param arr 数组
     * @param obj 目标元素
     * @param <T> 数据类型
     * @return 包含返回true，否则返回false
     */
    public static <T> boolean contains(T[] arr, T obj) {
        if (arr != null && arr.length > 0 && obj != null) {
            for (T t : arr) {
                if (t.equals(obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 截取数组
     *
     * @param arr   数组
     * @param start 起始索引（含）
     * @param end   结束索引（不含）
     * @param <T>   数据类型
     * @return 截取后的新数组，参数非法时原样返回原数组
     */
    public static <T> T[] sub(T[] arr, int start, int end) {
        if (arr == null || start < 0 || end < start || arr.length < end) {
            return arr;
        }
        return Arrays.copyOfRange(arr, start, end);
    }

    /**
     * 截取数组
     *
     * @param arr   数组
     * @param start 起始索引（含）
     * @param end   结束索引（不含）
     * @param <T>   数据类型
     * @return 截取后的新数组，参数非法时原样返回原数组
     */
    public static <T> T[] subarray(T[] arr, int start, int end) {
        return sub(arr, start, end);
    }

    /**
     * 截取字节数组
     *
     * @param arr   字节数组
     * @param start 起始索引（含）
     * @param end   结束索引（不含）
     * @return 截取后的新数组，参数非法时原样返回原数组
     */
    public static byte[] sub(byte[] arr, int start, int end) {
        if (arr == null || start < 0 || end < start || arr.length < end) {
            return arr;
        }
        return Arrays.copyOfRange(arr, start, end);
    }

    /**
     * 截取字节数组
     *
     * @param arr   字节数组
     * @param start 起始索引（含）
     * @param end   结束索引（不含）
     * @return 截取后的新数组，参数非法时原样返回原数组
     */
    public static byte[] subarray(byte[] arr, int start, int end) {
        return sub(arr, start, end);
    }

    /**
     * 集合转数组
     *
     * @param elements 集合
     * @param clazz    数组元素类型
     * @param <T>      数据类型
     * @return 转换后的数组，集合或类型为null时返回null
     */
    public static <T> T[] toArray(Collection<T> elements, Class<T> clazz) {
        if (elements == null || clazz == null) {
            return null;
        }
        T[] result = (T[]) Array.newInstance(clazz, 0);
        return elements.toArray(result);
    }

    /**
     * 复制字节数组到目标数组
     *
     * @param source 源数组
     * @param target 目标数组
     */
    public static void copy(byte[] source, byte[] target) {
        System.arraycopy(source, 0, target, 0, source.length);
    }

    /**
     * 复制指定长度的字节数组
     *
     * @param source 源数组
     * @param length 复制长度
     * @return 复制后的新数组
     */
    public static byte[] copy(byte[] source, int length) {
        byte[] target = new byte[length];
        System.arraycopy(source, 0, target, 0, length);
        return target;
    }

    /**
     * 反转字符数组
     *
     * @param charArray 字符数组
     * @return 反转后的新数组
     */
    public static char[] reverse(char[] charArray) {
        List<Character> list = new ArrayList<>();
        for (char c : charArray) {
            list.add(c);
        }
        list = list.reversed();
        char[] chars = new char[charArray.length];
        for (int i = 0; i < list.size(); i++) {
            char c = list.get(i);
            chars[i] = c;
        }
        return chars;
    }

    /**
     * 拼接
     *
     * @param arr     数组
     * @param joinStr 拼接字符
     * @param <T>     泛型
     * @return 结果
     */
    public static <T> String join(T[] arr, String joinStr) {
        StringBuilder builder = new StringBuilder();
        for (T t : arr) {
            builder.append(joinStr).append(t);
        }
        return builder.isEmpty() ? builder.toString() : builder.substring(1);
    }
}
