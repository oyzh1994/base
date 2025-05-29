package cn.oyzh.common.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 日历工具类
 *
 * @author oyzh
 * @since 2025-05-13
 */
public class CalendarUtil {

    public static Calendar of(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
