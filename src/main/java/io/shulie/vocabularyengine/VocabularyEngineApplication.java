package io.shulie.vocabularyengine;

import io.shulie.vocabularyengine.pipeline.JdkFilePipeline;
import io.shulie.vocabularyengine.processor.JdkPageProcessor;
import io.shulie.vocabularyengine.util.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import us.codecraft.webmagic.Spider;

import java.util.List;

import static io.shulie.vocabularyengine.common.constant.FileConstants.PACKAGE_URL_FILE_PATH;

@SpringBootApplication
public class VocabularyEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VocabularyEngineApplication.class, args);

        //1.读取文件，解析路径，遍历路径
        List<String> packageUrlList = FileUtil.readUrlFromFile(PACKAGE_URL_FILE_PATH);
        //2.解析包文档的描述、和java文档路径
        Spider.create(new JdkPageProcessor())
                .addPipeline(new JdkFilePipeline())
                .addUrl(packageUrlList.toArray(new String[0])).thread(5).run();
    }

}
