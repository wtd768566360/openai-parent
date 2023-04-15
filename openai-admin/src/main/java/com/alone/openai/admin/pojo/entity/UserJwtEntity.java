package com.alone.openai.admin.pojo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_jwt")
public class UserJwtEntity {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "jwt")
    private String jwt;

    @Column(name = "expires_time")
    private Long expiresTime;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;
}
