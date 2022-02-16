package cn.central.oauth.config;


import cn.central.auth.converter.CustomUserAuthenticationConverter;
import cn.central.oauth.service.SysClientDetailsService;
import cn.central.oauth.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * 鉴权服务器配置
 *
 * @author : heshenghao
 * @date : 18:03 2020/11/13
 */
@Configuration
@EnableAuthorizationServer
@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 提供用户信息查询的接口
     */
    @Autowired
    private   SysUserService userDetailsService;

    /**
     * 配置好的token储存方式
     */
    @Autowired
    private   TokenStore tokenStore;

    /**
     * security 认证管理器
     */
    @Autowired
    private   AuthenticationManager authenticationManager;

    /**
     * jwt与authentication 转换器
     */
    @Autowired
    private   JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 客户端接口
     */
    @Autowired
    private   SysClientDetailsService sysClientDetailsService;

    /**
     * 储存认证码接口
     */
    @Autowired
    private   AuthorizationCodeServices authorizationCodeServices;

    /**
     * 响应异常处理器
     */
    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    /**
     * 复合TokenGranter实例，CompositeTokenGranter
     */
    @Autowired
    private   TokenGranter tokenGranter;


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());

        converter.setKeyPair(keyPair());
        return converter;
    }


    /**
     * 密匙 keyPair
     * 用于生成jwt / jwk
     */
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("oauth2.jks"), "123456".toCharArray()
        );
        return keyStoreKeyFactory.getKeyPair("oauth2");
    }


    /**
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(sysClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore)
                .authorizationCodeServices(authorizationCodeServices)
                .tokenGranter(tokenGranter)
                .exceptionTranslator(webResponseExceptionTranslator)
                .accessTokenConverter(jwtAccessTokenConverter);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")

                //让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients();

    }


}
