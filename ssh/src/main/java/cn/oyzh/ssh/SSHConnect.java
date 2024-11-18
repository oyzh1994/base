package cn.oyzh.ssh;

import cn.oyzh.store.jdbc.Column;
import lombok.Data;

/**
 * ssh连接信息
 *
 * @author oyzh
 * @since 2023/12/15
 */
@Data
public class SSHConnect {

    /**
     * 连接端口，默认22
     */
    @Column
    private int port = 22;

    /**
     * 连接地址
     */
    @Column
    private String host;

    /**
     * ssh用户名
     */
    @Column
    private String user;

    /**
     * ssh密码
     */
    @Column
    private String password;

    /**
     * 连接超时
     */
    @Column
    private int timeout = 5000;
}
