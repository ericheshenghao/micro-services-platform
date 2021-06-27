package cn.central.auth.config;


import cn.central.auth.config.client.DefaultResourceTokenConfig;
import cn.central.auth.handler.DefaultSecurityHandler;
import cn.central.auth.properties.SecurityProperties;
import cn.central.common.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;

/**
 * 资源服务器的配置，受保护服务与鉴权服务都被认定为资源
 * 都需要继承该类
 * 内存实现
 *
 * @author he
 */
@Import(DefaultSecurityHandler.class)
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {


    /**
     * 通过yaml实例化的的储存介质，这里其实现类依赖于 cn.central.auth.store 包
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * yaml中配置的资源属性
     */
    @Autowired
    private ResourceServerProperties resourceServerProperties;

    /**
     * 认证节点
     */
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    /**
     * 未授权情况下的处理器，比如没有携带token请求时的处理
     */
    @Resource
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    /**
     * 自定义配置的属性
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public DefaultTokenServices defaultTokenServices(TokenStore tokenStore) {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore);
        return services;
    }

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
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 自定义的忽略认证的路径
                .antMatchers(securityProperties.getIgnore().getUrls()).permitAll()
                .anyRequest().authenticated();


//        http.formLogin()
//                .loginPage(SecurityConstants.LOGIN_PAGE)
//                .loginProcessingUrl(SecurityConstants.OAUTH_LOGIN_PRO_URL)
//                .successHandler(authenticationSuccessHandler);

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