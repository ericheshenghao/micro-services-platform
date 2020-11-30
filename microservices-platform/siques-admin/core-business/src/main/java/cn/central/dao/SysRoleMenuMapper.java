package cn.central.dao;


import cn.central.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    @Delete({"delete from sys_role_menu where role_id=#{roleId,jdbcType=BIGINT}"})
    void deleteByRoleId(Long roleId);
}