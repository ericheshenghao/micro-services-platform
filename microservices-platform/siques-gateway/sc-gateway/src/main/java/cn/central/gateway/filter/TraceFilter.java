package cn.central.gateway.filter;

import cn.central.common.constant.CommonConstant;
import cn.central.log.properties.TraceProperties;
import cn.hutool.core.util.IdUtil;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author : heshenghao
 * @date : 17:38 2020/11/22
 */
@Component
public class TraceFilter implements GlobalFilter , Ordered {

    @Autowired
    private TraceProperties traceProperties;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 开启链路追踪id
        // 最顶层生成追踪id
        if(traceProperties.getEnable()){
            String traceId = IdUtil.fastSimpleUUID();
            MDC.put(CommonConstant.LOG_TRACE_ID,traceId);
            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
                    .headers(h -> h.add(CommonConstant.TRACE_ID_HEADER, traceId))
                    .build();
            ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
            return chain.filter(build);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
