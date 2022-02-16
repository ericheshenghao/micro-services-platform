package cn.central.config;

import cn.central.auth.config.resource.DefaultResourceServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * 对该资源服务器资源访问路径的配置
 *
 * @author : heshenghao
 * @date : 23:26 2020/11/14
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends DefaultResourceServerConfig {


}
