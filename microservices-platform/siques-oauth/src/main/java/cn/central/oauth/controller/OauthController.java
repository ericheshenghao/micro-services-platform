package cn.central.oauth.controller;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author : heshenghao
 * @date : 17:43 2020/11/13
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {

    @RequestMapping("/user")
    public HashMap<String, Object> user(OAuth2Authentication user){
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("user",user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(
                user.getUserAuthentication().getAuthorities()));
        return userInfo;
    }
}
