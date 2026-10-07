package cn.oyzh.common.exception;


import cn.oyzh.common.util.ArrayUtil;

/**
 * 无效数据异常
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class InvalidDataException extends RuntimeException {

    /**
     * 创建无参数的无效数据异常
     */
    public InvalidDataException() {
        super("Data is not valid");
    }

    /**
     * 创建携带无效数据信息的异常
     *
     * @param param 无效的数据
     */
    public InvalidDataException(String... param) {
        super("Data %s is not valid".formatted(ArrayUtil.toString(param)));
    }
}
