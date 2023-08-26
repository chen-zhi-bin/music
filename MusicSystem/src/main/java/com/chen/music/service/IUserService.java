package com.chen.music.service;

import com.chen.music.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.music.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface IUserService extends IService<User> {

    ResponseResult initUserManagerAccount(User admin, HttpServletRequest request);

    void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception;

    ResponseResult sendEmail(String type, HttpServletRequest request, String emailAddress);

    ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request);

    ResponseResult doLogin(String captcha_key, String captcha, User user, HttpServletRequest request, HttpServletResponse response);

    User checkUser();

    ResponseResult updateUserPassword(String verifyCode, User user);

    ResponseResult getUserInfo(String userId);

    ResponseResult updateUserInfo(String userId, User user);

    ResponseResult listUser(int page, int size);

    ResponseResult deleteUserById(String userId);

    ResponseResult checkEmail(String email);

    ResponseResult checkUserName(String userName);

    ResponseResult updateEmail(String email, String verifyCode);

    ResponseResult doLogout();

    ResponseResult registerAdmin(User user, String roleId, String captchaCode, String captchaKey, HttpServletRequest request);

    ResponseResult deleteAdminById(String userId);

    ResponseResult parseToken();
}
