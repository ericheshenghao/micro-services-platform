package cn.central.oauth.config;

import cn.central.auth.config.DefaultResourceServerConfig;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


/**
 * @author : heshenghao
 * @date : 23:26 2020/11/14
 */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends DefaultResourceServerConfig {
//
//}
