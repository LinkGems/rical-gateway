package com.wtrue.rical.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author： Luzelong
 * @Created： 2023/1/23 19:27
 */
@Component
@Slf4j
public class LimitFilter implements GlobalFilter, Ordered
{

    @Value("${rical.gateway.request.qpslimit}")
    private Integer qpslimit;

    @Value("${rical.gateway.request.timeout}")
    private Integer timeout;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //创建一个限流器，参数代表每秒生成的令牌数(用户限流频率设置 每秒中限制qpslimit个请求)
        RateLimiter rateLimiter = RateLimiter.create(qpslimit);
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders header = response.getHeaders();
        header.add("Content-Type", "application/json; charset=UTF-8");
        RequestPath path = request.getPath();

        //设置等待超时时间的方式获取令牌，如果超timeout为0，则代表非阻塞，获取不到立即返回
        boolean tryAcquire = rateLimiter.tryAcquire(timeout, TimeUnit.SECONDS);
        if (!tryAcquire) {
            JSONObject jsonObject = setResultErrorMsg("当前访问用户过多，请稍后重试");
            DataBuffer buffer = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
            return response.writeWith(Mono.just(buffer));
        }
        // 放行
        return chain.filter(exchange);
    }

    private JSONObject setResultErrorMsg(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "406");
        jsonObject.put("message", msg);
        return jsonObject;
    }


    @Override
    public int getOrder()
    {
        return 0;
    }
}


