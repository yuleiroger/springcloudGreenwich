package com.roger.springcloudGreenwich.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2019/11/27.
 */
@Component
public class DeployComponent {
    @Value("${deploy.licence}")
    private boolean licence;

    @Bean
    public boolean needLicence(){
        if(licence){
            return true;
        }else{
            return false;
        }
    }
}
