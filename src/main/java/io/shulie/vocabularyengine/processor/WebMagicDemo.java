package io.shulie.vocabularyengine.processor;

import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebMagicDemo implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        FileOutputStream fileOutputStream = null;

        Selectable selectable = page.getHtml().xpath("body/div[@class=contentContainer]/table/tbody[2]/tr/td[1]/a/@href");
        List<String> all = selectable.all();
        List<String> urlList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(all)) {
            for (String url : all) {
                urlList.add("https://docs.oracle.com/javase/8/docs/api/" + url);
            }
        }
        if (!CollectionUtils.isEmpty(urlList)) {
            String toString = urlList.toString();
            toString = toString.substring(1, toString.length() - 1);
            String[] split = toString.split(", ");
            try {for (String s : split) {
                    s += "\r\n";
                    byte[] bytes = s.getBytes();
                    fileOutputStream = new FileOutputStream("..\\vocabulary-engine\\doc\\jdk\\package_url.txt", true);
                    fileOutputStream.write(bytes);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new WebMagicDemo()).addUrl("https://docs.oracle.com/javase/8/docs/api/overview-summary.html").thread(5).run();
    }
}
