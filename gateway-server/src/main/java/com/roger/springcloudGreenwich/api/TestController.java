package com.roger.springcloudGreenwich.api;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2019/10/21.
 */
@RestController
@Slf4j
public class TestController {
    private Config config;
    private String test;


    public TestController() {

    }

    @GetMapping(value = "/index")
    public String test(){
        Config config = ConfigService.getAppConfig();
        String someKey = "test";
        String someDefaultValue = "test";
        String value = config.getProperty(someKey, someDefaultValue);
        return value;
    }
}
