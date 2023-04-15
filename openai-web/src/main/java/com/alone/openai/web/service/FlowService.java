package com.alone.openai.web.service;

import com.alone.openai.web.pojo.entity.FlowEntity;

public interface FlowService {

    /**
     * 创建
     */
    void create(FlowEntity flowEntity);

    /**
     * 根据用户ID获取剩余多少请求次数,读取的缓存
     */
    Integer get(String userId);

    /**
     * 根据用户ID获取剩余多少次数,读取的数据库
     */
    FlowEntity findByUserId(String userId);

    /**
     * 根据用户ID更新数量
     */
    FlowEntity update(FlowEntity flowEntity);

    /**
     * 向某个用户的缓存中添加次数
     */
    void add(String userId, Integer number);

    /**
     * 向缓存放入数据
     */
    void put(String userId, Integer number);

    /**
     * 根据用户id,删除缓存
     */
    void remove(String userId);

    void syncCacheToDatabase();

}
