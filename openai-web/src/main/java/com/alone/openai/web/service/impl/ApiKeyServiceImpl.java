package com.alone.openai.web.service.impl;

import com.alone.openai.web.pojo.entity.ApiKeyEntity;
import com.alone.openai.web.repository.ApiKeyRepository;
import com.alone.openai.web.service.ApiKeyService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyServiceImpl(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public List<ApiKeyEntity> findAll() {
        Specification<ApiKeyEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            // 查询条件的集合
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("state"), ApiKeyEntity.STATE_AVAILABLE));
            // 转数组
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        return apiKeyRepository.findAll(specification);
    }
}
