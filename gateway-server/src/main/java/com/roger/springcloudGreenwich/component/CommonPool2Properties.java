package com.roger.springcloudGreenwich.component;

import com.ctrip.framework.apollo.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2019/11/25.
 */
@Component
public class CommonPool2Properties {

    private Integer maxTotal = 5;
    private Integer maxIdle = 2;
    private Integer minIdle = 2;

    //TODO 其他属性

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

}

