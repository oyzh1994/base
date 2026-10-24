package cn.oyzh.pkg.util.compact;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
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
import java.util.Properties;
import java.util.Set;

final class AotCacheManager {

    private static final String ENABLED_PROPERTY = "compact.jar.aot";
    private static final String ASSEMBLY_PROPERTY = "compact.jar.aot.assembly";
    private static final String CACHE_SUFFIX = ".aot";
    private static final String MARKER_SUFFIX = ".ok";
    private static final String TRAINING_SUFFIX = ".training";
    private static final String CONFIG_SUFFIX = ".aotconfig";
    private static final String LEGACY_CONFIG_SUFFIX = ".config";
    private static final String ASSEMBLY_STATE_SUFFIX = ".assembly";
    private static final String ASSEMBLY_LOG_SUFFIX = ".assembly.log";
    private static final String LOCK_FILE = "cache.lock";
    private static final String MARKER_FORMAT = "format=1";
    private static final String ASSEMBLY_FORMAT = "format=1";
    private static final int MAX_MARKER_SIZE = 4096;
    private static final int MAX_ASSEMBLY_ARGUMENTS = 2048;
    private static final int MINIMUM_JAVA_FEATURE = 24;
    private static final long ASSEMBLY_LOCK_WAIT_MILLIS = 10_000;
    private static final long ASSEMBLY_LOCK_RETRY_MILLIS = 50;
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
            Path configuration = configuration(cache);
            Path assemblyState = assemblyState(cache);
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
                Files.deleteIfExists(configuration);
                Files.deleteIfExists(legacyConfiguration(cache));
                Files.deleteIfExists(assemblyState);
                Files.deleteIfExists(temporaryCache(cache));
                Path training = directory.resolve(signature + TRAINING_SUFFIX);
                Files.writeString(training, Long.toString(ProcessHandle.current().pid()) + "\n",
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                AssemblyMode assemblyMode = assemblyMode();
                if (assemblyMode.twoStep()
                        && !writeAssemblyState(assemblyState, configuration, runtimeCache, signature,
                        inputArguments)) {
                    assemblyMode = AssemblyMode.ONE_STEP;
                    deleteQuietly(assemblyState);
                }
                return Session.training(cache, marker, training, configuration, assemblyState,
                        assemblyLog(cache), inputArguments, signature, assemblyMode, channel, lock);
            } catch (Exception ex) {
                closeQuietly(lock, channel);
                return Session.disabled(inputArguments);
            }
        } catch (Exception ex) {
            return Session.disabled(inputArguments);
        }
    }

    static boolean isEnabled() {
        if (Runtime.version().feature() < MINIMUM_JAVA_FEATURE
                || !isSupportedVm(System.getProperty("java.vm.name", ""))) {
            return false;
        }
        String value = System.getProperty(ENABLED_PROPERTY);
        return value != null && Boolean.parseBoolean(value.trim());
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
        List<String> compatibleArguments = compatibleArguments(inputArguments);
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

    private static List<String> compatibleArguments(List<String> inputArguments) {
        return inputArguments.stream()
                .filter(argument -> !isManagedArgument(argument) && !isSuppressedArgument(argument))
                .toList();
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

    private static void deleteQuietly(Path path) {
        if (path == null) {
            return;
        }
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }

    private static Path configuration(Path cache) {
        String name = cache.getFileName().toString();
        String signature = name.substring(0, name.length() - CACHE_SUFFIX.length());
        return cache.resolveSibling(signature + CONFIG_SUFFIX);
    }

    private static Path legacyConfiguration(Path cache) {
        String name = cache.getFileName().toString();
        String signature = name.substring(0, name.length() - CACHE_SUFFIX.length());
        return cache.resolveSibling(signature + LEGACY_CONFIG_SUFFIX);
    }

    private static Path assemblyState(Path cache) {
        return cache.resolveSibling(cache.getFileName() + ASSEMBLY_STATE_SUFFIX);
    }

    private static Path assemblyLog(Path cache) {
        return cache.resolveSibling(cache.getFileName() + ASSEMBLY_LOG_SUFFIX);
    }

    private static Path temporaryCache(Path cache) {
        String name = cache.getFileName().toString();
        String signature = name.substring(0, name.length() - CACHE_SUFFIX.length());
        return cache.resolveSibling(signature + ".aot.tmp");
    }

    private static AssemblyMode assemblyMode() {
        String value = System.getProperty(ASSEMBLY_PROPERTY, "auto").trim().toLowerCase(Locale.ROOT);
        return switch (value) {
            case "one-step" -> AssemblyMode.ONE_STEP;
            case "two-step" -> AssemblyMode.TWO_STEP;
            case "async" -> AssemblyMode.ASYNC;
            default -> isWindows() ? AssemblyMode.ASYNC : AssemblyMode.ONE_STEP;
        };
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).startsWith("windows");
    }

    private static boolean writeAssemblyState(Path state, Path configuration, Path runtimeCache,
                                              String signature, List<String> inputArguments) {
        Path temporary = state.resolveSibling(state.getFileName() + ".tmp");
        Properties properties = new Properties();
        properties.setProperty("format", ASSEMBLY_FORMAT);
        properties.setProperty("signature", signature);
        properties.setProperty("configuration", configuration.toAbsolutePath().normalize().toString());
        properties.setProperty("classpath",
                runtimeCache.resolve("classpath.jar").toAbsolutePath().normalize().toString());
        List<String> arguments = compatibleArguments(inputArguments);
        properties.setProperty("argument.count", Integer.toString(arguments.size()));
        for (int index = 0; index < arguments.size(); index++) {
            properties.setProperty("argument." + index, arguments.get(index));
        }
        try (OutputStream output = Files.newOutputStream(temporary, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            properties.store(output, "compact-jar AOT assembly state");
            moveAtomically(temporary, state);
            return true;
        } catch (Exception ex) {
            deleteQuietly(temporary);
            return false;
        }
    }

    private static void moveAtomically(Path source, Path target) throws IOException {
        try {
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException ex) {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void appendLog(Path log, String message) {
        if (log == null) {
            return;
        }
        try {
            Files.writeString(log, "[" + java.time.Instant.now() + "] " + message + System.lineSeparator(),
                    StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND,
                    StandardOpenOption.WRITE);
        } catch (Exception ignored) {
        }
    }

    private static boolean writeMarker(Path cache, Path marker, String signature) {
        if (!Files.isRegularFile(cache)) {
            return false;
        }
        try {
            long cacheSize = Files.size(cache);
            if (cacheSize <= 0) {
                return false;
            }
            String value = MARKER_FORMAT + "\n"
                    + "signature=" + signature + "\n"
                    + "size=" + cacheSize + "\n";
            Path temporaryMarker = marker.resolveSibling(marker.getFileName() + ".tmp");
            Files.writeString(temporaryMarker, value, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            moveAtomically(temporaryMarker, marker);
            return true;
        } catch (Exception ex) {
            deleteQuietly(marker);
            deleteQuietly(marker.resolveSibling(marker.getFileName() + ".tmp"));
            return false;
        }
    }

    private static void cleanupOperationFiles(Path cache) {
        Path marker = cache.resolveSibling(stripCacheSuffix(cache.getFileName().toString()) + MARKER_SUFFIX);
        Path training = cache.resolveSibling(stripCacheSuffix(cache.getFileName().toString()) + TRAINING_SUFFIX);
        deleteQuietly(training);
        deleteQuietly(configuration(cache));
        deleteQuietly(legacyConfiguration(cache));
        deleteQuietly(assemblyState(cache));
        deleteQuietly(temporaryCache(cache));
        deleteQuietly(marker.resolveSibling(marker.getFileName() + ".tmp"));
    }

    private static void cleanupTraining(Path cache, Path marker) {
        cleanupOperationFiles(cache);
        deleteQuietly(marker);
        deleteQuietly(cache);
    }

    private static String stripCacheSuffix(String name) {
        return name.endsWith(CACHE_SUFFIX) ? name.substring(0, name.length() - CACHE_SUFFIX.length()) : name;
    }

    private static boolean startAssemblyHelper(Path cache, String signature) {
        Path log = assemblyLog(cache);
        try {
            List<String> command = new ArrayList<>();
            command.add(helperJavaExecutable());
            command.add("-cp");
            command.add(helperClassPath());
            command.add(AotAssemblyMain.class.getName());
            command.add(cache.getParent().toAbsolutePath().normalize().toString());
            command.add(signature);
            ProcessBuilder builder = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .redirectOutput(ProcessBuilder.Redirect.appendTo(log.toFile()));
            Process process = builder.start();
            appendLog(log, "detached AOT assembly started, pid=" + process.pid());
            return true;
        } catch (Exception ex) {
            appendLog(log, "unable to start detached AOT assembly: " + ex);
            return false;
        }
    }

    private static String helperJavaExecutable() {
        String executable = isWindows() ? "javaw.exe" : "java";
        Path path = Path.of(System.getProperty("java.home"), "bin", executable);
        if (!Files.isRegularFile(path)) {
            path = Path.of(System.getProperty("java.home"), "bin", isWindows() ? "java.exe" : "java");
        }
        return path.toString();
    }

    private static String helperClassPath() throws URISyntaxException {
        var source = AotAssemblyMain.class.getProtectionDomain().getCodeSource();
        if (source != null) {
            return Path.of(source.getLocation().toURI()).toString();
        }
        return System.getProperty("java.class.path", "");
    }

    private static boolean assemble(Path cache, Path marker, Path configuration, Path state, Path log,
                                    String signature) {
        Path temporary = temporaryCache(cache);
        deleteQuietly(temporary);
        try {
            Properties properties = readAssemblyState(state, signature, configuration);
            if (properties == null) {
                appendLog(log, "assembly state is missing or invalid: " + state);
                cleanupTraining(cache, marker);
                return false;
            }
            if (!Files.isRegularFile(configuration) || Files.size(configuration) <= 0) {
                appendLog(log, "AOT training configuration is missing or empty: " + configuration);
                cleanupTraining(cache, marker);
                return false;
            }

            List<String> command = new ArrayList<>();
            command.add(helperJavaExecutable());
            List<String> assemblyArguments = assemblyArguments(properties);
            if (assemblyArguments == null) {
                appendLog(log, "AOT assembly arguments are invalid");
                cleanupTraining(cache, marker);
                return false;
            }
            command.addAll(assemblyArguments);
            String classpath = properties.getProperty("classpath");
            if (classpath == null || classpath.isBlank()) {
                appendLog(log, "AOT assembly classpath is missing");
                cleanupTraining(cache, marker);
                return false;
            }
            command.add("-cp");
            command.add(classpath);
            command.add("-XX:AOTMode=create");
            command.add("-XX:AOTConfiguration=" + configuration);
            command.add("-XX:AOTCacheOutput=" + temporary);
            command.add("-XX:-AOTClassLinking");
            appendLog(log, "AOT assembly started, configuration=" + Files.size(configuration) + " bytes");
            long started = System.nanoTime();
            ProcessBuilder builder = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .redirectOutput(ProcessBuilder.Redirect.appendTo(log.toFile()));
            Process process = builder.start();
            int status = process.waitFor();
            long elapsedMillis = (System.nanoTime() - started) / 1_000_000L;
            if (status != 0 || !Files.isRegularFile(temporary) || Files.size(temporary) <= 0) {
                appendLog(log, "AOT assembly failed, status=" + status + ", elapsedMs=" + elapsedMillis);
                cleanupTraining(cache, marker);
                return false;
            }
            moveAtomically(temporary, cache);
            if (!writeMarker(cache, marker, signature)) {
                appendLog(log, "AOT cache marker could not be written");
                cleanupTraining(cache, marker);
                return false;
            }
            appendLog(log, "AOT assembly complete, elapsedMs=" + elapsedMillis
                    + ", cacheBytes=" + Files.size(cache));
            cleanupOperationFiles(cache);
            return true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            appendLog(log, "AOT assembly interrupted");
            cleanupTraining(cache, marker);
            return false;
        } catch (Exception ex) {
            appendLog(log, "AOT assembly failed: " + ex);
            cleanupTraining(cache, marker);
            return false;
        } finally {
            deleteQuietly(temporary);
        }
    }

    private static Properties readAssemblyState(Path state, String signature, Path configuration) {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(state)) {
            properties.load(input);
            if (!ASSEMBLY_FORMAT.equals(properties.getProperty("format"))
                    || !signature.equals(properties.getProperty("signature"))) {
                return null;
            }
            Path storedConfiguration = Path.of(properties.getProperty("configuration", ""))
                    .toAbsolutePath().normalize();
            if (!storedConfiguration.equals(configuration.toAbsolutePath().normalize())) {
                return null;
            }
            int count = Integer.parseInt(properties.getProperty("argument.count", "-1"));
            if (count < 0 || count > MAX_ASSEMBLY_ARGUMENTS) {
                return null;
            }
            return properties;
        } catch (Exception ex) {
            return null;
        }
    }

    private static List<String> assemblyArguments(Properties properties) {
        int count = Integer.parseInt(properties.getProperty("argument.count", "0"));
        List<String> arguments = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            String argument = properties.getProperty("argument." + index);
            if (argument == null) {
                return null;
            }
            if (!isManagedArgument(argument) && !isSuppressedArgument(argument)) {
                arguments.add(argument);
            }
        }
        return arguments;
    }

    static int assembleDetachedCache(Path directory, String signature) {
        if (directory == null || !isSignature(signature)) {
            return 2;
        }
        Path aotDirectory = directory.toAbsolutePath().normalize();
        Path cache = aotDirectory.resolve(signature + CACHE_SUFFIX);
        Path marker = aotDirectory.resolve(signature + MARKER_SUFFIX);
        Path configuration = configuration(cache);
        Path state = assemblyState(cache);
        Path log = assemblyLog(cache);
        FileChannel channel = null;
        FileLock lock = null;
        try {
            channel = FileChannel.open(aotDirectory.resolve(LOCK_FILE), StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            long deadline = System.nanoTime() + ASSEMBLY_LOCK_WAIT_MILLIS * 1_000_000L;
            while (lock == null && System.nanoTime() < deadline) {
                try {
                    lock = channel.tryLock();
                } catch (OverlappingFileLockException ignored) {
                }
                if (lock == null) {
                    try {
                        Thread.sleep(ASSEMBLY_LOCK_RETRY_MILLIS);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return 3;
                    }
                }
            }
            if (lock == null) {
                appendLog(log, "AOT assembly skipped because cache lock is busy");
                return 3;
            }
            if (isValid(cache, marker, signature)) {
                cleanupOperationFiles(cache);
                return 0;
            }
            return assemble(cache, marker, configuration, state, log, signature) ? 0 : 1;
        } catch (Exception ex) {
            appendLog(log, "detached AOT assembly failed: " + ex);
            return 1;
        } finally {
            closeQuietly(lock, channel);
        }
    }

    private static boolean isSignature(String value) {
        if (value == null || value.length() != 64) {
            return false;
        }
        for (int index = 0; index < value.length(); index++) {
            char item = value.charAt(index);
            if (!((item >= '0' && item <= '9') || (item >= 'a' && item <= 'f'))) {
                return false;
            }
        }
        return true;
    }

    static final class Session implements AutoCloseable {

        private final Mode mode;
        private final Path cache;
        private final Path marker;
        private final Path training;
        private final Path configuration;
        private final Path assemblyState;
        private final Path assemblyLog;
        private final List<String> inputArguments;
        private final String signature;
        private final AssemblyMode assemblyMode;
        private final FileChannel channel;
        private final FileLock lock;
        private boolean completed;

        private Session(Mode mode, Path cache, Path marker, Path training, Path configuration,
                        Path assemblyState, Path assemblyLog, List<String> inputArguments, String signature,
                        AssemblyMode assemblyMode, FileChannel channel, FileLock lock) {
            this.mode = mode;
            this.cache = cache;
            this.marker = marker;
            this.training = training;
            this.configuration = configuration;
            this.assemblyState = assemblyState;
            this.assemblyLog = assemblyLog;
            this.inputArguments = inputArguments;
            this.signature = signature;
            this.assemblyMode = assemblyMode;
            this.channel = channel;
            this.lock = lock;
        }

        static Session disabled(List<String> inputArguments) {
            return new Session(Mode.DISABLED, null, null, null, null, null, null,
                    inputArguments, null, null, null, null);
        }

        private static Session use(Path cache, List<String> inputArguments, String signature) {
            return new Session(Mode.USE, cache, null, null, null, null, null,
                    inputArguments, signature, null, null, null);
        }

        private static Session training(Path cache, Path marker, Path training, Path configuration,
                                        Path assemblyState, Path assemblyLog, List<String> inputArguments,
                                        String signature, AssemblyMode assemblyMode,
                                        FileChannel channel, FileLock lock) {
            return new Session(Mode.TRAINING, cache, marker, training, configuration, assemblyState,
                    assemblyLog, inputArguments, signature, assemblyMode, channel, lock);
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
                if (assemblyMode.twoStep()) {
                    result.add("-XX:AOTMode=record");
                    result.add("-XX:AOTConfiguration=" + configuration);
                    result.add("-XX:-AOTClassLinking");
                } else {
                    result.add("-XX:AOTCacheOutput=" + cache);
                }
            } else if (mode == Mode.USE) {
                result.add("-XX:AOTCache=" + cache);
            }
            return List.copyOf(result);
        }

        void complete() {
            if (mode != Mode.TRAINING || completed) {
                return;
            }
            completed = true;
            boolean handedToHelper = false;
            try {
                if (assemblyMode == AssemblyMode.ASYNC) {
                    handedToHelper = startAssemblyHelper(cache, signature);
                    if (!handedToHelper) {
                        cleanupTraining(cache, marker);
                    }
                } else if (assemblyMode == AssemblyMode.TWO_STEP) {
                    if (!assemble(cache, marker, configuration, assemblyState, assemblyLog, signature)) {
                        cleanupTraining(cache, marker);
                    }
                } else {
                    if (!writeMarker(cache, marker, signature)) {
                        cleanupTraining(cache, marker);
                    }
                }
            } catch (Exception ex) {
                if (!handedToHelper) {
                    cleanupTraining(cache, marker);
                }
            } finally {
                if (!handedToHelper) {
                    cleanupOperationFiles(cache);
                }
                closeQuietly(lock, channel);
            }
        }

        @Override
        public void close() {
            if (mode == Mode.TRAINING && !completed) {
                cleanupTraining(cache, marker);
            }
            if (channel != null) {
                closeQuietly(lock, channel);
            }
        }
    }

    private enum AssemblyMode {
        ONE_STEP(false),
        TWO_STEP(true),
        ASYNC(true);

        private final boolean twoStep;

        AssemblyMode(boolean twoStep) {
            this.twoStep = twoStep;
        }

        boolean twoStep() {
            return twoStep;
        }
    }

    private enum Mode {
        DISABLED,
        TRAINING,
        USE
    }
}
