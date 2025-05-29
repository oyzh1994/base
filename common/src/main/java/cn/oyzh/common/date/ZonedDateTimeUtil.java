package cn.oyzh.common.date;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author oyzh
 * @since 2024-09-25
 */
public class ZonedDateTimeUtil {

    public static ZonedDateTime of(Date date) {
        return of(date.toInstant());
    }

    public static ZonedDateTime of(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
