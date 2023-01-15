package com.wtrue.rical.gateway.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 健康检查Http接口
 * @author: meidanlong
 * @date: 2023/1/11 11:16
 */
@RestController
public class HealthController {

    @GetMapping("health")
    public String checkHealth(){
        return "success";
    }
}
