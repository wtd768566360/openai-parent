package com.alone.openai.web.service;


import com.alone.openai.web.pojo.entity.UserEntity;

public interface UserService {

    UserEntity login(String account, String password);

    int password(UserEntity user, String oldPassword, String newPassword);

    UserEntity findById(String id);

    int modify(String id, String name, String phone, String email);

    UserEntity findByEmail(String mail);

    UserEntity create(UserEntity user);

    int forgetPassword(String email, String account, String password);

    UserEntity findByEmailAndAccount(String email, String account);

    UserEntity findByAccount(String account);
}
