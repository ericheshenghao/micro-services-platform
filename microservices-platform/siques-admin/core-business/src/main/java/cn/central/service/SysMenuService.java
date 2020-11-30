package cn.central.service;



import cn.central.common.model.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu>  findTree(String userCode, int menuType);

    List<SysMenu> findUserMenuByUserCode(String userCode);

    int removeIdAndChild(Long id);
}
