package com.roger.springcloudGreenwich.component;

import com.roger.springcloudGreenwich.utils.DesEncryptUtils;
import com.roger.springcloudGreenwich.utils.Licence;
import com.roger.springcloudGreenwich.utils.NetworkUtil;
import com.roger.springcloudGreenwich.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2019/11/27.
 */
@Component
@Slf4j
public class DeployComponent {
    @Value("${deploy.licence}")
    private boolean licence;
    @Value("${deploy.password}")
    private String password;

    @Bean
    public boolean validateLicence() throws Exception{
        log.info("licence:{}", licence);
        boolean result = false;
        if (licence){
            Licence licence = SystemUtil.read();
            String localMac = NetworkUtil.getMACAddress();
            password = DesEncryptUtils.decrypt(password);
            String licencePassword = DesEncryptUtils.decrypt(licence.getPassword());
            String licenceMac = DesEncryptUtils.decrypt(licence.getMac());
            //先验证密码
            if(!password.equals(licencePassword)){
                throw new RuntimeException("------------validate licence password fail-------------");
            }
            //验证MAC
            if(!localMac.equalsIgnoreCase(licenceMac)){
                throw new RuntimeException("------------validate licence mac fail-------------");
            }
            log.info("-----------validate licence successfully-----------");
            result = true;
        }else{
            result = true;
        }
        return result;
    }
}
