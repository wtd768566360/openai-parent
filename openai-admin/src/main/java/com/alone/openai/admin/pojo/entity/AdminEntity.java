package com.alone.openai.admin.pojo.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "admin")
public class AdminEntity {

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

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

}
