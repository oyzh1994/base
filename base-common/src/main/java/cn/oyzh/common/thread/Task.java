package cn.oyzh.common.thread;

import java.util.function.Consumer;

/**
 * 线程封装
 *
 * @author oyzh
 * @since 2023/9/14
 */
public class Task implements Runnable {

    /**
     * 开始操作
     */
    private IRunnable start;

    /**
     * 结束操作
     */
    private IRunnable finish;

    /**
     * 成功操作
     */
    private IRunnable success;

    /**
     * 错误操作
     */
    private Consumer<Exception> error;

    /**
     * 异常
     */
    private Exception exception;

    public void onStart() throws Exception {
        if (this.start != null) {
            this.start.run();
        }
    }

    public void onSuccess() throws Exception {
        if (this.success != null) {
            this.success.run();
        }
    }

    public void onFinish() throws Exception {
        if (this.finish != null) {
            this.finish.run();
        }
    }

    public void onError(Exception ex) {
        if (this.error != null) {
            this.error.accept(ex);
        }
    }

    @Override
    public final void run() {
        try {
            this.onStart();
            this.onSuccess();
        } catch (Exception ex) {
            this.exception = ex;
            this.onError(ex);
        } finally {
            try {
                this.onFinish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean hasException() {
        return this.exception != null;
    }

    public IRunnable getStart() {
        return start;
    }

    public void setStart(IRunnable start) {
        this.start = start;
    }

    public IRunnable getFinish() {
        return finish;
    }

    public void setFinish(IRunnable finish) {
        this.finish = finish;
    }

    public IRunnable getSuccess() {
        return success;
    }

    public void setSuccess(IRunnable success) {
        this.success = success;
    }

    public Consumer<Exception> getError() {
        return error;
    }

    public void setError(Consumer<Exception> error) {
        this.error = error;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void throwRuntimeException() {
        if (this.exception != null) {
            throw new RuntimeException(this.exception);
        }
    }
}
