package cn.central.service.impl;


import cn.central.common.page.PageRequest;
import cn.central.common.constant.AdminConstants;
import cn.central.common.model.Result;
import cn.central.common.model.SysMenu;
import cn.central.common.model.SysUser;
import cn.central.dao.SysMenuMapper;
import cn.central.dao.SysUserMapper;
import cn.central.dao.SysUserRoleMapper;

import cn.central.entity.SysUserRole;
import cn.central.service.SysUserRoleService;
import cn.central.service.SysUserService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author heshenghao
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    SysMenuMapper sysMenuMapper;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Resource
    SysUserRoleService sysUserRoleService;

    /**
     * 根据code查找菜单，再根据菜单查询权限
     *
     * @param userCode
     * @return
     */
    @Override
    public Set<String> findPermission(String userCode) {
        List<SysMenu> sysMenus;
        if (userCode.equals(AdminConstants.ADMIN)) {
            sysMenus = sysMenuMapper.selectList(new QueryWrapper<SysMenu>());
        } else {
            sysMenus = sysMenuMapper.findUserMenuByUserCode(userCode);
        }

        Set<String> perms = sysMenus.stream()
                .filter(sysMenu -> sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms()))
                .map(sysMenu -> sysMenu.getPerms()).collect(Collectors.toSet());
        return perms;
    }


    @Override
    public int delUserRoleByUserId(String id) {
        return sysUserRoleMapper.deleteByUserId(id);
    }


    public Result findPage(PageRequest pageRequest, QueryWrapper<SysUser> queryWrapper) {
        IPage<SysUser> userPage = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        // 超级管理员不加入该集合
        List<SysUser> records = baseMapper
                .selectPage(userPage, queryWrapper
                        .not(true, sysUserQueryWrapper ->
                                sysUserQueryWrapper.eq("user_code", AdminConstants.ADMIN)))
                .getRecords();

        long total = userPage.getTotal();

        if (total > 0 && records.size() > 0) {
            List<String> userIds = records.stream().map(SysUser::getId).collect(Collectors.toList());
            List<SysUserRole> sysUserRoles = sysUserRoleMapper
                    .selectList(new QueryWrapper<SysUserRole>().in("user_id", userIds));

            for (SysUser user : records) {
                user.setRoleIds(
                        sysUserRoles.stream()
                                .filter(ur -> ObjectUtil.equal(ur.getUserId(), user.getId()))
                                .map(ur -> ur.getRoleId().toString()).collect(Collectors.toList())
                );
            }
        }

        return Result.succeed(userPage);
    }

    @Override
    public Result findPage(PageRequest pageRequest) {
        SysUser user = (SysUser) pageRequest.getParams();
        Map<String, Object> stringObjectMap = user.toMap();
        if (stringObjectMap.size() != 0) {
            return findPage(pageRequest, new QueryWrapper<SysUser>().allEq(stringObjectMap));
        }

        return findPage(pageRequest, new QueryWrapper<>());
    }

    @Override
    @CacheEvict(value = "findMenuTree", key = "#record.userCode")
    public boolean saveUserRoles(SysUser record) {
        if (ObjectUtil.isNotNull(record.getRoleIds())) {
            delUserRoleByUserId(record.getId());
            List<SysUserRole> sysUserRoles = record.getRoleIds().stream()
                    .map(s -> new SysUserRole(record.getId(), String.valueOf(s)))
                    .collect(Collectors.toList());
            return sysUserRoleService.saveBatch(sysUserRoles);
        }
        return false;
    }


}
