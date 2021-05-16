package cn.central.auth.store;

import cn.central.auth.converter.CustomUserAuthenticationConverter;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

/**
 * @author : heshenghao
 * @date : 21:00 2021/5/16
 */
@Slf4j
@ConditionalOnProperty(prefix = "siques.oauth2.token.store", name = "type", havingValue = "redis")
public class ResRedisTokenStore {

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        return redisTokenStore;
    }

}
