package com.alone.openai.client.config;

import com.alone.openai.client.properties.OpenAIProperties;
import com.alone.openai.client.spi.OpenAIConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApiKeyConfig {

    private final OpenAIProperties openAIProperties;

    private final OpenAIConfig openAIConfig;

    public ApiKeyConfig(OpenAIProperties openAIProperties, @Nullable OpenAIConfig openAIConfig) {
        this.openAIProperties = openAIProperties;
        this.openAIConfig = openAIConfig;
    }

    @Bean(name = "properties-api-keys")
    public List<String> getPropertiesApiKeyList() {
        return openAIProperties.getApikey();
    }

    @Bean(name = "database-api-keys")
    public List<String> getDatabaseApiKeyList() {
        if (openAIConfig == null) {
            return new ArrayList<>();
        }
        return openAIConfig.databaseOpenAIKey();
    }
}
