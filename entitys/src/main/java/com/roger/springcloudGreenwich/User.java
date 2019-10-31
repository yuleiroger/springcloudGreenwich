package com.roger.springcloudGreenwich;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by admin on 2019/10/25.
 */
@Data
public class User implements Serializable{
    private static final long serialVersionUID = -2859466854096196299L;
    private Long id;
    private String userNo;
    private String userName;
    private String password;
}
