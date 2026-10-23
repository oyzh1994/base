package cn.oyzh.common.test;

import cn.oyzh.common.system.OSUtil;
import cn.oyzh.common.system.ProcessInstanceLock;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 测试基于文件锁和PID的进程实例互斥。
 *
 * @author oyzh
 * @since 2026-10-23
 */
public class ProcessInstanceLockTest {

    private Path workDirectory;

    private Path lockFile;

    @Before
    public void setUp() throws IOException {
        this.workDirectory = Files.createTempDirectory("process-instance-lock-");
        this.lockFile = this.workDirectory.resolve("app.lock");
    }

    @After
    public void tearDown() throws IOException {
        if (this.workDirectory != null) {
            try (Stream<Path> paths = Files.walk(this.workDirectory)) {
                paths.sorted(Comparator.reverseOrder()).forEach(this::deleteQuietly);
            }
        }
    }

    @Test
    public void acquiresLockWritesPidAndDeletesOnClose() throws Exception {
        Path actualFile;
        try (ProcessInstanceLock lock = ProcessInstanceLock.tryAcquire(this.lockFile)) {
            assertTrue(lock.isAcquired());
            assertEquals(ProcessInstanceLock.Status.ACQUIRED, lock.getStatus());
            assertEquals(ProcessHandle.current().pid(), lock.getOwnerPid());
            actualFile = lock.getLockFile();
            assertTrue(Files.isRegularFile(actualFile));
            String content = Files.readString(actualFile, StandardCharsets.UTF_8);
            assertTrue(content.contains("pid=" + ProcessHandle.current().pid()));
        }
        assertFalse(Files.exists(actualFile));
    }

    @Test
    public void secondAcquireReportsRunningProcess() throws Exception {
        try (ProcessInstanceLock first = ProcessInstanceLock.tryAcquire(this.lockFile)) {
            assertTrue(first.isAcquired());
            try (ProcessInstanceLock second = ProcessInstanceLock.tryAcquire(this.lockFile)) {
                assertFalse(second.isAcquired());
                assertEquals(ProcessInstanceLock.Status.ALREADY_RUNNING, second.getStatus());
                assertEquals(ProcessHandle.current().pid(), second.getOwnerPid());
            }
            assertTrue("duplicate lock close must not delete owner lock file", Files.isRegularFile(this.lockFile));

            Process external = this.startLockHolder();
            try {
                assertTrue("duplicate check released the owner lock",
                        this.waitForOutput(external, "FAILED ALREADY_RUNNING"));
                assertTrue(external.waitFor(10, TimeUnit.SECONDS));
            } finally {
                if (external.isAlive()) {
                    external.destroyForcibly();
                    external.waitFor(10, TimeUnit.SECONDS);
                }
            }
        }
    }

    @Test
    public void lockCanBeReacquiredAfterClose() throws Exception {
        ProcessInstanceLock first = ProcessInstanceLock.tryAcquire(this.lockFile);
        assertTrue(first.isAcquired());
        first.close();

        try (ProcessInstanceLock second = ProcessInstanceLock.tryAcquire(this.lockFile)) {
            assertTrue(second.isAcquired());
            assertEquals(ProcessHandle.current().pid(), second.getOwnerPid());
        }
    }

    @Test
    public void stalePidFileIsOverwritten() throws Exception {
        Files.writeString(this.lockFile, "pid=99999999\n", StandardCharsets.UTF_8);

        try (ProcessInstanceLock lock = ProcessInstanceLock.tryAcquire(this.lockFile)) {
            assertTrue(lock.isAcquired());
            assertEquals(ProcessInstanceLock.Status.ACQUIRED, lock.getStatus());
            assertEquals(ProcessHandle.current().pid(), lock.getOwnerPid());
            String content = Files.readString(this.lockFile, StandardCharsets.UTF_8);
            assertTrue(content.contains("pid=" + ProcessHandle.current().pid()));
        }
    }

    @Test
    public void emptyLockFileCanBeAcquired() throws Exception {
        Files.createFile(this.lockFile);

        try (ProcessInstanceLock lock = ProcessInstanceLock.tryAcquire(this.lockFile)) {
            assertTrue(lock.isAcquired());
            String content = Files.readString(this.lockFile, StandardCharsets.UTF_8);
            assertTrue(content.contains("pid=" + ProcessHandle.current().pid()));
        }
    }

    @Test
    public void retainedLockFileCanBeReused() throws Exception {
        ProcessInstanceLock first = ProcessInstanceLock.tryAcquire(this.lockFile, false);
        assertTrue(first.isAcquired());
        first.close();
        assertTrue(Files.isRegularFile(this.lockFile));

        try (ProcessInstanceLock second = ProcessInstanceLock.tryAcquire(this.lockFile, false)) {
            assertTrue(second.isAcquired());
        }
        assertTrue(Files.isRegularFile(this.lockFile));
    }

    @Test
    public void deleteFailureOnCloseDoesNotThrowAndReleasesLock() throws Exception {
        Assume.assumeFalse("cannot replace an open lock file on Windows", OSUtil.isWindows());
        ProcessInstanceLock lock = ProcessInstanceLock.tryAcquire(this.lockFile);
        assertTrue(lock.isAcquired());
        try {
            Files.delete(this.lockFile);
            Files.createDirectory(this.lockFile);
            Files.writeString(this.lockFile.resolve("blocked"), "x", StandardCharsets.UTF_8);

            // 路径已被替换为非空目录，删除锁文件必然失败，但不能影响关闭流程。
            lock.close();
        } finally {
            lock.close();
        }

        Files.delete(this.lockFile.resolve("blocked"));
        Files.delete(this.lockFile);
        try (ProcessInstanceLock second = ProcessInstanceLock.tryAcquire(this.lockFile)) {
            assertTrue(second.isAcquired());
        }
    }

    @Test
    public void overlappingJvmLockIsReportedRunningWithoutPid() throws Exception {
        try (FileChannel channel = FileChannel.open(this.lockFile,
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
             FileLock ignored = channel.lock()) {
            try (ProcessInstanceLock lock = ProcessInstanceLock.tryAcquire(this.lockFile)) {
                assertFalse(lock.isAcquired());
                assertEquals(ProcessInstanceLock.Status.ALREADY_RUNNING, lock.getStatus());
                assertEquals(ProcessHandle.current().pid(), lock.getOwnerPid());
            }
        }

        try (ProcessInstanceLock lock = ProcessInstanceLock.tryAcquire(this.lockFile)) {
            assertTrue(lock.isAcquired());
        }
    }

    @Test
    public void externalLockWithDeadPidIsReportedLocked() throws Exception {
        Process process = this.startLockHolder(DeadPidLockHolder.class);
        try {
            assertTrue("lock holder did not start", this.waitForLockHolder(process));
            try (ProcessInstanceLock lock = ProcessInstanceLock.tryAcquire(this.lockFile)) {
                assertFalse(lock.isAcquired());
                assertEquals(ProcessInstanceLock.Status.LOCKED, lock.getStatus());
                assertEquals(99999999L, lock.getOwnerPid());
            }
        } finally {
            if (process.isAlive()) {
                process.destroyForcibly();
                process.waitFor(10, TimeUnit.SECONDS);
            }
        }
    }

    @Test
    public void killedProcessLockCanBeAcquiredAgain() throws Exception {
        Process process = this.startLockHolder();
        try {
            assertTrue("lock holder did not start", this.waitForLockHolder(process));
            try (ProcessInstanceLock duplicate = ProcessInstanceLock.tryAcquire(this.lockFile)) {
                assertFalse(duplicate.isAcquired());
                assertEquals(ProcessInstanceLock.Status.ALREADY_RUNNING, duplicate.getStatus());
                assertEquals(process.pid(), duplicate.getOwnerPid());
            }

            process.destroyForcibly();
            assertTrue("lock holder did not terminate", process.waitFor(10, TimeUnit.SECONDS));

            ProcessInstanceLock recovered = null;
            long deadline = System.currentTimeMillis() + 3000L;
            while (System.currentTimeMillis() < deadline) {
                ProcessInstanceLock attempt = ProcessInstanceLock.tryAcquire(this.lockFile);
                if (attempt.isAcquired()) {
                    recovered = attempt;
                    break;
                }
                attempt.close();
                Thread.sleep(50L);
            }
            assertTrue("lock was not released after process kill", recovered != null && recovered.isAcquired());
            recovered.close();
        } finally {
            if (process.isAlive()) {
                process.destroyForcibly();
                process.waitFor(10, TimeUnit.SECONDS);
            }
        }
    }

    private Process startLockHolder() throws IOException {
        return this.startLockHolder(LockHolder.class);
    }

    private Process startLockHolder(Class<?> mainClass) throws IOException {
        String classPath = System.getProperty("surefire.test.class.path");
        if (classPath == null || classPath.isBlank()) {
            classPath = System.getProperty("java.class.path");
        }
        String javaName = OSUtil.isWindows() ? "java.exe" : "java";
        Path java = Path.of(System.getProperty("java.home"), "bin", javaName);
        return new ProcessBuilder(java.toString(), "-cp", classPath,
                mainClass.getName(), this.lockFile.toString())
                .redirectErrorStream(true)
                .start();
    }

    private boolean waitForLockHolder(Process process) throws Exception {
        return this.waitForOutput(process, "LOCKED");
    }

    private boolean waitForOutput(Process process, String prefix) throws Exception {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                process.getInputStream(), StandardCharsets.UTF_8))) {
            long deadline = System.currentTimeMillis() + 10_000L;
            while (System.currentTimeMillis() < deadline) {
                if (reader.ready()) {
                    String line = reader.readLine();
                    output.append(line).append('\n');
                    if (line != null && line.startsWith(prefix)) {
                        return true;
                    }
                }
                if (!process.isAlive()) {
                    // 进程退出后继续排空已写入管道的输出，避免遗漏最后一行业务输出。
                    while (reader.ready()) {
                        String line = reader.readLine();
                        output.append(line).append('\n');
                        if (line != null && line.startsWith(prefix)) {
                            return true;
                        }
                    }
                    break;
                }
                Thread.sleep(20L);
            }
        }
        throw new AssertionError("expected output: " + prefix + ", actual: " + output);
    }

    private void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }

    /**
     * 子进程锁持有器
     */
    public static final class LockHolder {

        public static void main(String[] args) throws Exception {
            ProcessInstanceLock lock = ProcessInstanceLock.tryAcquire(Path.of(args[0]), false);
            if (!lock.isAcquired()) {
                System.out.println("FAILED " + lock.getStatus());
                return;
            }
            System.out.println("LOCKED " + ProcessHandle.current().pid());
            System.out.flush();
            Thread.sleep(60_000L);
            lock.close();
        }
    }

    /**
     * 外部死PID锁持有器。
     */
    public static final class DeadPidLockHolder {

        public static void main(String[] args) throws Exception {
            Path file = Path.of(args[0]);
            try (FileChannel channel = FileChannel.open(file,
                    StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
                 FileLock ignored = channel.lock()) {
                byte[] content = ("pid=99999999" + System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
                channel.truncate(0L);
                channel.position(0L);
                channel.write(ByteBuffer.wrap(content));
                channel.force(true);
                System.out.println("LOCKED 99999999");
                System.out.flush();
                Thread.sleep(60_000L);
            }
        }
    }
}
