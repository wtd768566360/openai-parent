package com.alone.openai.admin.service;

import java.util.List;
import java.util.Map;

public interface WelcomeService {

    Map<String, Long> statistics();

    Map<String, List<Object>> echartsRecords();
}
