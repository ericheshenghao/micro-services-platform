package cn.central.controller;


import cn.central.common.annotation.LoginUser;
import cn.central.common.model.Result;
import cn.central.common.model.SysMenu;
import cn.central.common.model.SysUser;
import cn.central.common.utils.SecurityUtils;


import cn.central.dao.SysRoleMenuMapper;
import cn.central.entity.SysRoleMenu;
import cn.central.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/pri/menu")
@Api(tags = {"菜单接口"})
public class SysMenuController {

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @PostMapping()
    @ApiOperation(value = "保存菜单树，需要添加或编辑权限")
    @CacheEvict(value="findMenuTree",allEntries=true)
    @PreAuthorize("@el.check('sys:menu:add') AND @el.check('sys:menu:edit')")
    public Result save(@RequestBody SysMenu record) {

        List<String> parentArray = record.getParentArray();
        if(parentArray.size()>0 && !"".equals(parentArray.get(0)))   {
            System.out.println(parentArray.get(0));

            record.setParentId(Long.valueOf(parentArray.get(parentArray.size()-1)));
        }
        else {
            record.setParentId(0L);
        }
        
        StringBuilder builder = new StringBuilder();

        parentArray.stream().filter(s -> !"".equals(s)).forEach(s -> builder.append(s+","));

        record.setParentIds(builder.toString());

        return Result.succeed(sysMenuService.saveOrUpdate(record));
    }

    /**
     * 查询导航菜单
     * @param
     * @return
     */
    @GetMapping(value="/findNavTree")
    @ApiOperation(value = "查询导航菜单")
    public Result findNavTree() {
        return Result.succeed(sysMenuService.findTree(SecurityUtils.getUsername(), 1));
    }



    @PreAuthorize("@el.check('sys:menu:delete')")
    @DeleteMapping("{id}")
    @CacheEvict(value="findMenuTree",allEntries=true)
    @ApiOperation(value = "通过id删除菜单")
    @Transactional
    public Result delete(@PathVariable("id") Long id){
        // 菜单删除还需要清除角色菜单关系
          sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("menu_id",id));
          return Result.succeed(sysMenuService.removeIdAndChild(id));
    }



    @PreAuthorize("@el.check('sys:menu:view')")
    @GetMapping(value="/findMenuTree")
    @ApiOperation(value = "查询菜单树")
    public Result findMenuTree() {
        return Result.succeed(sysMenuService.findTree("", 0));
    }

}
