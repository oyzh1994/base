package cn.oyzh.common.thread;

import lombok.Data;

import java.util.List;

/**
 *
 * @author oyzh
 * @since 2025-02-11
 */
@Data
public class ProcessExecResult {

    private String input;

    private String error;

    private Integer exitCode;

    private boolean timedOut;

}
