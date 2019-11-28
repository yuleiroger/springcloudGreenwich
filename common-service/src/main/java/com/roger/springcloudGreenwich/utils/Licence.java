package com.roger.springcloudGreenwich.utils;

import java.io.Serializable;

/**
 * Created by admin on 2019/11/20.
 */
public class Licence implements Serializable{
    private static final long serialVersionUID = -7248212979816696368L;

    private String password;
    private String mac;
    private String expireDate;

    public Licence(String password, String mac){
        this.password = password;
        this.mac = mac;
    }

    public Licence(){
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
