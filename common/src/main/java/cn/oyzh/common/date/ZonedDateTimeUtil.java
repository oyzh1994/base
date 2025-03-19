package cn.oyzh.common.date;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author oyzh
 * @since 2024-09-25
 */
//@UtilityClass
public class ZonedDateTimeUtil {

    public static ZonedDateTime of(Date date) {
        return of(date.toInstant());
    }

    public static ZonedDateTime of(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
