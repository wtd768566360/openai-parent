package com.alone.openai.client.config;

import com.alone.openai.client.properties.OpenAIProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.alone.openai.client")
@EnableConfigurationProperties({OpenAIProperties.class})
public class ClientAutoConfig {
}
