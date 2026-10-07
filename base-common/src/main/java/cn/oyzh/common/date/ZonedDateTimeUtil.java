package cn.oyzh.common.date;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 带时区的日期时间工具类
 *
 * @author oyzh
 * @since 2024-09-25
 */
public class ZonedDateTimeUtil {

    /**
     * 将日期转换为系统默认时区的日期时间
     *
     * @param date 日期
     * @return 带时区的日期时间
     */
    public static ZonedDateTime of(Date date) {
        return of(date.toInstant());
    }

    /**
     * 将时间戳转换为系统默认时区的日期时间
     *
     * @param instant 时间戳
     * @return 带时区的日期时间
     */
    public static ZonedDateTime of(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
