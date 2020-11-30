package cn.central.dao;


import cn.central.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {


    @Delete({"delete from sys_user_role where user_id=#{id,jdbcType=BIGINT}"})
    int deleteByUserId(Long id);
}