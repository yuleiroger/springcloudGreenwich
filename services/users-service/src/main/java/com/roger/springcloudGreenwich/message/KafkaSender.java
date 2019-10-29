package com.roger.springcloudGreenwich.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roger.springcloudGreenwich.Message;
import com.roger.springcloudGreenwich.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.UUID;

/**
 * Created by yulei on 2019/6/16.
 */
@Component
@Slf4j
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();


    //发送消息方法
    public void send(String content) {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString() + ":" + content);
        message.setSendTime(new Date());
        log.info("发送消息={}", gson.toJson(message));
        ListenableFuture future =
                kafkaTemplate.send(Constants.KafkaTopic, gson.toJson(message));
        future.addCallback(o -> {}, err -> log.info("发送失败:{}", err.getCause()));

    }
}
