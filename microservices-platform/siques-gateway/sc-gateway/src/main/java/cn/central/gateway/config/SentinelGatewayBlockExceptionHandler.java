package cn.central.gateway.config;

import cn.central.common.model.BasicResponse;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.function.Supplier;
import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Sentinel 限流后自定义异常
 *
 * @author he
 * @Date 2020-03-17
 */
public class SentinelGatewayBlockExceptionHandler implements WebExceptionHandler {

    private List<ViewResolver> viewResolvers;
    private List<HttpMessageWriter<?>> messageWriters;

    public SentinelGatewayBlockExceptionHandler(
            List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {

        this.viewResolvers = viewResolvers;
        this.messageWriters = serverCodecConfigurer.getWriters();
    }

    /**
     *
     * @param response
     * @param exchange
     * @return
     */
    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange, String msg) {
        ServerHttpResponse resp = exchange.getResponse();
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        BasicResponse<Object> res = BasicResponse.failed(msg, HttpStatus.SERVICE_UNAVAILABLE.value());
        DataBuffer buffer = resp.bufferFactory().wrap( JSON.toJSONString(res).getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Mono.just(buffer));
    }


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (!BlockException.isBlockException(ex)) {
            return handleBlockedRequest(exchange, ex)
                    .flatMap(response -> writeResponse(response, exchange, "服务暂不可用"));
        }

        return handleBlockedRequest(exchange, ex)
                .flatMap(response -> writeResponse(response, exchange, "系统限流"));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }

    private final Supplier<ServerResponse.Context> contextSupplier = () -> new ServerResponse.Context() {
        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return SentinelGatewayBlockExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return SentinelGatewayBlockExceptionHandler.this.viewResolvers;
        }
    };
}