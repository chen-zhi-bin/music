package com.chen.music.utils;

import com.chen.music.pojo.User;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class ClaimsUtils {

    public static final String ID = "id";
    public static final String USER_NAME = "user_name";
    public static final String ROLES_ID = "roles";
    public static final String AVATAR = "avatar";
    public static final String EMAIL = "email";
    public static final String SIGN = "sign";

    public static Map<String,Object> user2Claims(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put(ID,user.getId());
        claims.put(USER_NAME,user.getUserName());
        claims.put(ROLES_ID,user.getRoleId());
        claims.put(AVATAR,user.getAvatar());
        claims.put(EMAIL,user.getEmail());
        claims.put(SIGN,user.getSign());
        return claims;
    }

    public static User claims2User(Claims claims){
        User user = new User();
        String id = (String) claims.get(ID);
        user.setId(id);
        String userName = (String) claims.get(USER_NAME);
        user.setUserName(userName);
        String roles = (String) claims.get(ROLES_ID);
        user.setRoleId(roles);
        String avatar = (String) claims.get(AVATAR);
        user.setAvatar(avatar);
        String email = (String) claims.get(EMAIL);
        user.setEmail(email);
        String sign = (String) claims.get(SIGN);
        user.setSign(sign);
        return user;
    }

}
