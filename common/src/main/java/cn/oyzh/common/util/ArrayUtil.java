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

    public static <T> T[] append(T[] arr1, T[] arr2) {
        int len1 = arr1.length;
        int len2 = arr2.length;
        T[] result = Arrays.copyOf(arr1, len1 + len2);
        System.arraycopy(arr2, 0, result, len1, len2);
        return result;
    }

    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    public static <T> T indexOf(T[] arr,int index) {
        if(index < 0 || index >= arr.length) {
            return null;
        }
        return arr[index];
    }

    public static  <T> boolean isNotEmpty(T[] arr) {
        return !isEmpty(arr);
    }

    public static <T> String toString(T[] arr) {
        if (isEmpty(arr)) {
            return "";
        }
        return Arrays.toString(arr);
    }

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

    public static <T> T[] sub(T[] arr, int start, int end) {
        if (arr == null || start < 0 || end < start || arr.length < end) {
            return arr;
        }
        return Arrays.copyOfRange(arr, start, end);
    }

    public static <T> T[] toArray(Collection<T> elements, Class<T> clazz) {
        if (elements == null || clazz == null) {
            return null;
        }
        T[] result = (T[]) Array.newInstance(clazz, 0);
        return elements.toArray(result);
    }

    public static void copy(byte[] source, byte[] target) {
        System.arraycopy(source, 0, target, 0, source.length);
    }

    public static byte[] copy(byte[] source, int length) {
        byte[] target = new byte[length];
        System.arraycopy(source, 0, target, 0, length);
        return target;
    }

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
}
