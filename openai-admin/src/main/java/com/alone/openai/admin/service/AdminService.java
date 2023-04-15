package com.alone.openai.admin.service;

import com.alone.openai.admin.pojo.entity.AdminEntity;

public interface AdminService {

    AdminEntity login(String account, String password);

    int password(AdminEntity admin, String oldPassword, String newPassword);

    int modify(String id, String name, String phone, String email);
}
