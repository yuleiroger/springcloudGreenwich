package com.roger.springcloudGreenwich.utils;

import java.io.*;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by admin on 2019/11/20.
 */
public class SystemUtil {
    // 获取mac地址
    public static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
                    continue;
                } else {
                    mac = netInterface.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        if (sb.length() > 0) {
                            return sb.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //_logger.error("MAC地址获取失败", e);
        }
        return "";
    }

    public static void create(){
        File file = new File("d:\\licence.dat") ; // 建立文件
        String password = DesEncryptUtils.encrypt("123456");
        String mac = DesEncryptUtils.encrypt("00-21-CC-D1-6B-48");

        try{
            Licence tc = new Licence(password, mac) ;
            if (!file.exists()) file.createNewFile() ;
            FileOutputStream fos = new FileOutputStream(file) ;
            ObjectOutputStream oos = new ObjectOutputStream(fos) ;
            oos.writeObject(tc);
            System.out.println("文件已存入");
            oos.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Licence read(){
        String mac;
        String password;

        Licence licence = new Licence();
        try{
            FileInputStream fis = new FileInputStream("d:\\licence.dat") ;
            ObjectInputStream ois = new ObjectInputStream(fis) ;
            Licence tc = (Licence) ois.readObject() ;
            mac = tc.getMac();
            password = tc.getPassword();

            licence.setMac(mac);
            licence.setPassword(password);

            ois.close();
            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return licence;
    }

    public static void main(String[] args) {
        create();
        //read();
    }
}
