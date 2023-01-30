package com.wtrue.rical.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @Author： Luzelong
 * @Created： 2023/1/24 21:33
 */
@Component
@Slf4j
public class LoggerFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求信息
        ServerHttpRequest request = exchange.getRequest();
        InetSocketAddress address = request.getRemoteAddress();
        String method = request.getMethodValue();
        URI uri = request.getURI();
        HttpHeaders requestHeaders = request.getHeaders();
        // 获取请求query
        MultiValueMap<String, String> map = request.getQueryParams();
        log.info("LoggerFilter.filter , request come in, please look down： \n{\n\trequest = {} \n\taddress = {} \n\tmethod = {} \n\turi = {} \n\trequestHeaders = {} \n\tmap = {}\n}",request,address,method,uri,requestHeaders,map);

        // 2.获取响应信息
        ServerHttpResponse response = exchange.getResponse();
        HttpStatus statusCode = response.getStatusCode();
        MultiValueMap<String, ResponseCookie> cookies = response.getCookies();
        HttpHeaders responseHeaders = response.getHeaders();
        log.info("LoggerFilter.filter , response is : \n{\n\tstatusCode = {} \n\tcookies = {} \n\tresponseHeaders = {}\n}",statusCode,cookies,responseHeaders);


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
