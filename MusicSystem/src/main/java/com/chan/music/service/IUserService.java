package com.chan.music.service;

import com.chan.music.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chan.music.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

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
