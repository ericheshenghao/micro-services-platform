package cn.central.common.config;

import cn.central.common.constant.AdminConstants;
import cn.central.common.feign.UserService;
import cn.central.common.utils.SecurityUtils;
import cn.central.log.monitor.PointUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 *  自定义url级别el表达式鉴权
 * @author : heshenghao
 * @date : 22:16 2020/11/19
 */

@Slf4j
public class ElPermissionConfig {

    @Autowired
    @Lazy
    UserService userService;

    // 发起远程调用鉴权 如果是admin直接返回true
    public Boolean check(String ...permission){
        String userCode = SecurityUtils.getUsername();
        System.out.println(userCode);
        boolean hasPermit = userCode.equals(AdminConstants.ADMIN) || userService.findPermissionsByUserCode(userCode).contains(permission);

        PointUtil.info("用户代码"+userCode, "el表达式鉴权", "permission={"+ Arrays.toString(permission) +"}"+"&status="+hasPermit);

        return hasPermit;
    }
}
