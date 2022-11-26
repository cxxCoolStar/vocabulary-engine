package io.shulie.vocabularyengine.processor;

import io.shulie.vocabularyengine.downloader.NullDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class BaiduPageProcessor implements PageProcessor {

    // mysql redis spring google
    private Site site = Site.me()
//            .setRetryTimes(3)
//            .setSleepTime(1000);
            .setUserAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Mobile Safari/537.36");
    @Override
    public void process(Page page) {
        //page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.putField("text", page.getHtml().xpath("//div[@class='block']").toString());
    }


    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BaiduPageProcessor())
                .addUrl("https://docs.oracle.com/javase/8/docs/api/overview-summary.html")
                .thread(5).run();
    }
}