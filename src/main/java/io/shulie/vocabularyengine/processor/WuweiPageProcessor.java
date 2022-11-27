package io.shulie.vocabularyengine.processor;

import io.shulie.vocabularyengine.common.constant.FileConstants;
import io.shulie.vocabularyengine.util.FileUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WuweiPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        TreeSet<String> set = new TreeSet<>();
        List<String> all = page.getHtml().xpath("//tr/td/code/a/text()|div[@class=\"block\"]/text()|//dt/text()|//dd/text()").all();
        for(String item : all){
            String[] strS = item.split(" ");
            for(String str:strS){
                Pattern compile = Pattern.compile(".*[0-9].*");
                Matcher matcher = compile.matcher(str);
                if(str.length() > 3 && matcher.matches() == false){//单词长度大于3且不包含数字
                  set.add(str.replaceAll("\"","").replaceAll(",","").replaceAll("\\.","").replaceAll(":","").toLowerCase());
                }
            }
        }

//        for (String s : set){
//            System.out.println(s);
//        }
        System.out.println(set);

        page.putField("word", set);

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new WuweiPageProcessor()).addUrl(FileUtil.readUrlFromFile(FileConstants.DOCUMENT_URL_FILE_PATH).toArray(new String[0])).addPipeline(new FilePipeline("D:\\webmagic\\")).thread(300).run();
    }
}