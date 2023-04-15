package com.alone.openai.admin.service;


import com.alone.openai.admin.pojo.entity.AdminEntity;
import com.alone.openai.common.response.Restful;

import java.util.List;

public interface ApiKeyService {

    Restful query(int page, int limit, List<String> state, String origin);

    int create(AdminEntity admin, String key, String origin);

    int available(String id, String state);

    int delete(String id);

    Long statistics();
}
