package com.alone.openai.web.repository;

import com.alone.openai.web.pojo.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeEntity, String>, JpaSpecificationExecutor<UserTypeEntity> {
    
    UserTypeEntity findByCode(String user);
}
