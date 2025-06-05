package cn.oyzh.ssh.util;

import com.jcraft.jsch.JSch;

/**
 * ssh持有器
 *
 * @author oyzh
 * @since 2025-03-25
 */
public class SSHHolder {

    /**
     * JSCH对象，只需要一个即可
     */
    private static JSch jSch;

    public static JSch getJsch() {
        synchronized (SSHHolder.class) {
            if (jSch == null) {
                jSch = new JSch();
                JSch.setLogger(new JschLogger());
            }
        }
        return jSch;
    }

}
