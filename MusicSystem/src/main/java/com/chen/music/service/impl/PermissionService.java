package com.chen.music.service.impl;

import com.chen.music.pojo.User;
import com.chen.music.service.IUserService;
import com.chen.music.utils.Constants;
import com.chen.music.utils.CookieUtils;
import com.chen.music.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service("permission")
public class PermissionService {

    @Autowired
    private IUserService userService;

    /**
     * 判断是不是超级管理员
     * @return
     */
    public boolean superAdmin(){
        //拿到request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        //没有令牌的key，肯定没有登录，不用往下执行了
        if (TextUtils.isEmpty(tokenKey)) {
            return false;
        }
        User user = userService.checkUser();
        if (user == null) {
            return false;
        }
        if (Constants.User.ROLE_ADMIN_SUPER_ID.equals(user.getRoleId())) {
            //管理员
            return true;
        }
        return false;
    }

    /**
     * 判断是不是用户管理员
     * @return
     */
    public boolean userAdmin(){
        //拿到request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        //没有令牌的key，肯定没有登录，不用往下执行了
        if (TextUtils.isEmpty(tokenKey)) {
            return false;
        }
        User user = userService.checkUser();
        if (user == null) {
            return false;
        }
        System.out.println(user.toString());
        if (Constants.User.ROLE_ADMIN_USER_ID.equals(user.getRoleId())) {
            //管理员
            return true;
        }
        return false;
    }

    public boolean imageAdmin() {
        //拿到request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        //没有令牌的key，肯定没有登录，不用往下执行了
        if (TextUtils.isEmpty(tokenKey)) {
            return false;
        }
        User user = userService.checkUser();
        if (user == null) {
            return false;
        }
        System.out.println(user.toString());
        if (Constants.User.ROLE_ADMIN_IMAGE_ID.equals(user.getRoleId())) {
            //管理员
            return true;
        }
        return false;
    }

    public boolean musicAdmin() {
        //拿到request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        //没有令牌的key，肯定没有登录，不用往下执行了
        if (TextUtils.isEmpty(tokenKey)) {
            return false;
        }
        User user = userService.checkUser();
        if (user == null) {
            return false;
        }
        System.out.println(user.toString());
        if (Constants.User.ROLE_ADMIN_MUSIC_ID.equals(user.getRoleId())) {
            //管理员
            return true;
        }
        return false;
    }

    public boolean assessorAdmin(){
        //拿到request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        //没有令牌的key，肯定没有登录，不用往下执行了
        if (TextUtils.isEmpty(tokenKey)) {
            return false;
        }
        User user = userService.checkUser();
        if (user == null) {
            return false;
        }
        System.out.println(user.toString());
        if (Constants.User.ROLE_ADMIN_ASSESSOR_ID.equals(user.getRoleId())) {
            //管理员
            return true;
        }
        return false;
    }
}