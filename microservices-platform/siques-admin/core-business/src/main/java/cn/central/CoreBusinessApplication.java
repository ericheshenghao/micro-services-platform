package cn.central;


import cn.central.common.ribbon.annotation.EnableFeignInterceptor;
import cn.central.search.annotation.EnableSearchClient;
import com.spring4all.swagger.EnableSwagger2Doc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2Doc
@EnableSearchClient
@EnableFeignClients
@EnableFeignInterceptor
@EnableTransactionManagement
@MapperScan("cn.central.dao")
public class CoreBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreBusinessApplication.class, args);
    }

}
