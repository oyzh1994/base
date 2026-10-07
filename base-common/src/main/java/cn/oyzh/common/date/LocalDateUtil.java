package cn.oyzh.common.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 本地日期工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class LocalDateUtil {

    /**
     * 将日期转换为本地日期
     *
     * @param date 日期
     * @return 本地日期
     */
    public static LocalDate of(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
