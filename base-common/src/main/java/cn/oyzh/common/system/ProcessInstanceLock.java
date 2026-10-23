package cn.oyzh.common.system;

import cn.oyzh.common.log.JulLog;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于文件锁和进程PID的单实例运行锁。
 *
 * 锁文件由当前进程独占锁定，并写入当前进程PID。进程异常退出时，操作系统会释放文件锁，
 * 下次启动可以覆盖遗留的PID继续运行；正常关闭时可以尽力删除锁文件。
 *
 * 该工具只适用于同一台机器上的进程互斥，不提供跨主机锁语义。
 *
 * @author oyzh
 * @since 2026-10-23
 */
public final class ProcessInstanceLock implements AutoCloseable {

    /**
     * 锁等待重试次数
     */
    private static final int LOCK_RETRY_COUNT = 3;

    /**
     * 锁等待重试间隔，单位毫秒
     */
    private static final long LOCK_RETRY_INTERVAL = 100L;

    /**
     * PID匹配规则
     */
    private static final Pattern PID_PATTERN = Pattern.compile("(?m)^\\s*pid\\s*=\\s*(\\d+)\\s*$");

    /**
     * 当前进程PID
     */
    private static final long CURRENT_PID = ProcessHandle.current().pid();

    /**
     * 当前JVM已持有的锁路径。
     *
     * POSIX记录锁在关闭同一文件的任意描述符时可能被释放，因此同JVM重复获取必须在打开新通道前拦截。
     */
    private static final Set<Path> LOCAL_LOCKS = ConcurrentHashMap.newKeySet();

    /**
     * 锁文件
     */
    private final Path lockFile;

    /**
     * 文件通道，仅获取锁成功时保留
     */
    private final FileChannel channel;

    /**
     * 文件锁，仅获取锁成功时保留
     */
    private final FileLock fileLock;

    /**
     * 获取结果
     */
    private final Status status;

    /**
     * 锁持有进程PID，未知时为-1
     */
    private final long ownerPid;

    /**
     * 关闭时是否删除锁文件
     */
    private final boolean deleteOnClose;

    /**
     * 是否已经关闭
     */
    private boolean closed;

    /**
     * 构造实例锁
     *
     * @param lockFile     锁文件
     * @param channel      文件通道
     * @param fileLock     文件锁
     * @param status       获取结果
     * @param ownerPid     锁持有进程PID
     * @param deleteOnClose 关闭时是否删除锁文件
     */
    private ProcessInstanceLock(Path lockFile, FileChannel channel, FileLock fileLock,
                                Status status, long ownerPid, boolean deleteOnClose) {
        this.lockFile = lockFile;
        this.channel = channel;
        this.fileLock = fileLock;
        this.status = status;
        this.ownerPid = ownerPid;
        this.deleteOnClose = deleteOnClose;
    }

    /**
     * 尝试获取实例锁，正常退出时删除锁文件。
     *
     * @param lockFile 锁文件
     * @return 实例锁
     * @throws IOException io异常
     */
    public static ProcessInstanceLock tryAcquire(Path lockFile) throws IOException {
        return tryAcquire(lockFile, true);
    }

    /**
     * 尝试获取实例锁。
     *
     * @param lockFile     锁文件
     * @param deleteOnClose 关闭时是否删除锁文件
     * @return 实例锁
     * @throws IOException io异常
     */
    public static ProcessInstanceLock tryAcquire(Path lockFile, boolean deleteOnClose) throws IOException {
        Objects.requireNonNull(lockFile, "lockFile");
        Path file = lockFile.toAbsolutePath().normalize();
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        if (!LOCAL_LOCKS.add(file)) {
            JulLog.warn("instance lock is already held in current process, file:{}, pid:{}",
                    file, CURRENT_PID);
            return new ProcessInstanceLock(file, null, null,
                    Status.ALREADY_RUNNING, CURRENT_PID, deleteOnClose);
        }

        FileChannel channel = null;
        boolean acquired = false;
        try {
            channel = FileChannel.open(file,
                    StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
            FileLock lock = null;
            long ownerPid = -1L;
            boolean overlapping = false;
            for (int attempt = 0; attempt < LOCK_RETRY_COUNT; attempt++) {
                try {
                    lock = channel.tryLock();
                } catch (OverlappingFileLockException ignored) {
                    lock = null;
                    overlapping = true;
                }
                if (lock != null) {
                    break;
                }
                if (overlapping) {
                    // 同一JVM内已有通道持有锁，当前进程本身就是锁持有者。
                    JulLog.warn("instance lock is already held in current process, file:{}, pid:{}",
                            file, CURRENT_PID);
                    return new ProcessInstanceLock(file, null, null,
                            Status.ALREADY_RUNNING, CURRENT_PID, deleteOnClose);
                }
                ownerPid = readOwnerPid(file);
                if (isProcessAlive(ownerPid)) {
                    JulLog.warn("instance lock is held by running process, file:{}, pid:{}", file, ownerPid);
                    return new ProcessInstanceLock(file, null, null,
                            Status.ALREADY_RUNNING, ownerPid, deleteOnClose);
                }
                if (attempt + 1 < LOCK_RETRY_COUNT) {
                    sleepBeforeRetry();
                }
            }

            if (lock == null) {
                if (ownerPid <= 0L) {
                    ownerPid = readOwnerPid(file);
                }
                JulLog.warn("instance lock is held but no live owner can be confirmed, file:{}, pid:{}", file, ownerPid);
                return new ProcessInstanceLock(file, null, null,
                        Status.LOCKED, ownerPid, deleteOnClose);
            }

            try {
                writeOwner(channel);
            } catch (IOException | RuntimeException ex) {
                releaseQuietly(lock);
                throw ex;
            }
            acquired = true;
            JulLog.info("instance lock acquired, file:{}, pid:{}", file, CURRENT_PID);
            return new ProcessInstanceLock(file, channel, lock,
                    Status.ACQUIRED, CURRENT_PID, deleteOnClose);
        } finally {
            if (!acquired) {
                closeQuietly(channel);
                LOCAL_LOCKS.remove(file);
            }
        }
    }

    /**
     * 写入当前进程信息。
     *
     * @param channel 文件通道
     * @throws IOException io异常
     */
    private static void writeOwner(FileChannel channel) throws IOException {
        String content = "pid=" + CURRENT_PID + System.lineSeparator()
                + "started_at=" + Instant.now() + System.lineSeparator();
        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
        channel.truncate(0L);
        channel.position(0L);
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        channel.force(true);
    }

    /**
     * 读取锁文件中的进程PID。
     *
     * @param file 锁文件
     * @return 进程PID，读取失败时返回-1
     */
    private static long readOwnerPid(Path file) {
        try {
            if (!Files.isRegularFile(file)) {
                return -1L;
            }
            return parseOwnerPid(Files.readString(file, StandardCharsets.UTF_8));
        } catch (Exception ignored) {
        }
        return -1L;
    }

    /**
     * 通过当前已持有的文件通道读取锁持有进程PID。
     *
     * 不能在此处重新打开锁文件：在POSIX系统中，关闭同一文件的另一个文件描述符会释放当前进程持有的记录锁。
     *
     * @param channel 文件通道
     * @return 进程PID，读取失败时返回-1
     */
    private static long readOwnerPid(FileChannel channel) {
        try {
            long position = channel.position();
            try {
                long size = Math.min(channel.size(), 4096L);
                ByteBuffer buffer = ByteBuffer.allocate((int) size);
                channel.position(0L);
                while (buffer.hasRemaining() && channel.read(buffer) >= 0) {
                    // 读完锁文件头部即可
                }
                return parseOwnerPid(new String(buffer.array(), 0, buffer.position(), StandardCharsets.UTF_8));
            } finally {
                channel.position(position);
            }
        } catch (Exception ignored) {
        }
        return -1L;
    }

    /**
     * 解析锁持有进程PID。
     *
     * @param content 锁文件内容
     * @return 进程PID，解析失败时返回-1
     */
    private static long parseOwnerPid(String content) {
        try {
            Matcher matcher = PID_PATTERN.matcher(content);
            if (matcher.find()) {
                return Long.parseLong(matcher.group(1));
            }
            String value = content.trim();
            if (!value.isEmpty() && value.chars().allMatch(Character::isDigit)) {
                return Long.parseLong(value);
            }
        } catch (Exception ignored) {
        }
        return -1L;
    }

    /**
     * 判断进程是否存活。
     *
     * @param pid 进程PID
     * @return 结果
     */
    private static boolean isProcessAlive(long pid) {
        if (pid <= 0L) {
            return false;
        }
        if (pid == CURRENT_PID) {
            return true;
        }
        return ProcessHandle.of(pid).map(ProcessHandle::isAlive).orElse(false);
    }

    /**
     * 等待后重试。
     *
     * @throws IOException io异常
     */
    private static void sleepBeforeRetry() throws IOException {
        try {
            Thread.sleep(LOCK_RETRY_INTERVAL);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while waiting for instance lock", ex);
        }
    }

    /**
     * 释放文件锁。
     *
     * @param lock 文件锁
     */
    private static void releaseQuietly(FileLock lock) {
        try {
            if (lock != null && lock.isValid()) {
                lock.release();
            }
        } catch (IOException ex) {
            JulLog.warn("release instance lock failed, error:{}", ex.getMessage());
        }
    }

    /**
     * 关闭文件通道。
     *
     * @param channel 文件通道
     */
    private static void closeQuietly(FileChannel channel) {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
        } catch (IOException ex) {
            JulLog.warn("close instance lock channel failed, error:{}", ex.getMessage());
        }
    }

    /**
     * 获取锁文件。
     *
     * @return 锁文件
     */
    public Path getLockFile() {
        return this.lockFile;
    }

    /**
     * 获取当前进程PID。
     *
     * @return 当前进程PID
     */
    public long getPid() {
        return CURRENT_PID;
    }

    /**
     * 获取锁持有进程PID。
     *
     * @return 锁持有进程PID，未知时为-1
     */
    public long getOwnerPid() {
        return this.ownerPid;
    }

    /**
     * 获取锁状态。
     *
     * @return 锁状态
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * 是否成功获取锁。
     *
     * @return 结果
     */
    public boolean isAcquired() {
        return this.status == Status.ACQUIRED;
    }

    @Override
    public synchronized void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        // 必须在仍持有文件锁时删除，避免释放锁后误删另一个实例刚写入的锁文件。
        if (this.deleteOnClose && this.status == Status.ACQUIRED) {
            long recordedPid = readOwnerPid(this.channel);
            if (recordedPid <= 0L || recordedPid == CURRENT_PID) {
                try {
                    Files.deleteIfExists(this.lockFile);
                } catch (Exception ignored) {
                    // Windows等平台可能不允许删除已打开的锁文件，保留文件不影响下次启动。
                }
            }
        }
        releaseQuietly(this.fileLock);
        closeQuietly(this.channel);
        if (this.status == Status.ACQUIRED) {
            LOCAL_LOCKS.remove(this.lockFile);
        }
    }

    /**
     * 实例锁状态
     */
    public enum Status {

        /**
         * 获取成功
         */
        ACQUIRED,

        /**
         * 已有存活进程持有锁
         */
        ALREADY_RUNNING,

        /**
         * 文件仍被锁定，但无法确认持锁进程是否存活
         */
        LOCKED
    }
}
