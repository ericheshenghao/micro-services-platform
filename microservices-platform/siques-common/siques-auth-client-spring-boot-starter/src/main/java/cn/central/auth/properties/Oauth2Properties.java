package cn.central.auth.properties;

import lombok.Getter;
import lombok.Setter;
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
    public static final String[] type = {
            "db",
            "redis"
    };
}
