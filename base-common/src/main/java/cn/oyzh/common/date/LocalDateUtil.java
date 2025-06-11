package cn.oyzh.common.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 本地日期工具类
 *
 * @author oyzh
 * @since 2024-09-25
 */
public class LocalDateUtil {

    public static LocalDate of(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
