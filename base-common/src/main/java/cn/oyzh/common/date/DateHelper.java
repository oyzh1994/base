package cn.oyzh.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * 日期辅助类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class DateHelper {

    /**
     * 全局通用日期时间格式化对象（含毫秒）
     */
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 全局通用日期时间格式化对象（不含毫秒）
     */
    public static final SimpleDateFormat DATE_TIME_SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 全局通用时间格式化对象（含毫秒）
     */
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * 全局通用时间格式化对象（不含毫秒）
     */
    public static final SimpleDateFormat TIME_SIMPLE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * 全局通用年份格式化对象
     */
    public static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");

    /**
     * 全局通用日期格式化对象
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取当前日期时间字符串（含毫秒）
     *
     * @return 日期时间字符串
     */
    public static String formatDateTime() {
        return DATE_TIME_FORMAT.format(System.currentTimeMillis());
    }

    /**
     * 格式化日期时间为字符串（含毫秒）
     *
     * @param date 日期
     * @return 日期时间字符串
     */
    public static String formatDateTime(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    /**
     * 解析日期时间字符串（含毫秒）
     *
     * @param date 日期时间字符串
     * @return 日期
     */
    public static Date parseDateTime(String date) {
        try {
            return DATE_TIME_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化时间戳为日期时间字符串（含毫秒）
     *
     * @param instant 时间戳
     * @return 日期时间字符串
     */
    public static String formatDateTime(Instant instant) {
        return DATE_TIME_FORMAT.format(instant.toEpochMilli());
    }

    /**
     * 获取当前日期时间字符串（不含毫秒）
     *
     * @return 日期时间字符串
     */
    public static String formatDateTimeSimple() {
        return DATE_TIME_SIMPLE_FORMAT.format(System.currentTimeMillis());
    }

    /**
     * 格式化日期时间为字符串（不含毫秒）
     *
     * @param date 日期
     * @return 日期时间字符串
     */
    public static String formatDateTimeSimple(Date date) {
        return DATE_TIME_SIMPLE_FORMAT.format(date);
    }

    /**
     * 获取当前年份字符串
     *
     * @return 年份字符串
     */
    public static String formatYear() {
        return YEAR_FORMAT.format(System.currentTimeMillis());
    }

    /**
     * 格式化日期为年份字符串
     *
     * @param date 日期
     * @return 年份字符串
     */
    public static String formatYear(Date date) {
        return YEAR_FORMAT.format(date);
    }

    /**
     * 获取当前时间字符串（含毫秒）
     *
     * @return 时间字符串
     */
    public static String formatTime() {
        return TIME_FORMAT.format(System.currentTimeMillis());
    }

    /**
     * 格式化时间为字符串（含毫秒）
     *
     * @param date 日期
     * @return 时间字符串
     */
    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }

    /**
     * 格式化时间戳为时间字符串（含毫秒）
     *
     * @param instant 时间戳
     * @return 时间字符串
     */
    public static String formatTime(Instant instant) {
        return TIME_FORMAT.format(instant.toEpochMilli());
    }

    /**
     * 获取当前时间字符串（不含毫秒）
     *
     * @return 时间字符串
     */
    public static String formatTimeSimple() {
        return TIME_SIMPLE_FORMAT.format(System.currentTimeMillis());
    }

    /**
     * 格式化时间为字符串（不含毫秒）
     *
     * @param date 日期
     * @return 时间字符串
     */
    public static String formatTimeSimple(Date date) {
        return TIME_SIMPLE_FORMAT.format(date);
    }

    /**
     * 获取当前日期字符串
     *
     * @return 日期字符串
     */
    public static String formatDate() {
        return DATE_FORMAT.format(System.currentTimeMillis());
    }

    /**
     * 格式化日期为日期字符串
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}
