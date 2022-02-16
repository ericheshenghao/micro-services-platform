package cn.central.search.client.feign.fallback;


import cn.central.common.model.BasicResponse;
import cn.central.search.client.feign.SearchService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;


/**
 * 降级工厂
 *
 * @author : heshenghao
 * @date : 10:37 2020/11/19
 */
@Slf4j
public class SearchServiceFallbackFactory implements FallbackFactory<SearchService> {


    @Override
    public SearchService create(Throwable throwable) {
        return (indexName, searchDto) -> {
            log.error("通过索引{}搜索异常:{}", indexName, throwable);
            return BasicResponse.failed("通过索引搜索异常");
        };
    }

}
