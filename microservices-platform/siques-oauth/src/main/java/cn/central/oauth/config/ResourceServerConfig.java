package cn.central.oauth.config;

import cn.central.auth.config.resource.DefaultResourceServerConfig;
import cn.central.auth.converter.CustomUserAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;


/**
 * @author : heshenghao
 * @date : 23:26 2020/11/14
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends DefaultResourceServerConfig {

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
}
