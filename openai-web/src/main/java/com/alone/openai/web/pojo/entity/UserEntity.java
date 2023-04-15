package com.alone.openai.web.pojo.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

    public final static String STATE_AVAILABLE = "正常";
    public final static String STATE_DELETE = "删除";
    public final static String STATE_DISABLE = "不可用";

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserTypeEntity userType;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

}
