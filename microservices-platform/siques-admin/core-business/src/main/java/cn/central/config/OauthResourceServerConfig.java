package cn.central.config;

import cn.central.auth.config.DefaultResourceServerConf;


import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * @author : heshenghao
 * @date : 23:26 2020/11/14
 */

@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OauthResourceServerConfig extends DefaultResourceServerConf {


}
