package cn.central.controller;


import cn.central.common.model.BasicResponse;
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
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    SysRoleService sysRoleService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;


    @ApiOperation(httpMethod = "GET", value = "查询所有角色")
    @GetMapping()
    @PreAuthorize("@el.check('sys:role:view')")
    public BasicResponse findAll() {
        return BasicResponse.succeed(sysRoleService.list());
    }


    @ApiOperation(httpMethod = "POST", value = "保存角色信息")
    @PreAuthorize("@el.check('sys:role:edit')")
    @PostMapping()
    public BasicResponse save(@RequestBody SysRole sysRole) {

        return BasicResponse.succeed(sysRoleService.saveOrUpdate(sysRole));

    }

    @ApiOperation(httpMethod = "DELETE", value = "删除角色，附带删除角色菜单关系")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('sys:role:delete')")
    @Transactional
    public BasicResponse delete(@PathVariable("id") Long id) {
        // 删除角色菜单关联关系
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
        // 删除角色
        return BasicResponse.succeed(sysRoleService.removeById(id));
    }


    @ApiOperation(httpMethod = "GET", value = "根据id查询角色菜单")
    @GetMapping(value = "/findRoleMenus/{id}")
    @PreAuthorize("@el.check('sys:role:view')")
    public BasicResponse findRoleMenus(@PathVariable("id") String id) {
        SysRole sysMenus = sysRoleService.findRoleMenus(id);
        return BasicResponse.succeed(sysMenus);
    }

    /**
     * 修改用户菜单关系
     *
     * @param
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "修改角色菜单")
    @PreAuthorize("@el.check('sys:role:edit')")
    @PostMapping(value = "/saveRoleMenus/{id}")
    public BasicResponse saveRoleMenus(@RequestBody() Set<String> menuSet, @PathVariable("id") String roleId) {

        SysRole role = sysRoleService.getById(roleId);

        // 修改角色的权限
        return BasicResponse.succeed(sysRoleService.saveRoleMenus(role.getRoleCode(), roleId, menuSet));
    }

}
