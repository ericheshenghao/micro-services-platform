package cn.central.log.controller;

import cn.central.common.model.Result;
import cn.central.search.model.SearchDto;
import cn.central.search.service.IQueryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审计日志
 *
 * @author
 * @date 2020/2/4
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@RestController
public class AuditLogController {
    private final IQueryService queryService;

    public AuditLogController(IQueryService queryService) {
        this.queryService = queryService;
    }

    @ApiOperation(value = "审计日志全文搜索列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页起始位置", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "分页结束位置", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "queryStr", value = "搜索关键字", dataType = "String")
    })
    @GetMapping(value = "/auditLog")
    public Result getPage(SearchDto searchDto) {
        searchDto.setIsHighlighter(true);
        searchDto.setSortCol("timestamp");
        return queryService.strQuery("audit-log-*", searchDto);
    }
}
