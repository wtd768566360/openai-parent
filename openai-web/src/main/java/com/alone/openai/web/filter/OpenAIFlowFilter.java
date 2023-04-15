package com.alone.openai.web.filter;

import com.alone.openai.common.constant.Constant;
import com.alone.openai.web.service.FlowService;
import com.alone.openai.web.service.UserJwtService;
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

/**
 * 针对于openai接口调度次数统计
 */
@Component
@Order(4)
public class OpenAIFlowFilter implements WebFilter {

    private final FlowService flowService;

    private final UserJwtService userJwtService;

    private static List<String> OPENAI_INTERFACE = new ArrayList<>();

    public OpenAIFlowFilter(FlowService flowService, UserJwtService userJwtService) {
        this.flowService = flowService;
        this.userJwtService = userJwtService;
        OPENAI_INTERFACE.add("/chat/**");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        if (isOpenAiInterface(path)) {
            //获取token
            String token = exchange.getRequest().getHeaders().getFirst(Constant.JWT_TOKEN);
            String userId = userJwtService.get(token);
            Integer unused = flowService.get(userId);
            if (unused == null || unused <= 0) {
                //剩余次数为null或者小于等于0,就返回错误信息给前端
                return failure403(exchange);
            } else {
                //接口次数减1
                flowService.put(userId, --unused);
            }
        }
        return chain.filter(exchange);
    }

    /**
     * 是否是openai的接口
     */
    private boolean isOpenAiInterface(String path) {
        boolean flag = false;
        for (String noPath : OPENAI_INTERFACE) {
            AntPathMatcher matcher = new AntPathMatcher();
            if (matcher.match(noPath, path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 接口次数用完，返回403状态码
     */
    public Mono<Void> failure403(ServerWebExchange exchange) {
        // 未登录，返回401 Unauthorized状态码
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}
