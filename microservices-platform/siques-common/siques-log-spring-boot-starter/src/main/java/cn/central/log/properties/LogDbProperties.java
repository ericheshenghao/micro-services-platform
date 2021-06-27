package cn.central.log.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志数据源配置
 * logType=db时生效(非必须)，如果不配置则使用当前数据源
 *
 * @author he
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "siques.audit-log.datasource")
public class LogDbProperties extends HikariConfig {
}
