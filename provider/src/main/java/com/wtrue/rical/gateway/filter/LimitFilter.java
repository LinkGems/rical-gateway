package com.wtrue.rical.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import com.wtrue.rical.common.adam.domain.BaseError;
import com.wtrue.rical.common.adam.domain.BaseResponse;
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

import javax.annotation.PostConstruct;
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
    private Double qpslimit;

    @Value("${rical.gateway.request.timeout}")
    private Integer timeout;

    //创建一个限流器，参数代表每秒生成的令牌数(用户限流频率设置 每秒中限制qpslimit个请求)
    RateLimiter rateLimiter;


    /**
     * 为什么要在bean的初始化方法赋值？
     * 因为@value注解是在spring的 populateBean 方法中 通过 AutowiredAnnotationBeanPostProcessor后置处理器中赋值
     *        * 而如果直接在上面赋值他的执行时机是jvm启动时赋值，该步骤会在spring之前就会产生npe异常
     */
    @PostConstruct
    public void init(){
        rateLimiter = RateLimiter.create(qpslimit);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders header = response.getHeaders();
        header.add("Content-Type", "application/json; charset=UTF-8");
        RequestPath path = request.getPath();

        //设置等待超时时间的方式获取令牌，如果超timeout为0，则代表非阻塞，获取不到立即返回
        boolean tryAcquire = rateLimiter.tryAcquire(timeout, TimeUnit.SECONDS);
        log.info("com.wtrue.rical.gateway.filter.LimitFilter.filter , tryAcquire = {}",tryAcquire);
        if (!tryAcquire) {
            BaseResponse<String> errResponse = setResultErrorMsg("当前访问用户过多，请稍后重试");
            DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(errResponse).getBytes());
            return response.writeWith(Mono.just(buffer));
        }
        // 放行
        return chain.filter(exchange);
    }

    private BaseResponse<String> setResultErrorMsg(String msg) {
        BaseResponse<String> response = new BaseResponse<>();
        BaseError baseError = new BaseError("406",msg);
        response.setError(baseError);
        return response;
    }


    @Override
    public int getOrder()
    {
        return 0;
    }
}


