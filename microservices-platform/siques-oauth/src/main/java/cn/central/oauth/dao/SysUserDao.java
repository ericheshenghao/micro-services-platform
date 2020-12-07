package cn.central.oauth.dao;



import cn.central.common.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Administrator
 */
@Repository
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 查找用户
     * @param username
     * @return
     */
    @Select({"select * from sys_user where user_name = #{username}"})
    Optional<SysUser> findUserByUsername(@Param("username") String username);



}