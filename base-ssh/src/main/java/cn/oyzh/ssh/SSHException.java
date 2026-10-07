package cn.oyzh.ssh;

/**
 * ssh异常
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class SSHException extends RuntimeException {

    /**
     * 构造ssh异常
     */
    public SSHException() {
        super();
    }

    /**
     * 构造ssh异常
     *
     * @param message 异常信息
     */
    public SSHException(String message) {
        super(message);
    }

    /**
     * 构造ssh异常
     *
     * @param s 异常信息
     * @param e 异常原因
     */
    public SSHException(String s, Throwable e) {
        super(s, e);
    }

    /**
     * 构造ssh异常
     *
     * @param ex 异常原因
     */
    public SSHException(Exception ex) {
        super(ex.getMessage(), ex);
    }
}
