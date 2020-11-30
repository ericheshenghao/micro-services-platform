package cn.central.oauth.dao;



import cn.central.common.model.SysMenu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author : heshenghao
 * @date : 20:38 2020/11/14
 */
@Repository
public interface SysMenuDao {


    // 通过用户名获取所有权限

    /**
     * 调用者 cn.siques.mangooauth.dao.SysUserDao#findUserByName
     * @param
     * @return
     */
    @Select({"SELECT m.* FROM sys_menu m, sys_user u, sys_user_role ur, sys_role_menu rm\n" +
            " WHERE u.id = #{id} AND u.id = ur.user_id \n" +
            " AND ur.role_id = rm.role_id AND rm.menu_id = m.id"})
    List<SysMenu> findMenusByUid(long id);

    @Select({"select * from sys_menu"})
    Set<SysMenu> findAllMenus();
}
