package com.alone.openai.admin.repository;

import com.alone.openai.admin.pojo.entity.UserJwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJwtRepository extends JpaRepository<UserJwtEntity, String>, JpaSpecificationExecutor<UserJwtEntity> {

    void deleteByUserId(String jwt);

}
