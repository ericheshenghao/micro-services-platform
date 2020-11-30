package cn.central.service;


import cn.central.common.model.Result;
import cn.central.common.model.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;


public interface SysRoleService extends IService<SysRole> {


    SysRole findRoleMenus(Long id);



    Result saveRoleMenus(String roleCode, Long roleId, Set<String> menuSet);



}
