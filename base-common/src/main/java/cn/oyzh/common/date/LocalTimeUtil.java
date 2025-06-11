package cn.oyzh.common.date;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 本地时间工具类
 *
 * @author oyzh
 * @since 2024-09-25
 */
public class LocalTimeUtil {

    public static LocalTime of(Date date) {
        return of(date.toInstant());
    }

    public static LocalTime of(Instant instant) {
        return LocalTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
