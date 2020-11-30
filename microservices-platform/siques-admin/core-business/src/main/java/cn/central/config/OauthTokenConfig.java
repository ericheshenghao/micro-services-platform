package cn.central.config;

import cn.central.common.config.OauthResourceTokenConfig;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : heshenghao
 * @date : 9:04 2020/11/20
 */
@Configuration
public class OauthTokenConfig extends OauthResourceTokenConfig {

    public OauthTokenConfig(ResourceServerProperties resourceServerProperties) {
        super(resourceServerProperties);
    }
}
