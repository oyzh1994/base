package cn.oyzh.common.date;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 本地时间工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class LocalTimeUtil {

    /**
     * 从Date转为本地时间对象
     *
     * @param date 时间
     * @return 结果
     */
    public static LocalTime of(Date date) {
        return of(date.toInstant());
    }

    /**
     * 从Instant转为本地时间对象
     *
     * @param instant 时间
     * @return 结果
     */
    public static LocalTime of(Instant instant) {
        return LocalTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
