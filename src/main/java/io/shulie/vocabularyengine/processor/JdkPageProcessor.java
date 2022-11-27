package io.shulie.vocabularyengine.processor;

import io.shulie.vocabularyengine.common.constant.JdkElementConstants;
import io.shulie.vocabularyengine.pipeline.JdkFilePipeline;
import io.shulie.vocabularyengine.util.FileUtil;
import io.shulie.vocabularyengine.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.shulie.vocabularyengine.common.constant.FileConstants.DOCUMENT_URL_FILE_PATH;
import static io.shulie.vocabularyengine.common.constant.FileConstants.PACKAGE_URL_FILE_PATH;

/**
 * @author chenxingxing
 * @date 2022/11/27 6:30 下午
 */
public class JdkPageProcessor implements PageProcessor {


    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {

        //2.解析包文档的描述、和java文档路径
        List<String> packageDescriptionList = page.getHtml().xpath("//tr/td/code/a/text()|div[@class=\"block\"]/text()|//dt/text()|//dd/text()").all();
        //3.描述存入文件
        Set<String> vocabularySet = new HashSet<>(2 ^ 10);
        packageDescriptionList.forEach(packageDescription -> {
            vocabularySet.addAll(StringUtil.split(packageDescription));
        });
        page.putField("vocabularySet", vocabularySet);
        //4.url存入文件
        List<String> rawDocumentUrlList = page.getHtml().xpath("//tr/td/a/@href").all();
        String pageUrl = page.getUrl().get();
        String [] strings= pageUrl.split("/");
        int lastStringLength = strings[strings.length-1].length();
        String pageUrlPrefix = pageUrl.substring(0,pageUrl.length()-lastStringLength);
        List<String> documentUrlList = StringUtil.listDocumentUrlList(pageUrlPrefix,rawDocumentUrlList);
        page.putField("documentUrlList", documentUrlList);

        //5.读取
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //1.读取文件，解析路径，遍历路径
        List<String> packageUrlList = FileUtil.readUrlFromFile(DOCUMENT_URL_FILE_PATH);
        //2.解析包文档的描述、和java文档路径
        Spider.create(new JdkPageProcessor())
                .addPipeline(new JdkFilePipeline())
                .addUrl(packageUrlList.toArray(new String[0])).thread(5).run();
        //3.将文件存入文件

    }
}
