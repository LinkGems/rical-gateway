package com.wtrue.rical.gateway.service.Impl;

import com.wtrue.rical.gateway.service.ITestService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author： Luzelong
 * @Created： 2023/2/1 11:43
 */
@Service
public class TestServiceImpl implements ITestService {
    @Override
    public Map getTestMap(String key, Integer value) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(key,value);
        return map;
    }
}
