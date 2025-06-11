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
     * JSCH对象，本地证书，只需要一个即可
     */
    private static JSch localJSch;

    public static JSch getJsch() {
        synchronized (SSHHolder.class) {
            if (localJSch == null) {
                localJSch = new JSch();
                JSch.setLogger(new JschLogger());
            }
        }
        return localJSch;
    }

    /**
     * JSCH对象，agent代理证书，只需要一个即可
     */
    private static JSch agentJSch;

    public static JSch getAgentJsch() {
        synchronized (SSHHolder.class) {
            if (agentJSch == null) {
                agentJSch = new JSch();
                JSch.setLogger(new JschLogger());
            }
        }
        return agentJSch;
    }
}
