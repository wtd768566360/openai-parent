package com.alone.openai.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alone.openai.admin.api.WebClient;
import com.alone.openai.admin.pojo.dto.flow.FlowAddDto;
import com.alone.openai.admin.pojo.entity.FlowEntity;
import com.alone.openai.admin.pojo.entity.UserEntity;
import com.alone.openai.admin.repository.FlowRepository;
import com.alone.openai.admin.service.FlowService;
import com.alone.openai.common.response.Restful;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlowServiceImpl implements FlowService {

    private final FlowRepository flowRepository;

    private final WebClient webClient;

    public FlowServiceImpl(FlowRepository flowRepository, WebClient webClient) {
        this.flowRepository = flowRepository;
        this.webClient = webClient;

    }


    @Override
    public Restful query(int page, int limit, String account, String name, String email) {
        Specification<FlowEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Join<FlowEntity, UserEntity> join = root.join("user", JoinType.INNER);
            // 查询条件的集合
            List<Predicate> list = new ArrayList<>();
            if (StrUtil.isNotEmpty(account)) {
                list.add(criteriaBuilder.like(join.get("account"), "%" + account + "%"));
            }
            if (StrUtil.isNotEmpty(name)) {
                list.add(criteriaBuilder.like(join.get("name"), "%" + name + "%"));
            }
            if (StrUtil.isNotEmpty(email)) {
                list.add(criteriaBuilder.like(join.get("email"), "%" + email + "%"));
            }
            // 转数组
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        // 指定排序和分页
        Pageable pageable = PageRequest.of(page < 1 ? 0 : page - 1, limit, Sort.by(Sort.Direction.DESC, "updateTime"));
        Page<FlowEntity> result = flowRepository.findAll(specification, pageable);
        return Restful.builder().count((int) result.getTotalElements()).msg("数据查询成功").data(result.getContent()).build();
    }

    @Override
    public int totalAdd(String userId, Integer number) {
        FlowEntity flowEntity = flowRepository.findByUserId(userId);
        flowEntity.setTotal(flowEntity.getTotal() + number);
        flowEntity.setUnused(flowEntity.getUnused() + number);
        flowRepository.save(flowEntity);
        FlowAddDto flowAddDto = new FlowAddDto();
        flowAddDto.setUserId(userId);
        flowAddDto.setNumber(number);
        //向web应用写入实时数据
        Restful restful = webClient.flowAdd(flowAddDto);
        return restful.getCode() == Restful.CODE_OK ? 1 : 0;
    }

    @Override
    public int syncCacheToDatabase() {
        Restful restful = webClient.flowSync();
        return restful.getCode() == Restful.CODE_OK ? 1 : 0;
    }

    @Override
    public Long statistics() {
        return flowRepository.sumUsed();
    }
}
