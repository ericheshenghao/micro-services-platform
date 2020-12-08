package cn.central.auth.config;


import cn.central.auth.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;

/**
 * @author he
 */


@Import(DefaultSecurityHandlerConfig.class)
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private  ResourceServerProperties resourceServerProperties;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Resource
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore)
                .resourceId(resourceServerProperties.getResourceId())
                .stateless(true)
                .authenticationEntryPoint(authenticationEntryPoint)
                .expressionHandler(expressionHandler)
                .accessDeniedHandler(oAuth2AccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().
                formLogin().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers(securityProperties.getIgnore().getUrls()).permitAll()
                .anyRequest().authenticated();

        // 基于密码 等模式可以无session,不支持授权码模式
        if (authenticationEntryPoint != null) {
            http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        } else {
            // 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        }
    }


    /**
     * 留给子类重写扩展功能
     * @param http
     */
//    public HttpSecurity setHttp(HttpSecurity http) {
//        return http;
//    }
}