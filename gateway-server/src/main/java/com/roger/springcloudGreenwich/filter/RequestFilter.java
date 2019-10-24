package com.roger.springcloudGreenwich.filter;

import com.roger.springcloudGreenwich.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by admin on 2019/10/23.
 */
@Component
@Slf4j
public class RequestFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        Flux<DataBuffer> body = request.getBody();
        log.info("headers:{},cookies:{},queryParams:{},body:{}", headers, cookies, queryParams, body);

        return gatewayFilterChain.filter(serverWebExchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }

}
