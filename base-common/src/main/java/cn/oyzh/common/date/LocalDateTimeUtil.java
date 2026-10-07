package cn.oyzh.common.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 本地日期时间工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class LocalDateTimeUtil {

    /**
     * 将日期转换为本地日期时间
     *
     * @param date 日期
     * @return 本地日期时间
     */
    public static LocalDateTime of(Date date) {
        return of(date.toInstant());
    }

    /**
     * 将本地日期与当前时间组合为本地日期时间
     *
     * @param date 本地日期
     * @return 本地日期时间
     */
    public static LocalDateTime of(LocalDate date) {
        return of(date, LocalTime.now());
    }

    /**
     * 将本地日期与本地时间组合为本地日期时间
     *
     * @param date      本地日期
     * @param localTime 本地时间
     * @return 本地日期时间
     */
    public static LocalDateTime of(LocalDate date, LocalTime localTime) {
        return LocalDateTime.of(date, localTime);
    }

    /**
     * 将时间戳转换为本地日期时间
     *
     * @param instant 时间戳
     * @return 本地日期时间
     */
    public static LocalDateTime of(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 格式化时间
     *
     * @param localDateTime 时间
     * @param format        格式
     * @return 结果
     */
    public static String format(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * 解析时间
     *
     * @param time   时间
     * @param format 格式
     * @return 结果
     */
    public static LocalDateTime parse(CharSequence time, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        TemporalAccessor accessor = formatter.parse(time);
        return LocalDateTime.from(accessor);
    }
}
