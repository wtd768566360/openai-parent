package com.alone.openai.admin.service;

import com.alone.openai.admin.pojo.entity.UserEntity;
import com.alone.openai.common.response.Restful;

import java.util.List;

public interface UserService {

    Restful query(int page, int limit, String type, String name, String phone, String email);

    int create(UserEntity user);

    int modify(UserEntity user);

    int available(String id, String state);

    /**
     * 根据用户ID,删除jwt
     */
    int jwtDelete(String userId);

    /**
     * 统计用户
     */
    long statistics();

    /**
     * 统计今日的注册用户
     */
    Long todayUser();

    /**
     * 根据创建时间间隔查询用户
     */
    List<UserEntity> findByCreateTimeBetween(Long startTime, Long endTime);
}
