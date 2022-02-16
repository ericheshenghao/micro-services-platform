package cn.siques.activiti.config;


import cn.central.auth.config.resource.DefaultResourceServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author he
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends DefaultResourceServerConfig {

}