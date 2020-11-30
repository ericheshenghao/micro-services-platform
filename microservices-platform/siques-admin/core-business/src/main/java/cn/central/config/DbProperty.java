package cn.central.config;


import cn.central.controller.dto.DbConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Validated
@ConfigurationProperties("spring")
public class DbProperty {
    /**多数据源地址**/
   private List<DbConfig> multiplesource;
}
