package cn.central.auth.store;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * redis中使用
 * 认证服务器使用Redis存取令牌，客户端同样使用redis读取令牌
 * 注意: 需要配置redis参数
 * matchIfMissing 如果不存在或未配置，则返回true，依然进行注入
 *
 * @date 2018/7/25 9:36
 */
@Slf4j
@ConditionalOnProperty(prefix = "siques.oauth2.token.store", name = "type", havingValue = "redisJwk")
public class AuthRedisTokenStore {


    @Bean
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory) {
        return new RedisTokenStore(connectionFactory);
    }


}
