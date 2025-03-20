package cn.oyzh.common.thread;

/**
 *
 * @author oyzh
 * @since 2025-02-11
 */
//@Data
public class ProcessExecResult {

    private String input;

    private String error;

    private Integer exitCode;

    private boolean timedOut;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    @Override
    public String toString() {
        return "ProcessExecResult{" +
                "input='" + input + '\'' +
                ", error='" + error + '\'' +
                ", exitCode=" + exitCode +
                ", timedOut=" + timedOut +
                '}';
    }
}
