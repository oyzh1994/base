package cn.oyzh.pkg.util.compact;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;

/**
 * 清理不再使用的历史版本运行缓存。
 * <p>
 * 缓存按最终 JAR 的 SHA-256 分目录，因此每次应用更新都会留下一个完整目录。
 * 启动器只在当前进程持有共享锁时使用缓存；本类使用排他锁挑选超过保留期的
 * 同应用缓存，并在删除前先原子重命名，避免与并发启动的旧版本互相竞争。
 */
final class CacheRetentionManager {

    static final Duration RETENTION = Duration.ofDays(30);
    private static final String CACHE_MARKER = "cache.ok";
    private static final String APPLICATION_JAR = "application.jar";
    private static final String APP_ID = "app-id";
    private static final String LAST_USED = "last-used";
    private static final String LOCK_DIRECTORY = ".locks";
    private static final String TOMBSTONE_PREFIX = ".compact-stale-";
    private static final int MAX_APP_ID_SIZE = 128;
    private static final int LOCK_ATTEMPTS = 5;
    private static final long LOCK_RETRY_MILLIS = 20;

    private CacheRetentionManager() {
    }

    /**
     * 生成稳定的应用标识。应用版本更新时主类通常保持不变，因此可据此识别历史版本。
     */
    static String applicationId(String startClass) {
        if (startClass == null || startClass.isBlank()) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return toHex(digest.digest(startClass.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    /**
     * 获取当前缓存的共享使用锁。锁文件位于缓存目录之外，缓存被重命名或删除时锁仍然有效。
     * 获取失败时不影响应用启动，只是本次进程无法阻止另一个清理任务并发执行。
     */
    static UsageLock acquire(Path runtimeCache) {
        FileChannel channel = null;
        FileLock lock = null;
        try {
            Path lockFile = lockFile(runtimeCache);
            Files.createDirectories(lockFile.getParent());
            setPrivatePermissions(lockFile.getParent());
            channel = FileChannel.open(lockFile, StandardOpenOption.CREATE,
                    StandardOpenOption.READ, StandardOpenOption.WRITE);
            for (int attempt = 0; attempt < LOCK_ATTEMPTS; attempt++) {
                try {
                    lock = channel.tryLock(0, Long.MAX_VALUE, true);
                } catch (OverlappingFileLockException ex) {
                    lock = null;
                }
                if (lock != null) {
                    return new UsageLock(channel, lock);
                }
                if (attempt + 1 < LOCK_ATTEMPTS) {
                    try {
                        Thread.sleep(LOCK_RETRY_MILLIS);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        closeQuietly(lock, channel);
        return UsageLock.NONE;
    }

    /**
     * 记录应用标识和最近使用时间。cache.ok 同时作为 last-used 的冗余时间标记，
     * 避免单个文件被手动删除后无法判断缓存是否仍在使用。任何失败都不会影响应用启动。
     */
    static void markUsed(Path runtimeCache, String applicationId) {
        if (applicationId != null && !applicationId.isBlank()) {
            try {
                Files.writeString(runtimeCache.resolve(APP_ID), applicationId, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (Exception ignored) {
            }
        }
        touch(runtimeCache.resolve(CACHE_MARKER));
        try {
            Files.write(runtimeCache.resolve(LAST_USED), new byte[0], StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception ignored) {
        }
    }

    /**
     * 在后台清理一次历史缓存。线程使用最低优先级，且不会阻塞应用启动或退出。
     */
    static void cleanupAsync(Path currentCache, String applicationId) {
        if (applicationId == null || applicationId.isBlank()) {
            return;
        }
        Path current = currentCache.toAbsolutePath().normalize();
        try {
            Thread thread = new Thread(() -> cleanupQuietly(current, applicationId),
                    "compact-jar-cache-cleanup");
            thread.setDaemon(true);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        } catch (Exception ignored) {
        }
    }

    private static void cleanupQuietly(Path currentCache, String applicationId) {
        try {
            cleanupExpired(currentCache, applicationId, Instant.now());
        } catch (Exception ignored) {
        }
    }

    /**
     * 同步执行一次清理，返回成功移入删除队列的缓存数量。该方法主要供测试和诊断使用。
     */
    static int cleanupExpired(Path currentCache, String applicationId, Instant now) {
        if (currentCache == null || applicationId == null || applicationId.isBlank() || now == null) {
            return 0;
        }
        Path current = currentCache.toAbsolutePath().normalize();
        Path parent = current.getParent();
        if (parent == null) {
            return 0;
        }
        List<Path> entries;
        try (Stream<Path> paths = Files.list(parent)) {
            entries = paths.sorted().toList();
        } catch (Exception ex) {
            return 0;
        }

        long cutoff;
        try {
            cutoff = now.minus(RETENTION).toEpochMilli();
        } catch (Exception ex) {
            return 0;
        }
        int removed = 0;
        for (Path entry : entries) {
            String name = entry.getFileName().toString();
            try {
                if (name.startsWith(TOMBSTONE_PREFIX)) {
                    if (isDirectory(entry) && deleteTree(entry)) {
                        notifyConsole("已清理未完成的缓存删除任务: " + entry);
                    }
                    continue;
                }
                if (!isCacheDirectoryName(name) || entry.equals(current)
                        || !isDirectory(entry) || !isExpired(entry, cutoff)
                        || !belongsToApplication(entry, applicationId)) {
                    continue;
                }
                Path tombstone = parent.resolve(TOMBSTONE_PREFIX + name + "-" + UUID.randomUUID());
                if (!retire(entry, tombstone, cutoff)) {
                    continue;
                }
                if (deleteTree(tombstone)) {
                    removed++;
                    notifyConsole("已清理过期缓存: " + entry);
                } else {
                    notifyConsole("过期缓存未能完全删除，将在下次启动时重试: " + entry);
                }
            } catch (Exception ex) {
                notifyConsole("清理过期缓存失败: " + entry);
            }
        }
        return removed;
    }

    private static boolean retire(Path cache, Path tombstone, long cutoff) {
        FileChannel channel = null;
        FileLock lock = null;
        try {
            Path lockFile = lockFile(cache);
            Files.createDirectories(lockFile.getParent());
            channel = FileChannel.open(lockFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            try {
                lock = channel.tryLock(0, Long.MAX_VALUE, false);
            } catch (OverlappingFileLockException ex) {
                return false;
            }
            if (lock == null || !isExpired(cache, cutoff)) {
                return false;
            }
            try {
                Files.move(cache, tombstone, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(cache, tombstone);
            }
            return true;
        } catch (NoSuchFileException ex) {
            return false;
        } catch (Exception ex) {
            notifyConsole("清理过期缓存失败: " + cache);
            return false;
        } finally {
            closeQuietly(lock, channel);
        }
    }

    private static boolean belongsToApplication(Path cache, String applicationId) {
        Path idFile = cache.resolve(APP_ID);
        if (Files.exists(idFile, LinkOption.NOFOLLOW_LINKS)) {
            if (!Files.isRegularFile(idFile, LinkOption.NOFOLLOW_LINKS)) {
                return false;
            }
            String stored = readSmallText(idFile);
            return stored != null && applicationId.equals(stored);
        }
        String legacyId = legacyApplicationId(cache);
        return legacyId != null && applicationId.equals(legacyId);
    }

    /**
     * 旧缓存没有 app-id 时从应用 JAR 清单推断身份；无法确定时保留缓存，避免误删其他应用。
     */
    private static String legacyApplicationId(Path cache) {
        Path applicationJar = cache.resolve(APPLICATION_JAR);
        if (!Files.isRegularFile(applicationJar, LinkOption.NOFOLLOW_LINKS)) {
            return null;
        }
        try (JarFile jarFile = new JarFile(applicationJar.toFile())) {
            Manifest manifest = jarFile.getManifest();
            if (manifest == null) {
                return null;
            }
            Attributes attributes = manifest.getMainAttributes();
            String startClass = attributes.getValue("Start-Class");
            if (startClass == null || startClass.isBlank()) {
                startClass = attributes.getValue("Main-Class");
            }
            return applicationId(startClass);
        } catch (Exception ex) {
            return null;
        }
    }

    private static String readSmallText(Path path) {
        try (InputStream input = Files.newInputStream(path)) {
            byte[] data = input.readNBytes(MAX_APP_ID_SIZE + 1);
            if (data.length > MAX_APP_ID_SIZE) {
                return null;
            }
            return new String(data, StandardCharsets.UTF_8).trim();
        } catch (Exception ex) {
            return null;
        }
    }

    private static boolean isExpired(Path cache, long cutoff) {
        long lastUsed = regularFileModified(cache.resolve(LAST_USED));
        long metadataTime = Math.max(lastUsed, regularFileModified(cache.resolve(CACHE_MARKER)));
        metadataTime = Math.max(metadataTime, lastModified(cache));
        if (metadataTime >= 0 && metadataTime >= cutoff) {
            return false;
        }
        if (lastUsed >= 0) {
            return true;
        }
        long artifactTime = latestCacheTime(cache);
        return artifactTime >= 0 && artifactTime < cutoff;
    }

    /**
     * last-used 缺失时递归检查缓存内容，取普通文件的最新修改时间或访问时间。
     * 应用启动时加载 JAR 通常会刷新访问时间，较新版本的 AOT 文件也会刷新修改时间。
     * 任一文件属性读取失败时返回 -1，调用方会保守保留缓存。
     */
    static long latestCacheTime(Path cache) {
        long[] latest = {-1};
        try {
            Files.walkFileTree(cache, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) {
                    latest[0] = Math.max(latest[0], attributes.lastModifiedTime().toMillis());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    latest[0] = Math.max(latest[0], attributes.lastModifiedTime().toMillis());
                    if (attributes.isRegularFile()) {
                        latest[0] = Math.max(latest[0], attributes.lastAccessTime().toMillis());
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exception) throws IOException {
                    throw exception;
                }
            });
            return latest[0];
        } catch (Exception ex) {
            return -1;
        }
    }

    private static long lastModified(Path path) {
        try {
            return Files.getLastModifiedTime(path, LinkOption.NOFOLLOW_LINKS).toMillis();
        } catch (Exception ex) {
            return -1;
        }
    }

    private static long regularFileModified(Path path) {
        try {
            return Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS) ? lastModified(path) : -1;
        } catch (Exception ex) {
            return -1;
        }
    }

    private static void touch(Path path) {
        try {
            if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
                Files.setLastModifiedTime(path, FileTime.from(Instant.now()));
            }
        } catch (Exception ignored) {
        }
    }

    private static boolean deleteTree(Path root) {
        List<Path> paths;
        try (Stream<Path> stream = Files.walk(root)) {
            paths = stream.sorted(Comparator.reverseOrder()).toList();
        } catch (Exception ex) {
            return false;
        }
        for (Path path : paths) {
            try {
                Files.deleteIfExists(path);
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCacheDirectoryName(String name) {
        if (name.length() != 64) {
            return false;
        }
        for (int index = 0; index < name.length(); index++) {
            char value = name.charAt(index);
            if (!((value >= '0' && value <= '9') || (value >= 'a' && value <= 'f'))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDirectory(Path path) {
        return Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
    }

    private static Path lockFile(Path runtimeCache) {
        Path cache = runtimeCache.toAbsolutePath().normalize();
        return cache.getParent().resolve(LOCK_DIRECTORY).resolve(cache.getFileName() + ".lock");
    }

    private static void setPrivatePermissions(Path directory) {
        try {
            if (Files.getFileAttributeView(directory, PosixFileAttributeView.class) == null) {
                return;
            }
            Files.setPosixFilePermissions(directory, Set.of(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE));
        } catch (Exception ignored) {
        }
    }

    private static void notifyConsole(String message) {
        try {
            System.out.println("[compact-jar] " + message);
        } catch (Exception ignored) {
        }
    }

    private static String toHex(byte[] value) {
        StringBuilder text = new StringBuilder(value.length * 2);
        for (byte item : value) {
            text.append(String.format("%02x", item));
        }
        return text.toString();
    }

    private static void closeQuietly(FileLock lock, FileChannel channel) {
        try {
            if (lock != null && lock.isValid()) {
                lock.release();
            }
        } catch (IOException ignored) {
        }
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 缓存使用锁；启动器的生命周期内保持有效。
     */
    static final class UsageLock implements AutoCloseable {

        private static final UsageLock NONE = new UsageLock(null, null);

        private final FileChannel channel;
        private final FileLock lock;

        private UsageLock(FileChannel channel, FileLock lock) {
            this.channel = channel;
            this.lock = lock;
        }

        boolean isHeld() {
            return lock != null && lock.isValid();
        }

        @Override
        public void close() {
            closeQuietly(lock, channel);
        }
    }
}
