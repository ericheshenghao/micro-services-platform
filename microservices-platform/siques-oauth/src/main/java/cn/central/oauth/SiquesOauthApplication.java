package cn.central.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@MapperScan("cn.central.oauth.dao")
@EnableResourceServer
public class SiquesOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiquesOauthApplication.class, args);
    }

}
