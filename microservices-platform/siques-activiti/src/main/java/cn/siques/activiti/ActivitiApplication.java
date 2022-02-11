package cn.siques.activiti;

import cn.central.common.ribbon.annotation.EnableFeignInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cn.central")
// 向下传递token, 不注解403
@EnableFeignInterceptor
@MapperScan("cn.siques.activiti.dao")
public class ActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiApplication.class, args);
	}

}
