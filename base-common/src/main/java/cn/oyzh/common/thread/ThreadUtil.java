package cn.oyzh.common.thread;

import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程工具类
 *
 * @author oyzh
 * @since 2023/1/3
 */
public class ThreadUtil {

    /**
     * 业务执行器
     */
    private volatile static ExecutorService executor;

    /**
     * 虚拟线程业务执行器
     */
    private volatile static ExecutorService virtualExecutor;

    /**
     * 获取业务执行器
     *
     * @return 业务执行器
     */
    public static ExecutorService executor() {
        if (executor == null) {
            synchronized (ThreadUtil.class) {
                if (executor == null) {
                    executor = Executors.newCachedThreadPool();
                }
            }
        }
        return executor;
    }

    /**
     * 获取虚拟线程业务执行器
     *
     * @return 业务执行器
     */
    public static ExecutorService virtualExecutor() {
        if (virtualExecutor == null) {
            synchronized (ThreadUtil.class) {
                if (virtualExecutor == null) {
                    virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();
                }
            }
        }
        return virtualExecutor;
    }

    static {
        RuntimeUtil.addShutdownHook(new Thread(() -> {
            if (executor != null) {
                executor.shutdown();
            }
            if (virtualExecutor != null) {
                virtualExecutor.shutdown();
            }
        }));
    }

    /**
     * 开始运行线程
     * TODO: 虚拟线程，轻量级别，适合io密集型任务
     *
     * @param task 任务
     * @return 线程
     */
    public static Thread startVirtual(Runnable task) {
        return Thread.ofVirtual().start(task);
    }

    /**
     * 执行任务列表
     * TODO: 虚拟线程，轻量级别，适合io密集型任务
     *
     * @param tasks 任务列表
     */
    public static void submitVirtual(List<Runnable> tasks) {
        if (CollectionUtil.isNotEmpty(tasks)) {
            try {
                List<Future<?>> futures = new ArrayList<>(tasks.size());
                for (Runnable task : tasks) {
                    futures.add(virtualExecutor().submit(task));
                }
                for (Future<?> future : futures) {
                    future.get();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 执行任务
     * TODO: 虚拟线程，轻量级别，适合io密集型任务
     *
     * @param task 任务
     */
    public static void submitVirtual(Runnable task) {
        if (task != null) {
            submitVirtual(Collections.singletonList(task));
        }
    }

    /**
     * 执行任务列表，并活得结果
     * TODO: 虚拟线程，轻量级别，适合io密集型任务
     *
     * @param tasks 任务列表
     */
    public static <V> List<V> invokeVirtual(List<Callable<V>> tasks) {
        List<V> results = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(tasks)) {
            try {
                List<Future<V>> futures = virtualExecutor().invokeAll(tasks);
                for (Future<V> future : futures) {
                    results.add(future.get());
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return results;
    }

    /**
     * 执行任务列表，并活得结果
     * TODO: 虚拟线程，轻量级别，适合io密集型任务
     *
     * @param task 任务
     */
    public static <V> V invokeVirtual(Callable<V> task) {
        if (task != null) {
            return invokeVirtual(Collections.singletonList(task)).getFirst();
        }
        return null;
    }

    /**
     * 开始运行线程
     *
     * @param task 任务
     * @return 线程
     */
    public static Thread start(Runnable task) {
        ThreadExt thread = new ThreadExt(task);
        thread.start();
        return thread;
    }

    /**
     * 创建线程
     *
     * @param task 任务
     * @return 线程
     */
    public static Thread newThread(Runnable task) {
        return new ThreadExt(task);
    }

    /**
     * 创建虚拟线程
     *
     * @param task 任务
     * @return 线程
     */
    public static Thread newThreadVirtual(Runnable task) {
        return Thread.ofVirtual().unstarted(task);
    }

    /**
     * 开始运行线程
     *
     * @param task  任务
     * @param delay 延迟
     * @return 线程
     */
    public static Thread start(Runnable task, long delay) {
        ThreadExt thread = new ThreadExt(task);
        ExecutorUtil.start(thread::start, delay);
        return thread;
    }

    /**
     * 执行任务列表，同步执行
     * TODO: 物理线程，重量级别，适合cpu密集型任务
     *
     * @param tasks 任务列表
     */
    public static void submit(List<Runnable> tasks) {
        if (CollectionUtil.isNotEmpty(tasks)) {
            try {
                List<Future<?>> futures = new ArrayList<>(tasks.size());
                for (Runnable task : tasks) {
                    futures.add(executor().submit(task));
                }
                for (Future<?> future : futures) {
                    future.get();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 执行任务列表，同步执行
     * TODO: 物理线程，重量级别，适合cpu密集型任务
     *
     * @param task 任务
     */
    public static void submit(Runnable task) {
        if (task != null) {
            submit(Collections.singletonList(task));
        }
    }

    /**
     * 执行任务列表，异步执行
     * TODO: 物理线程，重量级别，适合cpu密集型任务
     *
     * @param tasks 任务列表
     */
    public static void submitAsync(List<Runnable> tasks) {
        if (CollectionUtil.isNotEmpty(tasks)) {
            for (Runnable task : tasks) {
                executor().submit(task);
            }
        }
    }

    /**
     * 执行任务列表，异步执行
     * TODO: 物理线程，重量级别，适合cpu密集型任务
     *
     * @param task 任务
     */
    public static void submitAsync(Runnable task) {
        if (task != null) {
            submitAsync(Collections.singletonList(task));
        }
    }

    /**
     * 执行任务列表，并拿到返回结果
     * TODO: 物理线程，重量级别，适合cpu密集型任务
     *
     * @param tasks 任务列表
     * @param <V>   结果泛型
     * @return 结果列表
     */
    public static <V> List<V> invoke(List<Callable<V>> tasks) {
        if (CollectionUtil.isNotEmpty(tasks)) {
            try {
                List<Future<V>> futures = executor().invokeAll(tasks);
                List<V> results = new ArrayList<>(futures.size());
                for (Future<V> future : futures) {
                    V result = future.get();
                    if (result != null) {
                        results.add(result);
                    }
                }
                return results;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return Collections.emptyList();
    }

    /**
     * 执行任务列表，并拿到返回结果
     * TODO: 物理线程，重量级别，适合cpu密集型任务
     *
     * @param task 任务
     * @param <V>  结果泛型
     * @return 结果列表
     */
    public static <V> V invoke(Callable<V> task) {
        if (task != null) {
            return invoke(Collections.singletonList(task)).getFirst();
        }
        return null;
    }


    /**
     * 休眠
     *
     * @param millis 毫秒值
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程是否结束
     *
     * @return 结果
     */
    public static boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }

    /**
     * 线程是否结束
     *
     * @param thread 线程
     * @return 结果
     */
    public static boolean isInterrupted(Thread thread) {
        return thread == null || thread.isInterrupted();
    }

    /**
     * 线程是否活跃
     *
     * @param thread 线程
     * @return 结果
     */
    public static boolean isAlive(Thread thread) {
        return thread != null && thread.isAlive();
    }

    /**
     * 结束线程
     *
     * @param thread 线程
     */
    public static void interrupt(Thread thread) {
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
    }

    /**
     * 加入线程
     *
     * @param thread 线程
     */
    public static void join(Thread thread) {
        if (thread != null && thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 执行等待
     *
     * @param latch 闭锁对象
     */
    public static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException ignore) {
        }
    }
}
