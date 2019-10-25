package com.roger.springcloudGreenwich.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2019/1/28.
 */
public class JwtUtil implements Serializable{
    private static final long serialVersionUID = -8097664866188406888L;
    final static long secondTimeOut = 60 * 1000;//过期时间

    final static Key key = MacProvider.generateKey();

    public final static String secretKey = "d131076e9a941fb949b447e6fb208a65";//key.toString();//私钥

    final static Date expiration = new Date(System.currentTimeMillis() + secondTimeOut);



    public static String getToken(String userNo){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userNo", userNo.toUpperCase());
        claims.put("createTime", new Date());

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(SHAUtils.encodeData(userNo))
                .setNotBefore(now)
                .signWith(SignatureAlgorithm.HS256, secretKey);

//if it has been specified, let's add the expiration
        if (secondTimeOut >= 0) {
            long expMillis = nowMillis + secondTimeOut;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

//Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims checkToken(String token)  {
        if(isTokenExpired()){
            return null;
        }
        try {
            final Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return claims;
        }catch(Exception e){
            System.out.println("解析错误&&&&&&&&&&&&&&&&&&&");
            e.printStackTrace();
            return null;
        }

    }

    public static boolean isTokenExpired() {
        return expiration.before(new Date());
    }



    public static void main(String[] args){

        /**
         * 1把传递过来的工号通过SHAUtils加密，加密后应该是JWT的主题,加密后应该是作为REDIS的KEY,
         * 2通过getToken方法生成TOKEN传递到前端
         */
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4MzVkNmRjODhiNzA4YmM2NDZkNmRiODJjODUzZWY0MTgyZmFiYmQ0YThkZTU5YzIxM2YyYjVhYjNhZTdkOWJlIiwibmJmIjoxNTY4ODYwNTk2LCJjcmVhdGVUaW1lIjoxNTY4ODYwNTk2NTE5LCJ1c2VyTm8iOiJBRE1JTiIsImV4cCI6MTU2ODg2MDY1Nn0.DkNaFh9j9U4r96aXyIDDksA9R_vcIZUOYKeKqKVsbFQ";
        token = getToken("admin".toUpperCase());
        System.out.println(token);
        try {
            //Thread.sleep(10000);
            Claims claims = checkToken(token);
            System.out.println(claims.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
