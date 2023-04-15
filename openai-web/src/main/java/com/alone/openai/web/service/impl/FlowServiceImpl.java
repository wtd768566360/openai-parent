package com.alone.openai.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alone.openai.common.constant.Constant;
import com.alone.openai.web.pojo.entity.FlowEntity;
import com.alone.openai.web.repository.FlowRepository;
import com.alone.openai.web.service.FlowService;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@Repository
public class FlowServiceImpl implements FlowService {

    private final Cache<String, Integer> cache;

    private final FlowRepository flowRepository;

    public FlowServiceImpl(@Qualifier("flow-cache") Cache<String, Integer> cache,
                           FlowRepository flowRepository) {
        this.cache = cache;
        this.flowRepository = flowRepository;
    }

    @Override
    public void create(FlowEntity flowEntity) {
        flowEntity.setId(StrUtil.uuid().replace("-", ""));
        flowEntity.setTotal(Constant.REGISTRY_FLOW_TOTAL);
        flowEntity.setUsed(0);
        flowEntity.setUnused(Constant.REGISTRY_FLOW_TOTAL);
        Long dateTime = new Date().getTime();
        flowEntity.setCreateTime(dateTime);
        flowEntity.setUpdateTime(dateTime);
        flowRepository.save(flowEntity);
    }

    @Override
    public Integer get(String id) {
        try {
            return cache.get(id, () -> {
                FlowEntity flowEntity = findByUserId(id);
                if (flowEntity != null) {
                    return flowEntity.getUnused();
                }
                return null;
            });
        } catch (ExecutionException e) {
            return null;
        }
    }


    @Override
    public FlowEntity findByUserId(String userId) {
        return flowRepository.findByUserId(userId);
    }

    @Override
    public FlowEntity update(FlowEntity flowEntity) {
        return flowRepository.save(flowEntity);
    }

    @Override
    public void add(String userId, Integer number) {
        Integer oldNumber = get(userId);
        if (oldNumber != null) {
            oldNumber += number;
        } else {
            oldNumber = number;
        }
        cache.put(userId, oldNumber);
    }

    @Override
    public void put(String userId, Integer number) {
        cache.put(userId, number);
    }

    @Override
    public void remove(String userId) {
        cache.invalidate(userId);
    }

    @Override
    public void syncCacheToDatabase() {
        ConcurrentMap<String, Integer> cacheMap = cache.asMap();
        cacheMap.forEach((s, integer) -> {
            FlowEntity flowEntity = findByUserId(s);
            //使用了多少次
            flowEntity.setUsed(flowEntity.getTotal() - integer);
            //剩余次数
            flowEntity.setUnused(integer);
            //置入更新时间
            flowEntity.setUpdateTime(new Date().getTime());
            update(flowEntity);
        });
    }
}
