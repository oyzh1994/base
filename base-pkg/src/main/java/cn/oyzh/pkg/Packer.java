package cn.oyzh.pkg;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.pkg.appImage.AppImageHandler;
import cn.oyzh.pkg.comporess.CompressHandler;
// import cn.oyzh.fx.pkg.comporess.CompressNameHandler;
import cn.oyzh.pkg.comporess.CompressNameHandler;
import cn.oyzh.pkg.config.PackConfig;
import cn.oyzh.pkg.config.PackConfigHandler;
import cn.oyzh.pkg.config.PackConfigParser;
import cn.oyzh.pkg.config.ProjectHandler;
import cn.oyzh.pkg.github.GitHubActionsHandler;
import cn.oyzh.pkg.jar.JarHandler;
import cn.oyzh.pkg.jdeps.JDepsHandler;
import cn.oyzh.pkg.jlink.JLinkHandler;
import cn.oyzh.pkg.jpackage.JPackageHandler;
import cn.oyzh.pkg.jre.JreHandler;
import cn.oyzh.pkg.mvn.MvnHandler;
import cn.oyzh.pkg.pack.DestHandler;
import cn.oyzh.pkg.pack.EndHandler;
import cn.oyzh.pkg.pack.StartHandler;
import cn.oyzh.pkg.woa.WoaHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 打包器，负责注册各类处理器并按顺序执行打包流程
 *
 * @author oyzh
 * @since 2024/6/14
 */
public class Packer {

    /**
     * 处理器
     */
    private final List<Handler> handlers = new ArrayList<>();

    /**
     * 配置解析器
     */
    private final PackConfigParser configParser = new PackConfigParser();

    {
        this.registerEndHandler();
        this.registerJarHandler();
        this.registerJreHandler();
        this.registerDestHandler();
        this.registerJLinkHandler();
        this.registerStartHandler();
        this.registerJdepsHandler();
        this.registerCompressHandler();
        // this.registerAppConfigHandler();
        this.registerJPackageHandler();
        this.registerPackConfigHandler();
        this.registerCompressNameHandler();
    }

    /**
     * 注册目标目录处理器
     */
    public void registerDestHandler() {
        this.registerHandler(new DestHandler());
    }

    /**
     * 注册结束处理器
     */
    public void registerEndHandler() {
        this.registerHandler(new EndHandler());
    }

    /**
     * 注册开始处理器
     */
    public void registerStartHandler() {
        this.registerHandler(new StartHandler());
    }

    // public void registerPackrHandler() {
    //     this.registerHandler(new PackrHandler());
    // }

    /**
     * 注册jpackage处理器
     */
    public void registerJPackageHandler() {
        this.registerHandler(new JPackageHandler());
    }

    // public void registerAppConfigHandler() {
    //     this.registerHandler(new AppConfigHandler());
    // }

    /**
     * 注册jre处理器
     */
    public void registerJreHandler() {
        this.registerHandler(new JreHandler());
    }

    /**
     * 注册jar处理器
     */
    public void registerJarHandler() {
        this.registerHandler(new JarHandler());
    }

    /**
     * 注册maven处理器
     *
     * @param projectDir   项目目录
     * @param dependencies 依赖列表
     */
    public void registerMvnHandler(String projectDir, List<String> dependencies) {
        this.registerHandler(new MvnHandler(projectDir, dependencies));
    }

    /**
     * 注册项目信息处理器
     */
    public void registerProjectHandler() {
        this.registerHandler(new ProjectHandler());
    }

    /**
     * 注册项目信息处理器
     *
     * @param file 项目信息文件
     */
    public void registerProjectHandler(String file) {
        this.registerHandler(new ProjectHandler(file));
    }

    /**
     * 注册jdeps处理器
     */
    public void registerJdepsHandler() {
        this.registerHandler(new JDepsHandler());
    }

    /**
     * 注册github actions处理器
     */
    public void registerGitHubActionsHandler() {
        this.registerHandler(new GitHubActionsHandler());
    }

    /**
     * 注册AppImage处理器
     */
    public void registerAppImageHandler() {
        this.registerHandler(new AppImageHandler());
    }

//    public void registerWoaHandler() {
//        this.registerHandler(new WoaHandler());
//    }

    /**
     * 注册jlink处理器
     */
    public void registerJLinkHandler() {
        this.registerHandler(new JLinkHandler());
    }

    /**
     * 注册打包配置处理器
     */
    public void registerPackConfigHandler() {
        this.registerHandler(new PackConfigHandler());
    }

    /**
     * 注册压缩处理器
     */
    public void registerCompressHandler() {
        this.registerHandler(new CompressHandler());
    }

    /**
     * 注册压缩名称处理器
     */
    public void registerCompressNameHandler() {
        this.registerHandler(new CompressNameHandler());
    }

    /**
     * 注册处理器
     *
     * @param handler 处理器
     */
    public void registerHandler(Handler handler) {
        if (handler != null) {
            if (this.handlers.parallelStream().anyMatch(h -> h.unique() && (h == handler || StringUtil.equals(h.name(), handler.name())))) {
                throw new RuntimeException("处理器:" + handler.name() + "已存在");
            }
            this.handlers.add(handler);
            this.handlers.sort((o1, o2) -> Integer.compare(o2.order(), o1.order()));
        }
    }

    /**
     * 获取前置处理器
     *
     * @return 前置处理器列表
     */
    public List<PreHandler> preHandlers() {
        List<PreHandler> list = new ArrayList<>();
        for (Handler handler : this.handlers) {
            if (handler instanceof PreHandler preHandler) {
                list.add(preHandler);
            }
        }
        return list;
    }

    /**
     * 获取后置处理器
     *
     * @return 后置处理器列表
     */
    public List<PostHandler> postHandlers() {
        List<PostHandler> list = new ArrayList<>();
        for (Handler handler : this.handlers) {
            if (handler instanceof PostHandler postHandler) {
                list.add(postHandler);
            }
        }
        return list;
    }

    /**
     * 获取打包处理器
     *
     * @return 打包处理器列表
     */
    public List<PackHandler> packHandlers() {
        List<PackHandler> list = new ArrayList<>();
        for (Handler handler : this.handlers) {
            if (handler instanceof PackHandler packHandler) {
                list.add(packHandler);
            }
        }
        return list;
    }

    /**
     * 执行打包
     *
     * @param configFile 打包配置
     * @throws Exception 异常
     */
    public void pack(String configFile) throws Exception {
        this.pack(configFile, null, null);
    }

    /**
     * 执行打包
     *
     * @param configFile 打包配置
     * @param properties 属性
     * @throws Exception 异常
     */
    public void pack(String configFile, Map<String, Object> properties) throws Exception {
        this.pack(configFile, null, properties);
    }

    /**
     * 执行打包
     *
     * @param configFile         打包配置
     * @param platformConfigFile 平台打包配置
     * @param properties         属性
     * @throws Exception 异常
     */
    public void pack(String configFile, String platformConfigFile, Map<String, Object> properties) throws Exception {
        // 解析配置
        PackConfig packConfig = this.configParser.parse(configFile);
        // 平台配置
        if (platformConfigFile != null) {
            PackConfig platformPackConfig = this.configParser.parse(platformConfigFile);
            packConfig.marge(platformPackConfig);
        }
        if (CollectionUtil.isNotEmpty(properties)) {
            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                packConfig.putProperty(entry.getKey(), entry.getValue());
            }
        }
        // if (packConfig.isParkByPackr()) {
        //     this.registerPackrHandler();
        // } else {
        //            this.registerJPackageHandler();
        // }
        // AppImage
        if (StringUtil.isNotBlank(packConfig.getAppImageRuntime())) {
            this.registerAppImageHandler();
        }
        for (PreHandler preHandler : this.preHandlers()) {
            this.doHandle(preHandler, packConfig);
        }
        for (PackHandler packHandler : this.packHandlers()) {
            this.doHandle(packHandler, packConfig);
        }
        for (PostHandler postHandler : this.postHandlers()) {
            this.doHandle(postHandler, packConfig);
        }
    }

    /**
     * 执行业务
     *
     * @param handler    处理器
     * @param packConfig 打包配置
     * @throws Exception 异常
     */
    private void doHandle(Handler handler, PackConfig packConfig) throws Exception {
        long start = System.currentTimeMillis();
        JulLog.info("开始执行任务-{}", handler.name());
        handler.handle(packConfig);
        long end = System.currentTimeMillis();
        JulLog.info("任务执行结束-{}, 耗时:{}毫秒", handler.name(), (end - start));
    }

    /**
     * 配置github actions
     *
     * @param properties 属性
     */
    public void steupGitHub(Map<String, Object> properties) {
        // github dest设置
        String githubPath = properties.get(PackCost.PROJECT_PATH) + "/dist/";
        properties.put(PackCost.GITHUB_DIST, githubPath);
        // 覆盖dest设置
        String targetDestPath = properties.get(PackCost.PROJECT_PATH) + "/target/dist/";
        properties.put(PackCost.DEST, targetDestPath);
        // 注册处理器
        this.registerGitHubActionsHandler();
    }
}
