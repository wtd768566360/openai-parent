package com.alone.openai.admin.repository;

import com.alone.openai.admin.pojo.entity.FlowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowRepository extends JpaRepository<FlowEntity, String>, JpaSpecificationExecutor<FlowEntity> {

    FlowEntity findByUserId(String userId);

    @Query(value = "select sum(used) from flow", nativeQuery = true)
    Long sumUsed();
}
