package com.roger.springcloudGreenwich.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2019/12/13.
 */
@RestController
public class ProviderController {
    @RequestMapping(value="/index",method= {RequestMethod.GET})
    public String index() {
        return "hello world";
    }
}
