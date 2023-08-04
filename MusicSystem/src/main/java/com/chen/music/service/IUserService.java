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


}
