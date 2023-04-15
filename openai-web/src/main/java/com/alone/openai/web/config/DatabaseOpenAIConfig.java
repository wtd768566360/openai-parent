package com.alone.openai.web.config;

import com.alone.openai.client.spi.OpenAIConfig;
import com.alone.openai.web.pojo.entity.ApiKeyEntity;
import com.alone.openai.web.service.ApiKeyService;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DatabaseOpenAIConfig implements OpenAIConfig {

    private final ApiKeyService apiKeyService;

    public DatabaseOpenAIConfig(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    public List<String> databaseOpenAIKey() {
        List<ApiKeyEntity> apiKeyEntities = apiKeyService.findAll();
        return apiKeyEntities.stream()
                .map(ApiKeyEntity::getApiKey)
                .collect(Collectors.toList());
    }
}
