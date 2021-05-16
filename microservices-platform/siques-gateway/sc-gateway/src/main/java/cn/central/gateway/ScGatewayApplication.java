package cn.central.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author
 */
@EnableDiscoveryClient
@SpringBootApplication
@RefreshScope
public class ScGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScGatewayApplication.class, args);
    }

}
