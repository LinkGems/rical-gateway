package com.wtrue.rical.gateway.domain.dto;

import com.wtrue.rical.common.domain.BaseObject;
import lombok.Getter;
import lombok.Setter;


/**
 * @description:
 * @author: meidanlong
 * @date: 2021/11/28 7:11 PM
 */
@Getter
@Setter
public class UserBaseDTO extends BaseObject {

    /**
     * 用户唯一ID 用户唯一ID
     */
    private Long userId;

    /**
     * 用户名称 用户名称（姓名）
     */
    private String userName;

    /**
     * 用户性别 用户性别
     */
    private Integer sex;

    /**
     * 身份类型 1:身份证，2:护照
     */
    private Integer idType;

    /**
     * 证件号 用户身份证号
     */
    private String idNum;
}
