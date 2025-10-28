package cn.oyzh.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author oyzh
 * @since 2024/7/2
 */
public class DateUtil {

    public static Date of(Number n) {
        return of(n.longValue());
    }

    public static Date of(long l) {
        return new Date(l);
    }

    /**
     * 格式化时间
     * @param format 格式
     * @return 结果
     */
    public static String format(String format) {
        return format(System.currentTimeMillis(), format);
    }

    /**
     * 格式化时间
     * @param time 时间
     * @param format 格式
     * @return 结果
     */
    public static String format(long time, String format) {
        return new SimpleDateFormat(format).format(time);
    }

    /**
     * 格式化时间
     * @param time 时间
     * @param format 格式
     * @return 结果
     */
    public static String format(Date time, String format) {
        return new SimpleDateFormat(format).format(time);
    }

    /**
     * 格式化时间
     *
     * @param localDateTime 时间
     * @param format        格式
     * @return 结果
     */
    public static String format(LocalDateTime localDateTime, String format) {
        return LocalDateTimeUtil.format(localDateTime, format);
    }

    /**
     * 解析时间
     *
     * @param date   时间
     * @param format 格式
     * @return 结果
     * @throws ParseException 异常
     */
    public static Date parse(CharSequence date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date.toString());
    }

    /**
     * 解析时间
     *
     * @param date   时间
     * @param format 格式
     * @return 结果
     */
    public static LocalDateTime parseLocalDateTime(CharSequence date, String format) {
        return LocalDateTimeUtil.parse(date, format);
    }
}
