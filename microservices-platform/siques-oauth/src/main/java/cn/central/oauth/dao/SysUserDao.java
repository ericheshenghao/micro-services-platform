package cn.central.oauth.dao;



import cn.central.common.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 查找用户
     * @param loginCode
     * @return
     */
    @Select({"select * from sys_user where login_code = #{loginCode}"})
    Optional<SysUser> findUserByLoginCode(@Param("loginCode") String loginCode);



}