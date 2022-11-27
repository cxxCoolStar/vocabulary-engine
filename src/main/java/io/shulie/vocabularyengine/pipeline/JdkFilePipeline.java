package io.shulie.vocabularyengine.pipeline;

import io.shulie.vocabularyengine.common.constant.FileConstants;
import io.shulie.vocabularyengine.common.constant.JdkElementConstants;
import io.shulie.vocabularyengine.datafilter.VocabularyFilter;
import io.shulie.vocabularyengine.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenxingxing
 * @date 2022/11/27 7:03 下午
 */
public class JdkFilePipeline implements Pipeline {

    @Resource
    VocabularyFilter vocabularyFilter;

    @Override
    public void process(ResultItems resultItems, Task task) {

        Set<String> vocabularySet = resultItems.get("vocabularySet");
        StringBuilder vocabularyBuffer = new StringBuilder();
        vocabularySet.forEach(vocabulary -> {
            //如果不存在
            vocabularyBuffer.append(vocabulary).append(" ");
        });
        FileUtil.writeStringToFile(vocabularyBuffer.toString(), FileConstants.VOCABULARY_FILE_PATH);


        List<String> documentUrlList = resultItems.get("documentUrlList");
        StringBuilder documentUrlBuffer = new StringBuilder();
        documentUrlList.forEach(documentUrl -> {
            //如果不存在
            documentUrlBuffer.append(documentUrl).append("\r\n");
        });
        FileUtil.writeStringToFile(documentUrlBuffer.toString(),FileConstants.DOCUMENT_URL_FILE_PATH);
        //FileUtil.writeUrlToFile(resultItems.get(JdkElementConstants.PACKAGE_URL_LIST_KEY),FileConstants.DOCUMENT_URL_FILE_PATH);
    }
}
