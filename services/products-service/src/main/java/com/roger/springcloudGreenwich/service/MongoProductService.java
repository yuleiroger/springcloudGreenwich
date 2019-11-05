package com.roger.springcloudGreenwich.service;

import com.roger.springcloudGreenwich.entities.MongoProducts;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2019/10/17.
 */
public interface MongoProductService {
    void saveMongoProduct(MongoProducts products);

    void saveMongoProducts(LinkedList<MongoProducts> list);

    List<MongoProducts> queryMongoProducts();

    List<MongoProducts> queryProductLike(String name);

    void remove();

    MongoProducts update();
}
