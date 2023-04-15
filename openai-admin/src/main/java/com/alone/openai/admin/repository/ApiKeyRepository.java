package com.alone.openai.admin.repository;

import com.alone.openai.admin.pojo.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, String>, JpaSpecificationExecutor<ApiKeyEntity> {
}
