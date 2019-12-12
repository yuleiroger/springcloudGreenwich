package com.roger.springcloudGreenwich.api;

import com.roger.springcloudGreenwich.bean.Commodity;
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

    @GetMapping(value = "/save")
    public Object save(){
        Commodity commodity = new Commodity();
        commodity.setSkuId("1501009006");
        commodity.setName("商品");
        commodity.setCategory("102");
        commodity.setPrice(16);
        commodity.setBrand("brand");

        commodityService.save(commodity);
        return "success";
    }

    @GetMapping(value = "/getAll")
    public Object getAll(){
        return commodityService.getAll();
    }

    @GetMapping(value = "/getByName")
    public Object getByName(){
        return commodityService.getByName("吐司");
    }
}
