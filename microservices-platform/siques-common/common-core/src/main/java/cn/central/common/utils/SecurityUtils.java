package cn.central.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;


/**
 * @author Administrator
 */
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
     * 获取代表当前用户身份的唯一代码，数据区唯一索引
     * @return
     */
    public static String getUserCode() {
        String usercode = null;
        Authentication authentication = getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDetails){
                usercode = ((UserDetails) principal).getUsername();
            }else if(principal != null){
                usercode = principal.toString();
            }
        }
        return usercode;
    }


}
