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

    enum StoreType{
        /**
         * 客户端使用redis 作为token的转换方式
         */
        redis,
        /**
         * uaa端使用的 token转换方式
         */
        authRedis,
        /**
         * 客户端使用的token转换方式，只需提供publickey即可
         */
        jwt,
        /**
         * uaa中心使用的token转换方式，需提密钥对
         */
        authJwt,
        /**
         * 数据库储存方式
         */
        db
    }
}
