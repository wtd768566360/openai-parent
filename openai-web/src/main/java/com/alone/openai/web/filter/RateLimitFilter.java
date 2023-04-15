package com.alone.openai.web.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.Semaphore;

/**
 * 限流拦截器项目级别,整个项目最多同时处理100个请求,优先级最高
 */
@Component
@Order(1)
public class RateLimitFilter implements WebFilter {

    private final Semaphore semaphore;

    public RateLimitFilter() {
        // 设置最大并发数为100
        this.semaphore = new Semaphore(100);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.defer(() -> {
            if (semaphore.tryAcquire()) {
                return chain.filter(exchange)
                        .doFinally(signalType -> semaphore.release()); // 请求处理完成后释放许可证
            } else {
                //如果没有资源了,返回429状态码
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            }
        });
    }
}
