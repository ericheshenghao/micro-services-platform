package cn.central.oauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;

/**
 * @author : heshenghao
 * @date : 19:54 2020/11/13
 */



@Configuration
@RequiredArgsConstructor
public class Oauth2AuthorizationTokenConfig {


    /**
     * 声明 内存 TokenStore 实现，用来存储 token 相关.
     * 默认实现有 mysql、redis
     *
     * @return InMemoryTokenStore
     */

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    @Primary
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * jwt 令牌配置，非对称加密
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        final JwtAccessTokenConverter accessTokenConverter =new JwtAccessTokenConverter();
        accessTokenConverter.setKeyPair(keyPair());
        return accessTokenConverter;
    }

    /**
     * 密匙 keyPair
     * 用于生成jwt / jwk
     */
    @Bean
    public KeyPair keyPair(){
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("oauth2.jks"),"123456".toCharArray()
        );
        return keyStoreKeyFactory.getKeyPair("oauth2");
    }

    /**
     * 加密方式，使用 BCrypt.
     * 参数越大加密次数越多，时间越久.
     * 默认为 10.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

}
