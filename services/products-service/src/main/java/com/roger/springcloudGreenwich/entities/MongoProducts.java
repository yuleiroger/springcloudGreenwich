package com.roger.springcloudGreenwich.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by admin on 2019/9/12.
 */
@Document(collection = "tb_products")
@Data
public class MongoProducts implements Serializable{
    private static final long serialVersionUID = -5322534084455501606L;
    @NotNull(message = "product no can not be null")
    @Field("product_no")
    private String productNo;

    @NotNull
    @Field("product_name")
    private String productName;

    @Field("_id")
    private String id;
}
