package cn.oyzh.common.thread;

/**
 * 进程执行结果
 *
 * @author oyzh
 * @since 2025-02-11
 */
public class ProcessExecResult {

    /**
     * 标准输出内容
     */
    private String input;

    /**
     * 错误输出内容
     */
    private String error;

    /**
     * 进程退出码
     */
    private Integer exitCode;

    /**
     * 是否已超时
     */
    private boolean timedOut;

    /**
     * 获取标准输出内容
     *
     * @return 标准输出内容
     */
    public String getInput() {
        return input;
    }

    /**
     * 设置标准输出内容
     *
     * @param input 标准输出内容
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * 获取错误输出内容
     *
     * @return 错误输出内容
     */
    public String getError() {
        return error;
    }

    /**
     * 设置错误输出内容
     *
     * @param error 错误输出内容
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 获取进程退出码
     *
     * @return 进程退出码
     */
    public Integer getExitCode() {
        return exitCode;
    }

    /**
     * 设置进程退出码
     *
     * @param exitCode 进程退出码
     */
    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    /**
     * 是否已超时
     *
     * @return 结果
     */
    public boolean isTimedOut() {
        return timedOut;
    }

    /**
     * 设置是否已超时
     *
     * @param timedOut 是否已超时
     */
    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    @Override
    public String toString() {
        return "ProcessExecResult{" +
                "input=\n" + input + "\n" +
                ", error=\n" + error + "\n" +
                ", exitCode=" + exitCode +
                ", timedOut=" + timedOut +
                '}';
    }

    /**
     * 执行是否成功
     *
     * @return 退出码是否为0
     */
    public boolean isSuccess() {
        return exitCode == 0;
    }
}
