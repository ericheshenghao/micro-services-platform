package cn.central.controller;

import cn.central.common.Page.PageRequest;

import cn.central.common.model.Result;
import cn.central.entity.SysClientDetails;
import cn.central.entity.SysDictData;
import cn.central.service.SysDictDataService;
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
 * 字典数据表
 * </p>
 *
 * @package:  cn.siques.mangosound.controller
 * @description: 字典数据表
 * @author: Shenghao.He
 * @date: Created in 2020-09-03 11:29:19
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pri/sysDictData")
@Api( tags = {"字典数据接口"})
public class SysDictDataController {

    @Autowired
    SysDictDataService sysDictDataService;


    /**
   * 分页查询字典数据表
   * @param page
   * @return
   */
    @PostMapping("findPage")
    @ApiOperation(value = "分页查询字典数据表", notes = "分页查询字典数据表")
    public Result listSysRoleMenu(@RequestBody PageRequest page) {
        Page<SysDictData> detailsPage = new Page<>(page.getPageNum(), page.getPageSize());
        return Result.succeed(sysDictDataService.page(detailsPage));
    }


    /**
     * 通过id查询字典数据表
     * @param dictCode id
     * @return JsonData
     */
    @GetMapping("/{dictCode}")
    @ApiOperation(value = "通过id查询字典数据表", notes = "通过id查询字典数据表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "主键id", required = true)
    })
    public Result getSysDictData(@PathVariable("dictCode") String dictCode){
      return  Result.succeed(sysDictDataService.getById(dictCode));
    }

    /**
     * 新增字典数据表
     * @param sysDictData 字典数据表
     * @return JsonData
     */
    @PreAuthorize("@el.check('sys:dict:add')")
    @PostMapping
    @ApiOperation(value = "新增字典数据表", notes = "新增字典数据表")
    public Result saveSysDictData(@RequestBody SysDictData sysDictData){
      return  Result.succeed(sysDictDataService.save(sysDictData));
    }

    /**
     * 修改字典数据表
     * @param dictCode id
     * @param sysDictData 字典数据表
     * @return JsonData
     */
    @PutMapping("/{dictCode}")
    @ApiOperation(value = "修改字典数据表", notes = "修改字典数据表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "主键id", required = true)
    })
    public Result updateSysDictData(@PathVariable String dictCode, @RequestBody SysDictData sysDictData){
      return  Result.succeed(sysDictDataService.updateById(sysDictData));
    }

    /**
     * 通过id删除字典数据表
     * @param dictCode id
     * @return JsonData
     */
    @DeleteMapping("/{dictCode}")
    @ApiOperation(value = "删除字典数据表", notes = "删除字典数据表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "主键id", required = true)
    })
    public Result deleteSysDictData(@PathVariable String dictCode){
      return  Result.succeed(sysDictDataService.removeById(dictCode));
    }

}
