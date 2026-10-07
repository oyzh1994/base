package cn.oyzh.store.jdbc;

import cn.oyzh.common.date.DateUtil;
import cn.oyzh.common.date.LocalDateTimeUtil;
import cn.oyzh.common.date.LocalDateUtil;
import cn.oyzh.common.date.LocalTimeUtil;
import cn.oyzh.common.date.ZonedDateTimeUtil;
import cn.oyzh.common.util.IOUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.store.jdbc.h2.H2Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * jdbc数据转换工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class JdbcUtil {

    /**
     * 转为布尔对象
     *
     * @param sqlData 数据库数据
     * @return 布尔对象
     */
    public static Boolean toBool(Object sqlData) {
        if (sqlData instanceof Boolean b) {
            return b;
        }
        if (sqlData instanceof Number n) {
            return n.byteValue() == 1;
        }
        if (sqlData instanceof String n) {
            return StringUtil.equalsAnyIgnoreCase(n, "1", "y", "yes", "true");
        }
        return null;
    }

    /**
     * 转为布尔值，无法转换时返回false
     *
     * @param sqlData 数据库数据
     * @return 布尔值
     */
    public static boolean toBoolVal(Object sqlData) {
        Boolean b = toBool(sqlData);
        return b != null && b;
    }

    /**
     * 转为字节对象
     *
     * @param sqlData 数据库数据
     * @return 字节对象
     */
    public static Byte toByte(Object sqlData) {
        if (sqlData instanceof Byte) {
            return (Byte) sqlData;
        }
        if (sqlData instanceof Number n) {
            return n.byteValue();
        }
        if (sqlData instanceof String n) {
            return Byte.parseByte(n);
        }
        return null;
    }

    /**
     * 转为字节值，无法转换时返回0
     *
     * @param sqlData 数据库数据
     * @return 字节值
     */
    public static Byte toByteVal(Object sqlData) {
        Byte b = toByte(sqlData);
        return b == null ? 0 : b;
    }

    /**
     * 转为字符串
     *
     * @param sqlData 数据库数据
     * @return 字符串
     */
    public static String toString(Object sqlData) {
        return sqlData == null ? null : sqlData.toString();
    }

    /**
     * 转为StringBuffer
     *
     * @param sqlData 数据库数据
     * @return StringBuffer
     */
    public static StringBuffer toStringBuffer(Object sqlData) {
        return sqlData == null ? null : new StringBuffer(sqlData.toString());
    }

    /**
     * 转为StringBuilder
     *
     * @param sqlData 数据库数据
     * @return StringBuilder
     */
    public static StringBuilder toStringBuilder(Object sqlData) {
        return sqlData == null ? null : new StringBuilder(sqlData.toString());
    }

    /**
     * 转为int值
     *
     * @param sqlData 数据库数据
     * @return int值
     */
    public static int toInt(Object sqlData) {
        if (sqlData == null) {
            throw new IllegalArgumentException("sqlData");
        }
        if (sqlData instanceof Number n) {
            return n.intValue();
        }
        return Integer.parseInt(sqlData.toString());
    }

    /**
     * 转为Integer对象
     *
     * @param sqlData 数据库数据
     * @return Integer对象
     */
    public static Integer toIntVal(Object sqlData) {
        if (sqlData == null) {
            return null;
        }
        return toInt(sqlData);
    }

    /**
     * 转为long值
     *
     * @param sqlData 数据库数据
     * @return long值
     */
    public static long toLong(Object sqlData) {
        if (sqlData == null) {
            throw new IllegalArgumentException("sqlData");
        }
        if (sqlData instanceof Number n) {
            return n.longValue();
        }
        if (sqlData instanceof Date n) {
            return n.getTime();
        }
        return Long.parseLong(sqlData.toString());
    }

    /**
     * 转为Long对象
     *
     * @param sqlData 数据库数据
     * @return Long对象
     */
    public static Long toLongVal(Object sqlData) {
        if (sqlData == null) {
            return null;
        }
        return toLong(sqlData);
    }

    /**
     * 转为double值
     *
     * @param sqlData 数据库数据
     * @return double值
     */
    public static double toDouble(Object sqlData) {
        if (sqlData == null) {
            throw new IllegalArgumentException("sqlData");
        }
        if (sqlData instanceof Number n) {
            return n.doubleValue();
        }
        return Double.parseDouble(sqlData.toString());
    }

    /**
     * 转为Double对象
     *
     * @param sqlData 数据库数据
     * @return Double对象
     */
    public static Double toDoubleVal(Object sqlData) {
        if (sqlData == null) {
            return null;
        }
        return toDouble(sqlData);
    }

    /**
     * 转为float值
     *
     * @param sqlData 数据库数据
     * @return float值
     */
    public static float toFloat(Object sqlData) {
        if (sqlData == null) {
            throw new IllegalArgumentException("sqlData");
        }
        if (sqlData instanceof Number n) {
            return n.floatValue();
        }
        return Float.parseFloat(sqlData.toString());
    }

    /**
     * 转为Float对象
     *
     * @param sqlData 数据库数据
     * @return Float对象
     */
    public static Float toFloatVal(Object sqlData) {
        if (sqlData == null) {
            return null;
        }
        return toFloat(sqlData);
    }

    /**
     * 转为short值
     *
     * @param sqlData 数据库数据
     * @return short值
     */
    public static short toShort(Object sqlData) {
        if (sqlData == null) {
            throw new IllegalArgumentException("sqlData");
        }
        if (sqlData instanceof Number n) {
            return n.shortValue();
        }
        return Short.parseShort(sqlData.toString());
    }

    /**
     * 转为Short对象
     *
     * @param sqlData 数据库数据
     * @return Short对象
     */
    public static Short toShortVal(Object sqlData) {
        if (sqlData == null) {
            return null;
        }
        return toShort(sqlData);
    }

    /**
     * 转为char值
     *
     * @param sqlData 数据库数据
     * @return char值
     */
    public static char toChar(Object sqlData) {
        if (sqlData == null) {
            throw new IllegalArgumentException("sqlData");
        }
        if (sqlData instanceof CharSequence n) {
            return n.charAt(0);
        }
        return 0;
    }

    /**
     * 转为Character对象
     *
     * @param sqlData 数据库数据
     * @return Character对象
     */
    public static Character toCharVal(Object sqlData) {
        if (sqlData == null) {
            return null;
        }
        return toChar(sqlData);
    }

    /**
     * 转为字节数组
     *
     * @param sqlData 数据库数据
     * @return 字节数组
     * @throws SQLException 异常
     * @throws IOException  异常
     */
    public static byte[] toBytes(Object sqlData) throws SQLException, IOException {
        if (sqlData instanceof Blob blob) {
            InputStream inputStream = blob.getBinaryStream();
            byte[] bytes = blob.getBinaryStream().readAllBytes();
            IOUtil.close(inputStream);
            return bytes;
        }
        if (sqlData instanceof byte[] bytes) {
            return bytes;
        }
        if (sqlData instanceof Byte[] bytes) {
            byte[] bytes1 = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                bytes1[i] = bytes[i];
            }
            return bytes1;
        }
        return null;
    }

    /**
     * 转为Date对象
     *
     * @param sqlData 数据库数据
     * @return Date对象
     */
    public static Date toDate(Object sqlData) {
        if (sqlData instanceof Date date) {
            return date;
        }
        if (sqlData instanceof Number n) {
            return DateUtil.of(n);
        }
        return null;
    }

    /**
     * 转为LocalTime对象
     *
     * @param sqlData 数据库数据
     * @return LocalTime对象
     */
    public static LocalTime toLocalTime(Object sqlData) {
        if (sqlData instanceof Date date) {
            return LocalTimeUtil.of(date);
        }
        return null;
    }

    /**
     * 转为LocalDate对象
     *
     * @param sqlData 数据库数据
     * @return LocalDate对象
     */
    public static LocalDate toLocalDate(Object sqlData) {
        if (sqlData instanceof Date date) {
            return LocalDateUtil.of(date);
        }
        return null;
    }

    /**
     * 转为LocalDateTime对象
     *
     * @param sqlData 数据库数据
     * @return LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(Object sqlData) {
        if (sqlData instanceof Date date) {
            return LocalDateTimeUtil.of(date);
        }
        return null;
    }

    /**
     * 转为ZonedDateTime对象
     *
     * @param sqlData 数据库数据
     * @return ZonedDateTime对象
     */
    public static ZonedDateTime toZonedDateTime(Object sqlData) {
        if (sqlData instanceof Date date) {
            return ZonedDateTimeUtil.of(date);
        }
        return null;
    }

    /**
     * 转为java值
     *
     * @param javaType java类型
     * @param sqlData  数据库数据
     * @return 数据
     * @throws SQLException sql异常
     * @throws IOException  io异常
     */
    public static Object toJavaValue(Class<?> javaType, Object sqlData) throws SQLException, IOException {
        if (sqlData == null) {
            return null;
        }
        if (javaType == boolean.class) {
            return toBoolVal(sqlData);
        }
        if (javaType == Boolean.class) {
            return toBool(sqlData);
        }
        if (javaType == byte.class) {
            return toByteVal(sqlData);
        }
        if (javaType == Byte.class) {
            return toByte(sqlData);
        }
        if (javaType == int.class) {
            return toInt(sqlData);
        }
        if (javaType == Integer.class) {
            return toIntVal(sqlData);
        }
        if (javaType == long.class) {
            return toLong(sqlData);
        }
        if (javaType == Long.class) {
            return toLongVal(sqlData);
        }
        if (javaType == double.class) {
            return toDouble(sqlData);
        }
        if (javaType == Double.class) {
            return toDoubleVal(sqlData);
        }
        if (javaType == float.class) {
            return toFloat(sqlData);
        }
        if (javaType == Float.class) {
            return toFloatVal(sqlData);
        }
        if (javaType == short.class) {
            return toShort(sqlData);
        }
        if (javaType == Short.class) {
            return toShortVal(sqlData);
        }
        if (javaType == char.class) {
            return toChar(sqlData);
        }
        if (javaType == Character.class) {
            return toCharVal(sqlData);
        }
        if (javaType == String.class) {
            return toString(sqlData);
        }
        if (javaType == StringBuffer.class) {
            return toStringBuffer(sqlData);
        }
        if (javaType == StringBuilder.class) {
            return toStringBuilder(sqlData);
        }
        if (javaType == Date.class) {
            return toDate(sqlData);
        }
        if (javaType == LocalTime.class) {
            return toLocalTime(sqlData);
        }
        if (javaType == LocalDate.class) {
            return toLocalDate(sqlData);
        }
        if (javaType == LocalDateTime.class) {
            return toLocalDateTime(sqlData);
        }
        if (javaType == ZonedDateTime.class) {
            return toZonedDateTime(sqlData);
        }
        if (javaType == Byte[].class) {
            byte[] bytes = toBytes(sqlData);
            Byte[] bytes1 = new Byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                bytes1[i] = bytes[i];
            }
            return bytes1;
        }
        if (javaType == byte[].class) {
            return toBytes(sqlData);
        }
        return null;
    }

    /**
     * 包装标识符，按当前方言处理
     *
     * @param data 标识符
     * @return 包装后的标识符
     */
    public static Object wrap(String data) {
        if (JdbcManager.dialect == JdbcDialect.H2) {
            return H2Util.wrap(data);
        }
        return data;
//        return SqlLiteUtil.wrap(data);
    }

    /**
     * 包装数据值，按当前方言处理
     *
     * @param data 数据值
     * @return 包装后的数据值
     */
    public static Object wrapData(Object data) {
        if (JdbcManager.dialect == JdbcDialect.H2) {
            return H2Util.wrapData(data);
        }
        return data;
//        return SqlLiteUtil.wrapData(data);
    }
}
