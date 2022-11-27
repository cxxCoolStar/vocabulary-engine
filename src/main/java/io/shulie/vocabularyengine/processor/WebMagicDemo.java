package io.shulie.vocabularyengine.processor;

import io.shulie.vocabularyengine.common.constant.FileConstants;
import io.shulie.vocabularyengine.common.constant.JdkElementConstants;
import io.shulie.vocabularyengine.pipeline.JdkFilePipeline;
import io.shulie.vocabularyengine.util.FileUtil;
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
        page.putField(JdkElementConstants.PACKAGE_URL_LIST_KEY, page.getHtml().xpath("body/div[@class=contentContainer]/table/tbody[2]/tr/td[1]/a/@href").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new WebMagicDemo())
                .addUrl("https://docs.oracle.com/javase/8/docs/api/overview-summary.html")
                .addPipeline(new JdkFilePipeline())
                .thread(5).run();
    }
}
