package com.roger.springcloudGreenwich.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

/**
 * Created by admin on 2019/10/24.
 */
@RestController
public class OrdersController {
    @GetMapping("/websession")
    public Mono<String> getSession(WebSession session) {
        session.getAttributes().putIfAbsent("note", "Howdy Cosmic Spheroid!");
        return Mono.just((String) session.getAttributes().get("note"));
    }
}
