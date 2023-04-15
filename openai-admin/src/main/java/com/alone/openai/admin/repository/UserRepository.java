package com.alone.openai.admin.repository;

import com.alone.openai.admin.pojo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {
    
    List<UserEntity> findByCreateTimeBetween(Long startTime, Long endTime);
}