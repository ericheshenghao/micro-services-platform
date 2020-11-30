package cn.central.common.ribbon.annotation;


import cn.central.common.ribbon.config.FeignHttpInterceptorConfig;
import cn.central.common.ribbon.config.FeignInterceptorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启feign拦截器传递数据给下游服务，包含基础数据和http的相关数据
 *
 * @author zlt
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// 开启的话导入下面两个类
@Import({FeignInterceptorConfig.class, FeignHttpInterceptorConfig.class})
public @interface EnableFeignInterceptor {

}
