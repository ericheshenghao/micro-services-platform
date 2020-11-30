package cn.central.oauth.dao;



import cn.central.common.model.SysRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : heshenghao
 * @date : 11:20 2020/11/14
 */
@Repository
public interface SysRoleDao {

    @Select("SELECT * FROM sys_role WHERE id IN(SELECT role_id FROM sys_user_role WHERE user_id=#{uid})")
     List<SysRole> getAllRolesByUserId(int uid);
}
