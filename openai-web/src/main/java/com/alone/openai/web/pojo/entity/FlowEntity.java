package com.alone.openai.web.pojo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "flow")
public class FlowEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "total")
    private Integer total;

    @Column(name = "used")
    private Integer used;

    @Column(name = "unused")
    private Integer unused;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;
}
