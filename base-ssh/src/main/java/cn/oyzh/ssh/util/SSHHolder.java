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
    private static volatile JSch localJSch;

    public static JSch getJsch() {
        JSch result = localJSch;
        if (result == null) {
            synchronized (SSHHolder.class) {
                result = localJSch;
                if (result != null) {
                    return result;
                }
                localJSch = new JSch();
                if (JSch.getLogger() == null) {
                    JSch.setLogger(new JschLogger());
                }
                result = localJSch;
            }
        }
        return result;
    }

    /**
     * JSCH对象，agent代理证书，只需要一个即可
     */
    private static volatile JSch agentJSch;

    public static JSch getAgentJsch() {
        JSch result = agentJSch;
        if (result == null) {
            synchronized (SSHHolder.class) {
                result = agentJSch;
                if (result != null) {
                    return result;
                }
                agentJSch = new JSch();
                if (JSch.getLogger() == null) {
                    JSch.setLogger(new JschLogger());
                }
                result = agentJSch;
            }
        }
        return result;
    }

    public static JSch newJsch() {
        if (JSch.getLogger() == null) {
            synchronized (SSHHolder.class) {
                if (JSch.getLogger() == null) {
                    JSch.setLogger(new JschLogger());
                }
            }
        }
        return new JSch();
    }
}
