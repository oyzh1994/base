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

    /**
     * 私有构造，禁止实例化
     */
    private CalendarUtil() {
    }

    /**
     * 将日期转换为日历对象
     *
     * @param date 日期
     * @return 日历对象
     */
    public static Calendar of(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
