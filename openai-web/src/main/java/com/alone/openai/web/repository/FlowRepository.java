package com.alone.openai.web.repository;

import com.alone.openai.web.pojo.entity.FlowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowRepository extends JpaRepository<FlowEntity, String>, JpaSpecificationExecutor<FlowEntity> {
    FlowEntity findByUserId(String userId);
}
