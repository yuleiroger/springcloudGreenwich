package com.roger.springcloudGreenwich;

import lombok.Data;

import java.util.Date;

/**
 * Created by yulei on 2019/6/16.
 */
@Data
public class Message {
    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳
}
