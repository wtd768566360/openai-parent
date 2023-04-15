package com.alone.openai.admin.service;

import com.alone.openai.common.response.Restful;

public interface FlowService {

    Restful query(int page, int limit, String account, String name, String email);

    int totalAdd(String userId, Integer number);

    int syncCacheToDatabase();

    Long statistics();
}
