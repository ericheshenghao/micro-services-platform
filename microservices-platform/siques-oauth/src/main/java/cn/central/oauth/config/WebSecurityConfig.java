package cn.central.oauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author : heshenghao
 * @date : 19:10 2020/11/13
 */
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ClientLogoutSuccessHandler clientLogoutSuccessHandler;
    private final ClientLoginFailureHandler clientLoginFailureHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .formLogin()
//                .loginPage("/oauth/login")
//                .failureHandler(clientLoginFailureHandler)
//                .loginProcessingUrl("/authorization/form")
//                .and()
//                .logout()
//                .logoutUrl("/oauth/logout")
//                .logoutSuccessHandler(clientLogoutSuccessHandler)
//                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .httpBasic().and() //授权码模式必须打开
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest()
                .authenticated();
    }

    /**
     * 授权管理.
     *
     * @return 认证管理对象
     * @throws Exception 认证异常信息
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
