package com.alone.openai.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alone.openai.admin.pojo.entity.UserEntity;
import com.alone.openai.admin.service.ApiKeyService;
import com.alone.openai.admin.service.FlowService;
import com.alone.openai.admin.service.UserService;
import com.alone.openai.admin.service.WelcomeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WelcomeServiceImpl implements WelcomeService {

    private final UserService userService;

    private final FlowService flowService;

    private final ApiKeyService apiKeyService;

    public WelcomeServiceImpl(UserService userService, FlowService flowService,
                              ApiKeyService apiKeyService) {
        this.userService = userService;
        this.flowService = flowService;
        this.apiKeyService = apiKeyService;
    }

    @Override
    public Map<String, Long> statistics() {
        //获取总的用户数
        Long userCount = userService.statistics();
        //获取今天注册的用户数
        Long todayUser = userService.todayUser();
        //获取总的流量
        Long flowCount = flowService.statistics();
        //获取apikey的总数
        Long apiKey = apiKeyService.statistics();
        Map<String, Long> result = new HashMap<>();
        result.put("user", userCount);
        result.put("todayUser", todayUser);
        result.put("flow", flowCount);
        result.put("apiKey", apiKey);
        return result;
    }

    @Override
    public Map<String, List<Object>> echartsRecords() {
        Long startTime = DateUtil.beginOfMonth(DateUtil.date()).getTime();
        Long endTime = DateUtil.date().getTime();
        List<UserEntity> userEntities = userService.findByCreateTimeBetween(startTime, endTime);
        Map<String, Integer> map = new HashMap<>(50);
        for (UserEntity user : userEntities) {
            String createTime = DateUtil.format(new Date(user.getCreateTime()), "yyyy-MM-dd");
            int count = 1;
            if (map.containsKey(createTime)) {
                count += map.get(createTime);
            }
            map.put(createTime, count);
        }
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> date = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        for (; startTime < endTime; startTime += 86400000) {
            String createTime = DateUtil.format(new Date(startTime), "yyyy-MM-dd");
            date.add(createTime);
            int count = 0;
            if (map.containsKey(createTime)) {
                count = map.get(createTime);
            }
            data.add(count);
        }
        result.put("date", date);
        result.put("data", data);
        return result;
    }
}
