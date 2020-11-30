package cn.central.oauth.service;



import cn.central.common.model.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author : heshenghao
 * @date : 18:17 2020/11/13
 */

public interface SysUserService  extends IService<SysUser> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    SysUser findUserByLoginCode(String username);
}
