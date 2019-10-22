package com.roger.springcloudGreenwich.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by admin on 2019/10/22.
 */
@Slf4j
public class HostAddrKeyResolver implements KeyResolver{
    @Override
    public Mono<String> resolve(ServerWebExchange serverWebExchange) {
        log.info(">>>>>>>>>>>>>>>>>");
        return Mono.just(serverWebExchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
