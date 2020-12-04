package cn.central.controller;


import cn.central.common.model.Result;
import cn.central.common.model.SysRole;
import cn.central.entity.SysRoleMenu;
import cn.central.service.SysRoleMenuService;
import cn.central.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Shenghao.He
 */
@RestController
@Api(tags = {"角色管理接口"})
@RequestMapping("/pri/role")
public class SysRoleController {
    @Resource
    SysRoleService sysRoleService;



    @Resource
    private SysRoleMenuService sysRoleMenuService;


    @ApiOperation(httpMethod="GET", value="查询所有角色")
    @GetMapping()
    @PreAuthorize("@el.check('sys:role:view')")
    public Result findAll(){
        return Result.succeed(sysRoleService.list());
    }


    @ApiOperation(httpMethod="POST", value="保存角色信息")
    @PreAuthorize("@el.check('sys:role:edit')")
    @PostMapping()
    public Result save(@RequestBody SysRole sysRole){

        return Result.succeed(sysRoleService.saveOrUpdate(sysRole));

    }

    @ApiOperation(httpMethod="DELETE", value="删除角色，附带删除角色菜单关系")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('sys:role:delete')")
    @Transactional
    public Result delete(@PathVariable("id") Long id){
        // 删除角色菜单关联关系
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id",id));
        // 删除角色
        return Result.succeed(sysRoleService.removeById(id));
    }


    @ApiOperation(httpMethod="GET", value="根据id查询角色菜单")
    @GetMapping(value="/findRoleMenus/{id}")
    @PreAuthorize("@el.check('sys:role:view')")
    public Result  findRoleMenus(@PathVariable("id") Long id){
        SysRole sysMenus= sysRoleService.findRoleMenus(id);
        return Result.succeed(sysMenus);
    }

    /**
     * 修改用户菜单关系
     * @param
     * @return
     */
    @ApiOperation(httpMethod="POST", value="修改角色菜单")
    @PreAuthorize("@el.check('sys:role:edit')")
    @PostMapping(value="/saveRoleMenus/{id}")
    public Result saveRoleMenus(@RequestBody() Set<String> menuSet, @PathVariable("id") Long roleId) {

        SysRole role = sysRoleService.getById(roleId);

       // 修改角色的权限
        return Result.succeed(sysRoleService.saveRoleMenus(role.getRoleCode(),roleId,menuSet));
    }

}
