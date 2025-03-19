package cn.oyzh.common.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成工具
 *
 * @author oyzh
 * @since 2024-11-08
 */
//@UtilityClass
public class QRCodeUtil {

    /**
     * 生成二维码
     *
     * @param content 源内宿
     * @param charset 生成二维码保存的路径
     * @param imgW    图片宽
     * @param imgH    图片高
     * @return 返回二维码图片
     * @throws Exception 异常
     */
    public static BufferedImage createImage(String content, String charset, int imgW, int imgH) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.CHARACTER_SET, charset);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.QR_VERSION, Version.getVersionForNumber(40));
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, imgW, imgH, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 在生成的二维码中插入图片
     *
     * @param source       源文件
     * @param imgPath      文件路径
     * @param imgW         logo宽
     * @param imgH         logo高
     * @param needCompress 是否需要压缩
     * @throws Exception 异常
     */
    public static void insertImage(BufferedImage source, File imgPath, int imgW, int imgH, boolean needCompress) throws Exception {
        Image src = ImageIO.read(imgPath);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > imgW) {
                width = imgW;
            }
            if (height > imgH) {
                height = imgH;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的囿
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int sourceWidth = source.getWidth(null);
        int sourceHeight = source.getHeight(null);
        int x = (sourceWidth - width) / 2;
        int y = (sourceHeight - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
}
