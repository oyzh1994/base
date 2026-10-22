package cn.oyzh.ssh.jsch;

public final class JSchKeyType {

    public static final String SSH_RSA = "ssh-rsa";

    public static final String SSH_DSS = "ssh-dss";

    public static final String SSH_ED25519 = "ssh-ed25519";

    public static final String ECDSA_SHA2_NISTP256 = "ecdsa-sha2-nistp256";

    public static final String ECDSA_SHA2_NISTP384 = "ecdsa-sha2-nistp384";

    public static final String ECDSA_SHA2_NISTP521 = "ecdsa-sha2-nistp521";

    private JSchKeyType() {
    }
}
