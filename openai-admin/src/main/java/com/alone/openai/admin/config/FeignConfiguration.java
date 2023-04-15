package com.alone.openai.admin.config;

import com.alone.openai.common.constant.Constant;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("token", Constant.ADMIN_TOKEN);
            requestTemplate.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            // Add any other headers you need
        };
    }

}
