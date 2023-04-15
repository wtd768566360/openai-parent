package com.alone.openai.admin.service.impl;

import com.alone.openai.admin.pojo.entity.AdminEntity;
import com.alone.openai.admin.properties.OpenAIAdminProperties;
import com.alone.openai.admin.repository.AdminRepository;
import com.alone.openai.admin.service.AdminService;
import com.alone.openai.common.util.RsaUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final OpenAIAdminProperties openAIAdminProperties;

    public AdminServiceImpl(AdminRepository adminRepository, OpenAIAdminProperties openAIAdminProperties) {
        this.openAIAdminProperties = openAIAdminProperties;
        this.adminRepository = adminRepository;
    }

    @Override
    public AdminEntity login(String account, String password) {
        //根据用户名查询数据
        AdminEntity admin = adminRepository.findByAccount(account);
        if (admin != null) {
            try {
                //先将前端传过来的密文解密
                String httpText = RsaUtil.decrypt(openAIAdminProperties.getPrivateKey(), password);
                //再将数据库的密文解密
                String databaseText = RsaUtil.decrypt(openAIAdminProperties.getPrivateKey(), admin.getPassword());
                if (httpText.equals(databaseText)) {
                    return admin;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public int password(AdminEntity admin, String oldPassword, String newPassword) {
        oldPassword = RsaUtil.decrypt(openAIAdminProperties.getPrivateKey(), oldPassword);
        String sessionPasswor = RsaUtil.decrypt(openAIAdminProperties.getPrivateKey(), admin.getPassword());
        if (oldPassword.equals(sessionPasswor)) {
            admin.setPassword(newPassword);
            admin.setUpdateTime(new Date().getTime());
            adminRepository.save(admin);
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int modify(String id, String name, String phone, String email) {
        AdminEntity oldAdmin = adminRepository.findById(id).get();
        oldAdmin.setName(name);
        oldAdmin.setPhone(phone);
        oldAdmin.setEmail(email);
        oldAdmin.setUpdateTime(new Date().getTime());
        adminRepository.save(oldAdmin);
        return 1;
    }
}
