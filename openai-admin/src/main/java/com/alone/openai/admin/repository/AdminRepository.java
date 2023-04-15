package com.alone.openai.admin.repository;

import com.alone.openai.admin.pojo.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, String>, JpaSpecificationExecutor<AdminEntity> {

    AdminEntity findByAccountAndPassword(String account, String password);

    AdminEntity findByAccount(String account);
}
