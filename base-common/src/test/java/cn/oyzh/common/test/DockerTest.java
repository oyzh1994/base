package cn.oyzh.common.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 抓取 CSDN 文章中列举的镜像地址并逐个校验其可用性。
 *
 * @author oyzh
 * @since 2025-02-20
 */
public class DockerTest {

    private final String csdnUrl = "https://blog.csdn.net/u014390502/article/details/143472743";

    @Test
    public void checkLink() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(1))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest pageRequest = HttpRequest.newBuilder(URI.create(csdnUrl))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        HttpResponse<String> pageResponse = client.send(pageRequest,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        Document document = Jsoup.parse(pageResponse.body());
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
                    if (isAvailable(client, url1)) {
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
                    if (isAvailable(client, url2)) {
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

    private boolean isAvailable(HttpClient client, String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(1))
                .GET()
                .build();
        HttpResponse<InputStream> response = client.send(request,
                HttpResponse.BodyHandlers.ofInputStream());
        try (InputStream body = response.body()) {
            return response.statusCode() < 400 && body.read() != -1;
        }
    }
}
