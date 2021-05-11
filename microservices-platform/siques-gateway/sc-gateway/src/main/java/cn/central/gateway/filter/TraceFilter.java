package cn.central.gateway.filter;

import cn.central.common.constant.CommonConstant;
import cn.central.log.properties.TraceProperties;
import cn.hutool.core.util.IdUtil;
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
 * 分布式链路追踪ID向下游传递
 * @author : heshenghao
 * @date : 17:38 2020/11/22
 */
@Component
public class TraceFilter implements GlobalFilter , Ordered {

    @Autowired
    private TraceProperties traceProperties;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 初始化追踪id
        if(traceProperties.getEnable()){
            // 生成UUID
            String traceId = IdUtil.fastSimpleUUID();
            //通过 MDC进行传递
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
