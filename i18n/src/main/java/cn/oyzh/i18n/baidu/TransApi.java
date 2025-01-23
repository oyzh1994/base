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

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String trans(String query, String from, String to) {
        int count = 0;
        while (count++ < 30) {
            String result = this.doTrans(query, from, to);
            if (result == null || (StringUtil.contains(result, "error_msg") && StringUtil.containsAny("Invalid Access Limit", "TIMEOUT"))) {
                ThreadUtil.sleep(500);
                continue;
            }
            return result;
        }
        return null;
    }

    private String doTrans(String query, String from, String to) {
        Map<String, String> params = this.buildParams(query, from, to);
        return HttpGet.get("https://api.fanyi.baidu.com/api/trans/vip/translate", params);
    }

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
