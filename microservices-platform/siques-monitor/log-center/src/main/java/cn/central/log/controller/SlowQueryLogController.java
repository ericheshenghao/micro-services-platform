package cn.central.log.controller;


import cn.central.common.model.BasicResponse;
import cn.central.search.model.SearchDto;
import cn.central.search.service.IQueryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 慢查询日志
 *
 * @author zlt
 */
@RestController
public class SlowQueryLogController {
    @Autowired
    private IQueryService queryService;

    @ApiOperation(value = "慢sql日志全文搜索列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页起始位置", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "分页结束位置", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "queryStr", value = "搜索关键字", dataType = "String")
    })
    @GetMapping(value = "/slowQueryLog")
    public BasicResponse getPage(SearchDto searchDto) {
        searchDto.setIsHighlighter(true);
        searchDto.setSortCol("timestamp");
        return queryService.strQuery("mysql-slowlog-*", searchDto);
    }
}
