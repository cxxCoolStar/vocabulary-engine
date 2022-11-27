package io.shulie.vocabularyengine.util;

import io.shulie.vocabularyengine.common.constant.FileConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.selector.Selectable;

import java.io.*;
import java.util.*;

import static io.shulie.vocabularyengine.common.constant.FileConstants.PACKAGE_URL_FILE_PATH;

/**
 * @author chenxingxing
 * @date 2022/11/27 5:50 下午
 */
@Slf4j
public class FileUtil {

    /**
     * 私有化工具类
     */
    private FileUtil() {

    }

    /**
     * 从文件中读取包url
     *
     * @return 包url
     */
    public static List<String> readUrlFromFile(String filePath) {

        List<String> packageUrlList = new ArrayList<>();

        try (Scanner sc = new Scanner(new FileReader(filePath))) {
            while (sc.hasNextLine()) {  //按行读取字符串
                final String packageUrl = sc.nextLine();
                packageUrlList.add(packageUrl);
            }
        } catch (FileNotFoundException e) {
            log.error("没有找到文件路径,路径名:{}", filePath);
            e.printStackTrace();
        }
        return packageUrlList;
    }


    /**
     * 将词汇保存到文件中
     */

    public static void writeStringToFile(String vocabularyString, String filePath) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath,true);
            fw.write(vocabularyString);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 将包url保存到文件
     * @param packageUrlList 包url
     */
    public static void writeUrlToFile(List<String> packageUrlList,String filePath) {
        FileOutputStream fileOutputStream = null;

        if (!CollectionUtils.isEmpty(packageUrlList)) {
            String toString = packageUrlList.toString();
            toString = toString.substring(1, toString.length() - 1);
            String[] split = toString.split(", ");
            try {
                for (String s : split) {
                    s += "\r\n";
                    byte[] bytes = s.getBytes();
                    fileOutputStream = new FileOutputStream(filePath,true);
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

    /**
     * 文件去重
     */
    public static void deduplication() {
        Set<String> vocabularySet = new HashSet<>(readUrlFromFile(FileConstants.VOCABULARY_FILE_PATH));

        writeUrlToFile(new ArrayList<>(vocabularySet),FileConstants.VOCABULARY_NEW_FILE_PATH);

    }

    public static void main(String[] args) {
        deduplication();
    }

}
