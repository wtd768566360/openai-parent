package com.alone.openai.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "openai")
public class OpenAIProperties {

    private String baseUrl;

    private List<String> apikey;

    private Proxy proxy;

    @Data
    public static class Proxy {
        //是否启动代理
        private boolean enabled;
        //代理IP
        private String host;
        //代理端口
        private Integer port;
    }
}
