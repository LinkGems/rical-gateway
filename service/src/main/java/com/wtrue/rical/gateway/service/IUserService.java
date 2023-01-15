package com.wtrue.rical.gateway.service;

import com.wtrue.rical.gateway.domain.dto.UserBaseDTO;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/11/28 6:52 PM
 */
public interface IUserService {

    UserBaseDTO queryUser(Long userId);

}
