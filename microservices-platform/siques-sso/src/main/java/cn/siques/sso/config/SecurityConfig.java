package cn.siques.sso.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author : heshenghao
 * @date : 23:36 2021/5/11
 */
@EnableOAuth2Sso
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.oauth2.sso.login-path:}")
    private String loginPath;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable();
        if( loginPath != null){
            http.formLogin().loginProcessingUrl(loginPath);
        }
    }
}
