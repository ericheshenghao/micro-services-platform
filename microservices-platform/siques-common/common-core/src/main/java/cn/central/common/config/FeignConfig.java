package cn.central.common.config;

import cn.central.common.utils.SecurityUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @author : heshenghao
 * @date : 8:54 2020/11/20
 */
//@ConditionalOnClass(EnableFeignClients.class)
//@Configuration
//public class FeignConfig implements RequestInterceptor {
//    /**
//     * 远程调用时添加token
//     * @param requestTemplate
//     */
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        OAuth2Authentication authentication = (OAuth2Authentication)  SecurityContextHolder.getContext().getAuthentication();
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//        requestTemplate.header("Authorization","Bearer "+details.getTokenValue());
//    }
//}
