package cn.oyzh.common.exception;


/**
 * 无效参数异常
 *
 * @author oyzh
 * @since 2024-10-25
 */
public class InvalidParamException extends RuntimeException {

    /**
     * 创建携带无效参数信息的异常
     *
     * @param param 无效的参数
     */
    public InvalidParamException(String param) {
        super("Parameter '" + param + "' is invalid.");
    }
}
