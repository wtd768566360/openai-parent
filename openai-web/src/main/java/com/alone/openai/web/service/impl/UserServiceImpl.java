package com.alone.openai.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alone.openai.web.pojo.entity.UserEntity;
import com.alone.openai.web.repository.UserRepository;
import com.alone.openai.web.repository.UserTypeRepository;
import com.alone.openai.web.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;

    public UserTypeRepository userTypeRepository;

    public UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public UserEntity login(String account, String password) {
        return userRepository.findByAccountAndPassword(account, password);
    }

    @Override
    public int password(UserEntity user, String oldPassword, String newPassword) {
        if (oldPassword.equals(user.getPassword())) {
            user.setPassword(newPassword);
            user.setUpdateTime(new Date().getTime());
            userRepository.save(user);
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public UserEntity findById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public int modify(String id, String name, String phone, String email) {
        UserEntity oldUser = userRepository.findById(id).get();
        oldUser.setName(name);
        oldUser.setPhone(phone);
        oldUser.setEmail(email);
        oldUser.setUpdateTime(new Date().getTime());
        userRepository.save(oldUser);
        return 1;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity create(UserEntity user) {
        long dateTime = new Date().getTime();
        user.setUserType(userTypeRepository.findByCode("user"));
        user.setId(StrUtil.uuid().replace("-", ""));
        user.setState(UserEntity.STATE_AVAILABLE);
        user.setPassword("123456");
        user.setCreateTime(dateTime);
        user.setUpdateTime(dateTime);
        return userRepository.save(user);
    }

    @Override
    public int forgetPassword(String email, String account, String password) {
        UserEntity user = findByEmailAndAccount(email, account);
        user.setPassword(password);
        user.setUpdateTime(new Date().getTime());
        userRepository.save(user);
        return 1;
    }

    @Override
    public UserEntity findByEmailAndAccount(String email, String account) {
        return userRepository.findByEmailAndAccount(email, account);
    }

    @Override
    public UserEntity findByAccount(String account) {
        return userRepository.findByAccount(account);
    }
}
