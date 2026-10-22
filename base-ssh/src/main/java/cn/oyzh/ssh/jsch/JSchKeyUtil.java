package cn.oyzh.ssh.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public final class JSchKeyUtil {

    private static final JSch JSCH = new JSch();

    private JSchKeyUtil() {
    }

    public static String[] generateEd25519(int keySize, String password) throws Exception {
        return generate(KeyPair.ED25519, requireEd25519Size(keySize), password);
    }

    public static String[] generateRsa(int keySize, String password) throws Exception {
        return generate(KeyPair.RSA, requireRsaSize(keySize), password);
    }

    public static String[] generateDsa(int keySize, String password) throws Exception {
        return generate(KeyPair.DSA, requireDsaSize(keySize), password);
    }

    public static String[] generateEcdsa(int keySize, String password) throws Exception {
        return generate(KeyPair.ECDSA, requireEcdsaSize(keySize), password);
    }

    public static int getKeySize(String key, String password) throws Exception {
        byte[] publicKey = extractPublicKey(key);
        if (publicKey != null) {
            return getPublicKeySize(publicKey);
        }
        KeyPair keyPair = load(key, password);
        if (keyPair == null) {
            return -1;
        }
        try {
            return JSchKeyType.SSH_ED25519.equals(normalizeType(keyPair.getKeyTypeString()))
                    ? 256
                    : keyPair.getKeySize();
        } finally {
            keyPair.dispose();
        }
    }

    public static String getKeyType(String key, String password) throws Exception {
        byte[] publicKey = extractPublicKey(key);
        if (publicKey != null) {
            return new String(new Buffer(publicKey).getString(), StandardCharsets.UTF_8);
        }
        KeyPair keyPair = load(key, password);
        if (keyPair == null) {
            return null;
        }
        try {
            return normalizeType(keyPair.getKeyTypeString());
        } finally {
            keyPair.dispose();
        }
    }

    public static Iterable<KeyPair> loadKeysForStr(String key, String password) throws Exception {
        return loadKeysForBytes(key.getBytes(StandardCharsets.UTF_8), password);
    }

    public static Iterable<KeyPair> loadKeysForBytes(byte[] key, String password) throws Exception {
        KeyPair keyPair = KeyPair.load(JSCH, key, passwordBytes(password));
        return keyPair == null ? List.of() : List.of(keyPair);
    }

    public static Iterable<KeyPair> loadKeysFromFile(String path, String password) throws Exception {
        KeyPair keyPair = KeyPair.load(JSCH, path, password);
        return keyPair == null ? List.of() : List.of(keyPair);
    }

    private static String[] generate(int type, int keySize, String password) throws Exception {
        KeyPair keyPair = KeyPair.genKeyPair(JSCH, type, keySize);
        try {
            ByteArrayOutputStream publicKey = new ByteArrayOutputStream();
            ByteArrayOutputStream privateKey = new ByteArrayOutputStream();
            keyPair.writePublicKey(publicKey, null);
            if (password == null || password.isEmpty()) {
                keyPair.writeOpenSSHv1PrivateKey(privateKey, null);
            } else {
                keyPair.writeOpenSSHv1PrivateKey(privateKey, passwordBytes(password), "aes256-ctr", 16);
            }
            return new String[]{
                    publicKey.toString(StandardCharsets.UTF_8),
                    privateKey.toString(StandardCharsets.UTF_8)
            };
        } finally {
            keyPair.dispose();
        }
    }

    private static KeyPair load(String key, String password) throws JSchException {
        if (key == null || key.isBlank()) {
            return null;
        }
        return KeyPair.load(JSCH, key.getBytes(StandardCharsets.UTF_8), passwordBytes(password));
    }

    private static byte[] extractPublicKey(String key) {
        if (key == null) {
            return null;
        }
        for (String line : key.split("\\R")) {
            String value = line.trim();
            if (value.isEmpty() || value.startsWith("-----")) {
                continue;
            }
            String[] parts = value.split("\\s+");
            if (parts.length < 2 || !parts[0].startsWith("ssh-") && !parts[0].startsWith("ecdsa-")) {
                continue;
            }
            try {
                return Base64.getDecoder().decode(parts[1]);
            } catch (IllegalArgumentException ignored) {
                return null;
            }
        }
        return null;
    }

    private static int getPublicKeySize(byte[] publicKey) {
        Buffer buffer = new Buffer(publicKey);
        String type = new String(buffer.getString(), StandardCharsets.UTF_8);
        return switch (type) {
            case JSchKeyType.SSH_RSA -> {
                buffer.getMPInt();
                yield bitLength(buffer.getMPInt());
            }
            case JSchKeyType.SSH_DSS -> bitLength(buffer.getMPInt());
            case JSchKeyType.SSH_ED25519 -> 256;
            case JSchKeyType.ECDSA_SHA2_NISTP256 -> 256;
            case JSchKeyType.ECDSA_SHA2_NISTP384 -> 384;
            case JSchKeyType.ECDSA_SHA2_NISTP521 -> 521;
            default -> -1;
        };
    }

    private static int bitLength(byte[] value) {
        int index = 0;
        while (index < value.length && value[index] == 0) {
            index++;
        }
        if (index == value.length) {
            return 0;
        }
        return (value.length - index - 1) * 8 + Integer.SIZE - Integer.numberOfLeadingZeros(value[index] & 0xff);
    }

    private static String normalizeType(String type) {
        return switch (type) {
            case "ssh-rsa", "rsa-sha2-256", "rsa-sha2-512" -> JSchKeyType.SSH_RSA;
            case "ssh-dss" -> JSchKeyType.SSH_DSS;
            case "ssh-ed25519" -> JSchKeyType.SSH_ED25519;
            case "ecdsa-sha2-nistp256" -> JSchKeyType.ECDSA_SHA2_NISTP256;
            case "ecdsa-sha2-nistp384" -> JSchKeyType.ECDSA_SHA2_NISTP384;
            case "ecdsa-sha2-nistp521" -> JSchKeyType.ECDSA_SHA2_NISTP521;
            default -> type;
        };
    }

    private static int requireEd25519Size(int keySize) throws JSchException {
        if (keySize != 255 && keySize != 256) {
            throw new JSchException("Unsupported Ed25519 key size: " + keySize);
        }
        return keySize;
    }

    private static int requireRsaSize(int keySize) throws JSchException {
        if (keySize < 1024 || keySize > 16384) {
            throw new JSchException("Unsupported RSA key size: " + keySize);
        }
        return keySize;
    }

    private static int requireDsaSize(int keySize) throws JSchException {
        if (keySize != 1024) {
            throw new JSchException("Unsupported DSA key size: " + keySize);
        }
        return keySize;
    }

    private static int requireEcdsaSize(int keySize) throws JSchException {
        return switch (keySize) {
            case 256, 384, 521 -> keySize;
            default -> throw new JSchException("Unsupported ECDSA key size: " + keySize);
        };
    }

    private static byte[] passwordBytes(String password) {
        return password == null || password.isEmpty()
                ? null
                : password.getBytes(StandardCharsets.UTF_8);
    }
}
