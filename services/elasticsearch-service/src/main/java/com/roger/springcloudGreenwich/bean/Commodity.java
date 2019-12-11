package com.roger.springcloudGreenwich.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * Created by admin on 2019/12/11.
 */
@Data
@Document(indexName = "commodity", type = "_doc")
public class Commodity implements Serializable{
    private static final long serialVersionUID = -2066705510996541237L;
    @Id
    private String skuId;

    private String name;

    private String category;

    private Integer price;

    private String brand;

    private Integer stock;
}
