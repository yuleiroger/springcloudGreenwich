package com.roger.springcloudGreenwich.dao;

import com.roger.springcloudGreenwich.entities.MongoProducts;

import java.util.List;

/**
 * Created by admin on 2019/10/17.
 */
public interface MongoProductDao {

    void saveProduct(MongoProducts products);

    List<MongoProducts> queryMongoProducts();

    List<MongoProducts> queryAllMongoProducts();

    List<MongoProducts> queryProductLike(String name);

    void remove();

    MongoProducts update();
}
