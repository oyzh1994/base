package cn.oyzh.common.test;

import cn.oyzh.common.file.FileUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oyzh
 * @since 2025-09-18
 */
public class Tsxk2Capturer {

    private final String baseUrl = "http://www.xxbookben.net";

    private final String fileDir = "/Users/oyzh/Desktop/tsxk";

    private List<String> getUrls() throws IOException {
        Connection c = Jsoup.connect(baseUrl + "/txt/131043.html");
        Document document = c.get();
        Elements elements = document.getElementsByTag("a");
        List<String> urls = new ArrayList<>();
        for (Element element : elements) {
            String rel = element.attr("rel");
            if (rel.isEmpty()) {
                continue;
            }
            String url = element.attr("href");
            if (!url.startsWith("/read/")) {
                continue;
            }
            if (urls.contains(url)) {
                continue;
            }
            System.out.println(url);
            urls.add(url);
        }
        urls.sort(String::compareTo);
        return urls;
    }

    private void downloadHtml(String url) throws IOException {
        int index = 0;
        do {
            if (++index >= 3) {
                break;
            }
            String fileName = url.substring(url.lastIndexOf("/")) + "_" + index + ".txt";
            File file = new File(fileDir, fileName);
            if (file.exists()) {
                continue;
            }
            String reqUrl = baseUrl + url.replace(".html", "_" + index + ".html");
            Connection c = Jsoup.connect(reqUrl);
            Document document = c.get();
            Element booktxt = document.getElementById("booktxt");
            if (booktxt == null) {
                System.out.println("未找到标签");
                break;
            }
            Elements pList = booktxt.getElementsByTag("p");
            if (pList.isEmpty()) {
                System.out.println("未找到内容");
                break;
            }
            StringBuilder builder = new StringBuilder();
            for (Element p : pList) {
                builder.append(p.text()).append("\n");
            }
            Elements booknames = document.getElementsByClass("bookname");
            if (booknames.isEmpty()) {
                break;
            }
            Element bookname = booknames.getFirst();
            String booknameText = bookname.text();
            if (!booknameText.contains("/")) {
                System.out.println("未找到章节号");
                break;
            }
            String title = booknameText.substring(0, booknameText.lastIndexOf("（"));
            booknameText = booknameText.substring(booknameText.lastIndexOf("（") + 1, booknameText.lastIndexOf("）"));
            System.out.println(booknameText);
            String[] arr = booknameText.split("/");
            if (arr.length == 1) {
                break;
            }
            String text = builder.toString();
            if (index == 2) {
                text = title + "\n" + text;
            }
            // System.out.println(text);
            FileUtil.writeUtf8String(text, file);
            System.out.println("已写入内容:" + reqUrl);
            String start = arr[0].trim();
            String end = arr[1].trim();
            if (Integer.parseInt(start) == Integer.parseInt(end)) {
                System.out.println("本章已经到末尾:" + reqUrl);
                break;
            }

        } while (true);
    }

    private void doMerge() throws Exception {

        String fileName = "/Users/oyzh/Desktop/吞噬星空2起源大陆.txt";

        List<File> files =new ArrayList<>();
        FileUtil.getAllFiles(fileDir,file -> {
            if(file.getName().endsWith(".txt")){
                files.add(file);
            }
        });
        files.sort((o1, o2) -> {
            String fName11 = o1.getName().split("_")[0];
            String fName21 = o2.getName().split("_")[0];
            int compare1 = fName11.compareTo(fName21);
            if (compare1 == 0) {
                String fName12 = o1.getName().split("_")[1];
                String fName22 = o2.getName().split("_")[1];
                return fName12.compareTo(fName22);
            }
            return compare1;
        });

        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            sb.append(FileUtil.readUtf8String(file));
        }
        FileUtil.writeUtf8String(sb.toString(), fileName);

        System.out.println("文件合并完成");
    }

    @Test
    public void capture() throws Exception {
        List<String> urls = getUrls();
        System.out.println(urls);
        int index = 1;
        for (String url : urls) {
            this.downloadHtml(url);
            System.out.println("已爬取:" + index++ + "章");
        }
        this.doMerge();
    }
}
