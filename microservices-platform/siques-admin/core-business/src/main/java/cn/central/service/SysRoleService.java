package cn.central.service;


import cn.central.common.model.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;


public interface SysRoleService extends IService<SysRole> {


    SysRole findRoleMenus(String id);


    boolean saveRoleMenus(String roleCode, String roleId, Set<String> menuSet);


}
