package com.alone.openai.client.config;

import com.alone.openai.client.properties.OpenAIProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Configuration
public class WebClientConfig {

    public final static int BYTE_COUNT = 1024 * 1024 * 10;

    private OpenAIProperties openAIProperties;

    public WebClientConfig(OpenAIProperties openAIProperties) {
        this.openAIProperties = openAIProperties;
    }

    @Bean
    public WebClient webClient() {

        // 创建 HttpClient 对象
        HttpClient httpClient = HttpClient.create();
        //是否开启代理
        if (openAIProperties.getProxy().isEnabled()) {
            httpClient = httpClient.tcpConfiguration(client -> client.proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                    .host(openAIProperties.getProxy().getHost())
                    .port(openAIProperties.getProxy().getPort())));

        }
        // 创建 ClientHttpConnector 对象
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        // 创建 ExchangeStrategies 对象，用于指定 HTTP 请求和响应转换的策略
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(BYTE_COUNT))
                .build();

        return WebClient.builder()
                .clientConnector(connector)
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(openAIProperties.getBaseUrl())
                .build();
    }

}
