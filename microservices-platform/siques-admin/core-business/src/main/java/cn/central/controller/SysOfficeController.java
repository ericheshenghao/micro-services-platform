package cn.central.controller;


import cn.central.common.Page.PageRequest;
import cn.central.common.model.Result;
import cn.central.dao.SysRoleOfficeMapper;
import cn.central.entity.SysOffice;
import cn.central.entity.SysRoleOffice;
import cn.central.log.annotation.AuditLog;
import cn.central.service.SysOfficeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = {"机构管理接口"})
@RestController
@RequestMapping("/pri/office")
public class SysOfficeController {


    @Resource
    SysOfficeService sysOfficeService;

    @Resource
    SysRoleOfficeMapper sysRoleOfficeMapper;
    /**
     * 查询机构树
     * @return
     */
    @PreAuthorize("@el.check('sys:office:view')")
    @GetMapping(value="/findOfficeTree")
    public Result findNavTree() {
        return Result.succeed(sysOfficeService.findOfficeTree());
    }

    @ApiOperation(value = "所有机构分页", notes = "所有机构分页")
    @PreAuthorize("@el.check('sys:office:view')")
    @PostMapping(value = "/findPage")
    public Result findPage(@RequestBody PageRequest page){
        Page<SysOffice> officePage = new Page<>(page.getPageNum(),page.getPageSize());
        return Result.succeed(sysOfficeService.page(officePage));
    }

    @ApiOperation(value = "查询id对应机构分页", notes = "查询id对应机构分页")
    @PreAuthorize("@el.check('sys:office:view')")
    @PostMapping(value = "/findPage/{id}")
    public Result findPageById(@PathVariable("id") Long id, @RequestBody PageRequest page){
        Page<SysOffice> officePage = new Page<>(page.getPageNum(),page.getPageSize());
        return Result.succeed(sysOfficeService.findOfficePageById(officePage,id));
    }

     @ApiOperation(value = "保存或更新机构", notes = "保存或更新机构")
    @PreAuthorize("@el.check('sys:office:edit')")
    @PostMapping()
    public Result save(@RequestBody SysOffice sysOffice){
        if(sysOffice.getParentId()!=0){
            SysOffice parent = sysOfficeService.getOne(new QueryWrapper<SysOffice>().eq("id", sysOffice.getParentId()));
            sysOffice.setTreeNames(parent.getTreeNames()+"/"+sysOffice.getOfficeName());
        }else{
            sysOffice.setTreeNames(sysOffice.getOfficeName());
        }
         return Result.succeed(sysOfficeService.saveOrUpdate(sysOffice));
    }


    @ApiOperation(value = "删除机构", notes = "删除机构")
    @PreAuthorize("@el.check('sys:office:delete')")
    @DeleteMapping("/{id}")
    @Transactional
    @AuditLog(operation = "'删除机构：'+ #id")
    public Result delete(@PathVariable("id") String id){
        // 同时要删除机构与角色的关联关系
        sysRoleOfficeMapper.delete(new QueryWrapper<SysRoleOffice>().eq("office_id",id));
        // 同时需要删除下级机构
        sysOfficeService.remove(new QueryWrapper<SysOffice>().like("parent_ids",id));

        return Result.succeed(sysOfficeService.removeById(id));
    }


}
