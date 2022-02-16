package cn.central.service;


import cn.central.common.page.PageRequest;

import cn.central.common.model.BasicResponse;
import cn.central.common.model.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * CrudService已经有findpage方法
 *
 * @author Administrator
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据code查找权限
     *
     * @param userCode
     * @return
     */
    Set<String> findPermission(String userCode);

    /**
     * 根据id 删除用户角色
     *
     * @param id
     * @return
     */
    int delUserRoleByUserId(String id);

    /**
     * 根据id 删除用户角色
     *
     * @param pageRequest
     * @return
     */
    BasicResponse findPage(PageRequest pageRequest);


    /**
     * 保存用户角色
     *
     * @param record
     * @return
     */
    boolean saveUserRoles(SysUser record);

}
