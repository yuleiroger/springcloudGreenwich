package com.roger.springcloudGreenwich.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by admin on 2019/10/24.
 */
@RestController
public class OrdersController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
