package com.alone.openai.admin.pojo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "api_key")
public class ApiKeyEntity {

    public final static String STATE_AVAILABLE = "正常";
    public final static String STATE_DISABLE = "不正常";

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "state")
    private String state;

    @Column(name = "origin")
    private String origin;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;
}
