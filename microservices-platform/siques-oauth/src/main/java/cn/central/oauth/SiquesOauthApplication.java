package cn.central.oauth;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("cn.central.oauth.dao")
@EnableResourceServer
@EnableSwagger2Doc
public class SiquesOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiquesOauthApplication.class, args);
    }

}
