package com.roger.springcloudGreenwich.component;

import com.roger.springcloudGreenwich.bean.Commodity;
import com.roger.springcloudGreenwich.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2019/12/11.
 */
//@Component
public class Test {
    @Autowired
    public CommodityService commodityService;

    @Bean
    public String testInsert() {
        Commodity commodity = new Commodity();
        commodity.setSkuId("1501009005");
        commodity.setName("葡萄吐司面包（10片装）");
        commodity.setCategory("101");
        commodity.setPrice(160);
        commodity.setBrand("良品铺子");

        commodityService.save(commodity);

        return "success";
    }
}
