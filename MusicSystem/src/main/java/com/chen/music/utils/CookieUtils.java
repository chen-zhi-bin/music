package com.chen.music.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    //1年
    public static final int DEFAULT_AGE = 60*60*24*365;

    public static final String domain = "localhost";

    /**
     * 设置cookie
     * @param response
     * @param key
     * @param value
     */
    public static void setUpCookie(HttpServletResponse response, String key, String value) {
//        setUpCookie(response, key, value,-1);
        setUpCookie(response,key,value,DEFAULT_AGE);
    }

    public static void setUpCookie(HttpServletResponse response, String key, String value, int age) {
        Cookie cookie = new Cookie(key,value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(age);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     * @param request
     * @param response
     * @param key
     */
    public static void deleteCookie(HttpServletRequest request,HttpServletResponse response,String key){
        setUpCookie(response,key,null,0);
    }

    /**
     * 获取cookie
     * @param request
     * @param key
     * @return
     */
    public static String getCookie(HttpServletRequest request,String key){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    System.out.println(cookie.getName()+"===="+cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}