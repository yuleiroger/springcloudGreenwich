package com.roger.springcloudGreenwich.receiver;

import com.roger.springcloudGreenwich.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Created by yulei on 2019/6/16.
 */
@Component
@Slf4j
public class KafkaReceiver {

    @KafkaListener(topics = {Constants.KafkaTopic},groupId = "test-consumer-group")
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("接收到消息={}", message);
        }
    }
}
