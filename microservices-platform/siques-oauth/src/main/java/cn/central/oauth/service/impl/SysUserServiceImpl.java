package cn.central.oauth.service.impl;

/**
 * @author : heshenghao
 * @date : 18:58 2020/11/13
 */


import cn.central.common.model.SysUser;
import cn.central.oauth.dao.SysMenuDao;
import cn.central.oauth.dao.SysUserDao;

import cn.central.oauth.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysMenuDao sysMenuDao;

    @Override
    public UserDetails loadUserByUsername(String userCodeOrName) throws UsernameNotFoundException {
        SysUser user = sysUserDao.findUserByUserCodeOrName(userCodeOrName).orElseThrow(
                () -> new UsernameNotFoundException("找不到该用户!"));

        // 添加默认流程角色
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ACTIVITI_USER");
        grantedAuthorities.add(grantedAuthority);

        // 这里储存的是 userCode， 所以之后再进来时会通过 userCode 查找用户
        return new User(user.getUserCode(), user.getPassword(),grantedAuthorities);
    }
}
