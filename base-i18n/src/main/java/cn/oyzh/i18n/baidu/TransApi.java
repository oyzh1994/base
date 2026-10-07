//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.oyzh.i18n.baidu;

import cn.oyzh.common.thread.ThreadUtil;
import cn.oyzh.common.util.MD5Util;
import cn.oyzh.common.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 翻译api
 *
 * @author oyzh
 * @since 2025-01-23
 */
public class TransApi {

    /**
     * 百度翻译接口地址
     */
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 应用密钥
     */
    private String securityKey;

    /**
     * 构造翻译接口
     *
     * @param appid       应用ID
     * @param securityKey 应用密钥
     */
    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    /**
     * 执行翻译，遇到访问限制或超时会重试，最多重试30次
     *
     * @param query 待翻译文本
     * @param from  源语言
     * @param to    目标语言
     * @return 翻译结果，重试耗尽仍失败时返回null
     */
    public String trans(String query, String from, String to) {
        int count = 0;
        while (count++ <= 30) {
            String result = this.doTrans(query, from, to);
            if (result == null || (StringUtil.contains(result, "error_msg") && StringUtil.containsAny(result, "Invalid Access Limit", "TIMEOUT"))) {
                ThreadUtil.sleep(500);
                continue;
            }
            return result;
        }
        return null;
    }

    /**
     * 执行一次翻译请求
     *
     * @param query 待翻译文本
     * @param from  源语言
     * @param to    目标语言
     * @return 接口返回的原始结果
     */
    private String doTrans(String query, String from, String to) {
        Map<String, String> params = this.buildParams(query, from, to);
        return HttpGet.get("https://api.fanyi.baidu.com/api/trans/vip/translate", params);
    }

    /**
     * 构建翻译接口的请求参数，包含签名
     *
     * @param query 待翻译文本
     * @param from  源语言
     * @param to    目标语言
     * @return 请求参数
     */
    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", this.appid);
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        String src = this.appid + query + salt + this.securityKey;
        params.put("sign", MD5Util.md5Hex(src));
//        params.put("sign", MD5.md5(src));
        return params;
    }
}
