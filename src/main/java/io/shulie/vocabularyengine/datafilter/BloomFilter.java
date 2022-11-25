package io.shulie.vocabularyengine.datafilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


import static io.shulie.vocabularyengine.common.constant.CacheConstants.BLOOM_FILTER_VOCABULARY_LIST;

/**
 * @author chenxingxing
 * @date 2022/11/25 11:42 上午
 */
@Component
public class BloomFilter implements VocabularyFilter {

    @Resource
    private RedissonClient redisson;

    @Override
    public void addVocabulary(String vocabulary) {

        RBloomFilter<Object> bloomFilter = redisson.getBloomFilter(BLOOM_FILTER_VOCABULARY_LIST);
        //初始化布隆过滤器(数据量，误差率)
        bloomFilter.tryInit(1000000L, 0.02);
        //往过滤器中加入数据
        bloomFilter.add(vocabulary);

    }

    @Override
    public boolean isExist(String vocabulary) {
        //获取布隆过滤器
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter(BLOOM_FILTER_VOCABULARY_LIST);
        //判断数据是否在过滤器中
        return bloomFilter.contains(vocabulary);
    }
}
