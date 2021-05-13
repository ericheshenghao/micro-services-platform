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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService  {
    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysMenuDao sysMenuDao;

    @Override
    public UserDetails loadUserByUsername(String userCodeOrName) throws UsernameNotFoundException {
        SysUser user = sysUserDao.findUserByUserCodeOrName(userCodeOrName).orElseThrow(
                () -> new UsernameNotFoundException("找不到该用户!"));

        // 这里储存的是 userCode， 所以之后再进来时会通过 userCode 查找用户
        return new User(user.getUserCode(),user.getPassword(),new ArrayList<>());
    }
}
