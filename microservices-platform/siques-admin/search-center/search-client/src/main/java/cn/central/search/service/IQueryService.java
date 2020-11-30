package cn.central.search.service;

import cn.central.common.Page.PageResult;
import cn.central.common.model.Result;
import cn.central.search.model.LogicDelDto;
import cn.central.search.model.SearchDto;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

/**
 * @author : heshenghao
 * @date : 10:47 2020/11/19
 */
public interface IQueryService {
    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     */
    Result strQuery(String indexName, SearchDto searchDto);

    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @param logicDelDto 逻辑删除Dto
     */
    Result strQuery(String indexName, SearchDto searchDto, LogicDelDto logicDelDto);

    /**
     * 访问统计聚合查询
     * @param indexName 索引名
     * @param routing es的路由
     */
    Map<String, Object> requestStatAgg(String indexName, String routing);
}
