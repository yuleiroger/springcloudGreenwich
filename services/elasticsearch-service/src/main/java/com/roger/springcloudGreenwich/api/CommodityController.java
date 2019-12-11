package com.roger.springcloudGreenwich.api;

import com.roger.springcloudGreenwich.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2019/12/11.
 */
@RestController
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @GetMapping(value = "/getAll")
    public Object getAll(){
        return commodityService.getAll();
    }

    @GetMapping(value = "/getByName")
    public Object getByName(){
        return commodityService.getByName("吐司");
    }
}
