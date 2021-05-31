package cn.central.oauth.controller;

import cn.central.common.constant.AdminConstants;
import cn.central.common.model.SysUser;
import cn.central.common.page.PageRequest;


import cn.central.common.model.Result;

import cn.central.oauth.entity.SysClientDetails;
import cn.central.oauth.service.SysClientDetailsService;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 
 * </p>
 *
 * @package:  cn.siques.mangosound.controller
 * @description: 
 * @author: Shenghao.He
 * @date: Created in 2020-11-18 22:50:20
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Api(tags = {"客户端接口"})
public class SysClientDetailsController {

    @Autowired
    SysClientDetailsService sysClientDetailsService;

    @Resource
    BCryptPasswordEncoder passwordEncoder;
    /**
   * 分页查询
   * @param page
   * @return
   */
    @PostMapping("findPage")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Result listSysRoleMenu(@RequestBody PageRequest page) {
        Page<SysClientDetails> detailsPage = new Page<>(page.getPageNum(), page.getPageSize());
        return Result.succeed(sysClientDetailsService.page(detailsPage));
    }

    /**
     * 通过id查询,post请求会跑到这里，怎么解决？
     * @param id id
     * @return JsonData
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    public Result getSysClientDetails(@PathVariable("id") Long id){
      return Result.succeed(sysClientDetailsService.getById(id));
    }


    @PutMapping("/secret/{id}")
    @PreAuthorize("@el.check('sys:client:edit')")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    public Result resetClientSecret(@PathVariable("id") Long id){
        return Result.succeed(sysClientDetailsService.update(new UpdateWrapper<SysClientDetails>()
                .eq("id",id).set("client_secret",passwordEncoder.encode(AdminConstants.PASSWORD))));
    }

    /**
     * 新增
     * @param sysClientDetails 
     * @return JsonData
     */
    @PostMapping
    @ApiOperation(value = "新增", notes = "新增")
    public Result saveSysClientDetails(@RequestBody SysClientDetails sysClientDetails){
      return  Result.succeed(sysClientDetailsService.save(sysClientDetails));
    }

    /**
     * 修改
     * @param id id
     * @param sysClientDetails 
     * @return JsonData
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    public Result updateSysClientDetails(@PathVariable("id") Long id, @RequestBody SysClientDetails sysClientDetails){
      return  Result.succeed(sysClientDetailsService.updateById(sysClientDetails));
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
    public Result deleteSysClientDetails(@PathVariable("id") Long id){
      return  Result.succeed(sysClientDetailsService.removeById(id));
    }

}
