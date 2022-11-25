package io.shulie.vocabularyengine.datafilter;

/**
 * @author chenxingxing
 * @date 2022/11/25 11:43 上午
 */
/**
 * 数据过滤顶层接口
 */
public interface VocabularyFilter{

    /**
     * 添加词汇
     */
    void addVocabulary(String vocabulary);

    /**
     * 查询数据是否存在
     */
    boolean isExist(String vocabulary);
}
