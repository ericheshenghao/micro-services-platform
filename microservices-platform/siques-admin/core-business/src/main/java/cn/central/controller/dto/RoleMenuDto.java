package cn.central.controller.dto;



import cn.central.entity.SysRoleMenu;
import lombok.Data;

import java.util.List;

/**
 * @author : heshenghao
 * @date : 16:52 2020/11/16
 */
@Data
public class RoleMenuDto {


    private Long roleId;
    private List<SysRoleMenu> roleMenus;

}
