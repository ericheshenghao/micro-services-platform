package cn.central.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : heshenghao
 * @date : 18:56 2021/5/15
 */
@EnableDiscoveryClient
@MapperScan("cn.central.account.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}