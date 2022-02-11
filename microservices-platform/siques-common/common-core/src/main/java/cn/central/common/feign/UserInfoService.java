package cn.central.common.feign;

import cn.central.common.constant.ServiceNameConstants;
import cn.central.common.feign.fallback.AuthServiceFallbackFactory;
import cn.central.common.model.LoginAppUser;
import cn.central.common.model.SysUser;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author he
 */
@FeignClient(name = ServiceNameConstants.AUTH_SERVICE, fallbackFactory = AuthServiceFallbackFactory.class, decode404 = true)
public interface UserInfoService {
    /**
     * pri 接口只允许内部调用
     * feign rpc访问远程/user/{username}接口
     * 查询用户实体对象SysUser
     *
     * @return
     */
    @GetMapping(value = "/pri/user")
    SysUser getUserInfoByToken();


    /**
     * feign 访问远程
     *
     * @param userCode
     * @return
     */
    @GetMapping(value = "/pri/user/permissions/{userCode}")
    Set<String> findPermissionsByUserCode(@PathVariable("userCode") String userCode);


    /**
     * feign rpc访问远程/users-anon/login接口
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/users-anon/login", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);


}
