package com.alone.openai.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alone.openai.admin.api.WebClient;
import com.alone.openai.admin.pojo.entity.AdminEntity;
import com.alone.openai.admin.pojo.entity.ApiKeyEntity;
import com.alone.openai.admin.repository.ApiKeyRepository;
import com.alone.openai.admin.service.ApiKeyService;
import com.alone.openai.common.response.Restful;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    private final WebClient webClient;

    public ApiKeyServiceImpl(ApiKeyRepository apiKeyRepository, WebClient webClient) {
        this.apiKeyRepository = apiKeyRepository;
        this.webClient = webClient;
    }

    @Override
    public Restful query(int page, int limit, List<String> state, String origin) {
        Specification<ApiKeyEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            // 查询条件的集合
            List<Predicate> list = new ArrayList<>();
            if (StrUtil.isNotEmpty(origin)) {
                list.add(criteriaBuilder.like(root.get("origin"), "%" + origin + "%"));
            }
            list.add(criteriaBuilder.in(root.get("state")).value(state));
            // 转数组
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        // 指定排序和分页
        Pageable pageable = PageRequest.of(page < 1 ? 0 : page - 1, limit, Sort.by("createTime"));
        Page<ApiKeyEntity> result = apiKeyRepository.findAll(specification, pageable);
        return Restful.builder().count((int) result.getTotalElements()).msg("数据查询成功").data(result.getContent()).build();
    }

    @Override
    public int create(AdminEntity admin, String key, String origin) {
        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setId(StrUtil.uuid().replace("-", ""));
        apiKeyEntity.setApiKey(key);
        apiKeyEntity.setOrigin(origin);
        apiKeyEntity.setAdmin(admin);
        apiKeyEntity.setState(ApiKeyEntity.STATE_AVAILABLE);
        Long dateTime = DateUtil.date().getTime();
        apiKeyEntity.setCreateTime(dateTime);
        apiKeyEntity.setUpdateTime(dateTime);
        apiKeyRepository.save(apiKeyEntity);
        //访问web应用的接口,删除key
        Restful restful = webClient.keyAdd(key);
        return restful.getCode() == Restful.CODE_OK ? 1 : 0;
    }

    @Override
    public int available(String id, String state) {
        ApiKeyEntity apiKeyEntity = apiKeyRepository.findById(id).get();
        apiKeyEntity.setState(state);
        apiKeyRepository.save(apiKeyEntity);
        if (ApiKeyEntity.STATE_AVAILABLE.equals(state)) {
            Restful restful = webClient.keyAdd(apiKeyEntity.getApiKey());
            return restful.getCode() == Restful.CODE_OK ? 1 : 0;
        } else if (ApiKeyEntity.STATE_DISABLE.equals(state)) {
            Restful restful = webClient.keyDelete(apiKeyEntity.getApiKey());
            return restful.getCode() == Restful.CODE_OK ? 1 : 0;
        } else {
            return 0;
        }
    }

    @Transient
    @Override
    public int delete(String id) {
        //访问web应用的接口,删除key
        ApiKeyEntity apiKeyEntity = apiKeyRepository.findById(id).get();
        Restful restful = webClient.keyDelete(apiKeyEntity.getApiKey());
        apiKeyRepository.deleteById(id);
        return restful.getCode() == Restful.CODE_OK ? 1 : 0;
    }

    @Override
    public Long statistics() {
        return apiKeyRepository.count();
    }
}
