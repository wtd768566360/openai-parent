package com.alone.openai.web.service;

import com.alone.openai.web.pojo.entity.UserJwtEntity;

public interface UserJwtService {


    /**
     * 根据jwt获取id
     *
     * @return
     */
    String get(String jwt);

    /**
     * 删除jwt
     *
     * @return
     */
    int remove(String jwt);

    /**
     * 删除缓存
     */
    int removeCache(String key);


    /**
     * 将jwt存储
     *
     * @return
     */
    String put(String jwt, String value);

    /**
     * 创建token
     *
     * @return
     */
    String create(String id, String name, String userType);

    /**
     * 根据jwt获取数据
     *
     * @return
     */
    UserJwtEntity findByJwt(String jwt);

    /**
     * 根据jwt删除数据
     *
     * @return
     */
    int deleteByJwt(String jwt);

    /**
     * 根据用户ID,删除其用的所有jwt验证
     *
     * @return
     */
    int deleteByUserId(String id);

    /**
     * 保存数据到数据库
     */
    void save(UserJwtEntity userJwtEntity);

}
