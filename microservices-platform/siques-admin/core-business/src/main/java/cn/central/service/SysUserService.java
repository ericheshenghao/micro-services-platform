package cn.central.service;



import cn.central.common.Page.PageRequest;

import cn.central.common.model.Result;
import cn.central.common.model.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * CrudService已经有findpage方法
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据code查找权限
     * @param userCode
     * @return
     */
    Set<String> findPermission(String userCode);



    IPage<SysUser> findUsersByOfficeId(IPage page, Long deptId);


    int delUserRoleByUserId(Long id);


    SysUser getUserInfo(String userCode);

    Result findPage(PageRequest pageRequest);

    boolean saveUserRoles(SysUser record);

}
