package com.roger.springcloudGreenwich.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static Object jsonToObject(String json,Class beanClass) {
        Gson gson = new Gson();
        Object res = gson.fromJson(json, beanClass);
        return res;
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

    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        //System.out.println(getRandomString(5));
        /**
         * 符合IP地址的范围
         */
        String oneAddress = "10.127.30.45";
        /**
         * 符合IP地址的长度范围但是不符合格式
         */
        String twoAddress = "127.30.45";
        /**
         * 不符合IP地址的长度范围
         */
        String threeAddress = "7.0.4";
        /**
         * 不符合IP地址的长度范围但是不符合IP取值范围
         */
        String fourAddress = "255.255.255.2567";

        //判断oneAddress是否是IP
        System.out.println(isIP(oneAddress));

        //判断twoAddress是否是IP
        System.out.println(isIP(twoAddress));

        //判断threeAddress是否是IP
        System.out.println(isIP(threeAddress));

        //判断fourAddress是否是IP
        System.out.println(isIP(fourAddress));

        Gson gson = new GsonBuilder().create();
        String jsonStr = "{\"userNo\":\"admin\",\"password\":\"xvd\"}";

    }

}


