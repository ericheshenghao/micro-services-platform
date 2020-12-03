package cn.central.controller;


import cn.central.common.Page.PageRequest;
import cn.central.common.Page.PageResult;

import cn.central.common.model.Result;
import cn.central.common.utils.SecurityUtils;
import cn.central.entity.SysClientDetails;
import cn.central.entity.SysDictData;
import cn.central.entity.SysDictType;
import cn.central.service.SysDictDataService;
import cn.central.service.SysDictTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @package:  cn.siques.mangosound.controller
 * @description: 字典类型表
 * @author: Shenghao.He
 * @date: Created in 2020-09-03 11:22:09
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@RestController
@RequestMapping("/pri/sysDictType")
@Api(tags = {"字典类型接口"})
public class SysDictTypeController {

    @Autowired
    SysDictTypeService sysDictTypeService;

    @Autowired
    SysDictDataService sysDictDataService;

    /**
   * 分页查询字典类型表
   * @param page
   * @return
   */
    @PostMapping("findPage")
    @ApiOperation(value = "分页查询字典类型表", notes = "分页查询字典类型表")
    public Result listSysRoleMenu(@RequestBody PageRequest page) {
        Page<SysDictType> detailsPage = new Page<>(page.getPageNum(), page.getPageSize());
        return Result.succeed(sysDictTypeService.page(detailsPage));
    }

    @PostMapping("findKeyByType")
    @ApiOperation(value = "根据字典类型查字典树", notes = "根据字典型查字典树")
    public Result listKeyByType(@RequestBody SysDictType sysDictType) {

        List<SysDictData> list =  sysDictDataService.listKeyByType(sysDictType.getDictType());

        return Result.succeed(list);
    }

    /**
     * 通过id查询字典类型表
     * @param id id
     * @return JsonData
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询字典类型表", notes = "通过id查询字典类型表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    public Result getSysDictType(@PathVariable("id") String id){
      return  Result.succeed(sysDictTypeService.getById(id));
    }

    /**
     * 新增字典类型表
     * @param sysDictType 字典类型表
     * @return JsonData
     */
    @PreAuthorize("@el.check('sys:dict:add')")
    @PostMapping
    @ApiOperation(value = "新增字典类型表", notes = "新增字典类型表")
    public Result saveSysDictType(@RequestBody SysDictType sysDictType){
                sysDictType.setCreateBy(SecurityUtils.getUsername());
      return  Result.succeed(sysDictTypeService.save(sysDictType));
    }

    /**
     * 修改字典类型表
     * @param id id
     * @param sysDictType 字典类型表
     * @return JsonData
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "修改字典类型表", notes = "修改字典类型表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    public Result updateSysDictType(@PathVariable String id, @RequestBody SysDictType sysDictType){
      return  Result.succeed(sysDictTypeService.updateById(sysDictType));
    }

    /**
     * 通过id删除字典类型表
     * @param id id
     * @return JsonData
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除字典类型表", notes = "删除字典类型表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    public Result deleteSysDictType(@PathVariable String id){
      return  Result.succeed(sysDictTypeService.removeById(id));
    }

}
