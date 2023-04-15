package com.alone.openai.admin;

import com.alone.openai.admin.config.FeignConfiguration;
import com.alone.openai.admin.properties.OpenAIAdminProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
@EnableScheduling
@EnableConfigurationProperties({OpenAIAdminProperties.class})
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
