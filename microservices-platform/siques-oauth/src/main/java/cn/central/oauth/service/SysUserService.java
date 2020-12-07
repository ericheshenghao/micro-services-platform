package cn.central.oauth.service;



import cn.central.common.model.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author : heshenghao
 * @date : 18:17 2020/11/13
 */

public interface SysUserService  extends IService<SysUser>, UserDetailsService {


}
