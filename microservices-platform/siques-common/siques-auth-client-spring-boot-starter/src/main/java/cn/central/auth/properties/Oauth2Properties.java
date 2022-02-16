package cn.central.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : heshenghao
 * @date : 0:50 2021/5/10
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "siques.oauth2.token.store")
public class Oauth2Properties {
    // TODO 字段的自动配置
    StoreType type;

    public enum StoreType {
        /**
         * redis Jwk方式
         */
        redisJwk,
        /**
         * 内存中jwt方式
         */
        jwt,
        /**
         * 数据库储存方式
         */
        db
    }
}
