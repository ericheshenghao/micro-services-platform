package cn.central.controller;

import cn.central.common.Page.PageRequest;
import cn.central.common.model.Result;
import cn.central.entity.SysLogger;
import cn.central.service.SysLoggerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 
 * </p>
 *
 * @package:   cn.central.controller
 * @description: 
 * @author: Shenghao.He
 * @date: Created in 2020-11-18 22:12:02
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pri/sysLogger")
@Api(tags = {"日志接口"})
public class SysLoggerController {

    @Autowired
    SysLoggerService sysLoggerService;


    /**
   * 分页查询
   * @param page
   * @return
   */
    @PostMapping("findPage")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PreAuthorize("hasAuthority('sys:logger:view')")
    public Result<Page<SysLogger>> listSysRoleMenu(@RequestBody PageRequest page) {
        Page<SysLogger> detailsPage = new Page<>(page.getPageNum(), page.getPageSize());
        return Result.succeed(sysLoggerService.page(detailsPage));
    }


    /**
     * 通过id查询
     * @param id id
     * @return JsonData
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @PreAuthorize("@el.check('sys:logger:view')")
    public Result getSysLogger(@PathVariable("id") Integer id){
      return  Result.succeed(sysLoggerService.getById(id));
    }

    /**
     * 新增
     * @param sysLogger 
     * @return JsonData
     */
    @PostMapping
    @ApiOperation(value = "新增", notes = "新增")
    @PreAuthorize("@el.check('sys:logger:add')")
    public Result saveSysLogger(@RequestBody SysLogger sysLogger){
      return  Result.succeed(sysLoggerService.save(sysLogger));
    }

    /**
     * 更新
     * @param id id
     * @param sysLogger 
     * @return JsonData
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @PreAuthorize("@el.check('sys:logger:edit')")
    public Result updateSysLogger(@PathVariable Integer id, @RequestBody SysLogger sysLogger){
      return  Result.succeed(sysLoggerService.updateById(sysLogger));
    }

    /**
     * 通过id删除
     * @param id id
     * @return JsonData
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @PreAuthorize("@el.check('sys:logger:delete')")
    public Result deleteSysLogger(@PathVariable Integer id){
      return  Result.succeed(sysLoggerService.removeById(id));
    }

}
