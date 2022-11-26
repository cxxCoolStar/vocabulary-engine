package io.shulie.vocabularyengine.downloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;

/**
 * @author chenxingxing
 * @date 2022/11/26 6:25 下午
 */
public class NullDownloader implements Downloader {
    @Override
    public Page download(Request request, Task task) {
        return null;
    }

    @Override
    public void setThread(int i) {

    }
}
