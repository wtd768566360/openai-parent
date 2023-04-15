package com.alone.openai.web.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.alone.openai.common.constant.Constant;
import com.alone.openai.web.pojo.entity.UserEntity;
import com.alone.openai.web.pojo.entity.UserJwtEntity;
import com.alone.openai.web.repository.UserJwtRepository;
import com.alone.openai.web.service.UserJwtService;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service

public class UserJwtServiceImpl implements UserJwtService {

    private final Cache<String, String> cache;

    private final UserJwtRepository userJwtRepository;

    public UserJwtServiceImpl(@Qualifier("jwt-cache") Cache<String, String> cache,
                              UserJwtRepository userJwtRepository) {
        this.cache = cache;
        this.userJwtRepository = userJwtRepository;
    }

    @Override
    public String get(String key) {
        try {
            return cache.get(key, () -> {
                UserJwtEntity userJwtEntity = findByJwt(key);
                if (userJwtEntity != null) {
                    return userJwtEntity.getUser().getId();
                }
                return null;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public int remove(String key) {
        cache.invalidate(key);
        //删库
        return deleteByJwt(key);
    }

    @Override
    public int removeCache(String key) {
        cache.invalidate(key);
        return 1;
    }


    @Override
    public String put(String key, String value) {
        UserJwtEntity userJwtEntity = new UserJwtEntity();
        userJwtEntity.setJwt(key);
        UserEntity user = new UserEntity();
        user.setId(value);
        userJwtEntity.setUser(user);
        //先存入数据库,然后再通过缓存的get方法从该数据库加载到缓存
        save(userJwtEntity);
        return get(key);
    }


    @Override
    public String create(String id, String name, String userType) {

        return JWT.create()
                .setIssuer(Constant.JWT_ISSUER_SYSTEM)
                .setJWTId(id)
                .setPayload("name", name)
                .setPayload("userType", userType)
                // 设置签发时间
                .setIssuedAt(DateUtil.date())
                .setExpiresAt(DateUtil.offsetDay(DateUtil.date(), Constant.EXPIRE_TIME_DAY))
                .setKey(Constant.JWT_SIGNER_SECRET.getBytes())
                .sign(Constant.JWT_SIGNER);
    }

    @Override
    public UserJwtEntity findByJwt(String jwt) {
        return userJwtRepository.findByJwt(jwt);
    }

    @Override
    public int deleteByJwt(String jwt) {
        userJwtRepository.deleteByJwt(jwt);
        if (userJwtRepository.findByJwt(jwt) != null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int deleteByUserId(String id) {
        List<UserJwtEntity> userJwtEntities = userJwtRepository.findAllByUserId(id);
        int result = 0;
        for (UserJwtEntity userJwtEntity : userJwtEntities) {
            result += removeCache(userJwtEntity.getJwt());
        }
        return result == userJwtEntities.size() ? 0 : 1;
    }

    @Override
    public void save(UserJwtEntity userJwtEntity) {
        userJwtEntity.setId(StrUtil.uuid().replace("-", ""));
        Long dateTime = DateUtil.date().getTime();
        //计算过期时间
        userJwtEntity.setExpiresTime(dateTime + Constant.EXPIRE_TIME_DAY * 86400L * 1000L);
        userJwtEntity.setCreateTime(dateTime);
        userJwtEntity.setUpdateTime(dateTime);
        userJwtRepository.save(userJwtEntity);
    }

}
