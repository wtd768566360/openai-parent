package com.alone.openai.web.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(0)
public class CorsFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        String origin = exchange.getRequest().getHeaders().getOrigin();
        headers.add("Access-Control-Allow-Origin", origin);
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "token, content-type");
        headers.add("Access-Control-Max-Age", "3600");

        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
//            return Mono.empty();
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}
