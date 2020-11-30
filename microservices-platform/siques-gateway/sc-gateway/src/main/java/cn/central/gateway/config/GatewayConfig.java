package cn.central.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

@Configuration
public class GatewayConfig{

    private final List<ViewResolver> views;
    private final ServerCodecConfigurer configurer;

    public GatewayConfig(ObjectProvider<List<ViewResolver>> views,
                         ServerCodecConfigurer config) {
        this.views = views.getIfAvailable(Collections::emptyList);
        this.configurer = config;
    }

    /**
     * 配置SentinelGatewayBlockExceptionHandler，限流后异常处理
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public JsonSentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        //return new SentinelGatewayBlockExceptionHandler(views, configurer);
        return new JsonSentinelGatewayBlockExceptionHandler(views, configurer);
    }

    /**
     * Sentinel 过滤器
     * @return
     */
    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

}