package cn.oyzh.pkg.util.compact;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final class AotCacheManager {

    private static final String ENABLED_PROPERTY = "compact.jar.aot";
    private static final String CACHE_SUFFIX = ".aot";
    private static final String MARKER_SUFFIX = ".ok";
    private static final String TRAINING_SUFFIX = ".training";
    private static final String LOCK_FILE = "cache.lock";
    private static final String MARKER_FORMAT = "format=1";
    private static final int MAX_MARKER_SIZE = 4096;
    private static final int MINIMUM_JAVA_FEATURE = 24;
    private static final List<String> JVM_OPTION_ENVIRONMENTS =
            List.of("JAVA_TOOL_OPTIONS", "JDK_JAVA_OPTIONS", "_JAVA_OPTIONS");

    private AotCacheManager() {
    }

    static Session open(Path runtimeCache, String applicationIdentity) {
        List<String> inputArguments;
        try {
            inputArguments = List.copyOf(ManagementFactory.getRuntimeMXBean().getInputArguments());
        } catch (Exception ex) {
            return Session.disabled(List.of());
        }
        if (!isEnabled() || hasConflictingEnvironment(System.getenv())) {
            return Session.disabled(inputArguments);
        }
        try {
            if (isDisabledByArguments(inputArguments)) {
                return Session.disabled(inputArguments);
            }
            Path directory = runtimeCache.resolve("aot");
            createPrivateDirectory(directory);
            String signature = signature(runtimeCache, applicationIdentity, inputArguments);
            Path cache = directory.resolve(signature + CACHE_SUFFIX);
            Path marker = directory.resolve(signature + MARKER_SUFFIX);
            if (isValid(cache, marker, signature)) {
                return Session.use(cache, inputArguments, signature);
            }

            Path lockPath = directory.resolve(LOCK_FILE);
            FileChannel channel = null;
            FileLock lock = null;
            try {
                channel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                try {
                    lock = channel.tryLock();
                } catch (OverlappingFileLockException ignored) {
                }
                if (lock == null) {
                    closeQuietly(null, channel);
                    return Session.disabled(inputArguments);
                }
                if (isValid(cache, marker, signature)) {
                    closeQuietly(lock, channel);
                    return Session.use(cache, inputArguments, signature);
                }
                Files.deleteIfExists(marker);
                Files.deleteIfExists(marker.resolveSibling(marker.getFileName() + ".tmp"));
                Files.deleteIfExists(cache);
                Files.deleteIfExists(configuration(cache));
                Path training = directory.resolve(signature + TRAINING_SUFFIX);
                Files.writeString(training, Long.toString(ProcessHandle.current().pid()) + "\n",
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                return Session.training(cache, marker, training, inputArguments, signature, channel, lock);
            } catch (Exception ex) {
                closeQuietly(lock, channel);
                return Session.disabled(inputArguments);
            }
        } catch (Exception ex) {
            return Session.disabled(inputArguments);
        }
    }

    private static boolean isEnabled() {
        if (Runtime.version().feature() < MINIMUM_JAVA_FEATURE
                || !isSupportedVm(System.getProperty("java.vm.name", ""))) {
            return false;
        }
        String value = System.getProperty(ENABLED_PROPERTY);
        return value == null || value.isBlank() || Boolean.parseBoolean(value.trim());
    }

    static boolean isSupportedVm(String vmName) {
        String normalized = vmName.toLowerCase(Locale.ROOT);
        return !normalized.isBlank()
                && !normalized.contains("openj9")
                && !normalized.contains("substrate")
                && !normalized.contains("j9");
    }

    static boolean isDisabledByArguments(List<String> arguments) {
        return arguments.stream().anyMatch(argument -> {
            String normalized = argument.toLowerCase(Locale.ROOT);
            return normalized.equals("-xx:aotmode=off") || isUnsupportedSharedArchiveArgument(argument);
        });
    }

    static boolean hasConflictingEnvironment(Map<String, String> environment) {
        for (Map.Entry<String, String> entry : environment.entrySet()) {
            if (JVM_OPTION_ENVIRONMENTS.stream().noneMatch(name -> name.equalsIgnoreCase(entry.getKey()))) {
                continue;
            }
            for (String value : entry.getValue().trim().split("\\s+")) {
                String argument = unquote(value);
                if (!argument.isEmpty() && (isManagedArgument(argument)
                        || isSuppressedArgument(argument)
                        || isUnsupportedSharedArchiveArgument(argument))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String unquote(String value) {
        if (value.length() >= 2) {
            char first = value.charAt(0);
            char last = value.charAt(value.length() - 1);
            if (first == last && (first == '"' || first == '\'')) {
                return value.substring(1, value.length() - 1);
            }
        }
        return value;
    }

    private static String signature(Path runtimeCache, String applicationIdentity, List<String> inputArguments) {
        List<String> compatibleArguments = inputArguments.stream()
                .filter(argument -> !isManagedArgument(argument) && !isSuppressedArgument(argument))
                .toList();
        StringBuilder identity = new StringBuilder("format=3\n");
        appendField(identity, "application", applicationIdentity);
        appendField(identity, "classpath", runtimeCache.resolve("classpath.jar").toAbsolutePath().normalize());
        appendField(identity, "java.home", System.getProperty("java.home", ""));
        appendField(identity, "java.version", System.getProperty("java.version", ""));
        appendField(identity, "java.runtime.version", System.getProperty("java.runtime.version", ""));
        appendField(identity, "java.vm.name", System.getProperty("java.vm.name", ""));
        appendField(identity, "java.vm.version", System.getProperty("java.vm.version", ""));
        appendField(identity, "java.vm.vendor", System.getProperty("java.vm.vendor", ""));
        appendField(identity, "os.name", System.getProperty("os.name", ""));
        appendField(identity, "os.arch", System.getProperty("os.arch", ""));
        appendField(identity, "max.memory", Runtime.getRuntime().maxMemory());
        appendField(identity, "arguments", sha256(String.join("\u0000", compatibleArguments)));
        return sha256(identity.toString());
    }

    private static void appendField(StringBuilder identity, String name, Object value) {
        String text = String.valueOf(value);
        identity.append(name).append('=').append(text.length()).append(':').append(text).append('\n');
    }

    static boolean isValid(Path cache, Path marker, String signature) throws IOException {
        if (!Files.isRegularFile(cache) || !Files.isRegularFile(marker)) {
            return false;
        }
        long cacheSize = Files.size(cache);
        int markerSize = (int) Math.min(Files.size(marker), (long) MAX_MARKER_SIZE + 1);
        if (cacheSize == 0 || markerSize == 0 || markerSize > MAX_MARKER_SIZE) {
            return false;
        }
        String expected = MARKER_FORMAT + "\n"
                + "signature=" + signature + "\n"
                + "size=" + cacheSize + "\n";
        try (InputStream input = Files.newInputStream(marker)) {
            byte[] data = input.readNBytes(MAX_MARKER_SIZE + 1);
            return data.length <= MAX_MARKER_SIZE
                    && expected.equals(new String(data, StandardCharsets.UTF_8));
        }
    }

    private static boolean isManagedArgument(String argument) {
        String normalized = argument.toLowerCase(Locale.ROOT);
        return normalized.startsWith("-xx:aotcache=")
                || normalized.startsWith("-xx:aotcacheoutput=")
                || normalized.startsWith("-xx:aotconfiguration=")
                || normalized.startsWith("-xx:aotmode=");
    }

    private static boolean isSuppressedArgument(String argument) {
        String normalized = argument.toLowerCase(Locale.ROOT);
        return normalized.equals("-xshare:off") || normalized.equals("-xshare=off");
    }

    private static boolean isUnsupportedSharedArchiveArgument(String argument) {
        String normalized = argument.toLowerCase(Locale.ROOT);
        return ((normalized.startsWith("-xshare:") || normalized.startsWith("-xshare="))
                && !isSuppressedArgument(normalized))
                || normalized.startsWith("-xx:dumploadedclasslist=")
                || normalized.startsWith("-xx:sharedclasslistfile=")
                || normalized.startsWith("-xx:sharedarchivefile=")
                || normalized.startsWith("-xx:archiveclassesatexit=")
                || normalized.startsWith("-xx:sharedarchiveconfigfile=")
                || normalized.startsWith("-xx:extrasharedclasslistfile=")
                || normalized.equals("-xx:+autocreatesharedarchive")
                || normalized.equals("-xx:+usesharedspaces")
                || normalized.equals("-xx:+requiresharedspaces");
    }

    private static void createPrivateDirectory(Path directory) throws IOException {
        Files.createDirectories(directory);
        if (Files.getFileAttributeView(directory, PosixFileAttributeView.class) == null) {
            return;
        }
        Files.setPosixFilePermissions(directory, Set.of(
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.OWNER_EXECUTE));
    }

    private static String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return toHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 is unavailable", ex);
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

    private static Path configuration(Path cache) {
        return cache.resolveSibling(cache.getFileName() + ".config");
    }

    static final class Session implements AutoCloseable {

        private final Mode mode;
        private final Path cache;
        private final Path marker;
        private final Path training;
        private final List<String> inputArguments;
        private final String signature;
        private final FileChannel channel;
        private final FileLock lock;

        private Session(Mode mode, Path cache, Path marker, Path training, List<String> inputArguments,
                        String signature, FileChannel channel, FileLock lock) {
            this.mode = mode;
            this.cache = cache;
            this.marker = marker;
            this.training = training;
            this.inputArguments = inputArguments;
            this.signature = signature;
            this.channel = channel;
            this.lock = lock;
        }

        static Session disabled(List<String> inputArguments) {
            return new Session(Mode.DISABLED, null, null, null, inputArguments, null, null, null);
        }

        private static Session use(Path cache, List<String> inputArguments, String signature) {
            return new Session(Mode.USE, cache, null, null, inputArguments, signature, null, null);
        }

        private static Session training(Path cache, Path marker, Path training, List<String> inputArguments,
                                        String signature, FileChannel channel, FileLock lock) {
            return new Session(Mode.TRAINING, cache, marker, training, inputArguments, signature, channel, lock);
        }

        List<String> jvmArguments() {
            if (mode == Mode.DISABLED) {
                List<String> result = new ArrayList<>(inputArguments);
                result.removeIf(AotCacheManager::isManagedArgument);
                return List.copyOf(result);
            }
            List<String> result = new ArrayList<>(inputArguments);
            result.removeIf(argument -> AotCacheManager.isManagedArgument(argument)
                    || AotCacheManager.isSuppressedArgument(argument));
            if (mode == Mode.TRAINING) {
                result.add("-XX:AOTCacheOutput=" + cache);
            } else if (mode == Mode.USE) {
                result.add("-XX:AOTCache=" + cache);
            }
            return List.copyOf(result);
        }

        void complete() {
            if (mode != Mode.TRAINING) {
                return;
            }
            try {
                long cacheSize = Files.isRegularFile(cache) ? Files.size(cache) : 0;
                if (cacheSize > 0) {
                    String value = MARKER_FORMAT + "\n"
                            + "signature=" + signature + "\n"
                            + "size=" + cacheSize + "\n";
                    Path temporaryMarker = marker.resolveSibling(marker.getFileName() + ".tmp");
                    Files.writeString(temporaryMarker, value, StandardCharsets.UTF_8,
                            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                    try {
                        Files.move(temporaryMarker, marker, StandardCopyOption.ATOMIC_MOVE,
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (AtomicMoveNotSupportedException ex) {
                        Files.move(temporaryMarker, marker, StandardCopyOption.REPLACE_EXISTING);
                    }
                } else {
                    Files.deleteIfExists(cache);
                    Files.deleteIfExists(marker);
                }
            } catch (IOException ignored) {
                deleteQuietly(marker);
                deleteQuietly(cache);
                deleteQuietly(marker.resolveSibling(marker.getFileName() + ".tmp"));
            } finally {
                deleteQuietly(training);
                deleteQuietly(configuration(cache));
                closeQuietly(lock, channel);
            }
        }

        @Override
        public void close() {
            if (mode == Mode.TRAINING && lock != null && lock.isValid()) {
                deleteQuietly(training);
                deleteQuietly(marker);
                deleteQuietly(cache);
                deleteQuietly(configuration(cache));
            }
            if (channel != null) {
                closeQuietly(lock, channel);
            }
        }

        private static void deleteQuietly(Path path) {
            if (path == null) {
                return;
            }
            try {
                Files.deleteIfExists(path);
            } catch (IOException ignored) {
            }
        }
    }

    private enum Mode {
        DISABLED,
        TRAINING,
        USE
    }
}
