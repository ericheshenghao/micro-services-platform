package cn.central.log.config;


import cn.central.log.properties.AuditLogProperties;
import cn.central.log.properties.LogDbProperties;
import cn.central.log.properties.TraceProperties;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动配置
 * 开启TraceProperties 与  AuditLogProperties 配置检测
 * @author he
 * @date 2019/8/13
 */
@EnableConfigurationProperties({TraceProperties.class, AuditLogProperties.class})
public class LogAutoConfigure {
    /**
     * 日志数据库配置
     */
    @Configuration
    @ConditionalOnClass(HikariConfig.class)
    @EnableConfigurationProperties(LogDbProperties.class)
    public static class LogDbAutoConfigure {}
}
