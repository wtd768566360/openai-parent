package com.alone.openai.web.service;


import com.alone.openai.web.pojo.entity.ApiKeyEntity;

import java.util.List;

public interface ApiKeyService {

    /**
     * 查询所有的可用的key
     */
    List<ApiKeyEntity> findAll();
}
