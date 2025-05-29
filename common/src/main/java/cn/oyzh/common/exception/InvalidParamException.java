package cn.oyzh.common.exception;


/**
 * 无效参数异常
 * @author oyzh
 * @since 2024-09-26
 */
public class InvalidParamException extends RuntimeException {

    public InvalidParamException(String param) {
        super("Parameter '" + param + "' is invalid.");
    }
}
