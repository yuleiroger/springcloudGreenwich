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
        config = ConfigService.getAppConfig();
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                log.info("Changes for namespace {}", changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    log.info("Change - key: {}, oldValue: {}, newValue: {}, changeType: {}", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType());

                    if (key.equals("test")) {
                        test = change.getNewValue();
                    }
                }
            }
        });
    }

    @GetMapping(value = "/index")
    public String test(){
        Config config = ConfigService.getAppConfig();
        String someKey = "test";
        String someDefaultValue = "test";
        String value = config.getProperty(someKey, someDefaultValue);
        log.info("change value:{}", test);
        return value;
    }
}
