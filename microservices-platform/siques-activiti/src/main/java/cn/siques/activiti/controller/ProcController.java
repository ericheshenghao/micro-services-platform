package cn.siques.activiti.controller;

import cn.central.common.annotation.CheckRequestBody;
import cn.central.common.context.TenantContextHolder;
import cn.central.common.model.Result;
import cn.central.common.page.PageRequest;
import cn.siques.activiti.entity.ProcInstance;
import cn.siques.activiti.entity.ProcTaskInstance;
import cn.siques.activiti.service.AuditService;
import cn.siques.activiti.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author: 何胜豪
 * @Title: TODO
 * @Package: cn.siques.activiti.Controller
 * @Description:
 * @date : 2021/6/25 18:14
 */
@RestController
public class ProcController {

    @Autowired
    AuditService auditService;

     @Autowired
    ProcessService processService;
    /**
     * 查询申请列表
     * @return
     * 我发起的，待审批
     */
    @PostMapping("/instance")
    @CheckRequestBody
    public Result instanceList(@Valid  @RequestBody PageRequest<ProcInstance> req){
       Result result = auditService.getInstanceList(req.getParams(),req.getPageNum(),req.getPageSize());
    return result;
    }

    /**
     * 查询申请的详情数据
     * @param id
     * @return
     */
    @GetMapping("/instance/{id}")
    public Result definitionList(@PathVariable String id){
       ProcInstance instanceDetail = auditService.findInstanceDetail(id);
        return Result.succeed(instanceDetail);
    }

    /**
     * 流程申请
     */
    @PostMapping("/start")
    public Result start(@RequestBody Map map){
        auditService.start(map);
        return Result.succeed("");
    }


    /**
     * handleType
     * @param instance
     * @return
     */
    @PostMapping("/instance/commit")
    public Result commit(@RequestBody ProcTaskInstance instance){
        String tenant = TenantContextHolder.getTenant();
        auditService.commit(instance,tenant);
        return Result.succeed("");
    }


}
