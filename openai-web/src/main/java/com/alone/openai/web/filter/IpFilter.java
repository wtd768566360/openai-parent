package com.alone.openai.web.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 针对ip的限流,定义某个ip在3秒内只能访问一次接口
 */
@Component
@Order(2)
public class IpFilter implements WebFilter {

    private static final Map<String, RateLimiter> IP_RATE_LIMITER_MAP = new ConcurrentHashMap<>();

    private static List<String> NO_RATE_PATH = new ArrayList<>();

    static {
        NO_RATE_PATH.add("/api-doc");
        NO_RATE_PATH.add("/webjars/swagger-ui/**");
        NO_RATE_PATH.add("/v3/**");
    }

    //表示三秒生成一个令牌
    private final double limit = 1;

    //尝试获取令牌时间为1秒
    private final int timeout = 1;

    public IpFilter() {

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        if (isRateLimit(path)) {
            return chain.filter(exchange);
        }
        String ip = exchange.getRequest().getRemoteAddress().getHostString();
        RateLimiter rateLimiter = IP_RATE_LIMITER_MAP.computeIfAbsent(ip, k -> RateLimiter.create(limit));
        if (rateLimiter.tryAcquire(timeout, TimeUnit.SECONDS)) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
    }

    /**
     * 针对某些接口不做限流
     *
     * @return
     */
    public boolean isRateLimit(String path) {
        boolean flag = false;
        for (String noPath : NO_RATE_PATH) {
            AntPathMatcher matcher = new AntPathMatcher();
            if (matcher.match(noPath, path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}