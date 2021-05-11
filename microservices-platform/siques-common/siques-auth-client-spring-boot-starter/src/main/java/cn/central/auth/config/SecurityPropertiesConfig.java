package cn.central.auth.config;


import cn.central.auth.properties.Oauth2Properties;
import cn.central.auth.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 开启自定义配置
 * @author he
 * @date 2019/10/7
 */
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityPropertiesConfig {
}
