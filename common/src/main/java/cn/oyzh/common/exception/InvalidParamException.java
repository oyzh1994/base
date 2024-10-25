package cn.oyzh.common.exception;


/**
 * @author oyzh
 * @since 2024-09-26
 */
public class InvalidParamException extends RuntimeException {

    public InvalidParamException(String param) {
        super("Parameter '" + param + "' is invalid.");
    }
}
