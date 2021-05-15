package cn.central.oauth.config;

import cn.central.auth.properties.SecurityProperties;
import cn.central.common.config.DefaultPasswordConfig;
import cn.central.common.constant.SecurityConstants;
import cn.central.oauth.handler.OauthLogoutHandler;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

/**
 * 安全管理框架获取authenticationManagerBean
 * @author : heshenghao
 * @date : 19:10 2020/11/13
 */
@Import(DefaultPasswordConfig.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired(required = false)
    private AuthenticationEntryPoint authenticationEntryPoint;


    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private LogoutHandler oauthLogoutHandler;

    /**
     * 注入授权管理器
     * @return 认证管理对象
     * @throws Exception 认证异常信息
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()

                .and().logout().logoutUrl(SecurityConstants.LOGOUT_URL)
                .addLogoutHandler(oauthLogoutHandler)
                .clearAuthentication(true).and()
                .formLogin().permitAll(); //新增login form支持用户登录及授权


        // 基于密码 等模式可以无session,不支持授权码模式
//        if (authenticationEntryPoint != null) {
//            http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        } else {
        // 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//     }
    }
    /**
     * 全局用户信息
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


}
