package cn.central.search.service;


import cn.central.common.page.PageResult;
import cn.central.search.model.SearchDto;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * @author zlt
 * @date 2019/4/24
 */
public interface ISearchService {
    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException;
}
