package io.shulie.vocabularyengine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenxingxing
 * @date 2022/11/27 8:12 下午
 */
public class StringUtil {

    /**
     * 分割句子
     *
     * @param string
     * @return
     */
    public static List<String> split(String string) {
        List<String> vocabularyList = new ArrayList<>(2^8);
        String[] strs = string.split(" ");
        for (String str : strs) {
            Pattern compile = Pattern.compile(".*[0-9].*");
            Matcher matcher = compile.matcher(str);
            if (str.length() > 3 && matcher.matches() == false) {//单词长度大于3且不包含数字
                vocabularyList.add(str.replaceAll("\"", "").replaceAll(",", "").replaceAll("\\.", "").replaceAll(":", "").toLowerCase());
            }
        }
        return vocabularyList;
    }


    /**
     * 获取链接后缀
     * @param packageUrlPrefix
     * @param rawDocumentUrlList
     * @return
     */
    public static List<String> listDocumentUrlList(String packageUrlPrefix,List<String> rawDocumentUrlList) {
        List<String> documentUrlList = new ArrayList<>(2^2);
        rawDocumentUrlList.forEach(rawDocumentUrl->{
            String [] rawDocumentUrlSplit = rawDocumentUrl.split("/");
            documentUrlList.add(packageUrlPrefix+rawDocumentUrlSplit[rawDocumentUrlSplit.length-1]);
        });
        return documentUrlList;
    }


}
