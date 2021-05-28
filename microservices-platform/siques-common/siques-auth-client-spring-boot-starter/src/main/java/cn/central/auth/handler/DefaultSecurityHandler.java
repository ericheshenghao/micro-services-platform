package cn.central.auth.handler;


import cn.central.common.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常的处理器
 * @author he
 */
public class DefaultSecurityHandler {
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 匿名用户触发 ExceptionTranslationFilter 的 sendStartAuthentication
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        // 通过流写回前端
        return (request, response, authException) ->
                ResponseUtil.responseFailed(objectMapper, response, authException.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }



    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }


    /**
     * 匿名用户会触发 ExceptionTranslationFilter 不经过这个处理器了，会走
     * authenticationEntryPoint
     * 处理spring security oauth 处理失败返回消息格式
     */
    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler() {

            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) {
                try {
                    ResponseUtil.responseFailed(objectMapper, response, authException.getMessage(), HttpStatus.UNAUTHORIZED.value());
                } catch (IOException e) {
//                    logger.debug(e.getMessage());
                }
            }
        };
    }
}
