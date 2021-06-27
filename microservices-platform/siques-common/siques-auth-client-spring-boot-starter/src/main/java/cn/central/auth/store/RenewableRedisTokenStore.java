package cn.central.auth.store;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.Collection;

/**
 * token续签，支持redis
 *
 * @author : heshenghao
 * @date : 23:42 2021/5/16
 */
public class RenewableRedisTokenStore extends RedisTokenStore {

    public RenewableRedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

}
