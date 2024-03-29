package cn.central.common.feign.fallback;

import cn.central.common.feign.AuthService;

import cn.central.common.model.LoginAppUser;
import cn.central.common.model.SysUser;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * authService降级工场
 *
 * @author he
 * @date 2019/1/18
 */
@Slf4j
public class AuthServiceFallbackFactory implements FallbackFactory<AuthService> {
    @Override
    public AuthService create(Throwable throwable) {
        return new AuthService() {


            @Override
            public SysUser selectByUserCode(String userCode) {
                log.error("通过用户code查询用户异常:{}", userCode, throwable);
                return new SysUser();
            }

            @Override
            public Set<String> findPermissionsByUserCode(String userCode) {
                log.error("通过code查询用户异常:{}", userCode, throwable);
                return new HashSet<>();
            }

            @Override
            public LoginAppUser findByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return new LoginAppUser();
            }

            @Override
            public LoginAppUser findByMobile(String mobile) {
                log.error("通过手机号查询用户异常:{}", mobile, throwable);
                return new LoginAppUser();
            }

            @Override
            public LoginAppUser findByOpenId(String openId) {
                log.error("通过openId查询用户异常:{}", openId, throwable);
                return new LoginAppUser();
            }
        };
    }
}
