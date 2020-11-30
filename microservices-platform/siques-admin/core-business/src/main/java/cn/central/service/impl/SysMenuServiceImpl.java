package cn.central.service.impl;


import cn.central.common.constant.AdminConstants;
import cn.central.common.model.SysMenu;
import cn.central.dao.SysMenuMapper;

import cn.central.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    SysMenuMapper sysMenuMapper;

    @Override
    @Cacheable(cacheNames = {"findMenuTree"} ,key="#userCode")
    public List<SysMenu> findTree(String userCode, int menuType) {
        List<SysMenu> sysMenus = new ArrayList<>();
        // 用户所有的
        List<SysMenu> menus = findUserMenuByUserCode(userCode);

        for(SysMenu menu:menus){
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if(!exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        }
        sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        findChildren(sysMenus, menus, menuType);
        return sysMenus;
    }

    private void findChildren(List<SysMenu> SysMenus, List<SysMenu> menus, int menuType) {
        for (SysMenu SysMenu : SysMenus) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : menus) {
                if(menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue ;
                }
                if (SysMenu.getId() != null && SysMenu.getId().equals(menu.getParentId())) {
                    menu.setParentName(SysMenu.getName());
                    menu.setLevel(SysMenu.getLevel() + 1);
                    if(!exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }

            if(children.size()!=0){

                SysMenu.setChildren(children);
            }
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            findChildren(children, menus, menuType);
        }
    }


    private boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        for(SysMenu menu:sysMenus) {
            if(menu.getId().equals(sysMenu.getId())) {
                exist = true;
            }
        }
        return exist;
    }



    @Override
    public List<SysMenu> findUserMenuByUserCode(String userCode) {
        if(userCode==null || "".equals(userCode)|| AdminConstants.ADMIN.equalsIgnoreCase(userCode)){
            // 如果是超级管理员返回所有权限
            return sysMenuMapper.selectList(new QueryWrapper<>());
        }
        return sysMenuMapper.findUserMenuByUserCode(userCode);
    }

    @Override
    @Transactional
    public int removeIdAndChild(Long id) {
           sysMenuMapper.deleteById(id);
          return sysMenuMapper.deleteChild(id);
    }
}
