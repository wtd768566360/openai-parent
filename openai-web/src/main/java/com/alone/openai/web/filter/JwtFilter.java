package com.alone.openai.web.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.alone.openai.common.constant.Constant;
import com.alone.openai.web.service.UserJwtService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 身份验证
 */
@Component
@Order(3)
public class JwtFilter implements WebFilter {

    public final UserJwtService userJwtService;

    private static List<String> NO_VALIDATED_PATH = new ArrayList<>();

    private static List<String> IS_ADMIN = new ArrayList<>();

    public JwtFilter(UserJwtService userJwtService) {
        this.userJwtService = userJwtService;

        NO_VALIDATED_PATH.add("/system/login");
        NO_VALIDATED_PATH.add("/email/create/code");
        NO_VALIDATED_PATH.add("/email/find/account");
        NO_VALIDATED_PATH.add("/email/forget/password/code");
        NO_VALIDATED_PATH.add("/system/registry");
        NO_VALIDATED_PATH.add("/system/forget/password");
//        NO_VALIDATED_PATH.add("/webjars/swagger-ui/**");
        NO_VALIDATED_PATH.add("/api-doc");
//        NO_VALIDATED_PATH.add("/v3/**");

        IS_ADMIN.add("/admin/**");

    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 获取Session
        Mono<WebSession> sessionMono = exchange.getSession();
        String path = exchange.getRequest().getPath().value();
        return sessionMono.flatMap(session -> {
            //是否需要jwt验证
            if (isNoValidated(path)) {
                return chain.filter(exchange);
            }
            //判断jwt
            String token = exchange.getRequest().getHeaders().getFirst(Constant.JWT_TOKEN);
            try {
                //是否是admin接口
                if (StrUtil.isNotEmpty(token)) {
                    if (isAdmin(path)) {
                        //是admin的操作
                        if (Constant.ADMIN_TOKEN.equals(token)) {
                            return chain.filter(exchange);
                        } else {
                            return failure403(exchange);
                        }
                    }
                    //查看是否携带了token,并且token的格式是正确的
                    if (JWTUtil.verify(token, Constant.JWT_SIGNER)) {
                        //再检查jwt在缓存中是否存在
                        if (userJwtService.get(token) != null) {
                            //如果验证失败,会跳转到catch
                            JWTValidator.of(token).validateDate();
                            return chain.filter(exchange);
                        }
                    }
                }
            } catch (Exception e) {
                return failure401(exchange);
            }
            return failure401(exchange);
        });
    }

    /**
     * 是否需要验证jwt
     * 需要验证返回false
     * 不需要验证返回true
     */
    public boolean isNoValidated(String path) {
        boolean flag = false;
        for (String noPath : NO_VALIDATED_PATH) {
            AntPathMatcher matcher = new AntPathMatcher();
            if (matcher.match(noPath, path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 是否是admin访问
     */
    public boolean isAdmin(String path) {
        boolean flag = false;
        for (String noPath : IS_ADMIN) {
            AntPathMatcher matcher = new AntPathMatcher();
            if (matcher.match(noPath, path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /**
     * 未登录，返回401 Unauthorized状态码
     */
    public Mono<Void> failure401(ServerWebExchange exchange) {
        // 未登录，返回401 Unauthorized状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    /**
     * 越权操作，返回403
     */
    public Mono<Void> failure403(ServerWebExchange exchange) {
        // 未登录，返回401 Unauthorized状态码
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}
