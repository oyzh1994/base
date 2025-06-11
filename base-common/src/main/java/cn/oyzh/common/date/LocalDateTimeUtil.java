package cn.oyzh.common.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 本地时间工具类
 *
 * @author oyzh
 * @since 2024-09-25
 */
public class LocalDateTimeUtil {

    public static LocalDateTime of(Date date) {
        return of(date.toInstant());
    }

    public static LocalDateTime of(LocalDate date) {
        return of(date, LocalTime.now());
    }

    public static LocalDateTime of(LocalDate date, LocalTime localTime) {
        return LocalDateTime.of(date, localTime);
    }

    public static LocalDateTime of(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
