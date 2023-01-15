package com.wtrue.rical.gateway.domain.po.user;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "user_base")
public class UserBase {
    /**
     * 用户唯一ID 用户唯一ID
     */
    @Id
    @Column(name = "USER_ID")
    private Long userId;

    /**
     * 用户名称 用户名称（姓名）
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 用户性别 用户性别
     */
    @Column(name = "SEX")
    private Integer sex;

    /**
     * 身份类型 1:身份证，2:护照
     */
    @Column(name = "ID_TYPE")
    private Integer idType;

    /**
     * 证件号 用户身份证号
     */
    @Column(name = "ID_NUM")
    private String idNum;

    /**
     * 是否删除
     */
    @Column(name = "DELETED")
    private String deleted;

    /**
     * 创建时间
     */
    @Column(name = "CREATED_TIME")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "UPDATED_TIME")
    private Date updatedTime;
}