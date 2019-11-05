package com.roger.springcloudGreenwich.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by admin on 2019/11/5.
 */
@Document(collection = "work_sheet")
@Data
public class WorkRecord implements Serializable{

    private static final long serialVersionUID = -8339350266660691055L;
    @Field("_id")
    private String id;

    @Field("user_name")
    private String userName;

    @Field("user_no")
    private String userNo;

    @Field("work_hours")
    private String workHours;

    @Field("date_period")
    private String datePeriod;

    @Field("work_date")
    private String workDate;


}
