package com.roger.springcloudGreenwich.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2019/11/5.
 */
@Data
@Document("log")
public class Log implements Serializable{
    private static final long serialVersionUID = -7181408185204302333L;

    @Field("level")
    private String level;

    @Field("stack_trace")
    private String stackTrace;

    @Field("message")
    private String message;

    @Field("create_time")
    private Date createTime;
}
