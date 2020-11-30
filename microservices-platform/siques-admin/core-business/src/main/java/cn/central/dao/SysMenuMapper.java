package cn.central.dao;



import cn.central.common.model.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {



    @Select({"select m.* from sys_menu m, sys_user u, sys_user_role ur, sys_role_menu rm\n" +
            "  \twhere u.user_code = #{userCode,jdbcType=VARCHAR} and u.id = ur.user_id \n" +
            "  \tand ur.role_id = rm.role_id and rm.menu_id = m.id"})
    List<SysMenu> findUserMenuByUserCode(String userCode);

    @Select({"select m.* from sys_menu m, sys_role_menu rm\n" +
            "    where rm.role_id = #{roleId,jdbcType=BIGINT}\n" +
            "    and m.id = rm.menu_id"})
    List<SysMenu> findRoleMenus(Long roleId);


    @Delete({"DELETE FROM sys_menu WHERE parent_ids LIKE  CONCAT('%',#{id,jdbcType=VARCHAR},'%')"})
    int deleteChild(Long id);
}