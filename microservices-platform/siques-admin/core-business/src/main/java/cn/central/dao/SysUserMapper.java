package cn.central.dao;

import cn.central.common.model.SysMenu;
import cn.central.common.model.SysRole;
import cn.central.common.model.SysUser;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
public interface SysUserMapper  extends BaseMapper<SysUser> {




    @Select({"SELECT r.* FROM  sys_user u ,sys_user_role ur, sys_role r \n" +
            "WHERE u.id = #{id,jdbcType=BIGINT} AND u.id= ur.`user_id` AND ur.`role_id` = r.id"})
    List<SysRole> findUserRolesById(long id);


    @Select({"SELECT u.* FROM sys_user u,sys_role_office ro,sys_user_role ur WHERE office_id IN \n" +
            "(SELECT d.id FROM sys_office d WHERE d.parent_ids LIKE  CONCAT('%',#{id,jdbcType=VARCHAR},'%'))\n" +
            "AND ro.office_id=office_id AND ur.role_id=ro.role_id AND ur.user_id=u.id\n"})
     IPage<SysUser> findUsersByOfficeId(IPage page,@Param(value = "id") Long id);


    @Select({"select u.* from  sys_user u ,sys_user_role ur where ur.`role_id`=#{id,jdbcType=BIGINT} and ur.`user_id`=u.`id`\n"})
    List<SysUser> findUsersByRoleId(Long id);



}