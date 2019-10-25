package com.roger.springcloudGreenwich.api;

import com.roger.springcloudGreenwich.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2019/10/23.
 */
@RestController
@RequestMapping("/products-service")
@Slf4j
public class ProductsController {
    @Autowired
    private HttpSession session;

    @PostMapping(value = "/getSession")
    public Object getSession(@RequestHeader("userJson") String userJson){
        log.info("user is:{}", userJson);
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", session.getId());
        map.put("testKey", session.getAttribute(Constants.TestKey));
        log.info("session id is:{}",session.getId());
        return map;
    }
}
