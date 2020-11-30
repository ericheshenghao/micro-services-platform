package cn.central.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;


public class SecurityUtils {


    /**
     * 获取当前登录信息
     * @return
     */
    public static Authentication getAuthentication(){
        if(SecurityContextHolder.getContext()==null){
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return  authentication;
    }



    /**
     * 获取当前用户名
     * @return
     */
    public static String getUsername() {
        String username = null;
        Authentication authentication = getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            System.out.println(principal.toString());
            if(principal instanceof UserDetails){
                username = ((UserDetails) principal).getUsername();
            }else if(principal != null){
                username = principal.toString();
            }
        }
        return username;
    }


}
