package com.alone.openai.web.repository;

import com.alone.openai.web.pojo.entity.UserJwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJwtRepository extends JpaRepository<UserJwtEntity, String>, JpaSpecificationExecutor<UserJwtEntity> {

    UserJwtEntity findByJwt(String jwt);

    void deleteByJwt(String jwt);

    List<UserJwtEntity> findAllByUserId(String userId);
}
