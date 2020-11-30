package cn.central.search.client.feign;

import cn.central.common.Page.PageResult;
import cn.central.common.constant.ServiceNameConstants;
import cn.central.common.model.Result;
import cn.central.search.client.feign.fallback.SearchServiceFallbackFactory;
import cn.central.search.model.SearchDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



/**
 *
 */
@FeignClient(name = ServiceNameConstants.SEARCH_SERVICE, fallbackFactory = SearchServiceFallbackFactory.class, decode404 = true)
public interface SearchService {
    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     */
    @PostMapping(value = "/search/{indexName}")
    Result strQuery(@PathVariable("indexName") String indexName, @RequestBody SearchDto searchDto);
}
