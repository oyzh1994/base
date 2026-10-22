package cn.oyzh.ssh.jsch;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

/**
 * JSch password and keyboard-interactive authentication adapter.
 */
public class JSchPasswordUserInfo implements UserInfo, UIKeyboardInteractive {

    private String password;

    private String passphrase;

    public JSchPasswordUserInfo(String password) {
        this(password, null);
    }

    public JSchPasswordUserInfo(String password, String passphrase) {
        this.password = password;
        this.passphrase = passphrase;
    }

    @Override
    public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt, boolean[] echo) {
        if (prompt == null || prompt.length == 0) {
            return null;
        }
        String[] responses = new String[prompt.length];
        for (int index = 0; index < prompt.length; index++) {
            responses[index] = this.password;
        }
        return responses;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getPassphrase() {
        return this.passphrase;
    }

    @Override
    public boolean promptPassword(String message) {
        return this.password != null;
    }

    @Override
    public boolean promptPassphrase(String message) {
        return this.passphrase != null;
    }

    @Override
    public boolean promptYesNo(String message) {
        return true;
    }

    @Override
    public void showMessage(String message) {
    }
}
