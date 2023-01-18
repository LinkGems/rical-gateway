package com.wtrue.rical.gateway.api.controller;

import com.wtrue.rical.gateway.export.provider.IUserProvider;
import com.wtrue.rical.gateway.export.pojo.UserBaseModel;
import com.wtrue.rical.common.domain.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 用户Http接口
 * @author: meidanlong
 * @date: 2021/7/18 5:04 PM
 */
@RestController
@RequestMapping("user")
public class UserController {

//    @Resource
//    private IUserProvider userProvider;

    @GetMapping("query")
    public BaseResponse<UserBaseModel> queryUser(Long userId){
//        return userProvider.queryUser(userId);
        return new BaseResponse<>();
    }
}
