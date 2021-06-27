package cn.central.service.impl;


import cn.central.common.constant.AdminConstants;
import cn.central.common.model.SysRole;
import cn.central.dao.SysMenuMapper;
import cn.central.dao.SysRoleMapper;
import cn.central.dao.SysRoleMenuMapper;

import cn.central.entity.SysRoleMenu;
import cn.central.service.SysRoleMenuService;
import cn.central.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    SysRoleMapper sysRoleMapper;

    @Resource
    SysMenuMapper sysMenuMapper;

    @Resource
    SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    SysRoleMenuService sysRoleMenuService;

    @Override
    public SysRole findRoleMenus(String roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if (AdminConstants.ADMIN.equalsIgnoreCase(sysRole.getRoleCode())) {
            // 如果是超级管理员，返回全部的权限
            sysRole.setSysMenuList(sysMenuMapper.selectList(new QueryWrapper<>()));
            return sysRole;
        }
        sysRole.setSysMenuList(sysMenuMapper.findRoleMenus(roleId));
        return sysRole;
    }

    /**
     * 修改角色菜单权限，需要同时清空该用户的菜单缓存
     *
     * @param
     * @param roleCode
     * @return
     */
    @Transactional
    @Override
    @CacheEvict(value = "findMenuTree", key = "#roleCode")
    public boolean saveRoleMenus(String roleCode, String roleId, Set<String> menuSet) {

        // 清除已有的的菜单
        sysRoleMenuMapper.deleteByRoleId(roleId);
        // 批量插入
        List<SysRoleMenu> collect = menuSet.stream()
                .map(menuId -> new SysRoleMenu(roleId, String.valueOf(menuId)))
                .collect(Collectors.toList());

        return sysRoleMenuService.saveBatch(collect);
    }

//    @Override
//    public List<SysRole> findAll() {
//
//        List<SysRole> sysRoles = baseMapper.selectList(new QueryWrapper<SysRole>().orderByAsc("role_sort"));
//
//        List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
//
//        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper
//                .selectList(new QueryWrapper<SysRoleMenu>().in("role_id",roleIds));
//
//        sysRoles.forEach(sysRole -> sysRole.setMenuSet(
//                 sysRoleMenus.stream()
//                         .filter(sysRoleMenu -> sysRoleMenu.getRoleId().equals(sysRole.getId()))
//                 .map(sysRoleMenu -> sysRoleMenu.getMenuId().toString()).collect(Collectors.toSet())
//         ));
//         return sysRoles;
//    }

}
