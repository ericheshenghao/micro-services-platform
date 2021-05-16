package cn.central.config;

import cn.central.common.config.ElPermissionConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Service;

/**
 * el表达式配置
 * @author : heshenghao
 * @date : 23:36 2020/11/19
 */
@Service(value = "el")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PermissionConfig extends ElPermissionConfig {
}
