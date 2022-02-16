package cn.central.admin.controller;

import cn.central.admin.model.IndexDto;
import cn.central.admin.properties.IndexProperties;
import cn.central.admin.service.IIndexService;

import cn.central.common.model.BasicResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * 索引管理
 */
@Slf4j
@RestController
@Api(tags = "索引管理api")
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    private IIndexService indexService;

    @Autowired
    private IndexProperties indexProperties;

    @PostMapping("/index")
    public BasicResponse createIndex(@RequestBody IndexDto indexDto) throws IOException {
        if (indexDto.getNumberOfShards() == null) {
            indexDto.setNumberOfShards(1);
        }
        if (indexDto.getNumberOfReplicas() == null) {
            indexDto.setNumberOfReplicas(0);
        }
        indexService.create(indexDto);
        return BasicResponse.succeed("操作成功");
    }

    /**
     * 索引列表
     */
    @PreAuthorize("@el.check('sys:indices:view')")
    @PostMapping("/indices")
    public BasicResponse list(@RequestParam(required = false) String queryStr) throws IOException {
        return indexService.list(queryStr, indexProperties.getShow());
    }

    /**
     * 索引明细
     */
    @GetMapping("/index")
    public BasicResponse<Map<String, Object>> showIndex(String indexName) throws IOException {
        Map<String, Object> result = indexService.show(indexName);
        return BasicResponse.succeed(result);
    }

    /**
     * 删除索引
     */
    @DeleteMapping("/index")
    public BasicResponse deleteIndex(String indexName) throws IOException {
        indexService.delete(indexName);
        return BasicResponse.succeed("操作成功");
    }
}
