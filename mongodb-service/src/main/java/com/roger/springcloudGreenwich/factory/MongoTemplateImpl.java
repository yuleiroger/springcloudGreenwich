package com.roger.springcloudGreenwich.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by admin on 2019/10/25.
 */
@Configuration
@Slf4j
public class MongoTemplateImpl{
    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Bean
    @ConditionalOnMissingBean(name = "mongoTemplate")
    public MongoTemplate generateMongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }

}
