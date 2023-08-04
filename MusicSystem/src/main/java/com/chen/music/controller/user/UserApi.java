package com.chen.music.controller.user;

import com.chen.music.pojo.User;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private IUserService userService;

    /**
     * 初始化管理员
     */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody User admin, HttpServletRequest request){
        log.info("username ==>"+admin.getUserName());
        return userService.initUserManagerAccount(admin,request);
    }

    /**
     * 注册
     * @param sobUser
     * @return
     */
    @PostMapping("/join_in")
    public ResponseResult register(@RequestBody User sobUser,
                                   @RequestParam("email_code")String emailCode,
                                   @RequestParam("captcha_code")String captchaCode,
                                   @RequestParam("captcha_key")String captchaKey,
                                   HttpServletRequest request){
        return null;
    }

    /**
     * 登录
     *
     * @param captcha_key 图灵验证码的key
     * @param captcha   图灵验证码
     * @param user   用户bean类，封装账号和密码
     * @return
     */
    @PostMapping("/login/{captcha}/{captcha_key}")
    public ResponseResult login(@PathVariable("captcha_key")String captcha_key,
                                @PathVariable("captcha")String captcha,
                                @RequestBody User user,
                                HttpServletRequest request,
                                HttpServletResponse response){
        return null;
    }


    /**
     * 获取图灵验证码
     * 有效时间10分钟
     * @return
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response,@RequestParam("captcha_key")String captchaKey) {
        log.info("captcha");
        try {
            userService.createCaptcha(response,captchaKey);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

}
