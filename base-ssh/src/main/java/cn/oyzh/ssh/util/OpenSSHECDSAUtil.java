package cn.oyzh.ssh.util;

import cn.oyzh.common.util.StringUtil;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.encoders.Base64;

import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

/**
 * openssh格式，ecdsa密钥生成器
 *
 * @author oyzh
 * @since 2025/06/10
 */
public class OpenSSHECDSAUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
        // 1. 选择曲线 (secp256r1, secp384r1, secp521r1)
        String curveName = "secp256r1";
        KeyPair keyPair = generateECDSAKeyPair(curveName);

        // 2. 生成OpenSSH格式的公钥
        String sshPublicKey = generateOpenSSHPublicKey((BCECPublicKey) keyPair.getPublic(), curveName);
        System.out.println("OpenSSH Public Key:\n" + sshPublicKey);

        // 3. 生成PEM格式的私钥
        String pemPrivateKey = generatePEMPrivateKey(keyPair.getPrivate());
        System.out.println("\nPEM Private Key:\n" + pemPrivateKey);
    }

    /**
     * 生成密钥
     *
     * @param length 密钥长度，可选256、384、521
     * @return 结果
     */
    public static String[] generateKey(int length) throws Exception {
        return switch (length) {
            case 256:
                yield generateKey("secp256r1");
            case 384:
                yield generateKey("secp384r1");
            case 521:
                yield generateKey("secp521r1");
            default:
                throw new IllegalStateException("Unexpected value: " + length);
        };
    }

    /**
     * 生成密钥
     *
     * @param curveName 曲线，可选secp256r1、secp384r1、secp521r1
     * @return 结果
     */
    public static String[] generateKey(String curveName) throws Exception {
        try {
            // 1. 选择曲线 (secp256r1, secp384r1, secp521r1)
            KeyPair keyPair = generateECDSAKeyPair(curveName);
            // 2. 生成OpenSSH格式的公钥
            String publicKey = generateOpenSSHPublicKey((BCECPublicKey) keyPair.getPublic(), curveName);
            // 3. 生成PEM格式的私钥
            String privateKey = generatePEMPrivateKey(keyPair.getPrivate());
            return new String[]{publicKey, privateKey};
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String[]{"", ""};
    }

    // 方法1: 解析OpenSSH格式
    public static String detectCurveFromOpenSSH(String opensshKey) {
        String[] parts = opensshKey.trim().split("\\s+");
        if (parts.length < 1) {
            return null;
        }
        String curveName = parts[0];
        if (StringUtil.equalsIgnoreCase(curveName, "ecdsa-sha2-nistp256")) {
            return "secp256r1";
        }
        if (StringUtil.equalsIgnoreCase(curveName, "ecdsa-sha2-nistp384")) {
            return "secp384r1";
        }
        if (StringUtil.equalsIgnoreCase(curveName, "ecdsa-sha2-nistp521")) {
            return "secp521r1";
        }
        return null;
    }

    /**
     * 探测密钥长度
     *
     * @param pubKey 公钥
     * @return 结果
     */
    public static int getKeyLength(String pubKey) {
        String curveName = detectCurveFromOpenSSH(pubKey);
        if (curveName == null && pubKey.contains(" ")) {
            pubKey = pubKey.split("\\s+")[1];
        }
        if (curveName == null) {
            curveName = detectCurveType(pubKey.getBytes(), false);
        }
        if (curveName == null) {
            curveName = detectCurveType(pubKey.getBytes(), true);
        }
        if (curveName == null) {
            curveName = detectCurveType(Base64.decode(pubKey), false);
        }
        if (curveName == null) {
            curveName = detectCurveType(Base64.decode(pubKey), true);
        }
        if (curveName != null) {
            if (curveName.contains("256")) {
                return 256;
            }
            if (curveName.contains("384")) {
                return 384;
            }
            if (curveName.contains("521")) {
                return 521;
            }
        }
        return -1;
    }

    /**
     * 探测曲线类型
     *
     * @param publicKeyPoint 公钥
     * @param compress       是否压缩
     * @return 结果
     */
    private static String detectCurveType(byte[] publicKeyPoint, boolean compress) {
        // publicKeyPoint 是未压缩格式的 EC 点 (04 || X || Y)
        int keyLength;
        if (compress) {
            keyLength = publicKeyPoint.length;
        } else {
            keyLength = publicKeyPoint.length - 1; // 减去开头的 0x04
        }
        if (keyLength % 2 != 0) {
            return null;
        }
        int coordinateLength = keyLength / 2; // X 和 Y 坐标的长度

        if (coordinateLength == 32) {
            return "secp256r1";
        }
        if (coordinateLength == 48) {
            return "secp384r1";
        }
        if (coordinateLength == 66) {
            return "secp521r1";
        }
        return null;
    }

    // 生成ECDSA密钥对
    private static KeyPair generateECDSAKeyPair(String curveName) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
        keyGen.initialize(new ECGenParameterSpec(curveName));
        return keyGen.generateKeyPair();
    }

    // 生成OpenSSH格式的公钥
    private static String generateOpenSSHPublicKey(BCECPublicKey publicKey, String curveName) {
        String sshCurveName = curveNameToOpenSSHName(curveName);
        ECPoint point = publicKey.getQ();
        byte[] encodedPoint = point.getEncoded(false); // 未压缩格式

        // OpenSSH编码格式: [算法名] [曲线名] [公钥点]
        byte[] keyBytes = encodeOpenSSHKey(sshCurveName, encodedPoint);
        return sshCurveName + " " + Base64.toBase64String(keyBytes);
    }

    // 将曲线名转换为OpenSSH格式
    private static String curveNameToOpenSSHName(String curveName) {
        return switch (curveName) {
            case "secp256r1" -> "ecdsa-sha2-nistp256";
            case "secp384r1" -> "ecdsa-sha2-nistp384";
            case "secp521r1" -> "ecdsa-sha2-nistp521";
            default -> throw new IllegalArgumentException("Unsupported curve: " + curveName);
        };
    }

    // 编码OpenSSH公钥结构
    private static byte[] encodeOpenSSHKey(String algorithm, byte[] ecPoint) {
        byte[] algorithmBytes = algorithm.getBytes();
        byte[] curveNameBytes = algorithm.substring(algorithm.lastIndexOf('-') + 1).getBytes();

        // 结构: [算法名长度][算法名] [曲线名长度][曲线名] [公钥长度][公钥点]
        byte[] result = new byte[4 + algorithmBytes.length + 4 + curveNameBytes.length + 4 + ecPoint.length];
        int pos = 0;

        // 写入算法名
        pos = writeLengthPrefixed(algorithmBytes, result, pos);
        // 写入曲线名
        pos = writeLengthPrefixed(curveNameBytes, result, pos);
        // 写入公钥点
        writeLengthPrefixed(ecPoint, result, pos);

        return result;
    }

    // 写入长度前缀的数据
    private static int writeLengthPrefixed(byte[] data, byte[] target, int offset) {
        target[offset++] = (byte) (data.length >>> 24);
        target[offset++] = (byte) (data.length >>> 16);
        target[offset++] = (byte) (data.length >>> 8);
        target[offset++] = (byte) data.length;
        System.arraycopy(data, 0, target, offset, data.length);
        return offset + data.length;
    }

    // 生成PEM格式的私钥
    private static String generatePEMPrivateKey(PrivateKey privateKey) throws Exception {
        StringWriter writer = new StringWriter();
        try (JcaPEMWriter pemWriter = new JcaPEMWriter(writer)) {
            pemWriter.writeObject(privateKey);
        }
        return writer.toString();
    }
}
