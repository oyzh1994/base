package cn.oyzh.common.thread;

import java.util.function.Consumer;

/**
 * 任务封装，支持开始、成功、结束、异常回调
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

    /**
     * 执行开始回调
     *
     * @throws Exception 执行过程中的异常
     */
    public void onStart() throws Exception {
        if (this.start != null) {
            this.start.run();
        }
    }

    /**
     * 执行成功回调
     *
     * @throws Exception 执行过程中的异常
     */
    public void onSuccess() throws Exception {
        if (this.success != null) {
            this.success.run();
        }
    }

    /**
     * 执行结束回调
     *
     * @throws Exception 执行过程中的异常
     */
    public void onFinish() throws Exception {
        if (this.finish != null) {
            this.finish.run();
        }
    }

    /**
     * 执行异常回调
     *
     * @param ex 异常
     */
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

    /**
     * 是否存在异常
     *
     * @return 结果
     */
    public boolean hasException() {
        return this.exception != null;
    }

    /**
     * 获取开始操作
     *
     * @return 开始操作
     */
    public IRunnable getStart() {
        return start;
    }

    /**
     * 设置开始操作
     *
     * @param start 开始操作
     */
    public void setStart(IRunnable start) {
        this.start = start;
    }

    /**
     * 获取结束操作
     *
     * @return 结束操作
     */
    public IRunnable getFinish() {
        return finish;
    }

    /**
     * 设置结束操作
     *
     * @param finish 结束操作
     */
    public void setFinish(IRunnable finish) {
        this.finish = finish;
    }

    /**
     * 获取成功操作
     *
     * @return 成功操作
     */
    public IRunnable getSuccess() {
        return success;
    }

    /**
     * 设置成功操作
     *
     * @param success 成功操作
     */
    public void setSuccess(IRunnable success) {
        this.success = success;
    }

    /**
     * 获取错误操作
     *
     * @return 错误操作
     */
    public Consumer<Exception> getError() {
        return error;
    }

    /**
     * 设置错误操作
     *
     * @param error 错误操作
     */
    public void setError(Consumer<Exception> error) {
        this.error = error;
    }

    /**
     * 获取异常
     *
     * @return 异常
     */
    public Exception getException() {
        return exception;
    }

    /**
     * 设置异常
     *
     * @param exception 异常
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * 若存在异常则以运行时异常抛出
     */
    public void throwRuntimeException() {
        if (this.exception != null) {
            throw new RuntimeException(this.exception);
        }
    }
}
