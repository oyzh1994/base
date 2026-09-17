package cn.oyzh.common.log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * 控制台日志处理器
 *
 * @author oyzh
 * @since 2024-11-15
 */
public class JulConsoleHandler extends StreamHandler {

    public JulConsoleHandler() throws UnsupportedEncodingException {
        super(System.out, new JulConsoleFormatter());
        String enc = this.resolveEncoding();
        if (enc != null) {
            this.setEncoding(enc);
        }
    }

    /**
     * 解析字符集
     *
     * @return 结果
     */
    private static String resolveEncoding() {
        // 1) 真实控制台优先（物理机场景）
        if (System.console() != null) {
            Charset cs = System.console().charset();
            if (cs != null) {
                return cs.name();
            }
        }

        // 2) 明确在 CI 场景：强制 UTF-8
        if ("true".equalsIgnoreCase(System.getenv("CI"))
                || System.getenv("GITHUB_ACTIONS") != null) {
            return "UTF-8";
        }

        // 3) JDK 18+：显式设置的 stdout.encoding 优先
        String v = System.getProperty("stdout.encoding");
        if (v != null && !v.isEmpty()) {
            return v;
        }

        // 4) JDK 8~17：sun.stdout.encoding
        v = System.getProperty("sun.stdout.encoding");
        if (v != null && !v.isEmpty()) {
            return v;
        }

        // 5) 其它情况交给平台默认
        return null;
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        super.flush();
    }

    @Override
    public void close() {
        super.flush();
        super.close();
    }
}
