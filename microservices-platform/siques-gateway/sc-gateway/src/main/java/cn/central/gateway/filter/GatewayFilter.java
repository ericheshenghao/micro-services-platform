package cn.central.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayFilter implements GlobalFilter, Ordered {
    @Value("${server.port}")
    String port;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       String authorization = String.valueOf(exchange.getRequest().getHeaders().get("authorization"));
       System.out.println(port);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
