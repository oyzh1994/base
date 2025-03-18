package cn.oyzh.common.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oyzh
 * @since 2025-02-12
 */
public class DockerTest {

    private final String csdnUrl = "https://blog.csdn.net/u014390502/article/details/143472743";

    @Test
    public void checkLink() throws IOException, InterruptedException {
        Connection connection = Jsoup.connect(csdnUrl);
        Document document = connection.get();
        Element element = document.getElementById("content_views");
        Elements tables = element.getElementsByTag("table");
        Element tbody = tables.getFirst().getElementsByTag("tbody").getFirst();
        Elements codes = tbody.getElementsByTag("code");
        List<String> urls = new ArrayList<>();
        int size = codes.size();
        int count = 0;
        for (Element element1 : codes) {
            System.out.println(element1.text());
            String text = element1.text();
            if (text.contains(".")) {
                String url1 = "http://" + text;
                try {
                    HttpRequest request1 = HttpUtil.createGet(url1);
                    request1.timeout(1500);
                    HttpResponse response1 = request1.execute();
                    response1.close();
                    if (response1.contentLength() > 0) {
                        System.out.println("地址:" + url1 + "可用");
                        urls.add(url1);
                    } else {
                        System.err.println("地址:" + url1 + "不可用");
                    }
                } catch (Exception ex) {
                    System.err.println("地址:" + url1 + "不可用");
                }
                String url2 = "https://" + text;
                try {
                    HttpRequest request2 = HttpUtil.createGet(url1);
                    request2.timeout(3000);
                    HttpResponse response2 = request2.execute();
                    response2.close();
                    if (response2.contentLength() > 0) {
                        urls.add(url2);
                        System.out.println("地址:" + url2 + "可用");
                    } else {
                        System.err.println("地址:" + url2 + "不可用");
                    }
                } catch (Exception ex) {
                    System.err.println("地址:" + url2 + "不可用");
                }

                System.out.println("已完成:" + (++count) + " 总数:" + size);
            }
        }
        System.out.println(urls);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            builder.append("    \"").append(url).append("\"");
            if (i != urls.size() - 1) {
                builder.append(",\n");
            }
        }
        System.out.println(builder);
        // System.out.println(tbody.text());
    }
}
