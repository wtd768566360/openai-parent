package com.alone.openai.web.config;

import com.alone.openai.client.entity.completion.ChatRequestDto;
import com.alone.openai.common.constant.Constant;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CacheConfig {

    /**
     * 用jwt作为key,用户id作为value
     *
     * @return
     */
    @Bean(name = "jwt-cache")
    public Cache<String, String> jwtCache() {
        return CacheBuilder
                .newBuilder()
                // 设置并发级别为1000，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(1000)
                // 设置读取缓存后的过期策略
                .expireAfterAccess(Constant.EXPIRE_TIME_DAY, TimeUnit.DAYS)
                // 设置写缓存后2分钟过期
                .expireAfterWrite(Constant.EXPIRE_TIME_DAY, TimeUnit.DAYS)
                // 设置缓存容器的初始容量为100
                .initialCapacity(100)
                // 设置缓存最大容量为10000，超过10000之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(10000)
                // 设置要统计缓存的命中率
                .recordStats().build();
    }

    /**
     * 用用户邮箱作为key,发送的code作为value
     *
     * @return
     */
    @Bean(name = "email-cache")
    public Cache<String, String> emailCache() {
        return CacheBuilder
                .newBuilder()
                // 设置并发级别为1000，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(1000)
                // 设置读取缓存后的过期策略
                .expireAfterAccess(1, TimeUnit.MINUTES)
                // 设置写缓存后10分钟过期
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 设置缓存容器的初始容量为100
                .initialCapacity(100)
                // 设置缓存最大容量为10000，超过10000之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(10000)
                // 设置要统计缓存的命中率
                .recordStats().build();
    }

    /**
     * 用户ID作为key,表示还剩余多少次请求.
     */
    @Bean(name = "flow-cache")
    public Cache<String, Integer> flowCache() {
        return CacheBuilder
                .newBuilder()
                // 设置并发级别为1000，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(1000)
                // 设置读取缓存后的过期策略
                .expireAfterAccess(1, TimeUnit.DAYS)
                // 设置写缓存后过去策略
                .expireAfterWrite(1, TimeUnit.DAYS)
                // 设置缓存容器的初始容量为100
                .initialCapacity(100)
                // 设置缓存最大容量为10000，超过10000之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(10000)
                // 设置要统计缓存的命中率
                .recordStats().build();
    }

    /**
     * 用uuid做key,存储用户每次使用chat接口前的一个参数转存
     */
    @Bean(name = "chat-params-cache")
    public Cache<String, List<ChatRequestDto.Message>> chatParamsCache() {
        return CacheBuilder
                .newBuilder()
                // 设置并发级别为1000，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(1000)
                // 设置读取缓存后的过期策略  读取后10秒过期
                .expireAfterAccess(30, TimeUnit.SECONDS)
                // 设置写缓存后过去策略   写入后2分钟过期
                .expireAfterWrite(2, TimeUnit.MINUTES)
                // 设置缓存容器的初始容量为100
                .initialCapacity(100)
                // 设置缓存最大容量为10000，超过10000之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(10000)
                // 设置要统计缓存的命中率
                .recordStats().build();
    }

}
