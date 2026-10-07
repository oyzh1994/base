package cn.oyzh.pkg.comporess;

import cn.oyzh.common.compress.CompressUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.pkg.PackOrder;
import cn.oyzh.pkg.PostHandler;
import cn.oyzh.pkg.config.PackConfig;

import java.io.File;

/**
 * 压缩处理器
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class CompressHandler implements PostHandler {

    private int order = PackOrder.ORDER_M6;

    @Override
    public int order() {
        return order;
    }

    @Override
    public void order(int order) {
        this.order = order;
    }

    @Override
    public void handle(PackConfig packConfig) throws Exception {
        CompressConfig compressConfig = packConfig.getCompressConfig();
        if (compressConfig == null) {
            return;
        }
        if (StringUtil.isNotBlank(compressConfig.getType())) {
            String compressName = compressConfig.getName();
            if (StringUtil.isBlank(compressName)) {
                throw new Exception("compressName为空！");
            }
            String dest = packConfig.getDest();
            File compressFile = switch (compressConfig.getType().toLowerCase()) {
                case "zip" -> CompressUtil.zipDest(compressName, dest);
                case "tar" -> CompressUtil.tarDest(compressName, dest);
                case "tar.gz", "tgz" -> CompressUtil.tgzDest(compressName, dest);
                default ->
                        throw new IllegalStateException("Unexpected value: " + compressConfig.getType().toLowerCase());
            };
            packConfig.setCompressFile(compressFile);
            // 设置为临时文件路径
            packConfig.addTempFile(packConfig.getDest());
        }
    }

    @Override
    public String name() {
        return "压缩处理";
    }
}
