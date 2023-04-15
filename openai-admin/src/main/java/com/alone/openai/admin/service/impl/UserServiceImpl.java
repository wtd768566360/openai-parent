package com.alone.openai.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alone.openai.admin.api.WebClient;
import com.alone.openai.admin.pojo.entity.UserEntity;
import com.alone.openai.admin.pojo.entity.UserTypeEntity;
import com.alone.openai.admin.repository.UserJwtRepository;
import com.alone.openai.admin.repository.UserRepository;
import com.alone.openai.admin.repository.UserTypeRepository;
import com.alone.openai.admin.service.UserService;
import com.alone.openai.common.response.Restful;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;

    private final WebClient webClient;

    private final UserJwtRepository userJwtRepository;

    public UserServiceImpl(UserRepository userRepository,
                           UserTypeRepository userTypeRepository,
                           WebClient webClient,
                           UserJwtRepository userJwtRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.webClient = webClient;
        this.userJwtRepository = userJwtRepository;
    }

    @Override
    public Restful query(int page, int limit, String type, String name, String phone, String email) {
        Specification<UserEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Join<UserEntity, UserTypeEntity> join = root.join("userType", JoinType.INNER);
            // 查询条件的集合
            List<Predicate> list = new ArrayList<>();
            if (StrUtil.isNotEmpty(type)) {
                list.add(criteriaBuilder.like(join.get("type"), "%" + type + "%"));
            }
            if (StrUtil.isNotEmpty(name)) {
                list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (StrUtil.isNotEmpty(phone)) {
                list.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }
            if (StrUtil.isNotEmpty(email)) {
                list.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }
            list.add(criteriaBuilder.notEqual(root.get("state"), UserEntity.STATE_DELETE));
            // 转数组
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        // 指定排序和分页
        Pageable pageable = PageRequest.of(page < 1 ? 0 : page - 1, limit, Sort.by("createTime"));
        Page<UserEntity> result = userRepository.findAll(specification, pageable);
        return Restful.builder().count((int) result.getTotalElements()).msg("数据查询成功").data(result.getContent()).build();
    }

    @Override
    public int create(UserEntity user) {
        long dateTime = new Date().getTime();
        user.setUserType(userTypeRepository.findByCode("user"));
        user.setId(StrUtil.uuid().replace("-", ""));
        user.setState(UserEntity.STATE_AVAILABLE);
        user.setPassword("123456");
        user.setCreateTime(dateTime);
        user.setUpdateTime(dateTime);
        userRepository.save(user);
        return 1;
    }

    @Override
    public int modify(UserEntity user) {
        UserEntity oldUser = userRepository.findById(user.getId()).get();
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());
        oldUser.setEmail(user.getEmail());
        oldUser.setUpdateTime(new Date().getTime());
        userRepository.save(oldUser);
        return 1;
    }

    @Override
    public int available(String id, String state) {
        UserEntity user = userRepository.findById(id).get();
        user.setState(state);
        userRepository.save(user);
        if (UserEntity.STATE_DELETE.equals(state) || UserEntity.STATE_DISABLE.equals(state)) {
            //禁用或者删除都要将人踢出去
            userRepository.save(user);
            return jwtDelete(id);
        } else if (UserEntity.STATE_AVAILABLE.equals(state)) {
            userRepository.save(user);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @Transactional
    public int jwtDelete(String userId) {
        Restful restful = webClient.userDisable(userId);
        if (restful.getCode() == Restful.CODE_OK) {
            //再去删除数据库里面的数据
            userJwtRepository.deleteByUserId(userId);
            return 1;
        }
        return 0;
    }

    @Override
    public long statistics() {
        return userRepository.count((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.notEqual(root.get("state"), UserEntity.STATE_DELETE));
            // 转数组
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        });
    }

    @Override
    public Long todayUser() {
        return userRepository.count((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.notEqual(root.get("state"), UserEntity.STATE_DELETE));
            long todayStart = DateUtil.beginOfDay(DateUtil.date()).getTime();
            list.add(criteriaBuilder.ge(root.get("createTime"), todayStart));
            // 转数组
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        });
    }

    @Override
    public List<UserEntity> findByCreateTimeBetween(Long startTime, Long endTime) {
        return userRepository.findByCreateTimeBetween(startTime, endTime);
    }
}
