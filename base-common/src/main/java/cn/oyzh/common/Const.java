package cn.oyzh.common;

import java.text.SimpleDateFormat;

/**
 * 常量对象
 *
 * @author oyzh
 * @since 2020/9/14
 */
public class Const {

    /**
     * 全局通用日期时间格式化对象（格式：yyyy-MM-dd HH:mm:ss.SSS）
     */
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 全局通用时间格式化对象（格式：HH:mm:ss.SSS）
     */
    public final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

}
