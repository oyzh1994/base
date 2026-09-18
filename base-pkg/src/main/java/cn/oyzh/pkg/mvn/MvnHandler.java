package cn.oyzh.pkg.mvn;

import cn.hutool.core.collection.CollectionUtil;
import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.pkg.PackOrder;
import cn.oyzh.pkg.PreHandler;
import cn.oyzh.pkg.SingleHandler;
import cn.oyzh.pkg.config.PackConfig;
import cn.oyzh.pkg.util.MvnUtil;

import java.io.File;
import java.util.List;


/**
 * @author oyzh
 * @since 2024/6/19
 */
public class MvnHandler implements PreHandler, SingleHandler {

    private int order = PackOrder.ORDER_P9;

    @Override
    public int order() {
        return order;
    }

    @Override
    public void order(int order) {
        this.order = order;
    }

    /**
     * 项目工程
     */
    private final String projectDir;

    /**
     * 依赖工程
     */
    private final List<String> dependencies;

    private boolean executed;

    @Override
    public boolean isExecuted() {
        return executed;
    }

    @Override
    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public MvnHandler(String projectDir, List<String> dependencies) {
        this.dependencies = dependencies;
        this.projectDir = projectDir;
    }

    @Override
    public String name() {
        return "maven处理";
    }

    @Override
    public void handle(PackConfig packConfig) throws Exception {
        if (this.executed) {
            return;
        }
        String mvnExe = MvnUtil.mvnExec();
        if (StringUtil.isBlank(mvnExe)) {
            throw new RuntimeException("maven程序未找到!");
        }
        // 安装依赖工程
        if (CollectionUtil.isNotEmpty(this.dependencies)) {
            for (String dependency : this.dependencies) {
                String[] mvnCommand = new String[]{mvnExe, "-X", "install", "-DskipTests"};
                RuntimeUtil.execForResult(mvnCommand, null, new File(dependency));
            }
        }
        // 打包项目工程
        String[] mvnCommand = new String[]{mvnExe, "-X", "package", "-DskipTests"};
        RuntimeUtil.execForResult(mvnCommand, null, new File(this.projectDir));
        this.executed = true;
    }

}
