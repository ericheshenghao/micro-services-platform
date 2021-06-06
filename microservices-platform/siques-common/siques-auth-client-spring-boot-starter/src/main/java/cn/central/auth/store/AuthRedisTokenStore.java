package cn.central.auth.store;


import cn.central.auth.converter.CustomUserAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;

/**
 * 认证服务器使用Redis存取令牌，客户端同样使用redis读取令牌
 * 注意: 需要配置redis参数
 *matchIfMissing 如果不存在或未配置，则返回true，依然进行注入
 * @date 2018/7/25 9:36
 */
@Slf4j
@ConditionalOnProperty(prefix = "siques.oauth2.token.store", name = "type", havingValue = "authRedis")
public class AuthRedisTokenStore {


    @Bean
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory) {
        return  new RedisTokenStore(connectionFactory);
    }

    //
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final  JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());

        converter.setKeyPair(keyPair());
        return converter;
    }


    /**
     * 密匙 keyPair
     * 用于生成jwt / jwk
     */
    public KeyPair keyPair(){
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("oauth2.jks"),"123456".toCharArray()
        );
        return keyStoreKeyFactory.getKeyPair("oauth2");
    }


}
