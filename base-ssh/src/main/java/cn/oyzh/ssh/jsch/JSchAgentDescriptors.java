package cn.oyzh.ssh.jsch;

import cn.oyzh.ssh.util.JschUtil;

public final class JSchAgentDescriptors {

    public static final String PAGEANT_IDENTITY_AGENT = JschUtil.PAGEANT_IDENTITY_AGENT;

    public static final String UNIX_IDENTITY_AGENT = JschUtil.UNIX_IDENTITY_AGENT;

    private JSchAgentDescriptors() {
    }

    public static String defaultIdentityAgent() {
        return JschUtil.defaultIdentityAgent();
    }
}
