<h1 align="center">Micro-Services-Platform</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.2.10.RELEASE-blue" alt="Downloads"/>
  <img src="https://img.shields.io/badge/Spring%20Cloud-Hoxton.SR8-blue" alt="Downloads"/>
  <img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2.2.3.RELEASE-blue" alt="Downloads"/>
  <img src="https://img.shields.io/badge/Elasticsearch-7.x-brightgreen" alt="Downloads"/>
</p>
## 架构图
![img](https://cdn.siques.cn/post/1/2020-12/275992%E6%9E%B6%E6%9E%84.png)
## 项目介绍
基于SpringBoot2.2、SpringCloud和SpringCloudAlibaba并采用前后端分离的企业级微服务多租户系统架构。并引入组件化的思想实现高内聚低耦合并且高度可配置化。实现了基于RBAC、jwt和oauth2的无状态统一权限认证的解决方案，使用rancher搭建kubernetes集群，支持CI/CD多环境部署，并提供应用管理方便第三方系统接入；同时还集合各种微服务治理功能和监控功能。模块包括:企业级的认证系统、开发平台、应用监控、慢sql监控、统一日志、配置中心、分布式任务调度、接口文档、代码生成等等。

## 项目演示
* **演示环境地址**： 加紧上线中
  * 账号密码：corpAdmin/123456
  * APM监控账号密码：admin/admin
  * Grafana账号：admin/heshenghao
  * txlcn事务管理器密码：admin
  * Sentinel：sentinel/sentinel
* **演示环境有全方位的监控示例：日志系统 + APM系统 + GPE系统**
* Gitee地址：https://gitee.com/ericheshenghao/micro-services-platform
* Github地址：https://github.com/ericheshenghao/micro-services-platform
* 前后端分离的企业级微服务架构
* 主要针对解决微服务和业务开发时常见的**非功能性需求**
* 深度定制`Spring Security`真正实现了基于`RBAC`、`jwt`和`oauth2`的无状态统一权限认证的解决方案
* 提供应用管理，方便第三方系统接入。
* 引入组件化的思想实现高内聚低耦合并且高度可配置化
* 注重代码规范，严格控制包依赖，每

## 项目模块说明
```lua
microservices-fontend -- 前端父项目，使用 nuxtjs+antdv开发
│  ├─api -- 封装api请求
│  ├─components -- 封装组件
│  ├─layouts -- 布局
│  ├─pages -- 路由页面
│  ├─plugins -- 插件
│  ├─pro-layout -- 修改pro-layout包以适应本项目
microservices-platform -- 父项目，公共依赖
│  ├─docker-compose -- 组件部署yml
│  ├─manifests -- kubenetes部署yml
│  ├─siques-admin -- 业务模块一级工程
│  │  ├─core-business -- 核心组件[8083]
│  │  ├─search-center -- 搜索中心
│  │  │  ├─search-client -- 搜索中心客户端
│  │  │  ├─search-server -- 搜索中心服务端[7100]
│  │─siques-commons -- 通用工具一级工程
│  │  ├─siques-auth-client-spring-boot-starter -- 封装spring security client端的通用操作逻辑
│  │  ├─common-core -- 封装通用操作逻辑
│  │  ├─siques-common-spring-boot-starter -- 封装通用操作逻辑
│  │  ├─siques-db-spring-boot-starter -- 封装数据库通用操作逻辑
│  │  ├─siques-log-spring-boot-starter -- 封装log通用操作逻辑
│  │  ├─siques-redis-spring-boot-starter -- 封装Redis通用操作逻辑
│  │  ├─siques-ribbon-spring-boot-starter -- 封装Ribbon和Feign的通用操作逻辑
│  │  ├─siques-sentinel-spring-boot-starter -- 封装Sentinel的通用操作逻辑
│  ├─siques-config -- 配置中心
│  ├─siques-gateway -- api网关一级工程
│  │  ├─sc-gateway -- spring-cloud-gateway[9001]
│  ├─siques-monitor -- 监控一级工程
│  │  ├─log-center -- 日志中心[7200]
│  ├─siques-oauth -- spring-security认证中心[8000]
│  ├─siques-transaction -- 事务一级工程
│  │  ├─txlcn-tm -- tx-lcn事务管理器[7970]
```
## 项目预览（点击可大图预览） 
<table>
    <tr>
        <td><img alt="首页" src="https://cdn.siques.cn/post/1/2020-12/118628image.png"/></td>
        <td><img alt="用户搜索" src="https://cdn.siques.cn/post/1/2020-12/81723image.png"/></td>
    </tr>
	<tr>
        <td><img alt="服务监控" src="https://cdn.siques.cn/post/1/2020-12/159327image.png"/></td>
        <td><img alt="application_metrics" src="https://cdn.siques.cn/post/1/2020-12/266049image.png"/></td>
    </tr>
	<tr>
        <td><img alt="流水线" src="https://cdn.siques.cn/post/1/2020-12/116595image.png"/></td>
        <td><img alt="持续集成" src="https://cdn.siques.cn/post/1/2020-12/88798image.png"/></td>
    </tr>
    <tr>
        <td><img alt="skywalking首页.png" src="https://cdn.siques.cn/post/1/2020-12/78012image.png"/></td>
        <td><img alt="集群部署" src="https://cdn.siques.cn/post/1/2020-12/117775image.png"/></td>
    </tr>
    <tr>
        <td><img alt="菜单管理" src="https://cdn.siques.cn/post/1/2020-12/116795image.png"/></td>
        <td><img alt="权限分配" src="https://cdn.siques.cn/post/1/2020-12/110167image.png"/></td>
    </tr>
    <tr>
        <td><img alt="服务注册发现" src="https://cdn.siques.cn/post/1/2020-12/83014image.png"/></td>
        <td><img alt="应用吞吐量监控" src="https://cdn.siques.cn/post/1/2020-12/195514image.png"/></td>
    </tr>
</table>


