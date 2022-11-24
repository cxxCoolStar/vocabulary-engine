package io.shulie.vocabularyengine.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class BaiduPageProcessor implements PageProcessor {

    private Site site = Site.me();
//            .setRetryTimes(3)
//            .setSleepTime(1000);
//            .addHeader("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Mobile Safari/537.36")
//            .addHeader("cookie", "_octo=GH1.1.1167904857.1667555432; _device_id=5e59853c7ca52c5d965a9deb7f4d4919; user_session=z7s5pOKnqlD9Pbfxr6pvYEkx1w9fSNK3f2pNE70r5upSxo6a; __Host-user_session_same_site=z7s5pOKnqlD9Pbfxr6pvYEkx1w9fSNK3f2pNE70r5upSxo6a; logged_in=yes; dotcom_user=cxxCoolStar; has_recent_activity=1; color_mode=%7B%22color_mode%22%3A%22auto%22%2C%22light_theme%22%3A%7B%22name%22%3A%22light%22%2C%22color_mode%22%3A%22light%22%7D%2C%22dark_theme%22%3A%7B%22name%22%3A%22dark%22%2C%22color_mode%22%3A%22dark%22%7D%7D; preferred_color_mode=light; tz=Asia%2FShanghai; _gh_sess=BLVpc0k9VYEItggIJEi57YUekmsGySJ4mhwl3xESR2yaNQnYEr%2FNPEaO9wLJAFM%2FtpvVeA4qpFBu%2FKgRXFq5ugcpa4yb73EU6HymhPrRUrqsru9OXcNfsCMVFvF%2BgOH8jKVAw%2BlDA2va1f2E%2FksRFODD9GYOglwLQveyuOHv78BJAs10qO03cQIRe2MkEJ09suS5Ezsx1T1zZC3TMegxu%2Fh%2FPqr1xyQY32VN89QQfWoL0lsR%2BIQZU5pC6h9oGgh4YF26H%2BbkkIzVppm8zdJgo3hS17mZedGdEbZ2lAx8NJBwyYJzHTLw69sF2CHPDqUmD2KTj%2BVvOe%2FRtUOtHxXmksumnUEr3m5c--7uLWpf9sXq%2FSkAfN--IjbunZZkIO3vFPF9LpAjhQ%3D%3D");

    @Override
    public void process(Page page) {
        //page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.putField("text", page.getHtml().xpath("//div[@class='para']/text()").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BaiduPageProcessor())
                .addUrl("https://baike.baidu.com/item/%E7%99%BE%E5%BA%A6/6699?fr=aladdin")
                .addPipeline(new FilePipeline("/Users/sulei/Downloads"))
                .thread(5).run();
    }
}