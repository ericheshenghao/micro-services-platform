package cn.central.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 审计日志配置类
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "siques.audit-log")
@RefreshScope
public class AuditLogProperties {
    /**
     * 是否开启审计日志
     */
    private Boolean enabled = false;
    /**
     * 日志记录类型(logger/redis/db/es)
     */
    private LogType logType = LogType.logger;

    private enum LogType {
        logger,
        redis,
        db,
        es
    }
}
