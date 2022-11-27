package io.shulie.vocabularyengine;

import io.shulie.vocabularyengine.datafilter.VocabularyFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class VocabularyEngineApplicationTests {

    @Resource
    VocabularyFilter vocabularyFilter;

    @Test
    void contextLoads() {
    }


    @Test
    void bloomFilterTest() {
        vocabularyFilter.addVocabulary("hello");
        System.out.println(vocabularyFilter.isExist("world"));
    }


    @Test
    void fileTest() {
        vocabularyFilter.addVocabulary("hello");
        System.out.println(vocabularyFilter.isExist("world"));
    }

}
