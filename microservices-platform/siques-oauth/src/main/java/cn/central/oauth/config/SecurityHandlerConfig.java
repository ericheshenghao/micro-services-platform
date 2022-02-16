package cn.central.oauth.config;


import cn.central.common.model.BasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一响应返回格式
 *
 * @author he
 */
@Slf4j
@Configuration
public class SecurityHandlerConfig {

    /**
     * 包装所有错误类型返回
     *
     * @return
     */
    @Bean
    public WebResponseExceptionTranslator WebResponseExceptionTranslator() {
        return e -> {

            ResponseEntity<BasicResponse> response = new ResponseEntity<>
                    (BasicResponse.failed( e.getMessage(),HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);

            return response;
        };
    }

    /**
     * 登陆成功
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
}
