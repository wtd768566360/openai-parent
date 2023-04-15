package com.alone.openai.web.repository;

import com.alone.openai.web.pojo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByAccountAndPassword(String account, String password);

    UserEntity findByEmail(String email);

    UserEntity findByEmailAndAccount(String email, String account);

    UserEntity findByAccount(String account);
}