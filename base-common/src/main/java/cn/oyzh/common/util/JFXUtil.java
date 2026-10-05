package cn.oyzh.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class JFXUtil {

    private static final String[] msLibNames = {
            "api-ms-win-core-console-l1-1-0",
            "api-ms-win-core-console-l1-2-0",
            "api-ms-win-core-datetime-l1-1-0",
            "api-ms-win-core-debug-l1-1-0",
            "api-ms-win-core-errorhandling-l1-1-0",
            "api-ms-win-core-file-l1-1-0",
            "api-ms-win-core-file-l1-2-0",
            "api-ms-win-core-file-l2-1-0",
            "api-ms-win-core-handle-l1-1-0",
            "api-ms-win-core-heap-l1-1-0",
            "api-ms-win-core-interlocked-l1-1-0",
            "api-ms-win-core-libraryloader-l1-1-0",
            "api-ms-win-core-localization-l1-2-0",
            "api-ms-win-core-memory-l1-1-0",
            "api-ms-win-core-namedpipe-l1-1-0",
            "api-ms-win-core-processenvironment-l1-1-0",
            "api-ms-win-core-processthreads-l1-1-0",
            "api-ms-win-core-processthreads-l1-1-1",
            "api-ms-win-core-profile-l1-1-0",
            "api-ms-win-core-rtlsupport-l1-1-0",
            "api-ms-win-core-string-l1-1-0",
            "api-ms-win-core-synch-l1-1-0",
            "api-ms-win-core-synch-l1-2-0",
            "api-ms-win-core-sysinfo-l1-1-0",
            "api-ms-win-core-timezone-l1-1-0",
            "api-ms-win-core-util-l1-1-0",
            "api-ms-win-crt-conio-l1-1-0",
            "api-ms-win-crt-convert-l1-1-0",
            "api-ms-win-crt-environment-l1-1-0",
            "api-ms-win-crt-filesystem-l1-1-0",
            "api-ms-win-crt-heap-l1-1-0",
            "api-ms-win-crt-locale-l1-1-0",
            "api-ms-win-crt-math-l1-1-0",
            "api-ms-win-crt-multibyte-l1-1-0",
            "api-ms-win-crt-private-l1-1-0",
            "api-ms-win-crt-process-l1-1-0",
            "api-ms-win-crt-runtime-l1-1-0",
            "api-ms-win-crt-stdio-l1-1-0",
            "api-ms-win-crt-string-l1-1-0",
            "api-ms-win-crt-time-l1-1-0",
            "api-ms-win-crt-utility-l1-1-0",
            "ucrtbase",

            // Finally load VS 2017 DLLs in the following order
            "vcruntime140",
            "vcruntime140_1",
            "msvcp140",
            "msvcp140_1",
            "msvcp140_2"
    };

    /**
     * 获取微软库名称
     *
     * @return 结果
     */
    public static List<String> msLibNames() {
        List<String> list = new ArrayList<>();
        for (String s : msLibNames) {
            list.add(s + ".dll");
        }
        return list;
    }
}
