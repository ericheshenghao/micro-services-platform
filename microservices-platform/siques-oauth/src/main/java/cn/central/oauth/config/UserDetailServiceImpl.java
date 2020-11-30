package cn.central.oauth.config;





import cn.central.common.model.SysUser;
import cn.central.oauth.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 数据库操作
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String loginCode) throws UsernameNotFoundException {
       SysUser user =  sysUserService.findUserByLoginCode(loginCode);

        return new User(user.getUserCode(),user.getPassword(),new ArrayList<>());
    }
}

