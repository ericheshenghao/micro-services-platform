# Spring Cloud in Practice

This project can be used as a starter for spring cloud micro services development. It is the micro services version of [Spring Boot in Practice](https://www.siques.cn/p/125). It use [Spring Cloud Nacos](https://nacos.io/zh-cn/docs/quick-start.html) for service discovery, [Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/) to implement api gateway. The graphql api service exposed by gateway can be used as the backend api service for this flutter app [Flutter in Practice](https://github.com/jaggerwang/flutter-in-practice). There is an article [Spring Cloud 后端开发详解](https://nacos.io/zh-cn/docs/quick-start.html) for learning this project.

## Dependent frameworks and packages

1. [Spring Boot](https://spring.io/projects/spring-boot) Web framework and server
1. [Mybatis-plus](https://mp.baomidou.com) Access database

1. [Spring Security](https://spring.io/projects/spring-security) Authenticate and authrorize
1. [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) Api gateway
1. [Spring Cloud Nacos](https://nacos.io/zh-cn/docs/quick-start.html) Service discovery
1. [Spring Cloud Sentinel](https://github.com/alibaba/Sentinel) Circuit breaker
1. [Spring Cloud openfeign](https://github.com/OpenFeign) Inter service call
1. [Spring Cloud RabbitMq](https://www.rabbitmq.com/) Message queuing

## Microservice Architecture

<<<<<<< HEAD
![架构图](https://eric-he.oss-cn-beijing.aliyuncs.com/git/%E5%BE%AE%E6%9C%8D%E5%8A%A1%E6%9E%B6%E6%9E%84sy.jpg)
=======
![架构图](https://shuxie.oss-cn-hangzhou.aliyuncs.com/public/%E5%BE%AE%E6%9C%8D%E5%8A%A1%E6%9E%B6%E6%9E%84sy.jpg)
>>>>>>> 428116391748fcfc080763336343fb0a0c98a184
### With Token

![spring-cloud-micro-service-architecture](https://user-images.githubusercontent.com/1255011/80553599-06adb500-89fd-11ea-9cf8-f887e156e51b.png)


## How to run the server

This project need java 8+.


### By docker compose

#### Package all modules

```bash
cd ./server
./mvnw package
```

#### Start all services at once

```bash
docker-compose up
```

If you repackaged any module, you should add `--build` option to enable the new jar package.

Then you can access all APIs at `http://localhost:9001` or `http://localhost:9002` .

## How to run the client

```bash
cd ./mango-front
npm i 
npm run dev
```
Then you can access the dashboard at `http://localhost:3000` .

## live demo
[http://139.198.189.114/](http://139.198.189.114/)  
username:admin  
password:admin  


