package cn.central.oauth;

import cn.central.common.ribbon.annotation.EnableBaseFeignInterceptor;
import cn.central.common.ribbon.annotation.EnableFeignInterceptor;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("cn.central.oauth.dao")
@EnableSwagger2Doc
@EnableFeignInterceptor
@EnableFeignClients(basePackages = "cn.central.common.feign")
public class UaaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

}
