package com.roger.springcloudGreenwich.utils;

import com.google.gson.Gson;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by admin on 2019/10/10.
 */
public class StringUtil {
    /**
     *
     * @param list
     * @return
     */
    public static String listToJson(List<?> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    public static String javabeanToJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    /**
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(52);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomString(5));
    }

}
