package com.roger.springcloudGreenwich.api;

import com.roger.springcloudGreenwich.entities.MongoProducts;
import com.roger.springcloudGreenwich.service.MongoProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2019/10/23.
 */
@RestController
@RequestMapping("/products-service")
@Slf4j
public class ProductsController {
    @Autowired
    private MongoProductService mongoProductService;

    @PostMapping(value = "/getSession")
    public Object getSession(@RequestHeader("userJson") String userJson){
        log.info("user is:{}", userJson);
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @GetMapping(value = "/index")
    public Object index(){
        Map<String, Object> map = new HashMap<>();
        return "index";
    }

    @GetMapping(value = "/saveProducts")
    public String products(){

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        LinkedList<MongoProducts> list = new LinkedList();
        for(int i = 5; i < 1000; i++){
            MongoProducts products = new MongoProducts();
            products.setProductNo(i + "");
            products.setProductName("数据库入门与实践" + i);
            list.add(products);
        }
        mongoProductService.saveMongoProducts(list);
        return "products success";
    }
}
