package cn.central.oauth.config;

import cn.central.auth.config.DefaultResourceServerConfig;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * 资源服务器配置，因为鉴权服务器也属于受保护资源
 * @author : heshenghao
 * @date : 23:26 2020/11/14
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends DefaultResourceServerConfig {


}
